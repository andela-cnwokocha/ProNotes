package com.example.andela.pronotes;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.Matchers.*;


import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.andela.pronotes.activities.AllNotesActivity;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Chidi on 2/18/16.
 */
public class AllNotesTest {

  @Rule
  public ActivityTestRule<AllNotesActivity> allNotesTest = new ActivityTestRule<>(AllNotesActivity.class);

  @Test
  public void testClickingFloatingActionButtonOnAllNotesCreatesNote() {
    onView(withId(R.id.fab))
        .perform(click());
    onView(withId(R.id.createnotebook_line))
        .perform(typeText("Now we see how espresso works"))
        .check(matches(withText("Now we see how espresso works")));
    onView(withId(R.id.create_note_notebook))
        .perform(typeText("Espresso"))
        .check(matches(withText("Espresso")));
    onView(withId(R.id.create_note_tag))
        .perform(typeText("E"))
        .check(matches(withText("E")));
    onView(withId(R.id.note_title))
        .perform(typeText("Is All Done"))
        .check(matches(withText("Is All Done")));
    ViewActions.closeSoftKeyboard();
  }

  @Test
  public void testClickingOnListItemToReadNote() {
    onData(anything())
        .inAdapterView(withId(R.id.dynamiclistview))
        .atPosition(0)
        .perform(click());
    onView(withId(R.id.note_read)).check(matches(withText("Now we see how espresso works")));
  }

  @Test
  public void testClickingFabButtonOnReadingToEditNote() {
    onView(withId(R.id.fab))
        .perform(click());
    onView(withId(R.id.note_title))
        .perform(typeText("All Not Done?"))
        .check(matches(withText("All Not Done?")));
    ViewActions.closeSoftKeyboard();
  }

  @Test
  public void testLongClickOnListViewItem() {

  }


}
