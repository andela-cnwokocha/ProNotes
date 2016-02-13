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


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_new_note);
    setTitle("New Note");

    initialize();
  }

  @Override
  public void onBackPressed() {
    Intent allNotes = new Intent(this, AllNotesActivity.class);
    saveNoteToDb();
    resetVariables();
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
    noteModel.currentTime = getLogTime();
    noteModel.note_text = noteText;
    noteModel.trashId = 0;
    noteModel.note_title = title;
    noteModel.tag = tagnameString;
    noteModel.save();
    Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
  }

  private void setNoteDetails() {
    tagnameString = this.tagname.getText().toString();
    category = this.notebookCategory.getText().toString();
    title = this.noteTitle.getText().toString();
    noteText = this.note.getText().toString();
  }

  private String getLogTime() {
    Date currentTime = new Date();
    Locale myLocale = new Locale("en");
    String myDate = DateFormat.getDateInstance(DateFormat.DEFAULT, myLocale).format(currentTime);
    return myDate;
  }
  private void resetVariables() {
    tagname.setText("");
    notebookCategory.setText("");
    noteTitle.setText("");
    note.setText("");
  }
}
