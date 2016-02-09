package com.example.andela.pronotes.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andela.pronotes.R;

public class HomeDashboardFragement extends Fragment {
  @Nullable
  @Override
  public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedinstancestate) {
    return layoutInflater.inflate(R.layout.fragment_home_dashboard_fragement, null);
  }
}
