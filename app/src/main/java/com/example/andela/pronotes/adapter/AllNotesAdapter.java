package com.example.andela.pronotes.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.model.NoteModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by andela on 2/9/16.
 */
public class AllNotesAdapter extends CursorAdapter {
  public AllNotesAdapter(Context context, Cursor cursor) {
    super(context, cursor, 0);
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return LayoutInflater.from(context).inflate(R.layout.note_list, parent, false);
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    TextView title = (TextView) view.findViewById(R.id.listTitle);
    TextView date = (TextView) view.findViewById(R.id.listDate);
    TextView brief = (TextView) view.findViewById(R.id.listSomeMessage);

    String bodyTitle = cursor.getString(cursor.getColumnIndexOrThrow("Note_book_titles"));
    String bookDate = cursor.getString(cursor.getColumnIndexOrThrow("Modified_time"));
    String bookBrief = cursor.getString(cursor.getColumnIndexOrThrow("Note_text"));


    title.setText(bodyTitle);
    date.setText(bookDate);
    brief.setText(bookBrief);
  }
}