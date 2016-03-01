package com.example.andela.pronotes;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.Matchers.*;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;

import com.example.andela.pronotes.activities.AllNotesActivity;

/**
 * Created by Chidi on 2/18/16.
 */
public class AllNotesTest extends ActivityInstrumentationTestCase2<AllNotesActivity> {

  public AllNotesTest() {
    super(AllNotesActivity.class);
  }

  protected void setUp() throws Exception{
    super.setUp();
    getActivity();
  }

  public void testClickingFloatingActionButtonOnAllNotesCreatesNote() {
    onView(withId(R.id.fabbutt))
        .perform(click());
    onView(withId(R.id.createnotebook_line))
        .perform(typeText("Now we see how espresso works"))
        .check(matches(withText("Now we see how espresso works")));
    onView(withId(R.id.create_note_notebook))
        .perform(typeText("Espresso"))
        .check(matches(withText("Espresso")));
    onView(withId(R.id.note_title))
        .perform(typeText("Edit the title"))
        .check(matches(withText("Edit the title")));
    closeSoftKeyboard();
    pressBack();
  }

  public void testClickingOnListItemToReadNoteAndEditNote() {
    onView(withId(R.id.rv))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    onView(withId(R.id.note_read)).check(matches(withText("Now we see how espresso works")));
    onView(withId(R.id.fabread))
        .perform(click());
    onView(withId(R.id.note_title))
        .perform(typeText("Is All Done"))
        .check(matches(withText(containsString("Is All Done"))));
    ViewActions.closeSoftKeyboard();
    pressBack();

  }
}
