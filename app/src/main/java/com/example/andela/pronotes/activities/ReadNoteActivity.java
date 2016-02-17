package com.example.andela.pronotes.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.model.NoteModel;

import org.parceler.Parcels;

public class ReadNoteActivity extends AppCompatActivity {

  private FloatingActionButton fab;
  private ShareActionProvider sharer;
  private NoteModel note;
  private TextView contentView;
  private long noteId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_readnote);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });
    getSupportActionBar().setTitle("Pronote");


    note = Parcels.unwrap(getIntent().getParcelableExtra("Note"));
    contentView = (TextView) findViewById(R.id.note_read);
    contentView.setText(note.note_text);
    noteId = Parcels.unwrap(getIntent().getParcelableExtra("ID"));

    fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent editNoteIntent = new Intent(ReadNoteActivity.this, CreateNewNote.class);
        Log.i("idz", String.valueOf(noteId));
        editNoteIntent.putExtra("NoteId", noteId);
        startActivity(editNoteIntent);
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_readnote, menu);
    MenuItem shareIcon = menu.findItem(R.id.share);
    sharer = (ShareActionProvider) MenuItemCompat.getActionProvider(shareIcon);
    setShareIntent();
    return true;
  }

  private void setShareIntent() {
    if (sharer != null) {
      Intent shareIntent = new Intent(Intent.ACTION_SEND);
      shareIntent.setType("text/plain");
      shareIntent.putExtra(Intent.EXTRA_SUBJECT, note.note_title);
      shareIntent.putExtra(Intent.EXTRA_TEXT, contentView.getText());
      sharer.setShareIntent(shareIntent);
    }
  }

}
