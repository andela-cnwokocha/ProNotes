package com.example.andela.pronotes.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
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

  /*listview.setO (new OnItemLongClickListener() {
      builder.setTitle("Action:");
      builder.setItems(items, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
          cart = cartList.get(position);
          db.removeProductFromCart(context, cart);
          new AlertDialog.Builder(context)
              .setTitle(getString(R.string.success))
              .setMessage(getString(R.string.item_removed))
              .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  Intent intent = new Intent(CartDetailsActivity.this, HomeScreen.class);
                  startActivity(intent);
                }
              })
              .show();
        }
      });
      AlertDialog alert = builder.create();
      alert.show();
      //do your stuff here
      return false;
    }
  });*/

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
            Log.i("Dial_1", String.valueOf(noteModel.trashId));
            noteModel.trashId = 0;
            noteModel.save();
            cursor.requery();
            trashAdapter.notifyDataSetChanged();
            Log.i("Dial_2", String.valueOf(noteModel.trashId));
            Log.i("bma", "Restoreitem");
          }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Log.i("bma", "Deleteitem");
          }
        });
        builder.create();
        builder.show();
      }
    });
  }
  /*@Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    if (v.getId()==R.id.trash_list) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.listitem_trash_menu, menu);
      Log.i("Tag", "Restore and forget");
    }
  }*/

 /* @Override
  public boolean onContextItemSelected(MenuItem item) {
    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
    switch(item.getItemId()) {
      case R.id.restore:
        Log.i("Tag_restore", "Restore");
        return true;
      case R.id.forget:
        Log.i("Tag_forget", "Forget");
        return true;
      default:
        return super.onContextItemSelected(item);
    }
  }*/

 /* @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
    Log.i("Trrr", "Restorare");
    PopupMenu menud = new PopupMenu(this, view);
    Log.i("Trrr", "Restorare");
    menud.getMenuInflater().inflate(R.menu.listitem_trash_menu, menud.getMenu());

    menud.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()) {
          case R.id.restore:
            //NoteModel noteModel = NoteModel.load(NoteModel.class, id);
            //noteModel.trashId = 0;
            //cursor.requery();
            //trashAdapter.notifyDataSetChanged();
            Log.i("Trrr_restore", "Restoreitem");
            return true;
          case R.id.forget:
            //NoteModel noteModel1 = NoteModel.load(NoteModel.class, id);
            //noteModel1.delete();
            //cursor.requery();
            //trashAdapter.notifyDataSetChanged();
            Log.i("Trrr_forget", "Forgetitem");
            return true;
        }
        return true;
      }
    });
  }*/
}
