package com.example.andela.pronotes.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.andela.pronotes.R;

public class Settings extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.settings);
    Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });

    getFragmentManager().beginTransaction()
         .replace(R.id.preference_area, new SettingsFragment()).commit();
  }

}
