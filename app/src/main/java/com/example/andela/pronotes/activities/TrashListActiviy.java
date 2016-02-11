package com.example.andela.pronotes.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.adapter.AllNotesAdapter;
import com.example.andela.pronotes.adapter.TrashAdapter;
import com.example.andela.pronotes.model.NoteModel;

public class TrashListActiviy extends AppCompatActivity {

  private ListView listview;
  private Cursor cursor;
  private TrashAdapter trashAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trash_list_activiy);
    setTitle("Trash Notes");

    listview = (ListView) findViewById(R.id.trash_list);
    cursor = NoteModel.fetchResults(16);

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
            trash.trashId = 1;
            trash.save();
            cursor.requery();
            trashAdapter.notifyDataSetChanged();
            return true;
          }
        }
    );
  }
}
