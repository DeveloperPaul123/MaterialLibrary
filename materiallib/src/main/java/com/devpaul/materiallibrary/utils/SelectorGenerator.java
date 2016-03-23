package com.devpaul.materiallibrary.utils;

import android.animation.ArgbEvaluator;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

/**
 * This class is injectable into custom views and view groups.
 */
public class SelectorGenerator {

    /**
     * Evaluator for the color change.
     */
    ArgbEvaluator evaluator;

    /**
     * Colors and paint color holders.
     */
    private int mNormalColor, mPressedColor, paintColor;

    /**
     * Holder for the current view.
     */
    private View mView;

    /**
     * Sets if the view is long clickable.
     */
    private boolean isLongClickable;

    /**
     * Sets if the view is clickable. True by default.
     */
    private boolean isClickable;

    /**
     * Press time for a long click.
     */
    private static final long LONG_PRESS_TIME = 1200;

    /**
     * Duration of the animation.
     */
    private static long ANIM_DURATION = 150;

    /**
     * Class that generates a selector for buttons and other views.
     * @param view the view to apply to.
     */
    public SelectorGenerator(View view) {
        this.mView = view;
        this.evaluator = new ArgbEvaluator();
        this.isLongClickable = false;
        this.isClickable = true;
    }

    /**
     * Set the normal color for the selector.
     * @param color the color for the normal state.
     */
    public void setNormalColor(int color) {
        this.mNormalColor = color;
    }

    /**
     * Set the pressed color of the selector.
     * @param color the color for the pressed state.
     */
    public void setPressedColor(int color) {
        this.mPressedColor = color;
    }

    /**
     * This method needs to be injected into the view's onDraw method.
     * @param paint, the {@code Paint} that is used to draw the view.
     */
    public void onDraw(Paint paint) {
        paint.setColor(paintColor);
    }

    /**
     * This method needs to be injected into the view's onTouchEvent method.
     * @param event, the {@code MotionEvent} of the view.
     */
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if(isLongClickable) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mView.performLongClick();
                        }
                    }, LONG_PRESS_TIME);
                }
                onDown();
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if(isClickable) {
                    mView.performClick();
                }
                onUp();
                return true;
        }
        return false;
    }

    /**
     * Handles the down animation.
     */
    private void onDown() {
        ValueGeneratorAnim anim = new ValueGeneratorAnim(new ValueGeneratorAnim.InterpolatedTimeCallback() {
            @Override
            public void onTimeUpdate(float interpolatedTime) {
                paintColor = (int) evaluator.evaluate(interpolatedTime, mNormalColor, mPressedColor);
                mView.invalidate();
            }
        });
        mView.clearAnimation();
        anim.setDuration(ANIM_DURATION);
        mView.startAnimation(anim);
    }

    /**
     * Handles the up animation.
     */
    private void onUp() {
        ValueGeneratorAnim anim = new ValueGeneratorAnim(new ValueGeneratorAnim.InterpolatedTimeCallback() {
            @Override
            public void onTimeUpdate(float interpolatedTime) {
                paintColor = (int) evaluator.evaluate(interpolatedTime, mPressedColor, mNormalColor);
                mView.invalidate();
            }
        });
        mView.clearAnimation();
        anim.setDuration(ANIM_DURATION);
        mView.startAnimation(anim);
    }
}
