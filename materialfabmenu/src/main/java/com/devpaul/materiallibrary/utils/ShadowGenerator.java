package com.devpaul.materiallibrary.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;

import com.devpaul.materialfabmenu.R;

/**
 * Created by Pauly D on 3/9/2015.
 */
public class ShadowGenerator {

    /**
     * The view that will have the shadow.
     */
    private View mView;

    /**
     * The paint to add the shadow layer to.
     */
    private Paint shadowPaint;

    /**
     * Shadow colors.
     */
    private int mShadowColor,shadowColor;

    /**
     * Mins and maxes.
     */
    private static final float MIN_ELEVATION = 0.0f;
    private static final float DEFAULT_ELEVATION = 0.2f;
    private static final float MAX_ELEVATION = 0.92f;
    private static final float MIN_SHADOW_ALPHA = 0.1f;
    private static final float MAX_SHADOW_ALPHA = 0.4f;

    /**
     * Various holders for values.
     */
    private float elevation, mShadowRadius, minShadowOffset, maxShadowOffset,
            mShadowOffset, maxShadowSize, minShawdowSize, mShadowAlpha;

    /**
     * Booleans for this view.
     */
    private boolean isFlat;
    private boolean shouldAnimate;

    /**
     * Animation set for changing the elevation of the view.
     */
    private AnimationSet animationSet;

    /**
     * Holder for the resources.
     */
    private Resources mResources;

