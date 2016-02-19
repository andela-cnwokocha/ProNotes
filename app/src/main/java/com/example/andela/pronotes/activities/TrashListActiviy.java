package com.example.andela.pronotes.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.andela.pronotes.R;
import com.example.andela.pronotes.adapter.AllNotesAdapter;
import com.example.andela.pronotes.model.NoteModel;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

public class TrashListActiviy extends AppCompatActivity {

  private ListView listview;
  private Cursor cursor;
  private AllNotesAdapter trashAdapter;
  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_all_notes);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setVisibility(View.GONE);

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
    getSupportActionBar().setTitle("Pronote");

    this.listview = (DynamicListView) findViewById(R.id.dynamiclistview);
    cursor = NoteModel.fetchResults(1);
    trashAdapter = new AllNotesAdapter(this, cursor);
    this.listview = (DynamicListView) findViewById(R.id.dynamiclistview);
    AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(trashAdapter);
    animationAdapter.setAbsListView(listview);
    listview.setAdapter(animationAdapter);

    toEmptyTrashRecords();
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

  private void toEmptyTrashRecords() {
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TrashListActiviy.this);
        builder.setTitle("What to do");
        builder.setPositiveButton("Restore", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            NoteModel noteModel = NoteModel.load(NoteModel.class, id);
            noteModel.trashId = 0;
            noteModel.save();
            updateView();
          }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            NoteModel noteModel = NoteModel.load(NoteModel.class, id);
            noteModel.delete();
            updateView();
          }
        });
        builder.create();
        builder.show();
      }
    });
  }

  private void updateView() {
    cursor.requery();
    trashAdapter.notifyDataSetChanged();
  }

}
