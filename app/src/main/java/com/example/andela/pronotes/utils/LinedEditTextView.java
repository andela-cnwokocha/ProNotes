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
  private Paint linePaint;
  private static final String LINECOLOR = "#00796B";

  public LinedEditTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    line = new Rect();
    linePaint = new Paint();

    linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    linePaint.setColor(Color.parseColor(LINECOLOR));

  }
  @Override
  protected void onDraw(Canvas canvas) {
    int height = getHeight();
    int lineHeight = getLineHeight();
    int count = height / lineHeight;
    if (getLineCount() > count)
      count = getLineCount();

    Rect rect = line;
    Paint paint = linePaint;
    int baseline = getLineBounds(0, rect);

    for (int line = 0; line < count; line++) {

      canvas.drawLine(rect.left, baseline + 1, rect.right, baseline + 1, paint);
      baseline += getLineHeight();
    }
    super.onDraw(canvas);
  }

}
