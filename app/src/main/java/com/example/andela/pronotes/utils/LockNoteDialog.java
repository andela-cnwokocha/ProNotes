package com.example.andela.pronotes.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.activities.CreateNewNote;


/**
 * implements TextView.OnEditorActionListener
 * A simple {@link Fragment} subclass.
 */
public class LockNoteDialog extends DialogFragment implements EditText.OnEditorActionListener {
  private EditText password;
  private ImageButton showIcon;
  private Button okButton;
  private Button cancelButton;

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

    okButton = (Button) view.findViewById(R.id.ok_button);
    cancelButton = (Button) view.findViewById(R.id.cancel_button);
    password = (EditText) view.findViewById(R.id.edittext_lock);
    showIcon = (ImageButton) view.findViewById(R.id.password_icon);
    password.setOnEditorActionListener(this);

    cancelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });

    okButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getNotePassword();
      }
    });

    showIcon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int start = password.getSelectionStart();
        int end = password.getSelectionEnd();
        if(password.getTransformationMethod() != null) {
          password.setTransformationMethod(null);
        } else {
          password.setTransformationMethod(new PasswordTransformationMethod());
        }
        password.setSelection(start, end);
      }
    });

    String title = getArguments().getString("title", "Enter Password");

    getDialog().setTitle(title);
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
    password.setText(pref.getString("password", "").trim());
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
  }

  @Override
  public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    if (EditorInfo.IME_ACTION_DONE == actionId) {
      getNotePassword();
      return true;
    }
    return false;
  }

  public interface NoteLockDialogListener {
    void onFinishPasswordEntry(String password);
  }

  private void getNotePassword() {
    NoteLockDialogListener listener = (NoteLockDialogListener) getActivity();
    listener.onFinishPasswordEntry(password.getText().toString());
    dismiss();
  }

}
