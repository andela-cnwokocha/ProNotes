package com.example.andela.pronotes.activities;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.example.andela.pronotes.R;

//public class SettingsActivity extends AppCompatPreferenceActivity {
 // @Override
  //public void onCreate(Bundle savedInstanceState) {
  //  super.onCreate(savedInstanceState);

  //  getFragmentManager().beginTransaction()
  //      .replace(android.R.id.content, new SettingsFragment()).commit();

  //}
  /*public class SettingsFragment extends PreferenceFragment
      implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.pref_settings);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
      if (key.equals("autosave") || key.equals("autosaveRate")) {
        Preference pref = findPreference(key);
        pref.setSummary(sharedPreferences.getString(key, "Autosaved this key"));
      }
    }
  }*/
//}
public class SettingsActivity extends PreferenceFragment implements
      SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.pref_settings);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
      if (key.equals("autosave") || key.equals("autosaveRate")) {
        Preference pref = findPreference(key);
        pref.setSummary(sharedPreferences.getString(key, "Autosaved this key"));
      }
    }
  }
