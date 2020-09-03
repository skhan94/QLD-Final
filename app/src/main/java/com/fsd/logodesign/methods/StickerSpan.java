package com.fsd.logodesign.methods;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

public class StickerSpan extends DynamicDrawableSpan {
    Drawable mDrawable;

    public StickerSpan(Context context, Bitmap bitmap) {
        super(DynamicDrawableSpan.ALIGN_BASELINE);
        setBitmap(context, bitmap);
    }

    public void setBitmap(Context context, Bitmap bitmap) {
        mDrawable = new BitmapDrawable(context.getResources(), bitmap);
        int width = mDrawable.getIntrinsicWidth();
        int height = mDrawable.getIntrinsicHeight();
        mDrawable.setBounds(0, 0, width > 0 ? width : 0, height > 0 ? height : 0); 
    }

    @Override
    public Drawable getDrawable() {
        return mDrawable;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text,
                     int start, int end, float x, 
                     int top, int y, int bottom, Paint paint) {
        Drawable b = mDrawable;
        canvas.save();

        int transY = bottom - b.getBounds().bottom;
        if (mVerticalAlignment == ALIGN_BASELINE) {
            int textLength = text.length();
            for (int i = 0; i < textLength; i++) {
                if (Character.isLetterOrDigit(text.charAt(i))) {
                    transY -= paint.getFontMetricsInt().descent;
                    break;
                }
            }
        }

        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}
