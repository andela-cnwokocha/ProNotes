package com.example.andela.pronotes.activities;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

import com.example.andela.pronotes.R;

public class SettingsActivity extends AppCompatActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getFragmentManager().beginTransaction()
        .replace(android.R.id.content, new SettingsFragment()).commit();
  }

  public static class SettingsFragment extends PreferenceFragment
      implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.pref_settings);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
      if (key.equals("autosave")) {
        Preference pref = findPreference(key);
        pref.setSummary(sharedPreferences.getString(key, "Autosaved this key"));
      }
    }
  }

}
