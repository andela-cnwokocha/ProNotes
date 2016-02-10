package com.example.andela.pronotes;

/**
 * Created by andela on 2/10/16.
 */
import android.app.Activity;
import android.os.Build;

import com.example.andela.pronotes.activities.CreateNewNote;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.*;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class CreateNoteTest {
  private ActivityController<CreateNewNote> createNewNoteActivityController;
  private CreateNewNote createNewNote;

  @Before
  public void setUp() {
    createNewNoteActivityController = Robolectric.buildActivity(CreateNewNote.class);
  }

  @Test
  public void testCreateNewNoteOnBackPressedEvent() {

  }
  @Test
  public void createAndDestroyActivity() {
    createNewNoteActivityController
        .create()
        .start()
        .resume()
        .visible()
        .get();
  }
  @After
  public void tearDown() {
    createNewNoteActivityController
        .pause()
        .stop()
        .destroy();
  }

  @Test
  public void testThatModelCapturesDataOnBackPress() {

  }

  @Test
  public void testThatDataIsSavedToDbOnBackKeyPress() {

  }
}
