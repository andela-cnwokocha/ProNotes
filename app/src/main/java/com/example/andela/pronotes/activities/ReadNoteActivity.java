package com.example.andela.pronotes.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_readNote);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });

    note = Parcels.unwrap(getIntent().getParcelableExtra("Note"));
    setTitle(note.note_title);
    contentView = (TextView) findViewById(R.id.note_read);
    contentView.setText(note.note_text);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_readNote, menu);
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
