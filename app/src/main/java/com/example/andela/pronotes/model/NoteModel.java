package com.example.andela.pronotes.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by andela on 2/9/16.
 */

@Table(name = "Notes")
public class NoteModel {

 /* @Column(name = "note_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
  public long note_id;*/

  @Column(name = "Note_book_titles")
  public String book_title;

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
    this.book_title = note_title;
    //this.note_id = note_id;
    this.noteBook = noteBook_category;
    this.currentTime = logNote_time;
    this.trashId = trashId;
    this.note_text = note_text;
  }
}
