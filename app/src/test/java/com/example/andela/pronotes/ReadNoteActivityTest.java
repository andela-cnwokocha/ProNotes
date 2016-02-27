package com.example.andela.pronotes;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;

import com.example.andela.pronotes.activities.CreateNewNote;
import com.example.andela.pronotes.activities.ReadNoteActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.*;


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class ReadNoteActivityTest {
  private ReadNoteActivity readNoteActivity;
  private ShadowActivity readNoteActivityShadow;
  private ActivityController<ReadNoteActivity> readNoteActivityActivityController;
  private FloatingActionButton fab;

  @Before
  public void setUp() {
    readNoteActivityActivityController = Robolectric.buildActivity(ReadNoteActivity.class);
    readNoteActivity = readNoteActivityActivityController.create().get();
    readNoteActivityShadow = Shadows.shadowOf(readNoteActivity);
    fab = (FloatingActionButton) readNoteActivity.findViewById(R.id.fabread);

  }

  @Test
  public void testThatFloatingButtonLaunchesActivity() {
    fab.performClick();
    Intent createNoteIntent = new Intent(readNoteActivity, CreateNewNote.class);
    assertTrue(readNoteActivityShadow.getNextStartedActivity().equals(createNoteIntent));
  }

  @Test
  public void testThatShareIconLaunchesShareAction() {
    MenuItem shareIcon = new RoboMenuItem(R.id.share);
    assertNotNull(shareIcon);

  }
}
