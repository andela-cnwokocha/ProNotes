package com.example.andela.pronotes;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.not;

import com.example.andela.pronotes.activities.TrashListActivity;

/**
 * Created by andela on 2/26/16.
 */
public class TrashNotesTest {

  @Rule
  public ActivityTestRule<TrashListActivity> trashlistTest = new ActivityTestRule<>(TrashListActivity.class);

  @Test
  public void testRestoreFromTrash() {
      onView(withText("Is All Done"))
          .check(matches(isDisplayed()));
      onView(withId(R.id.rv))
          .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
      onView(withText("Restore"))
          .perform(click());
      try {
        onView(withText("Is All Done"))
            .check(matches(not(isDisplayed())));
      } catch (NoMatchingViewException nmv) {
      nmv.printStackTrace();
    }
  }

  @Test
  public void testDeleteIndividualItemFromTrash() {
      onView(withText("Edit the title"))
          .check(matches(isDisplayed()));
      onView(withId(R.id.rv))
          .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
      onView(withText("Delete"))
          .perform(click());
    try {
      onView(withText("Edit the title"))
          .check(matches(not(isDisplayed())));
    } catch (NoMatchingViewException nmv) {
      nmv.printStackTrace();
    }
  }

  @Test
  public void testEmptyButtonShowsDialog() {
    onView(withId(R.id.action_trash))
        .perform(click());
    onView(withText("Are you sure you want to empty trash?"))
        .check(matches(isDisplayed()));
    onView(withText("Empty"))
        .check(matches(isDisplayed()));
    onView(withText("Cancel"))
        .check(matches(isDisplayed()));
    onView(withText("Empty Trash"))
        .check(matches(isDisplayed()));

  }

  @Test
  public void testEmptyButtonTrashAction() {
    try {
      onView(withText("Trash is empty"))
          .check(matches(not(isDisplayed())));
    } catch ( NoMatchingViewException nmv) {
      nmv.printStackTrace();
    } finally {
      onView(withId(R.id.action_trash))
          .perform(click());
      onView(withText("Empty"))
          .perform(click());
      onView(withText("Trash is empty"))
          .check(matches(isDisplayed()));
      pressBack();
    }
  }
}
