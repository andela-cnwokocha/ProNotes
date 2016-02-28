package com.example.andela.pronotes.utils;


import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.andela.pronotes.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LockNoteDialog extends DialogFragment implements TextView.OnEditorActionListener {
  private EditText password;

  public LockNoteDialog() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.notelock_dialog, container);
  }

  public static LockNoteDialog newInstance(String title) {
    LockNoteDialog lockNoteDialog = new LockNoteDialog();
    Bundle arguments = new Bundle();
    arguments.putString("title", title);
    lockNoteDialog.setArguments(arguments);
    return lockNoteDialog;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    password = (EditText) view.findViewById(R.id.edittext_lock);
    String title = getArguments().getString("title", "Enter Password");

    password.setOnEditorActionListener(this);
    getDialog().setTitle(title);
    password.requestFocus();
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
  }

  @Override
  public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    if (EditorInfo.IME_ACTION_DONE == actionId) {
      NoteLockDialogListener lockDialogListener = (NoteLockDialogListener) getActivity();
      lockDialogListener.onFinishPasswordEntry(password.getText().toString());
      dismiss();
      return true;
    }
    return false;
  }

  public interface NoteLockDialogListener {
    void onFinishPasswordEntry(String password);
  }

}
