package com.devpaul.materiallibrary.behaviors;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;

import com.devpaul.materiallibrary.views.MaterialFloatingActionButton;

/**
 * Created by Paul on 7/9/2015.
 */
public class MaterialFabDefaultBehavior extends CoordinatorLayout.Behavior<MaterialFloatingActionButton> {

    public MaterialFabDefaultBehavior(Context context, AttributeSet attrs) {

    }

    public MaterialFabDefaultBehavior() {}

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, MaterialFloatingActionButton child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, MaterialFloatingActionButton child, View dependency) {
        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);
        return true;
    }
}
