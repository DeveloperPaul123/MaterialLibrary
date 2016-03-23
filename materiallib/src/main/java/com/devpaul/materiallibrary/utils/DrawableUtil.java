package com.devpaul.materiallibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Paul T. on 5/1/2015.
 */
public class DrawableUtil {

    /**
     * Converts a drawable to a bitmap.
     * @param drawable the drawable to convert.
     * @return the bitmap rendition of the drawable.
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if(drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }
}
