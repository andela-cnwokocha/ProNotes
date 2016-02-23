package com.example.andela.pronotes.activities;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.adapter.NotesViewAdapter;
import com.example.andela.pronotes.model.NoteModel;

import java.util.List;


public class AllNotesActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private List<NoteModel> notes;
  private NotesViewAdapter pva;
  private Toolbar toolbar;
  private Button noNoteButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    toolbar = (Toolbar) findViewById(R.id.tool_bar);
    setSupportActionBar(toolbar);
    setUpNavigation();
    setTitle("Pronote");

    PreferenceManager.setDefaultValues(this, R.xml.pref_settings, false);

    noNoteButton = (Button) findViewById(R.id.noNote);

    noNoteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startCreateNote();
      }
    });

    notes = NoteModel.fetchNotes(0);
    setView();

    RecyclerView rcv = (RecyclerView) findViewById(R.id.rv);

    GridLayoutManager llm = new GridLayoutManager(this,2);
    llm.setOrientation(LinearLayoutManager.VERTICAL);
    rcv.setLayoutManager(llm);
    rcv.setHasFixedSize(true);

    pva = new NotesViewAdapter(NoteModel.fetchNotes(0));
    rcv.setAdapter(pva);

    addNewNote();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.home_dashboard, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      Intent settingsIntent = new Intent(this, Settings.class);
      startActivity(settingsIntent);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      minimizeApp();
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    int id = item.getItemId();

   if (id == R.id.nav_notebooks) {
      Intent allNotesIntent = new Intent(this, AllNotesActivity.class);
      startActivity(allNotesIntent);
    } else if (id == R.id.nav_settings) {
      Intent settingsIntent = new Intent(this, Settings.class);
      startActivity(settingsIntent);
    } else if (id == R.id.nav_trash) {
      Intent trashIntent = new Intent(this, TrashListActiviy.class);
      startActivity(trashIntent);
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
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

  private void addNewNote() {
    FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.fab);
    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startCreateNote();
      }
    });
  }

  private void setView() {

    if(notes.size() < 1) {
      LinearLayout layout = (LinearLayout) findViewById(R.id.noNoteLayout);
      layout.setVisibility(View.VISIBLE);
    }
  }

  private void minimizeApp() {
    Intent main = new Intent(Intent.ACTION_MAIN);
    main.addCategory(Intent.CATEGORY_HOME);
    main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(main);
  }

  private void startCreateNote() {
    Intent createNoteIntent = new Intent(AllNotesActivity.this, CreateNewNote.class);
    startActivity(createNoteIntent);
  }

}
