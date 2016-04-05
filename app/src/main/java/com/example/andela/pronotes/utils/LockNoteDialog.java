package com.example.andela.pronotes.utils;


import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.andela.pronotes.R;


/**
 * implements TextView.OnEditorActionListener
 * A simple {@link Fragment} subclass.
 */
public class LockNoteDialog extends DialogFragment  {
  private EditText password;
  private ImageButton showIcon;

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
    showIcon = (ImageButton) view.findViewById(R.id.password_icon);

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
    password.requestFocus();
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
  }

  public interface NoteLockDialogListener {
    void onFinishPasswordEntry(String password);
  }

}
