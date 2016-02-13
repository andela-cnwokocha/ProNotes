package com.example.andela.pronotes.model;

import android.database.Cursor;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;


/**
 * Created by andela on 2/9/16.
 */

@Table(name = "NoteModel")
public class NoteModel extends Model {

  @Column(name = "Note_book_titles")
  public String note_title;

  @Column(name = "Note_Books_categories")
  public String noteBook;

  @Column(name = "Modified_time")
  public String currentTime;

  @Column(name = "note_books_tags")
  public String tag;

  @Column(name = "Trash")
  public int trashId;

  @Column(name = "Note_text")
  public String note_text;

  public NoteModel(){
    super();
  }

  public NoteModel(String note_title, String noteBook_category, String logNote_time, String note_tag, int trashId, String note_text) {
    super();
    this.note_title = note_title;
    this.noteBook = noteBook_category;
    this.currentTime = logNote_time;
    this.trashId = trashId;
    this.note_text = note_text;
    this.tag = note_tag;
  }

  public static Cursor fetchResults(int id) {
   String tablename = Cache.getTableInfo(NoteModel.class).getTableName();
   String query = new Select(tablename + " .*, " + tablename + " .Id as _id").from(NoteModel.class).where("Trash = "+ id).toSql();
   return Cache.openDatabase().rawQuery(query,null);
  }

  public static void deleteRecords(int id) {
    new Delete().from(NoteModel.class).where("Trash = " + id).execute();
  }
}
