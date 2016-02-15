package com.example.andela.pronotes.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.andela.pronotes.R;
import com.example.andela.pronotes.adapter.TrashAdapter;
import com.example.andela.pronotes.fragments.TrashDialog;
import com.example.andela.pronotes.model.NoteModel;

public class TrashListActiviy extends AppCompatActivity {

  private ListView listview;
  private Cursor cursor;
  private TrashAdapter trashAdapter;
  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trash_list_activiy);
    setTitle("Trash Notes");
    toolbar = (Toolbar) findViewById(R.id.my_toolbar);
    setSupportActionBar(toolbar);

    listview = (ListView) findViewById(R.id.trash_list);
    cursor = NoteModel.fetchResults(1);
    trashAdapter = new TrashAdapter(this, cursor);
    listview.setAdapter(trashAdapter);

    emptyTrashRecords();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.trash_list_menu, menu);
    return true;
  }

  @Override
  public void onBackPressed() {
    Intent homeIntent = new Intent(this, HomeDashboardActivity.class);
    startActivity(homeIntent);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_trash:
        showDialog();
        cursor.requery();
        trashAdapter.notifyDataSetChanged();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void showDialog() {
    FragmentManager fm = getSupportFragmentManager();
    TrashDialog trashDialog = TrashDialog.newInstance("Empty Trash");
    trashDialog.show(fm, "Ob my zsh");
  }

  private void emptyTrashRecords() {
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
