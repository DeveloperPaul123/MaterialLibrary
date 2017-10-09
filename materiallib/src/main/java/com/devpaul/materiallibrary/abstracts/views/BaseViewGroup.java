package com.devpaul.materiallibrary.abstracts.views;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Paul T.
 *
 * Abstract view group class for creating custom view groups.
 */
public abstract class BaseViewGroup extends ViewGroup {

    public BaseViewGroup(Context context) {
        super(context);
        init(context, null);
    }

    public BaseViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
     * @throws Resources.NotFoundException
     */
    public @Dimension float getDimension(int resId) throws Resources.NotFoundException {
        return getResources().getDimension(resId);
    }
}
