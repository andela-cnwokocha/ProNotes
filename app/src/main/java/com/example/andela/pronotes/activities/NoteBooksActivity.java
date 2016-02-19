package com.example.andela.pronotes.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.andela.pronotes.R;
import com.example.andela.pronotes.model.NoteModel;

import java.util.HashMap;
import java.util.List;


public class NoteBooksActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {
  private Toolbar toolbar;
  private Button noNoteButton;
  private HashMap<String, Integer> category = new HashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_note_books);
    setTitle("All Notebooks");

    toolbar = (Toolbar) findViewById(R.id.tool_bar);
    setSupportActionBar(toolbar);
    setUpNavigation();

    noNoteButton = (Button) findViewById(R.id.noNote);
    //category = NoteModel.fetchNoteByCategory();

    if(category.size() < 7) {
      TextView noNoteView = (TextView) findViewById(R.id.noNote_text);
      noNoteView.setVisibility(View.VISIBLE);
      noNoteButton.setVisibility(View.VISIBLE);
    }

    addNewNote();
  }


  private void setUpNavigation() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    return false;
  }

  private void addNewNote() {
    FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.fab);
    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent createNoteIntent = new Intent(NoteBooksActivity.this, CreateNewNote.class);
        startActivity(createNoteIntent);
      }
    });
  }
}
