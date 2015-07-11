package com.devpaul.materiallibrary.abstracts;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Pauly D on 5/1/2015.
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

    public abstract void init(Context context, AttributeSet attrs);

    public int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public float getDimension(int resId) {
        return getResources().getDimension(resId);
    }
}
