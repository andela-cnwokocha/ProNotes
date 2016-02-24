package com.example.andela.pronotes.utils;

/**
 * Created by andela on 2/10/16.
 */
public enum DbConstants {
  DATABASE ("NoteModel"),
  TABLE ("notes"),
  DATABASE_VERSION ("1"),
  TITLE ("Note_book_titles"),
  HOME_SCREEN ("Pronote");

  private final String name;

  DbConstants(String names) {
    name = names;
  }

  public String toString() {
    return this.name;
  }

}
