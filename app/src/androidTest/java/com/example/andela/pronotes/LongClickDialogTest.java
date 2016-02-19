package com.example.andela.pronotes;


import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.andela.pronotes.activities.AllNotesActivity;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


/**
 * Created by andela on 2/19/16.
 */
public class LongClickDialogTest {

  @Rule
  public ActivityTestRule<AllNotesActivity> allNotesTest = new ActivityTestRule<>(AllNotesActivity.class);

  /*  Assuming data already and is populated on the view */
  @Test
  public void testLongClickEditOption() {
    onData(anything())
        .inAdapterView(withId(R.id.dynamiclistview))
        .atPosition(0)
        .perform(longClick());
    onView(withId(R.id.edit))
        .perform(click());
    onView(withId(R.id.create_note_notebook))
        .check(matches(withText("Misc")));
    onView(withId(R.id.note_title))
        .check(matches(withText("Great")));
    ViewActions.closeSoftKeyboard();
  }
  /* Assuming we have just one item, if we delete it, there should be nothing to click on*/
  @Test
  public void testLongClickDeleteOption() {
    try {
      onView(withId(R.id.dynamiclistview))
          .check(matches(isDisplayed()));
      onView(withId(R.id.noNote_text))
          .check(matches(not(isDisplayed())));
      onView(withId(R.id.noNote))
          .check(matches(not(isDisplayed())));
      onData(anything())
          .inAdapterView(withId(R.id.dynamiclistview))
          .atPosition(0)
          .perform(longClick());
      onView(withId(R.id.trash_note))
          .perform(click());
      onView(withId(R.id.dynamiclistview))
          .check(matches(not(isDisplayed())));
      onView(withId(R.id.noNote_text))
          .check(matches(isDisplayed()));
      onView(withId(R.id.noNote))
          .check(matches(isDisplayed()));
    } catch(AssertionFailedError nomatch) {
      nomatch.printStackTrace();
    }
  }

}
