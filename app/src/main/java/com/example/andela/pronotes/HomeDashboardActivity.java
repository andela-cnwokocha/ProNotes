package com.example.andela.pronotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.andela.pronotes.activities.AllNotesActivity;
import com.example.andela.pronotes.activities.CreateNewNote;
import com.example.andela.pronotes.activities.NoteBooksActivity;
import com.example.andela.pronotes.activities.ReminderActivity;
import com.example.andela.pronotes.activities.SettingsActivity;
import com.example.andela.pronotes.activities.TrashActivity;
import com.example.andela.pronotes.activities.TrashListActiviy;
import com.example.andela.pronotes.fragments.HomeDashboardFragement;

public class HomeDashboardActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_dashboard);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);


    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);


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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.home_dashboard, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
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

  public void newNoteAction(View view) {
    Intent createNewNoteIntent = new Intent(this, CreateNewNote.class);
    startActivity(createNewNoteIntent);
  }

  public void actionReminder(View view) {
    //action performed when remonder button is clicked
  }

  public void actionViewCollections(View view) {
    ///action performed when view collections button is clicked
  }

  public void actionViewNotes(View view) {
    Intent listAllIntent = new Intent(this, AllNotesActivity.class);
    startActivity(listAllIntent);
  }
}
