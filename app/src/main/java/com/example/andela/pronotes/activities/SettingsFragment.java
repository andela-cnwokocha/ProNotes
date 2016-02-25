package com.example.andela.pronotes.activities;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.andela.pronotes.R;

import java.util.prefs.Preferences;

public class SettingsFragment extends PreferenceFragment {
  private SharedPreferences.OnSharedPreferenceChangeListener listener;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.pref_settings);

    listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
      @Override
      public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("autosave")) {
          findPreference(key).setSummary(String.valueOf(sharedPreferences.getBoolean(key, false)));
          findPreference("autosaveRate").setSummary(sharedPreferences.getString("autosaveRate", "2"));
        } else if (key.equals("autosaveRate")) {
          findPreference(key).setSummary(sharedPreferences.getString(key, "2"));
        } else if (key.equals("titleFontType")) {
          findPreference(key).setSummary(sharedPreferences.getString("titleFontType", "no selection"));
        } else if (key.equals("bodyFontType")){
          findPreference(key).setSummary(sharedPreferences.getString("bodyFontType", "no selection"));
        }
      }
    };
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
  }

  @Override
  public void onResume() {
    super.onResume();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
  }

  @Override
  public void onPause() {
    super.onPause();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
  }


}
