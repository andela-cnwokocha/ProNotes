package com.example.andela.pronotes;

import android.content.Intent;
import android.os.Build;

import com.example.andela.pronotes.activities.CreateNewNote;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


/**
 * Created by andela on 2/9/16.
 * About: This is the test for the main class, here I test if the right class is called given a method.
 */


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class HomeDashboardActivityTest {
  private HomeDashboardActivity homeDashboardActivity;

  @Before
  public void setUp() {
    homeDashboardActivity = Robolectric.setupActivity(HomeDashboardActivity.class);
  }

  @Test
  public void testStartCreateNewNoteActivityOnCLick() {
    homeDashboardActivity.findViewById(R.id.createNote).performClick();
    Intent expectedIntent = new Intent(homeDashboardActivity, CreateNewNote.class);

    ShadowActivity homedashboardShadowable = Shadows.shadowOf(homeDashboardActivity);
    Intent actualStartedIntent = homedashboardShadowable.getNextStartedActivity();

    assertTrue(actualStartedIntent.filterEquals(expectedIntent));

  }
}
