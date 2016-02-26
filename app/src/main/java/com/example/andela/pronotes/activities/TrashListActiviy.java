package com.example.andela.pronotes.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.andela.pronotes.R;
import com.example.andela.pronotes.adapter.NotesViewAdapter;
import com.example.andela.pronotes.adapter.TrashNotesAdapter;
import com.example.andela.pronotes.model.NoteModel;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

public class TrashListActiviy extends AppCompatActivity {
  private Toolbar toolbar;
  private TrashNotesAdapter pva;
  private List<NoteModel> trashNotes;
  private RecyclerView rcv;
  private GridLayoutManager layoutManager;
  private LinearLayout layout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabbutt);
    fab.setVisibility(View.GONE);
    DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    toolbar = (Toolbar) findViewById(R.id.tool_bar);
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
    getSupportActionBar().setTitle("Trash");

    layout = (LinearLayout) findViewById(R.id.noNoteLayout);
    trashNotes = NoteModel.fetchNotes(1);
    layoutManager = new GridLayoutManager(this, 1);
    setView();
    pva = new TrashNotesAdapter(NoteModel.fetchNotes(1), layout, layoutManager);
    layoutManager = new GridLayoutManager(this, 1);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rcv = (RecyclerView) findViewById(R.id.rv);
    rcv.setLayoutManager(layoutManager);
    rcv.setHasFixedSize(true);
    rcv.setAdapter(pva);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.trash_list_menu, menu);
    return true;
  }

  @Override
  public void onBackPressed() {
    Intent homeIntent = new Intent(this, AllNotesActivity.class);
    startActivity(homeIntent);
    super.onBackPressed();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_trash:
        emptyTrash();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void emptyTrash() {
    AlertDialog.Builder builder = new AlertDialog.Builder(TrashListActiviy.this);
    builder.setTitle("Empty Trash");
    builder.setMessage("Are you sure you want to empty trash?");
    builder.setPositiveButton("Empty", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        trashNotes.clear();
        NoteModel.deleteRecords(1);
        dialog.dismiss();
        updateView();
      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    });
    builder.create();
    builder.show();
  }

  private void updateView() {
    setView();
    layoutManager.requestLayout();
    pva.notifyDataSetChanged();
  }

  private void setView() {
    if (trashNotes.size() < 1) {
      LinearLayout layout = (LinearLayout) findViewById(R.id.noNoteLayout);
      Button button = (Button) findViewById(R.id.noNote);
      TextView label = (TextView) findViewById(R.id.noNote_text);
      label.setText(R.string.trashed_notice);
      button.setVisibility(View.GONE);
      layout.setVisibility(View.VISIBLE);
    }
  }
}
