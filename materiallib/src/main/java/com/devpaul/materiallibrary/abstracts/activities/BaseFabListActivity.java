package com.devpaul.materiallibrary.abstracts.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.devpaul.materialfabmenu.R;
import com.devpaul.materiallibrary.abstracts.AbstractScrollListener;
import com.devpaul.materiallibrary.behaviors.FloatingActionButtonBehavior;

/**
 * Created by Paul on 6/21/2015.
 *
 * This is a basic list activity for you to use that includes a floating action button
 * already.
 */
public abstract class BaseFabListActivity extends BaseToolbarListActivity {

    private FloatingActionButton materialFloatingActionButton;
    private static OvershootInterpolator interpolator = new OvershootInterpolator();
    private boolean isShowing;
    private CoordinatorLayout coordinatorLayout;
    private static final int fab_margin = 16;

    /**
     * Scroll listener for the list view.
     */
    private class ScrollListener extends AbstractScrollListener {

        @Override
        public void onScrollUp() {
            toggleButton(false);
        }

        @Override
        public void onScrollDown() {
            toggleButton(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout.LayoutParams params = new FrameLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;

        coordinatorLayout = new CoordinatorLayout(this);
        coordinatorLayout.setLayoutParams(params);

        CoordinatorLayout.LayoutParams coorLayoutParams = new CoordinatorLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
//        coorLayoutParams.setBehavior(new FloatingActionButtonBehavior());
        coorLayoutParams.gravity = Gravity.BOTTOM|Gravity.END;
        coorLayoutParams.setMargins(fab_margin, fab_margin, fab_margin, fab_margin);
        materialFloatingActionButton = new FloatingActionButton(this);
        materialFloatingActionButton.setLayoutParams(coorLayoutParams);
        coordinatorLayout.addView(materialFloatingActionButton);

        getHomeLayout().addView(coordinatorLayout);
        ScrollListener scrollListener = new ScrollListener();
        scrollListener.setListView(getListView());
        scrollListener.setScrollThreshold(getResources().getDimensionPixelOffset(R.dimen.material_library_scroll_threshold));

        getListView().setOnScrollListener(scrollListener);

        materialFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionButtonClicked();
            }
        });

        isShowing = true;

    }

    /**
     * Returns the coordinator layout containing the floating action button.
     * @return the fab containing coordinator layout.
     */
    public CoordinatorLayout getCoordinatorLayout() {
        return this.coordinatorLayout;
    }

    /**
     * Toggles the material floating action button.
     * @param visible
     */
    public void toggleButton(final boolean visible) {
        if (isShowing != visible) {
            isShowing = visible;
            int height = materialFloatingActionButton.getHeight() + fab_margin;
            if (height == 0) {
                ViewTreeObserver vto = materialFloatingActionButton.getViewTreeObserver();
                if (vto.isAlive()) {
                    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ViewTreeObserver currentVto = materialFloatingActionButton.getViewTreeObserver();
                            if (currentVto.isAlive()) {
                                currentVto.removeOnPreDrawListener(this);
                            }
                            toggleButton(visible);
                            return true;
                        }
                    });
                    return;
                }
            }
            int translationY = visible ? 0 : height;
            materialFloatingActionButton.animate().setInterpolator(interpolator)
                    .setDuration(500)
                    .translationY(translationY);

            // On pre-Honeycomb a translated view is still clickable, so we need to disable clicks manually
            materialFloatingActionButton.setClickable(visible);
        }
    }

    /**
     * This is called when the floating action button is clicked.
     */
    public abstract void onActionButtonClicked();
}
