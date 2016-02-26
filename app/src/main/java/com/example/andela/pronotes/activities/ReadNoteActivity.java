package com.example.andela.pronotes.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.andela.pronotes.utils.FontManager;
import com.vstechlab.easyfonts.EasyFonts;

import org.parceler.Parcels;

public class ReadNoteActivity extends AppCompatActivity implements FontManager {
  private FloatingActionButton fab;
  private ShareActionProvider sharer;
  private NoteModel note;
  private TextView contentView;
  private TextView notetitle;
  private long noteId;
  private SharedPreferences preferences;

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
    notetitle = (TextView) findViewById(R.id.note_title);

    setFontType(notetitle, "titleFontType");
    setFontType(contentView,"bodyFontType");

    getSupportActionBar().setTitle(note.noteBook);
    contentView.setText(note.note_text);
    notetitle.setText(note.note_title);
    noteId = Parcels.unwrap(getIntent().getParcelableExtra("ID"));

    fab = (FloatingActionButton) findViewById(R.id.fabread);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent editNoteIntent = new Intent(ReadNoteActivity.this, CreateNewNote.class);
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

  @Override
  public void setFontType(TextView view, String fonttype) {
    preferences = PreferenceManager.getDefaultSharedPreferences(this);
    String fontType = preferences.getString(fonttype, "no selection");
    switch (fontType) {
      case "RobotoMedium":
        view.setTypeface(EasyFonts.robotoMedium(this));
        break;
      case "Funraiser":
        view.setTypeface(EasyFonts.funRaiser(this));
        break;
      case "Ostrichbold":
        view.setTypeface(EasyFonts.ostrichBold(this));
        break;
      case "RobotoBlack":
        view.setTypeface(EasyFonts.robotoBlack(this));
        break;
      case "DroidSerifItalic":
        view.setTypeface(EasyFonts.droidSerifItalic(this));
        break;
      case "DroidSerifRegular":
        view.setTypeface(EasyFonts.droidSerifRegular(this));
        break;
      case "Tangerinebold":
        view.setTypeface(EasyFonts.tangerineBold(this));
        break;
      case "Windsong":
        view.setTypeface(EasyFonts.windSong(this));
        break;
      case "CavierDreams":
        view.setTypeface(EasyFonts.caviarDreams(this));
        break;
      case "CaptureIt":
        view.setTypeface(EasyFonts.captureIt(this));
        break;
      default:
        view.setTypeface(EasyFonts.ostrichBold(this));
    }
  }
}
