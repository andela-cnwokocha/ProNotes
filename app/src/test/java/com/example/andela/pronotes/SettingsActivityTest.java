package com.example.andela.pronotes;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.example.andela.pronotes.activities.SettingActivity;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

/**
 * Created by andela on 2/27/16.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class SettingsActivityTest {
  private SettingActivity settingActivity;
  private ShadowActivity settingActivityShadow;
  private android.content.SharedPreferences sharedPreferences;

  @Before
  public void setup() {
    settingActivity = Robolectric.setupActivity(SettingActivity.class);
    settingActivityShadow = Shadows.shadowOf(settingActivity);
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(settingActivity);
  }

}
