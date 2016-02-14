package com.example.andela.pronotes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.model.NoteModel;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNewNote extends AppCompatActivity {
  private EditText tagname;
  private String tagnameString;
  private EditText notebookCategory;
  private String category;
  private EditText noteTitle;
  private String title;
  private EditText note;
  private String noteText;
  private boolean isFromEdit = false;
  private long noteId;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_new_note);
    setTitle("New Note");
    initialize();

    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      noteId = extras.getLong("NoteId");
      NoteModel note = getNote(noteId);
      tagname.setText(note.tag);
      notebookCategory.setText(note.noteBook);
      noteTitle.setText(note.note_title);
      this.note.setText(note.note_text);
      isFromEdit = true;
    }
  }

  @Override
  public void onBackPressed() {
    Intent allNotes = new Intent(this, AllNotesActivity.class);
    saveNoteToDb();
    startActivity(allNotes);
  }

  public void initialize() {
    tagname = (EditText) findViewById(R.id.create_note_tag);
    notebookCategory = (EditText) findViewById(R.id.create_note_notebook);
    noteTitle = (EditText) findViewById(R.id.note_title);
    note = (EditText) findViewById(R.id.createnotebook_line);
  }

  private void saveNoteToDb() {
    setNoteDetails();
    NoteModel noteModel = new NoteModel();
    if(isFromEdit) {
      noteModel = NoteModel.load(NoteModel.class, noteId);
    }
    noteModel.currentTime = getLogTime();
    noteModel.note_text = noteText;
    noteModel.trashId = 0;
    noteModel.note_title = title;
    noteModel.tag = tagnameString;
    noteModel.noteBook = category;
    Log.i("NoteCate_ToDb", noteModel.noteBook);
    noteModel.save();
    resetVariables();
  }

  private void setNoteDetails() {
    tagnameString = this.tagname.getText().toString();
    category = this.notebookCategory.getText().toString();
    Log.i("NoteCate",category);
    title = this.noteTitle.getText().toString();
    noteText = this.note.getText().toString();
  }

  private String getLogTime() {
    Date currentTime = new Date();
    Locale myLocale = new Locale("en");
    return DateFormat.getDateInstance(DateFormat.DEFAULT, myLocale).format(currentTime);
  }
  private void resetVariables() {
    tagname.setText("");
    notebookCategory.setText("");
    noteTitle.setText("");
    note.setText("");
  }
  private NoteModel getNote(long noteId) {
    return NoteModel.load(NoteModel.class, noteId);
  }
}
