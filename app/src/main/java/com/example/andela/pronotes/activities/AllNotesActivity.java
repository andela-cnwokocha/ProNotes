package com.example.andela.pronotes.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.adapter.AllNotesAdapter;
import com.example.andela.pronotes.model.NoteModel;
import com.example.andela.pronotes.utils.ViewConstants;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import org.parceler.Parcels;


public class AllNotesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
  private Cursor notesCursor;
  private AllNotesAdapter allNotesAdapter;
  private ListView listview;
  private ActionMode actionMode;
  private long itemId;
  private Toolbar toolbar;
  private Button noNoteButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_all_notes);
    setTitle("All Notes");

    toolbar = (Toolbar) findViewById(R.id.tool_bar);
    setSupportActionBar(toolbar);
    setUpNavigation();
    setTitle("Pronote");

    PreferenceManager.setDefaultValues(this, R.xml.pref_settings, false);

    notesCursor = NoteModel.fetchResults(0);
    Log.i("ZIX", String.valueOf(notesCursor.getCount()));
    noNoteButton = (Button) findViewById(R.id.noNote);

    if(notesCursor.getCount() < 1) {
      TextView noNoteView = (TextView) findViewById(R.id.noNote_text);
      noNoteView.setVisibility(View.VISIBLE);
      noNoteButton.setVisibility(View.VISIBLE);
    }

    allNotesAdapter = new AllNotesAdapter(this, notesCursor);
    this.listview = (DynamicListView) findViewById(R.id.dynamiclistview);
    AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(allNotesAdapter);
    animationAdapter.setAbsListView(listview);
    listview.setAdapter(animationAdapter);

    moveToTrash();
    readNote();
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
            itemId = id;
            Log.i("tosp_long_id", String.valueOf(id));
            Log.i("tosp_long_pos", String.valueOf(position));
            actionMode = startActionMode(modeCallBack);
            item.setSelected(true);
            return true;
          }
        });
  }

  private ActionMode.Callback modeCallBack = new ActionMode.Callback() {
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
      mode.setTitle(ViewConstants.HOME_SCREEN.toString());
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
          Log.i("tosp_shared", "Shared selected");
          mode.finish();
          return true;
        case R.id.trash_note:
          NoteModel trash = NoteModel.load(NoteModel.class, itemId);
          trash.trashId = 1;
          trash.save();
          notesCursor.requery();
          allNotesAdapter.notifyDataSetChanged();
          mode.finish();
        default:
          return false;
      }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
      actionMode = null;
    }

  };

  private void readNote() {
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NoteModel noteModel = NoteModel.load(NoteModel.class, id);
        Intent readNoteIntent = new Intent(AllNotesActivity.this, ReadNoteActivity.class);
        readNoteIntent.putExtra("Note", Parcels.wrap(noteModel));
        readNoteIntent.putExtra("ID", Parcels.wrap(id));
        startActivity(readNoteIntent);
      }
    });
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
        Intent createNoteIntent = new Intent(AllNotesActivity.this, CreateNewNote.class);
        startActivity(createNoteIntent);
      }
    });
  }

}
