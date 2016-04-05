package com.example.andela.pronotes.activities;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.model.NoteModel;
import com.example.andela.pronotes.utils.IntentRunner;
import com.example.andela.pronotes.utils.LockNoteDialog;
import com.vstechlab.easyfonts.EasyFonts;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNewNote extends AppCompatActivity implements LockNoteDialog.NoteLockDialogListener {
  private EditText notebookCategory;
  private EditText noteTitle;
  private EditText note;

  private String category;
  private String title;
  private String noteText;
  private boolean isFromEdit = false;
  private long noteId;
  private boolean autoSaveNotebook;
  private SharedPreferences preferences;
  private Toolbar toolbar;
  private int repeatRate = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_new_note);
    setTitle("New Note");
    initialize();
    setToolbar();

    Bundle extras = getIntent().getExtras();
    noteFromBundle(extras);

    setDefaultFonts();
    setSaveOptions();
  }

  private void setSaveOptions() {
    preferences = PreferenceManager.getDefaultSharedPreferences(this);
    autoSaveNotebook = preferences.getBoolean("autosave", false);

    if (autoSaveNotebook) {
      repeatRate = getUpdateRate();
    }
    setAutoSave();
  }

  private void setDefaultFonts() {
    notebookCategory.setTypeface(EasyFonts.robotoBlack(this));
    noteTitle.setTypeface(EasyFonts.robotoBold(this));
    note.setTypeface(EasyFonts.robotoItalic(this));
  }

  private void setToolbar() {
    toolbar = (Toolbar) findViewById(R.id.tool_bar);
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.createnote_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if(id == R.id.note_lock) {
      showNoteLockDialog();
    }
    return true;
  }

  @Override
  public void onBackPressed() {
    if (isNoteProvided()) {
      saveNoteToDb();
    }
    autoSaveHandler.removeCallbacks(runAutoSave);
    IntentRunner.startIntent(this, AllNotesActivity.class);
  }

  @Override
  public void onResume() {
    super.onResume();
    autoSaveNotebook = preferences.getBoolean("autosave", false);
  }

  public void initialize() {
    notebookCategory = (EditText) findViewById(R.id.create_note_notebook);
    noteTitle = (EditText) findViewById(R.id.note_title);
    note = (EditText) findViewById(R.id.createnotebook_line);
  }

  private void saveNoteToDb() {
    NoteModel noteModel = new NoteModel();
    if (isFromEdit) {
      noteModel = NoteModel.load(NoteModel.class, noteId);
    }
    noteModel.currentTime = getLogTime();
    noteModel.note_text = noteText;
    noteModel.trashId = 0;
    noteModel.note_title = title;
    noteModel.noteBook = category;
    noteModel.save();
    noteId = noteModel.getId();
    isFromEdit = true;
  }

  private void setNoteDetails() {
    category = this.notebookCategory.getText().toString().trim();
    if (category.length() < 1) {
      category = "Misc";
    }
    title = this.noteTitle.getText().toString().trim();
    noteText = this.note.getText().toString().trim();
  }

  private boolean isWrongInputs() {
    boolean outcome = false;
    if ((noteText.length() > 1) && (category.length() < 1)) {
      notebookCategory.setError("Category is required.");
      outcome = true;
    } else if ((category.length() > 1) && (noteText.length() < 1)) {
      note.setError("No note added");
      outcome = true;
    }
    return outcome;
  }

  private boolean isContentProvided() {
    return ((noteText.length() > 0) && (category.length() > 0));
  }

  private String getLogTime() {
    Date currentTime = new Date();
    Locale myLocale = new Locale("en");
    return DateFormat.getDateInstance(DateFormat.DEFAULT, myLocale).format(currentTime);
  }

  private NoteModel getNote(long noteId) {
    return NoteModel.load(NoteModel.class, noteId);
  }

  Handler autoSaveHandler = new Handler();

  Runnable runAutoSave = new Runnable() {
    @Override
    public void run() {
      if (isNoteProvided()) {
        saveNoteToDb();
      }
      autoSaveHandler.postDelayed(this, 1000 * repeatRate);
    }
  };

  private void setAutoSave() {
    this.note.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && autoSaveNotebook) {
          runAutoSave.run();
        }
      }
    });
  }

  private void noteFromBundle(Bundle extras) {
    if (extras != null) {
      noteId = extras.getLong("NoteId");
      NoteModel note = getNote(noteId);
      notebookCategory.setText(note.noteBook);
      noteTitle.setText(note.note_title);
      this.note.setText(note.note_text);
      isFromEdit = true;
      if (note.note_title.length() > 0) {
        setTitle(note.note_title);
      } else {
        setTitle("Untitled");
      }
    }
  }

  private boolean isNoteProvided() {
    setNoteDetails();
    return (!isWrongInputs() && isContentProvided());
  }

  protected void onStop() {
    super.onStop();
    autoSaveHandler.removeCallbacks(runAutoSave);
  }

  protected void onPause() {
    super.onPause();
    autoSaveHandler.removeCallbacks(runAutoSave);
  }

  private int getUpdateRate() {
    int updaterate = Integer.parseInt(preferences.getString("autosaveRate", "2"));
    return (updaterate > 0.5 ? updaterate : 2);
  }

  private void showNoteLockDialog() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    LockNoteDialog lockNoteDialog = LockNoteDialog.newInstance("Password Lock");
    lockNoteDialog.show(fragmentManager, "notelock_dialog");
  }

  @Override
  public void onFinishPasswordEntry(String password) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString("password", password);
    editor.apply();
  }
}