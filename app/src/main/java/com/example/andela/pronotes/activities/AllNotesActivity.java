package com.example.andela.pronotes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.andela.pronotes.R;

public class AllNotesActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_all_notes);
    setTitle("All Papers");
  }
}
