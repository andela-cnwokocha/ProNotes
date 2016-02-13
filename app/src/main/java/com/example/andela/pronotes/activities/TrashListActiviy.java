package com.example.andela.pronotes.activities;

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
import android.widget.Toast;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.adapter.AllNotesAdapter;
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

  private void emptyTrashRecords() {
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //showDialog();
        cursor.requery();
        trashAdapter.notifyDataSetChanged();
      }
    });
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.trash_list_menu, menu);
    return true;
  }

  private void showDialog() {
    FragmentManager fm = getSupportFragmentManager();
    TrashDialog trashDialog = TrashDialog.newInstance("Ebele");
    trashDialog.show(fm, "Ob my zsh");
  }

}
