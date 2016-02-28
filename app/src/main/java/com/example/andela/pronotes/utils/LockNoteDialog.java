package com.example.andela.pronotes.utils;


//import android.app.Dialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.andela.pronotes.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LockNoteDialog extends DialogFragment {
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

    getDialog().setTitle(title);
    password.requestFocus();
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
  }

}
