package com.example.andela.pronotes;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;

import com.example.andela.pronotes.activities.AllNotesActivity;
import com.example.andela.pronotes.activities.TrashListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;

import static junit.framework.Assert.*;

/**
 * Created by andela on 2/27/16.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class TrashNoteActivityTest {

  private TrashListActivity trashListActivity;
  private ShadowActivity trashlistShadow;

  @Before
  public void setup() {
    trashListActivity = Robolectric.setupActivity(TrashListActivity.class);
    trashlistShadow = Shadows.shadowOf(trashListActivity);
  }

  @Test
  public void testOptionsDialogToEmptyNotes() {
    MenuItem menu = new RoboMenuItem(R.id.action_trash);
    assertNotNull(menu);
    trashListActivity.onOptionsItemSelected(menu);
    AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
    assertNotNull(dialog);
    ShadowAlertDialog shadowAlert = Shadows.shadowOf(dialog);
    assertTrue(shadowAlert.getTitle().toString().equals("Empty Trash"));
    assertTrue(shadowAlert.getMessage().toString().equals("Are you sure you want to empty trash?"));
    assertTrue(shadowAlert.isCancelable());
    assertTrue(shadowAlert.isCancelableOnTouchOutside());
  }

  @Test
  public void testOnBackPressLaunchesAllNotesActivity() {
    trashListActivity.onBackPressed();
    Intent allnotesIntent = new Intent(trashListActivity, AllNotesActivity.class);
    assertTrue(trashlistShadow.getNextStartedActivity().equals(allnotesIntent));
  }
}
