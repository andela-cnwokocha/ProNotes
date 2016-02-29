package com.example.andela.pronotes.utils;

import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Type;

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

  public static void startIntentWithData(Context context, Class<?> activity, String itemskey, long value) {
    Intent activityIntent = new Intent(context, activity);
    activityIntent.putExtra(itemskey, value);
    context.startActivity(activityIntent);
  }
}
