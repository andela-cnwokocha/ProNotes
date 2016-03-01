package com.example.andela.pronotes.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;

/**
 * Created by andela on 2/29/16.
 */
public class FontMaker {
  private FontMaker() {}

  private static class FontMakerHolder {
    private static final FontMaker FONT_MAKER = new FontMaker();
  }

  public static FontMaker getInstance() {
    return FontMakerHolder.FONT_MAKER;
  }

  public static void selectFontType(TextView view, String font, Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    String fontType = preferences.getString(font, "no selection");
    switch (fontType) {
      case "RobotoMedium":
        view.setTypeface(EasyFonts.robotoMedium(context));
        break;
      case "Funraiser":
        view.setTypeface(EasyFonts.funRaiser(context));
        break;
      case "Ostrichbold":
        view.setTypeface(EasyFonts.ostrichBold(context));
        break;
      case "RobotoBlack":
        view.setTypeface(EasyFonts.robotoBlack(context));
        break;
      case "DroidSerifItalic":
        view.setTypeface(EasyFonts.droidSerifItalic(context));
        break;
      case "DroidSerifRegular":
        view.setTypeface(EasyFonts.droidSerifRegular(context));
        break;
      case "Tangerinebold":
        view.setTypeface(EasyFonts.tangerineBold(context));
        break;
      case "Windsong":
        view.setTypeface(EasyFonts.windSong(context));
        break;
      case "CavierDreams":
        view.setTypeface(EasyFonts.caviarDreams(context));
        break;
      case "CaptureIt":
        view.setTypeface(EasyFonts.captureIt(context));
        break;
      default:
        view.setTypeface(EasyFonts.ostrichBold(context));
    }
  }
}
