package com.example.andela.pronotes.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by andela on 2/29/16.
 */
public class IntentRunner {

  private IntentRunner() {}

  private static class IntentRunnerHolder {
    private static final IntentRunner INTENT_RUNNER = new IntentRunner();
  }

  public static IntentRunner getInstance() {
    return IntentRunnerHolder.INTENT_RUNNER;
  }

  public static void startIntent(Context context, Class<?> activity) {
    Intent activityIntent = new Intent(context, activity);
    context.startActivity(activityIntent);
  }
}
