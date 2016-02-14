package com.example.andela.pronotes.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
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
  private ActionMode actionMode;
  private long itemId;

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
      Intent reminderIntent = new Intent(this, HomeDashboardActivity.class);
      startActivity(reminderIntent);
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
            if (actionMode != null) {
              return true;
            }
            Log.i("action", "log_action");
            itemId = id;
            actionMode = startActionMode(modeCallBack);
            item.setSelected(true);
            return true;
          }
        }
    );
  }

  private ActionMode.Callback modeCallBack = new ActionMode.Callback() {
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
      mode.setTitle("Wallup");
      mode.getMenuInflater().inflate(R.menu.listitem_trash_menu, menu);
      return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
      return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
      switch (item.getItemId()) {
        case R.id.edit:
          Intent editNoteIntent = new Intent(AllNotesActivity.this, CreateNewNote.class);
          editNoteIntent.putExtra("NoteId", itemId);
          startActivity(editNoteIntent);
          mode.finish();
          return true;
        case R.id.share:

          mode.finish();
          return true;
        case R.id.trash_note:
          NoteModel trash = NoteModel.load(NoteModel.class, itemId);
          trash.trashId = 1;
          trash.save();
          notesCursor.requery();
          allNotesAdapter.notifyDataSetChanged();
        default:
          return false;
      }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
      actionMode = null;
    }
  };


}
