package com.example.andela.pronotes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.andela.pronotes.R;

public class CreateNewNote extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_new_note);
    setTitle("New Note");
  }

  @Override
  public void onBackPressed() {
    Intent allNotes = new Intent(this, AllNotesActivity.class);
    startActivity(allNotes);
  }
}
