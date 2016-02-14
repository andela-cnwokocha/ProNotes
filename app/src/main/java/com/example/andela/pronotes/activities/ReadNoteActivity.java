package com.example.andela.pronotes.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.adapter.AllNotesAdapter;
import com.example.andela.pronotes.model.NoteModel;

import org.parceler.Parcels;

public class ReadNoteActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trash);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });

    NoteModel note = Parcels.unwrap(getIntent().getParcelableExtra("Note"));
    setTitle(note.note_title);
    TextView content = (TextView) findViewById(R.id.note_read);
    content.setText(note.note_text);
  }
}
