package com.example.andela.pronotes.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.adapter.AllNotesAdapter;
import com.example.andela.pronotes.model.NoteModel;
//

public class AllNotesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
  private Cursor notesCursor;
  private AllNotesAdapter allNotesAdapter;
  private ListView listview;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_all_notes);
    setTitle("All Notes");

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    this.listview = (ListView) findViewById(R.id.listData);
    notesCursor = NoteModel.fetchResults(0);

    allNotesAdapter = new AllNotesAdapter(this, notesCursor);
    listview.setAdapter(allNotesAdapter);

    moveToTrash();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.nav_home) {
      Intent reminderIntent = new Intent(this, HomeDashboardActivity.class);
      startActivity(reminderIntent);
    } else if (id == R.id.nav_reminder) {
      Intent reminderIntent = new Intent(this, ReminderActivity.class);
      startActivity(reminderIntent);
    } else if (id == R.id.nav_notebooks) {
      Intent allNotesIntent = new Intent(this, AllNotesActivity.class);
      startActivity(allNotesIntent);
    } else if (id == R.id.nav_collection) {
      Intent noteCollectionsIntent = new Intent(this, NoteBooksActivity.class);
      startActivity(noteCollectionsIntent);
    } else if (id == R.id.nav_settings) {
      Intent settingsIntent = new Intent(this, SettingsActivity.class);
      startActivity(settingsIntent);
    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_sync) {

    } else if (id == R.id.nav_trash) {
      Intent trashIntent = new Intent(this, TrashListActiviy.class);
      startActivity(trashIntent);
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
  private void moveToTrash() {
    listview.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapter, View item, int position, long id) {
            NoteModel trash = NoteModel.load(NoteModel.class, id);
            trash.trashId = 1;
            trash.save();
            notesCursor.requery();
            allNotesAdapter.notifyDataSetChanged();
            return true;
          }
        }
    );
  }
}
