package com.example.andela.pronotes.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by andela on 2/9/16.
 */
public class LinedEditTextView extends EditText{

  private Rect line;
  private Paint linepaint;
  private static final String LINECOLOR = "#00796B";

  public LinedEditTextView(Context context, AttributeSet attributeset) {
    super(context, attributeset);

    line = new Rect();
    linepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    float weight = getResources().getDisplayMetrics().density;
    linepaint.setStrokeWidth(weight);
    linepaint.setStyle(Paint.Style.STROKE);
    linepaint.setColor(Color.parseColor(LINECOLOR));
  }

  @Override
  protected void onDraw(Canvas canvas) {
    int count = getLineCount();
    Rect aLine = line;
    Paint paintline = linepaint;

    for(int i = 0; i < count; i++) {
      int baseline = getLineBounds(i, aLine);
      canvas.drawLine(aLine.left, baseline + 1, aLine.right, baseline + 1, paintline);
    }
    super.onDraw(canvas);
  }
}
