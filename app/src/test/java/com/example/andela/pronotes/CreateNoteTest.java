package com.example.andela.pronotes;

/**
 * Created by andela on 2/10/16.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import com.example.andela.pronotes.activities.AllNotesActivity;
import com.example.andela.pronotes.activities.CreateNewNote;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.*;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class CreateNoteTest {
  private ActivityController<CreateNewNote> createNewNoteActivityController;
  private CreateNewNote createNewNote;
  private Activity activity;
  private ShadowActivity activityShadow;

  @Before
  public void setUp() {
    createNewNoteActivityController = Robolectric.buildActivity(CreateNewNote.class);
    createNewNote = Robolectric.setupActivity(CreateNewNote.class);
    activity = createNewNoteActivityController.create().get();
    activityShadow = Shadows.shadowOf(activity);
  }

  @Test
  public void testThatActivityClosesOnBackPress() {
    ShadowActivity activityShadow = Shadows.shadowOf(activity);
    activityShadow.onBackPressed();
    assertTrue(activityShadow.isFinishing());
  }

  @Test
  public void testThatNextStartedActivityIsAllNotesActivity() {
    createNewNote.onBackPressed();
    Intent expectedIntent = new Intent(createNewNote, AllNotesActivity.class);
    assertTrue(activityShadow.getNextStartedActivity().equals(expectedIntent));
  }


  @After
  public void tearDown() {
    createNewNoteActivityController
        .pause()
        .stop()
        .destroy();
  }
}
