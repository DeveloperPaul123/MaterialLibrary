package com.devpaul.materiallibrary.abstracts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Pauly D on 5/1/2015.
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

    public abstract void init(Context context, AttributeSet attrs);

    public int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public float getDimension(int resId) {
        return getResources().getDimension(resId);
    }
}
