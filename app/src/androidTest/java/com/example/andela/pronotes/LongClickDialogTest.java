package com.example.andela.pronotes;


import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.andela.pronotes.activities.AllNotesActivity;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.not;


/**
 * Created by andela on 2/19/16.
 */
public class LongClickDialogTest {

  @Rule
  public ActivityTestRule<AllNotesActivity> allNotesTest = new ActivityTestRule<>(AllNotesActivity.class);

  @Test
  public void testLongClickEditOption() {
    onView(withId(R.id.rv))
        .perform(RecyclerViewActions.actionOnItemAtPosition(2, longClick()));
    onView(withId(R.id.edit))
        .perform(click());
    onView(withId(R.id.create_note_notebook))
        .check(matches(withText("Borot")));
    onView(withId(R.id.note_title))
        .check(matches(withText("Botor")));
    ViewActions.closeSoftKeyboard();
    pressBack();
  }

  @Test
  public void testLongClickDeleteOption() {
    try {
      onView(withText("Botor"))
          .check(matches(isDisplayed()));
      onView(withId(R.id.rv))
          .perform(RecyclerViewActions.actionOnItemAtPosition(2, longClick()));
      onView(withId(R.id.trash_note))
          .perform(click());
      onView(withText("Botor"))
          .check(matches(not(isDisplayed())));
    } catch (NoMatchingViewException nomatch) {
      nomatch.printStackTrace();
    }
  }

}
