package com.example.andela.pronotes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.andela.pronotes.R;

public class CreateNewNote extends AppCompatActivity {
  private EditText tagname;
  private EditText notebookCategory;
  private EditText noteTitle;
  private EditText note;


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
    startActivity(allNotes);
  }

  public void initialize() {
    tagname = (EditText) findViewById(R.id.create_note_tag);
    notebookCategory = (EditText) findViewById(R.id.create_note_notebook);
    noteTitle = (EditText) findViewById(R.id.note_title);
    note = (EditText) findViewById(R.id.createnotebook_line);
  }
}
