package com.example.andela.pronotes.utils;

/**
 * Created by andela on 2/15/16.
 */
public enum ViewConstants {
   HOME_SCREEN ("Pronote");

    private final String name;

    ViewConstants(String names) {
      name = names;
    }

    public String toString() {
      return name;
    }

}
