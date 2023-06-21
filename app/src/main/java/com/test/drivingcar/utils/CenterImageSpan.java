package com.test.drivingcar.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public class CenterImageSpan extends ImageSpan {

    private final float LINE_HEIGHT = 1.2f;

    public CenterImageSpan(@NonNull Context context, @DrawableRes int resourceId) {
        super(context, resourceId, ALIGN_BASELINE);
    }

    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        Drawable b = getDrawable();
        canvas.save();

        int transY = bottom - b.getBounds().bottom;
        // this is the key
        transY -= paint.getFontMetricsInt().descent / 2;

        // adjust it for the current line height
        transY *= 1 - (LINE_HEIGHT - 1) * 2;

        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}
