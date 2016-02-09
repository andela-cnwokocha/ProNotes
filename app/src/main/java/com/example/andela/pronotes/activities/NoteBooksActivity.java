package com.example.andela.pronotes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.andela.pronotes.R;


public class NoteBooksActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_note_books);
    setTitle("All Notebooks");
  }
}
