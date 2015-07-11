package com.devpaul.materiallibrary.utils;

import android.graphics.Color;

/**
 * Created by Pauly D on 2/22/2015.
 * Class that does simple color transformations and variations.
 */
public class ColorUtils {

    /**
     * Gets a darker color.
     * @param color the color to darken
     * @return the newly darkened color.
     */
    public static int getDarkerColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.80f*hsv[2];
        if(hsv[2] > 1) {
            hsv[2] = 1.0f;
        } else if(hsv[2] < 0) {
            hsv[2] = 0.0f;
        }
        return Color.HSVToColor(hsv);
    }

    /**
     * Gets a lighter color.
     * @param color the color to lighten.
     * @return the newly lightened color.
     */
    public static int getLigherColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        float value = 1.0f - 0.6f*(1.0f - hsv[2]);
        if(value > 1) {
            hsv[2] = 1.0f;
        } else if(value < 0) {
            hsv[2] = 0.0f;
        } else {
            hsv[2] = value;
        }
        return Color.HSVToColor(hsv);
    }

    /**
     * Alters the alpha of a given color and spits out the new color.
     * @param color the color to change the alpha of.
     * @param value the new value of the alpha field.
     * @return the new color with the new alpha level.
     */
    public static int getNewColorAlpha(int color, float value) {
        int alpha = Math.round(Color.alpha(color)*value);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(alpha, r, g, b);
    }

    /**
     * Alters the alpha of the given color without multiplying the current value.
     * The current alpha value is replaced by the new one. See {#getNewColorAlpha} to see
     * how to get a new alpha color by multiplying the current value by the new value.
     * @param color the color to alter.
     * @param value the new alpha value.
     * @return the new color.
     */
    public static int getNewColorAlphaNoMult(int color, float value) {
        int alpha = Math.round(value);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(alpha, r, g, b);
    }

    /**
     * Returns a contrasting color given a color. This can be used to get the optimal text
     * color for a given backgroupd.
     * @param color the background color.
     * @return the contrast color.
     */
    public static int getContrastColor(int color) {
        int d = 0;
        // Counting the perceptive luminance - human eye favors green color...
        double a = 1 - ( 0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color))/255;
        if (a < 0.5)
            d = 0; // bright colors - black font
        else
            d = 255; // dark colors - white font
        return  Color.rgb(d, d, d);
    }

}
