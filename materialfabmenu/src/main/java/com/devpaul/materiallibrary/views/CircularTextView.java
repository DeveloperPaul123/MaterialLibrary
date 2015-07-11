package com.devpaul.materiallibrary.views;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;

import com.devpaul.materialfabmenu.R;
import com.devpaul.materiallibrary.utils.ColorGenerator;

import java.util.Random;


/**
 * Created by Paul T on 11/13/2014.
 *
 * Simple circle with text in the middle. Just a single letter though.
 */
public class CircularTextView extends View {

    private Paint textPaint;
    private Paint backgroundPaint;
    private Paint checkMarkPaint;

    private ObjectAnimator textAnimator;
    private ArgbEvaluator evaluator;

    private int diameter;
    private int mColor;
    private int mSecondColor;

    private Path checkMarkPath;
    private float pathLength;

    private float mSize;
    private float mPadding;
    private float mTextSize;
    private float maxTextSize;
    private float oneDp;

    int[] colors;
    float cx, cy, textcx, textcy, dx;
    private String text;

    private boolean isBackShowing;
    private static OvershootInterpolator interpolator = new OvershootInterpolator();

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            toggle();
            performLongClick();
        }
    }

    private GestureDetector gestureDetector;

    public CircularTextView(Context context) {
        super(context);
        initialize(context, null);
    }

    public CircularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public CircularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    /**
     * Initialize the view.
     */
    private void initialize(Context context, AttributeSet attrs) {

        if(isInEditMode()) {
            text = "A";
            colors = new int[3];
            colors[0] = ColorGenerator.getRandomMaterialColor();
            colors[1] = ColorGenerator.getRandomMaterialColor();
            colors[2] = ColorGenerator.getRandomMaterialColor();
        } else {
            colors = new int[ColorGenerator.MATERIAL_COLORS_600.length];
            for(int i = 0; i < ColorGenerator.MATERIAL_COLORS_600.length; i++) {
                colors[i] = Color.parseColor(ColorGenerator.MATERIAL_COLORS_600[i]);
            }
        }

        mTextSize = getDimension(R.dimen.circular_text_size);
        maxTextSize = getDimension(R.dimen.circular_text_size);
        mSize = getDimension(R.dimen.circular_text_view_size);
        cx = mSize/2;
        cy = cx;
        mPadding = getDimension(R.dimen.circular_text_view_padding);
        oneDp = getDimension(R.dimen.circular_text_view_one_dp);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(mTextSize);
        textPaint.setColor(getResources().getColor(android.R.color.white));
        textPaint.setAntiAlias(true);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setStrokeWidth(1);
        textPaint.setTextAlign(Paint.Align.CENTER);

        checkMarkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        checkMarkPaint.setStrokeWidth(2*oneDp);
        checkMarkPaint.setStyle(Paint.Style.STROKE);
        checkMarkPaint.setColor(getResources().getColor(android.R.color.white));
        checkMarkPaint.setStrokeCap(Paint.Cap.ROUND);
        checkMarkPaint.setAntiAlias(true);

        mColor = colors[new Random().nextInt(colors.length)];
        mSecondColor = getResources().getColor(R.color.circular_text_view_second_color);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(mColor);
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        textAnimator = ObjectAnimator.ofFloat(this, "mTextSize", 0.0f, maxTextSize);
        textAnimator.setDuration(400);
        evaluator = new ArgbEvaluator();
        isBackShowing = false;

        gestureDetector = new GestureDetector(getContext(), new GestureListener());
    }

    /**
     * Sets the current text size.
     * @param textSize
     */
    private void setMTextSize(float textSize) {
        this.mTextSize = textSize;
        invalidate();
    }

    /**
     * Returns the current text size.
     * @return the size of the text.
     */
    private float getMTextSize() {
        return this.mTextSize;
    }

    /**
     * Set the text for the view. Can only be 1 letter.
     * @param text
     */
    public void setText(String text) {
        if(text.length() > 2) {
            this.text = text.substring(0, 2);
        } else {
            this.text = text;
        }
    }

    /**
     * Get the text for the view.
     * @return String, the text.
     */
    public String getText() {
        return this.text;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(text == null) {
            text = "A";
        }
        if(checkMarkPath == null) {
            checkMarkPath = getCheckMarkPath();
            PathMeasure measure = new PathMeasure(checkMarkPath, false);
            pathLength = measure.getLength();
        }
        if(text.length() != 1) {
            textPaint.setTextSize(mTextSize *0.87f);
        } else {
            textPaint.setTextSize(mTextSize);
        }
        canvas.drawCircle(cx, cy, diameter/2, backgroundPaint);
        canvas.drawText(text, cx, cy + mTextSize/3.0f, textPaint);
        if(isBackShowing) {
            canvas.save();
            canvas.translate(-2*oneDp, 4*oneDp);
            canvas.drawPath(checkMarkPath, checkMarkPaint);
            canvas.restore();
        }
    }

    /**
     * Returns a path that draws a check mark.
     * @return the check mark path.
     */
    private Path getCheckMarkPath() {
        Path path = new Path();
        float radius = diameter/2.00f;
        float radiusOne = radius * 0.35f;
        float radiusTwo = radius * 0.68f;
        float lineOneX = (float) (cx + radiusOne * Math.cos(Math.toRadians(-135.0)));
        float lineOneY = (float) (cy + radiusOne * Math.sin(Math.toRadians(-135.0)));
        float lineTwoX = (float) (cx + radiusTwo * Math.cos(Math.toRadians(-45.0)));
        float lineTwoY = (float) (cy + radiusTwo * Math.sin(Math.toRadians(-45.0)));
        path.moveTo(lineOneX, lineOneY);
        path.lineTo(cx, cy);
        path.lineTo(lineTwoX, lineTwoY);
        checkMarkPaint.setPathEffect(new DashPathEffect(new float[] {pathLength, pathLength},0.0f));
        return path;
    }

    /**
     * Draws the check mark after the view is switched.
     */
    private void drawCheckMark() {
        InterpolatedAnimation anim = new InterpolatedAnimation(new InterpolatedAnimationCallback() {
            @Override
            public void onTimeUpDate(float interpTime) {
                checkMarkPaint.setPathEffect(new DashPathEffect(new float[]
                        {pathLength, pathLength},(1.0f-interpTime)*pathLength));
                invalidate();
            }
        });
        clearAnimation();
        anim.setDuration(100);
        startAnimation(anim);
    }

    /**
     * Undraws the check mark before the view is switched.
     */
    private void undrawCheckMark() {
        InterpolatedAnimation anim = new InterpolatedAnimation(new InterpolatedAnimationCallback() {
            @Override
            public void onTimeUpDate(float interpTime) {
                checkMarkPaint.setPathEffect(new DashPathEffect(new float[]
                        {pathLength, pathLength},interpTime*pathLength));
                invalidate();
            }
        });
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                transitionToFront();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anim.setDuration(200);
        startAnimation(anim);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    /**
     * Toggles the switching of the view.
     */
    private void toggle() {
        clearAnimation();
        if(!isBackShowing) {
            transitionToBack();
        } else {
            undrawCheckMark();
        }
    }

    /**
     * Transitions to the front main view.
     */
    private void transitionToFront() {
        InterpolatedAnimation anim = new InterpolatedAnimation(new InterpolatedAnimationCallback() {
            @Override
            public void onTimeUpDate(float interpTime) {
                int newColor = (int) evaluator.evaluate(interpTime, mSecondColor, mColor);
                mTextSize = maxTextSize * interpTime;
                backgroundPaint.setColor(newColor);
                invalidate();
            }
        });
        isBackShowing = false;
        anim.setDuration(200);
        anim.setInterpolator(interpolator);
        startAnimation(anim);
    }

    /**
     * Transition view to back view.
     */
    private void transitionToBack() {
        InterpolatedAnimation anim = new InterpolatedAnimation(new InterpolatedAnimationCallback() {
            @Override
            public void onTimeUpDate(float interpTime) {
                int newColor = (int) evaluator.evaluate(interpTime, mColor, mSecondColor);
                mTextSize = maxTextSize* (1.0f - interpTime);
                backgroundPaint.setColor(newColor);
                invalidate();
            }
        });
        anim.setInterpolator(interpolator);
        anim.setDuration(200);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isBackShowing = true;
                drawCheckMark();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(anim);
    }

    /**
     * Returns a color given and resource ID.
     * @param resId, the resource ID.
     * @return the color.
     */
    private int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * Returns a dimension given a resource ID.
     * @param resId, the resource ID.
     * @return, the dimension.
     */
    private float getDimension(int resId) {
        return getResources().getDimension(resId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        diameter = (int) (mSize-mPadding);
        cx = (mSize)/2;
        cy = (mSize)/2;
        setMeasuredDimension((int) mSize, (int) mSize);
    }

    /**
     * Class that aids in interpolations for animations.
     */
    public class InterpolatedAnimation extends Animation {
        private InterpolatedAnimationCallback mCallback;
        public InterpolatedAnimation(InterpolatedAnimationCallback callback) {
            mCallback = callback;
        }
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if(mCallback != null) {
                mCallback.onTimeUpDate(interpolatedTime);
            }
        }

    }

    /**
     * Interface for the interpolation animation.
     */
    public interface InterpolatedAnimationCallback {
        /**
         * Gets the interpolated time.
         * @param interpTime, the time between 0 and 1 after it goes through the animation
         *                    interpolator.
         */
        public void onTimeUpDate(float interpTime);
    }
}
