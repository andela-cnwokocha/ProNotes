package com.example.andela.pronotes.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.adapter.AllNotesAdapter;
import com.example.andela.pronotes.adapter.TrashAdapter;
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

    deleteRecord();
  }

  private void deleteRecord() {
    listview.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapter, View item, int position, long id) {
            NoteModel trash = NoteModel.load(NoteModel.class, id);
            trash.delete();
            cursor.requery();
            trashAdapter.notifyDataSetChanged();
            return true;
          }
        }
    );
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
        NoteModel.deleteRecords(1);
        cursor.requery();
        trashAdapter.notifyDataSetChanged();
        Toast.makeText(this, "It is clicked", Toast.LENGTH_LONG).show();
        return true;
      default:
        return super.onOptionsItemSelected(item);

    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.trash_list_menu, menu);
    return true;
  }
}
