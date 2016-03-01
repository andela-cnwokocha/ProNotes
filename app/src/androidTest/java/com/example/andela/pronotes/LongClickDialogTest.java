package com.example.andela.pronotes;


import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.andela.pronotes.activities.AllNotesActivity;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;


/**
 * Created by andela on 2/19/16.
 */
public class LongClickDialogTest {

  @Rule
  public ActivityTestRule<AllNotesActivity> allNotesTest = new ActivityTestRule<>(AllNotesActivity.class);

  public void createNotes() {
    onView(withId(R.id.fabbutt))
        .perform(click());
    onView(withId(R.id.createnotebook_line))
        .perform(typeText("Second note"));
    onView(withId(R.id.note_title))
        .perform(typeText("Edit the title"));
    onView(withId(R.id.create_note_notebook))
        .perform(typeText("Note"));
    closeSoftKeyboard();
    pressBack();
  }

  public void setUpNotes() {
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
    createNotes();
  }

  @Test
  public void testLongClickEditOption() {
    setUpNotes();
    onView(withId(R.id.rv))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
    onView(withId(R.id.edit))
        .perform(click());
    onView(withId(R.id.create_note_notebook))
        .check(matches(withText(containsString("Espresso"))));
    onView(withId(R.id.note_title))
        .check(matches(withText(containsString("title"))));
    closeSoftKeyboard();
    pressBack();
  }

  @Test
  public void testLongClickDeleteOption() {
    onView(withId(R.id.rv))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
    onView(withId(R.id.trash_note))
        .perform(click());
    deleteNextNote();
  }

  public void deleteNextNote() {
    onView(withId(R.id.rv))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
    onView(withId(R.id.trash_note))
        .perform(click());
    onView(withId(R.id.rv))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
    onView(withId(R.id.trash_note))
        .perform(click());
  }
}
