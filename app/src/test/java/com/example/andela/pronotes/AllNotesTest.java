package com.example.andela.pronotes;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.andela.pronotes.activities.AllNotesActivity;
import com.example.andela.pronotes.activities.CreateNewNote;
import com.example.andela.pronotes.activities.SettingActivity;
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

import static junit.framework.Assert.*;

/**
 * Created by andela on 2/11/16.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class AllNotesTest {

  private AllNotesActivity allNotesActivity;
  private ShadowActivity allNotesActivityShadow;
  private Button button;
  private TextView noNoteText;
  private FloatingActionButton fab;
  private DrawerLayout drawerLayout;

  @Before
  public void setUp() {
    allNotesActivity = Robolectric.setupActivity(AllNotesActivity.class);
    allNotesActivityShadow = Shadows.shadowOf(allNotesActivity);
    button = (Button) allNotesActivity.findViewById(R.id.noNote);
    noNoteText = (TextView) allNotesActivity.findViewById(R.id.noNote_text);
    fab = (FloatingActionButton) allNotesActivity.findViewById(R.id.fabbutt);
    drawerLayout = (DrawerLayout) allNotesActivity.findViewById(R.id.drawer_layout);
  }

  @Test
  public void testAllNotesActivityProperties() {
    assertTrue(allNotesActivity.getTitle().toString().equals("Pronote"));
  }

  @Test
  public void testDisplayOnEmptyActivity() {
    assertTrue(button.getText().toString().equals("Click To Add Notes"));
    assertTrue(noNoteText.getText().toString().equals("No notes added"));
  }

  @Test
  public void testOptionsMenuActions() {
    MenuItem menuItem = new RoboMenuItem(R.id.action_settings);
    assertNotNull(menuItem);
    allNotesActivity.onOptionsItemSelected(menuItem);
    Intent expectedIntent = new Intent(allNotesActivity, SettingActivity.class);
    assertTrue(allNotesActivityShadow.getNextStartedActivity().equals(expectedIntent));
  }

  @Test
  public void testDrawerLayoutSettingsOption() {
    assertNotNull(drawerLayout);
    MenuItem settingmenu = new RoboMenuItem(R.id.nav_settings);
    assertNotNull(settingmenu);

    Intent settingIntent = new Intent(allNotesActivity, SettingActivity.class);
    allNotesActivity.onNavigationItemSelected(settingmenu);
    assertTrue(allNotesActivityShadow.getNextStartedActivity().equals(settingIntent));
  }

  @Test
  public void testDrawerLayoutTrashOption() {
    assertNotNull(drawerLayout);
    MenuItem trashmenu = new RoboMenuItem(R.id.nav_trash);
    assertNotNull(trashmenu);

    Intent trashIntent = new Intent(allNotesActivity, TrashListActivity.class);
    allNotesActivity.onNavigationItemSelected(trashmenu);
    assertTrue(allNotesActivityShadow.getNextStartedActivity().equals(trashIntent));
  }

  @Test
  public void testClickingFabButton() {
    fab.performClick();
    Intent expectedIntent = new Intent(allNotesActivity, CreateNewNote.class);
    assertTrue(allNotesActivityShadow.getNextStartedActivity().equals(expectedIntent));
  }

  @Test
  public void testClickingOnNoNoteButton() {
    button.performClick();
    Intent expectedIntent = new Intent(allNotesActivity, CreateNewNote.class);
    assertTrue(allNotesActivityShadow.getNextStartedActivity().equals(expectedIntent));
  }

}
