package com.devpaul.materiallibrary.abstracts.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Paul T.
 *
 * Abstract view class to make custom view creation easier.
 */
public abstract class BaseCustomView extends View {
    public BaseCustomView(Context context) {
        super(context);
        init(context, null);
    }

    public BaseCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * Method that must be overridden in children classes. This is called in the constructor of the
     * view.
     * @param context the passed in {@link Context}.
     * @param attrs the passes in {@link AttributeSet}
     */
    public abstract void init(Context context, @Nullable AttributeSet attrs);

    /**
     * Returns a color integer from a given color resource id.
     * @param resId the color resource id (i.e. R.color.blue)
     * @return the color.
     * @throws Resources.NotFoundException
     */
    public @ColorInt int getColor(@ColorRes int resId) throws Resources.NotFoundException {
        return getResources().getColor(resId);
    }

    /**
     * Returns a dimension from a given resource id.
     * @param resId the resource id to look up.
     * @return float the found dimension.
     * @throws Resources.NotFoundException Exception thrown
     */
    public @Dimension float getDimension(@DimenRes int resId) throws Resources.NotFoundException{
        return getResources().getDimension(resId);
    }
}
