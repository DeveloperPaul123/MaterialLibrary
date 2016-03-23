package com.devpaul.materiallibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Pauly D on 4/29/2015.
 */
public class ScreenUtil {

    /**
     * Returns the screens width in pixels.
     * @param context the calling context.
     * @return the width in pixels.
     */
    public static float getScreenWidthInPixels(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return dm.widthPixels;
    }

    /**
     * Returns the screen's height in pixels
     * @param context the calling context.
     * @return the height in pixels.
     */
    public static float getScreenHeightInPixels(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return dm.heightPixels;
    }

    /**
     * Returns the display metrics for the context.
     * @param context the calling context.
     * @return the display metrics for the screen.
     */
    private static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    /**
     * Returns the height of the root layout excluding the title bar and the action bar.
     * @param context the calling context.
     * @return the height of the root layout.
     */
    public static float getRootLayoutHeightInPixels(Context context) {
        Activity activity = (Activity) context;
        Rect rect = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusHeight = rect.top;
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleHeight = contentViewTop + statusHeight;
        float height = getScreenHeightInPixels(context);
        return height - titleHeight;
    }
}