    /**
     * Create a new shadow generator.
     * @param view the view that will have the shadow
     * @param paint the paint that is used to do the main drawing of your view.
     */
    public ShadowGenerator(View view, Paint paint) {
        this.mView = view;
        this.mResources = mView.getContext().getResources();
        this.shadowPaint = paint;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mView.setLayerType(View.LAYER_TYPE_SOFTWARE, shadowPaint);
        }
        animationSet = new AnimationSet(true);
        animationSet.setInterpolator(interpolator);
        maxShadowOffset = mResources.getDimension(R.dimen.mat_fab_shadow_offset) * 1.5f;
        minShadowOffset = maxShadowOffset / 1.5f;
        maxShadowSize = mResources.getDimension(R.dimen.mat_fab_shadow_max_radius);
        minShawdowSize = mResources.getDimension(R.dimen.mat_fab_shadow_min_radius) / 2;
        init();
    }

    /**
     * Set true if you want your view to animate when touched.
     * @param animate true if it should animate.
     */
    public void setShouldAnimate(boolean animate) {
        this.shouldAnimate = animate;
    }

    /**
     * The max offset of the shadow. This is the maximum amount of distance the shadow comes out
     * from underneath the view. The default should work fine.
     * @param maxShadowOffset the maximum offset in pixels.
     */
    public void setMaxShadowOffset(float maxShadowOffset) {
        this.maxShadowOffset = maxShadowOffset;
    }

    /**
     * The minimum shadow offset for when the view is in its normal state (not elevated). The
     * default should work fine.
     * @param minShadowOffset the minimum offset, in pixels.
     */
    public void setMinShadowOffset(float minShadowOffset) {
        this.minShadowOffset = minShadowOffset;
        this.mShadowOffset = minShadowOffset;
    }

    /**
     * The maximum size of the shadow. The default should work fine.
     * @param maxShadowSize the maximum size of the shadow in pixels.
     */
    public void setMaxShadowSize(float maxShadowSize) {
        this.maxShadowSize = maxShadowSize;
    }

    /**
     * The minimum size of the shadow. The default should work fine.
     * @param minShawdowSize minimum shadow size in pixels.
     */
    public void setMinShadowSize(float minShawdowSize) {
        this.minShawdowSize = minShawdowSize;
    }

    /**
     * Set the duration of the animation. The default should work fine.
     * @param duration the duration in milliseconds.
     */
    public void setAnimationDuration(long duration) {
        this.animationSet.setDuration(duration);
    }

    public float getMaxShadowOffset() {
        return maxShadowOffset;
    }

    public float getMaxShadowSize() {
        return maxShadowSize;
    }

    public float getMinShadowOffset() {
        return minShadowOffset;
    }

    public float getMinShawdowSize() {
        return minShawdowSize;
    }

    /**
     * Initialize and do the first drawing.
     */
    private void init() {
        mShadowColor = Color.BLACK;
        shadowColor = ColorUtils.getNewColorAlpha(mShadowColor, MIN_SHADOW_ALPHA);
        elevation = DEFAULT_ELEVATION;
        mShadowAlpha = (MAX_SHADOW_ALPHA - MIN_SHADOW_ALPHA)*(elevation/MAX_ELEVATION)  + MAX_SHADOW_ALPHA;
        mShadowRadius = (maxShadowSize - minShawdowSize)*(elevation/MAX_ELEVATION)  + minShawdowSize;
        mShadowOffset = (maxShadowOffset-minShadowOffset)*(elevation/MAX_ELEVATION) + minShadowOffset;
        shadowColor = ColorUtils.getNewColorAlpha(mShadowColor, mShadowAlpha);
        mView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                setElevation(elevation);
                mView.invalidate();
            }
            @Override
            public void onViewDetachedFromWindow(View v) {
            }
        });
        isFlat = false;
        shouldAnimate = true;
    }

    /**
     * Call this in the views onDraw method.
     * @param paint, the paint to draw with.
     */
    public void onDraw(Paint paint) {
        shadowPaint.setShadowLayer(mShadowRadius, 0.0f, mShadowOffset, shadowColor);
    }

    /**
     * Inject this into the views onTouchEvent to handle the touch event.
     * @param event the motion event
     */
    public boolean onTouchEvent(final MotionEvent event) {
        if(shouldAnimate) {
            int action = MotionEventCompat.getActionMasked(event);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    if(!isFlat)
                        elevate();
                    return true;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    lower();
                    return true;
            }
        }
        return false;
    }

    private static OvershootInterpolator interpolator = new OvershootInterpolator();
    private static AccelerateDecelerateInterpolator accelDecel = new AccelerateDecelerateInterpolator();

    /**
     * Handles the animation to flatten the view.
     */
    public void flatten() {
        ValueGeneratorAnim anim = new ValueGeneratorAnim(new ValueGeneratorAnim.InterpolatedTimeCallback() {
            @Override
            public void onTimeUpdate(float interpolatedTime) {
                ShadowGenerator.this.setFlattenElevation(1 - interpolatedTime);
            }
        });

        animationSet.cancel();
        animationSet.getAnimations().clear();
        animationSet.addAnimation(anim);
        mView.startAnimation(animationSet);
        isFlat = true;
    }

    /**
     * Handles the animation from flat back to normal.
     */
    public void unflatten() {
        ValueGeneratorAnim anim = new ValueGeneratorAnim(new ValueGeneratorAnim.InterpolatedTimeCallback() {
            @Override
            public void onTimeUpdate(float interpolatedTime) {
                ShadowGenerator.this.setFlattenElevation(interpolatedTime);
            }
        });
        anim.setDuration(100);
        ValueGeneratorAnim next = new ValueGeneratorAnim(new ValueGeneratorAnim.InterpolatedTimeCallback() {
            @Override
            public void onTimeUpdate(float interpolatedTime) {
                ShadowGenerator.this.setElevation(interpolatedTime);
            }
        });
        next.setDuration(110);
        next.setInterpolator(interpolator);

        ValueGeneratorAnim theEnd = new ValueGeneratorAnim(new ValueGeneratorAnim.InterpolatedTimeCallback() {
            @Override
            public void onTimeUpdate(float interpolatedTime) {
                ShadowGenerator.this.setElevation(1.0f-interpolatedTime);
            }
        });
        theEnd.setDuration(150);
        animationSet.cancel();
        animationSet.setInterpolator(accelDecel);
        animationSet.getAnimations().clear();
        animationSet.addAnimation(anim);
        animationSet.addAnimation(next);
        animationSet.addAnimation(theEnd);
        mView.startAnimation(animationSet);
        isFlat = false;
    }
    /**
     * Elevate the view.
     */
    private void elevate() {
        ValueGeneratorAnim anim = new ValueGeneratorAnim(new ValueGeneratorAnim.InterpolatedTimeCallback() {
            @Override
            public void onTimeUpdate(float interpolatedTime) {
                ShadowGenerator.this.setElevation(interpolatedTime);
            }
        });
        animationSet.cancel();
        animationSet.getAnimations().clear();
        animationSet.addAnimation(anim);
        mView.startAnimation(animationSet);
    }

    /**
     * Elevate the view.
     * @param listener animation listener for the animation.
     * @param duration Duration of the elevation animation.
     */
    private void elevate(Animation.AnimationListener listener, long duration) {
        ValueGeneratorAnim anim = new ValueGeneratorAnim(new ValueGeneratorAnim.InterpolatedTimeCallback() {
            @Override
            public void onTimeUpdate(float interpolatedTime) {
                ShadowGenerator.this.setElevation(interpolatedTime);
            }
        });
        anim.setAnimationListener(listener);
        anim.setDuration(duration);
        animationSet.cancel();
        animationSet.getAnimations().clear();
        animationSet.addAnimation(anim);
        mView.startAnimation(animationSet);
    }

    /**
     * Lower the view.
     */
    private void lower() {
        ValueGeneratorAnim anim = new ValueGeneratorAnim(new ValueGeneratorAnim.InterpolatedTimeCallback() {
            @Override
            public void onTimeUpdate(float interpolatedTime) {
                ShadowGenerator.this.setElevation(1.0f-interpolatedTime);
            }
        });
        animationSet.cancel();
        animationSet.getAnimations().clear();
        animationSet.addAnimation(anim);
        mView.startAnimation(animationSet);
    }

    private void setFlattenElevation(float interpolatedTime) {
        this.elevation = interpolatedTime;
        if(elevation > DEFAULT_ELEVATION) {
            elevation = DEFAULT_ELEVATION;
        }

        if(elevation > 0.0f) {
            this.mShadowAlpha =  (MAX_SHADOW_ALPHA - MIN_SHADOW_ALPHA)*(elevation/MAX_ELEVATION)  + MAX_SHADOW_ALPHA;
            this.mShadowRadius = (maxShadowSize - minShawdowSize)*(elevation/MAX_ELEVATION)  + minShawdowSize;
            this.mShadowOffset = (maxShadowOffset-minShadowOffset)*(elevation/MAX_ELEVATION) + minShadowOffset;
        } else {
            this.mShadowAlpha = 0.0f;
            this.mShadowRadius = 0.0f;
            this.mShadowOffset = 0.0f;
        }
        shadowColor = ColorUtils.getNewColorAlpha(mShadowColor, mShadowAlpha);
        mView.invalidate();
    }

    /**
     * Sets the elevation and changes the parameters of the shadow drawing.
     * @param interpolatedTime the new elevation.
     */
    private void setElevation(float interpolatedTime) {
        if(interpolatedTime == 0.0f) {
            interpolatedTime+=MIN_ELEVATION;
        } else if (interpolatedTime > MAX_ELEVATION) {
            interpolatedTime = MAX_ELEVATION;
        }
        this.elevation = interpolatedTime;
        this.mShadowAlpha = (MAX_SHADOW_ALPHA - MIN_SHADOW_ALPHA)*(elevation/MAX_ELEVATION)  + MAX_SHADOW_ALPHA;
        this.mShadowRadius = (maxShadowSize - minShawdowSize)*(elevation/MAX_ELEVATION)  + minShawdowSize;
        this.mShadowOffset = (maxShadowOffset-minShadowOffset)*(elevation/MAX_ELEVATION) + minShadowOffset;
        shadowColor = ColorUtils.getNewColorAlpha(mShadowColor, mShadowAlpha);
        mView.invalidate();
    }

    private float getElevation() {
        return this.elevation;
    }

}
