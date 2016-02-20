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
import com.example.andela.pronotes.utils.DbConstants;

import org.parceler.Parcel;

import java.util.HashMap;
import java.util.List;


/**
 * Created by Chidi on 2/9/16.
 */

@Table(name = "NoteModel")
@Parcel(analyze={NoteModel.class})
public class NoteModel extends Model {

  @Column(name = "Note_book_titles")
  public String note_title;

  @Column(name = "Note_Books_categories")
  public String noteBook;

  @Column(name = "Modified_time")
  public String currentTime;

  @Column(name = "Trash")
  public int trashId;

  @Column(name = "Note_text")
  public String note_text;

  public NoteModel(){
    super();
  }

  public NoteModel(String note_title, String noteBook_category, String logNote_time,
                   int trashId, String note_text) {
    super();
    this.note_title = note_title;
    this.noteBook = noteBook_category;
    this.currentTime = logNote_time;
    this.trashId = trashId;
    this.note_text = note_text;
  }

  public static Cursor fetchResults(int id) {
   String tablename = Cache.getTableInfo(NoteModel.class).getTableName();
   String query = new Select(tablename + " .*, " + tablename + " .Id as _id").from(NoteModel.class)
       .where("Trash = "+ id).toSql();
   return Cache.openDatabase().rawQuery(query,null);
  }

  public static void deleteRecords(int id) {
    new Delete().from(NoteModel.class).where("Trash = " + id).execute();
  }

  public static HashMap<String, Integer> fetchNoteByCategory() {
    List<NoteModel> categoryList = new Select(new String[]{"Id,Note_Books_categories"}).from(NoteModel.class).where("Trash = 0").execute();
    HashMap<String, Integer > category = new HashMap<String, Integer>();
    for (NoteModel note : categoryList) {
      if (category.containsKey(note.noteBook)) {
        int curVal = category.get(note.noteBook);
        category.put(note.noteBook, curVal + 1);
      } else {
        category.put(note.noteBook, 1);
      }
    }
    return category;
  }

  @Override
  public  String toString() {
    return String.format(" %s [NoteModel - %s, %s]", getClass().getSimpleName(), this.noteBook, this.getId());
  }

  public static void saveToDb(String time, String text, String noteBook, String title, int trashId) {
    NoteModel note = new NoteModel(title, noteBook, time, trashId, text);
    note.save();
  }

}
