package com.devpaul.materiallibrary.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;

/**
 * Created by Pauly D on 3/8/2015.
 * Handles creation and drawing of ripples.
 */
public class RippleGenerator {

    /**
     * Holder for the view.
     */
    private View mView;

    /**
     * Holders for the color, the alpha and the effect color.
     */
    private int rippleColor, rippleAlpha, effectColor;

    /**
     * Minimm ripple alpha.
     */
    private static final int MIN_RIPPLE_ALPHA = 100;

    /**
     * Holders for a bunch of values.
     */
    private float clipRadius , mRadius, maxRippleRadius, touchX, touchY, endRippleRadius;

    /**
     * Holder for if touch even is outside the view.
     */
    private boolean isOutsideView;

    /**
     * Animation set for animating in series.
     */
    private AnimationSet animationSet;

    /**
     * Main paint.
     */
    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * Main path.
     */
    private Path circlePath = new Path();

    /**
     * Rectangle to draw in.
     */
    private RectF drawRect;

    /**
     * Create a new ripple generator for a passed in view. This is an injectable class that
     * automatically takes care of drawing ripples for you. All you need to do is pass in the
     * view, and set all the values for drawing the ripples.
     *
     * For example, say you have a custom view called MyView.java
     * <pre>
     * {@code
     * public class MyView extends View {
     *     private RippleGenerator ripGen;
     *     public MyView(Context) {
     *         super(context);
     *         init();
     *     }
     *
     *     public void init() {
     *          //note that these are dummy functions.
     *          float cx = getCenterX();
     *          float cy = getCenterY();
     *          float halfsize = getHalfSize();
     *
     *          ripGen = new RippleGenerator(this);
     *          ripGen.setRippleColor(ColorUtils.getDarkerColor(mButtonColor));
     *          ripGen.setClipRadius((int) halfsize);
     *          ripGen.setAnimationDuration(200);
     *          ripGen.setMaxRippleRadius((int) (0.75f*halfsize));
     *          ripGen.setBoundingRect(new RectF(cx - halfsize, cy-halfsize, cx + halfsize, cy + halfsize));
     *     }
     * }
     * }
     * </pre>
     * @param view the view to inject the ripples into.
     */
    public RippleGenerator(View view) {
        this.mView = view;
        animationSet = new AnimationSet(true);
        animationSet.setInterpolator(interpolator);
        setAnimationDuration(350);
        init();
    }

    /**
     * Set the duration of the animation.
     * @param duration duration in milliseconds.
     */
    public void setAnimationDuration(long duration) {
        this.animationSet.setDuration(duration);
    }

    /**
     * Set clip radius of the rectangle that will be drawn. If this isn't equal to the radius of
     * the ripples you won't get a circle.
     * @param clipRadius the clip radius in pixels.
     */
    public void setClipRadius(float clipRadius) {
        this.clipRadius = clipRadius;
    }

    /**
     * Sets the bounding rectangle of where you want to draw the ripples. This should be relative
     * to the view.
     * @param boundingRect the bounding rectangle.
     */
    public void setBoundingRect(RectF boundingRect) {
        this.drawRect = boundingRect;
    }

    /**
     * Set the maximum radius of the ripples.
     * @param rippleRadius the maximum radius in pixels.
     */
    public void setMaxRippleRadius(float rippleRadius) {
        this.maxRippleRadius = rippleRadius;
        this.endRippleRadius = rippleRadius*2.1f;
    }

    /**
     * Set the color of the ripples.
     * @param rippleColor the ripple color.
     */
    public void setRippleColor(int rippleColor) {
        this.rippleColor = ColorUtils.getNewColorAlpha(rippleColor, rippleAlpha);
        this.effectColor = rippleColor;
        circlePaint.setColor(rippleColor);
    }

    /**
     * Initialize and do the first drawing.
     */
    private void init() {
        rippleAlpha = MIN_RIPPLE_ALPHA;
        mRadius = 0.0f;
        drawRect = new RectF(0.0f, 0.0f, mView.getWidth(), mView.getHeight());
        isOutsideView = false;
        endRippleRadius = 0.0f;
    }

    /**
     * Call this in the views onDraw method.
     *
     * <pre>
     *     {@code
     *
     *     protected void OnDraw(Canvas canvas) {
     *         //draw stuff...
     *         ripGen.onDraw(canvas);
     *     }}
     * </pre>
     */
    public void onDraw(Canvas canvas) {
        if(isOutsideView) {
            circlePath.reset();
            circlePath.addRoundRect(drawRect, clipRadius, clipRadius, Path.Direction.CW);
            canvas.clipPath(circlePath);
            canvas.drawCircle(touchX, touchY, mRadius, circlePaint);
        }
    }

    /**
     * Inject this into the view onTouchEvent to handle the touch event.
     * @param event, {@code MotionEvent} from the custom view.
     */
    public boolean onTouchEvent(final MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
//                endRippleRadius = touchY > touchX ? Math.max(Math.abs(touchY - drawRect.top), Math.abs(touchY - drawRect.bottom))
//                        : Math.max(Math.abs(touchX - drawRect.left), Math.abs(touchX - drawRect.right));
//                endRippleRadius *= 2.5f;
                isOutsideView = drawRect.contains(touchX, touchY);
                startRipple();
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                finishRipple();
                return true;
        }
        return false;
    }

    private static OvershootInterpolator interpolator = new OvershootInterpolator();
    private static AccelerateDecelerateInterpolator accelDecel = new AccelerateDecelerateInterpolator();


    /**
     * Start the ripple.
     */
    private void startRipple() {
        rippleAlpha = MIN_RIPPLE_ALPHA;
        rippleColor = ColorUtils.getNewColorAlpha(effectColor, rippleAlpha);
        circlePaint.setColor(rippleColor);
        ValueGeneratorAnim anim = new ValueGeneratorAnim(new ValueGeneratorAnim.InterpolatedTimeCallback() {
            @Override
            public void onTimeUpdate(float interpolatedTime) {
                mRadius = maxRippleRadius*interpolatedTime;
                mView.invalidate();
            }
        });
        animationSet.cancel();
        animationSet.getAnimations().clear();
        animationSet.addAnimation(anim);
        mView.startAnimation(animationSet);
    }

    /**
     * Finish the ripple.
     */
    private void finishRipple() {
        ValueGeneratorAnim anim = new ValueGeneratorAnim(new ValueGeneratorAnim.InterpolatedTimeCallback() {
            @Override
            public void onTimeUpdate(float interpolatedTime) {
                float dif = 0.0f;
                if(mRadius < maxRippleRadius) {
                    dif = maxRippleRadius - mRadius;
                }
                mRadius = (endRippleRadius - maxRippleRadius - dif)*interpolatedTime + (maxRippleRadius - dif);
                rippleAlpha = (int) (MIN_RIPPLE_ALPHA * (1.0f - interpolatedTime));
                rippleColor = ColorUtils.getNewColorAlpha(effectColor, rippleAlpha);
                circlePaint.setColor(rippleColor);
                mView.invalidate();
            }
        });

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mView.performClick();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationSet.cancel();
        animationSet.getAnimations().clear();
        animationSet.addAnimation(anim);
        mView.startAnimation(animationSet);
    }
}
