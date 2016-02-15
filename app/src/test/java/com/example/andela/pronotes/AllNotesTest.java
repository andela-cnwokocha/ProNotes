package com.example.andela.pronotes;

import android.os.Build;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andela.pronotes.activities.AllNotesActivity;
import com.example.andela.pronotes.model.NoteModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.internal.Shadow;
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
  private TextView listItemTitle;
  private TextView listItemDate;
  private TextView listItemText;
  private ListView viewedList;

  @Before
  public void setUp() {
    allNotesActivity = Robolectric.setupActivity(AllNotesActivity.class);
    allNotesActivityShadow = Shadows.shadowOf(allNotesActivity);
  }

  @Test
  public void testAllNotesActivityTitle() {
    assertTrue(allNotesActivity.getTitle().equals("All Notes"));
  }

  @Test
  public void testThatAllNotesViewIsPopulated() {

    listItemTitle = (TextView) allNotesActivityShadow.findViewById(R.id.listTitle);
    listItemText = (TextView) allNotesActivityShadow.findViewById(R.id.listSomeMessage);
    listItemDate = (TextView) allNotesActivityShadow.findViewById(R.id.listDate);
    //viewedList = (ListView) allNotesActivityShadow.findViewById(R.id.listData).
  }
}
