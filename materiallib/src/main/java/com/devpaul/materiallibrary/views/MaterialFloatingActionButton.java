package com.devpaul.materiallibrary.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.devpaul.materialfabmenu.R;
import com.devpaul.materiallibrary.behaviors.FloatingActionButtonBehavior;
import com.devpaul.materiallibrary.utils.ColorUtils;
import com.devpaul.materiallibrary.utils.ShadowRippleGenerator;
import com.devpaul.materiallibrary.utils.ShadowSelectorGenerator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Paul T. on 2/28/2015.
 *
 * This class is a floating action button view based on the design guidelines from Google.
 * @deprecated Use the implementation of the floating action button in the Android Design Library
 * from Google.
 */
@Deprecated
public class MaterialFloatingActionButton extends View {
    public static final int SIZE_NORMAL = 0;
    public static final int SIZE_MINI = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ SIZE_NORMAL, SIZE_MINI })
    public @interface FAB_SIZE {
    }

    private Paint mButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint bitmapPaint;
    private float mSize, cx, cy, buttonSize;
    private int mButtonColor, mButtonPressedColor;

    boolean useSelector = false;

    private ShadowRippleGenerator shadowRippleGenerator;
    private ShadowSelectorGenerator shadowSelectorGenerator;
    private Bitmap iconBitmap;
    private BitmapDrawable drawable;
    private Rect bitmapRect;
    private RectF bitmapDrawRect;
    private float iconSize;

    private float rotation;
    private static final float MAX_ROTATION = -45.0f;
    private ObjectAnimator rotationAnimator;

    @DrawableRes
    private int mIcon;

    @CoordinatorLayout.DefaultBehavior(FloatingActionButtonBehavior.class)

    /**
     * Default constructor.
     * @param context context for the button.
     */
    public MaterialFloatingActionButton(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * Constructor from XML
     * @param context context for the button.
     * @param attrs attribute set from xml
     */
    public MaterialFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * Lollipop constructor.
     * @param context context for the button.
     * @param attrs attribute set for the button.
     * @param defStyleAttr style attribute.
     */
    public MaterialFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * Initialize various things for this view.
     * @param context the context passed from the constructor.
     * @param attributeSet attributeSet passed from the constructor.
     */
    private void init(Context context, AttributeSet attributeSet) {
        int size = 0;

        //try to first deduce the accent color.
        int accentColor = -1;
        TypedArray a = null;
        try {
            TypedValue typedValue = new TypedValue();
            a = context.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorAccent });
            accentColor = a.getColor(0, -1);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } finally {
            if(a != null) {
                a.recycle();
            }
        }

        //read the attributes.
        TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.MaterialFloatingActionButton, 0, 0);
        mButtonColor = attr.getColor(R.styleable.MaterialFloatingActionButton_mat_fab_colorNormal,
                accentColor == -1 ? getColor(android.R.color.holo_blue_dark):accentColor);
        mButtonPressedColor = attr.getColor(R.styleable.MaterialFloatingActionButton_mat_fab_colorPressed,
                ColorUtils.getDarkerColor(mButtonColor));
        mIcon = attr.getResourceId(R.styleable.MaterialFloatingActionButton_mat_fab_icon, 0);
        useSelector = attr.getBoolean(R.styleable.MaterialFloatingActionButton_mat_fab_use_selector, false);
        size = attr.getInt(R.styleable.MaterialFloatingActionButton_mat_fab_size, 0);
        attr.recycle();

        iconSize = getDimension(R.dimen.mat_fab_icon_size);

        if(mIcon != 0) {
            iconBitmap = BitmapFactory.decodeResource(getResources(), mIcon);
            drawable = new BitmapDrawable(getResources(), iconBitmap);
            drawable.setAntiAlias(true);
            bitmapRect = new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());
            bitmapDrawRect = new RectF(0.0f, 0.0f, iconSize, iconSize);
        } else {
            iconBitmap = getDefaulBitmap();
            bitmapRect = new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());
            bitmapDrawRect = new RectF(0.0f, 0.0f, iconSize, iconSize);
        }

        float maxShadowOffset = getDimension(R.dimen.mat_fab_shadow_offset) * 1.5f;
        float minShadowOffset = maxShadowOffset / 1.5f;
        float maxShadowSize = getDimension(R.dimen.mat_fab_shadow_max_radius);
        float minShawdowSize = getDimension(R.dimen.mat_fab_shadow_min_radius) / 2;

        buttonSize = size == 0 ? getDimension(R.dimen.mat_fab_normal_size):getDimension(R.dimen.mat_fab_mini_size);
        mSize = buttonSize + maxShadowSize * 4 + maxShadowOffset * 3;

        cx = mSize/2;
        cy = mSize/2;

        rotation = 0;
        rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", MAX_ROTATION);
        rotationAnimator.setDuration(200);
        rotationAnimator.setInterpolator(new OvershootInterpolator());

        float halfsize = buttonSize/2;
        float halfBitmapSize = iconSize/2;
        bitmapDrawRect.set(cx - halfBitmapSize, cy - halfBitmapSize, cx + halfBitmapSize, cy + halfBitmapSize);

        mButtonPaint.setStyle(Paint.Style.FILL);
        mButtonPaint.setColor(mButtonColor);

        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setFilterBitmap(true);
        bitmapPaint.setDither(true);

        shadowRippleGenerator = new ShadowRippleGenerator(this, mButtonPaint);
        shadowRippleGenerator.setRippleColor(ColorUtils.getDarkerColor(mButtonColor));
        shadowRippleGenerator.setClipRadius((int) buttonSize/2);
        shadowRippleGenerator.setAnimationDuration(200);
        shadowRippleGenerator.setMaxRippleRadius((int) (0.75f*buttonSize/2));
        shadowRippleGenerator.setBoundingRect(new RectF(cx - halfsize, cy-halfsize, cx + halfsize, cy + halfsize));

        shadowSelectorGenerator = new ShadowSelectorGenerator(this, mButtonPaint);
        shadowSelectorGenerator.setNormalColor(mButtonColor);
        shadowSelectorGenerator.setPressedColor(mButtonPressedColor);
        shadowSelectorGenerator.setAnimationDuration(200);
        shadowSelectorGenerator.setShadowLimits(minShadowOffset, maxShadowOffset, minShawdowSize, maxShadowSize);

        invalidate();
    }

    /**
     * Set whether to use s selector generator instead of a ripple generator. This means that when
     * clicked the button will change colors instead of having a ripple effect.
     * @param selector true to use a selector. Default is false.
     */
    public void setUseSelector(boolean selector) {
        this.useSelector = selector;
    }


    /**
     * Setter used by the object animator.
     * @param newRotation the new rotation value.
     */
    public void setRotation(float newRotation) {
        this.rotation = newRotation;
        invalidate();
    }

    /**
     * Gets the current rotation.
     * @return the current rotation.
     */
    public float getRotation() {
        return this.rotation;
    }

    /**
     * Rotates the icon of the view only.
     */
    public void rotate() {
        clearAnimation();
        if(rotation == MAX_ROTATION) {
            rotationAnimator.setFloatValues(0.0f);
            rotationAnimator.start();
        } else {
            rotationAnimator.setFloatValues(MAX_ROTATION);
            rotationAnimator.start();
        }
    }

    /**
     * Rotates only the icon of the view.
     * @param listener an animator listener for the view.
     */
    public void rotate(Animator.AnimatorListener listener) {
        clearAnimation();
        rotationAnimator.removeAllListeners();
        rotationAnimator.addListener(listener);
        if(rotation == MAX_ROTATION) {
            rotationAnimator.setFloatValues(0.0f);
            rotationAnimator.start();
        } else {
            rotationAnimator.setFloatValues(MAX_ROTATION);
            rotationAnimator.start();
        }
    }

    /**
     * Generates the default bitmap for the button. Default is a plus button.
     * @return the default bitmap.
     */
    private Bitmap getDefaulBitmap() {
        Bitmap bitmap = Bitmap.createBitmap((int) iconSize, (int) iconSize, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(bitmap);
        float oneDp = getDimension(R.dimen.mat_fab_single_dp);
        int halfWidthStart = (int) (canvas.getWidth()/2 - oneDp);
        int halfWidthEnd = (int) (canvas.getWidth()/2 + oneDp);
        Rect rect = new Rect(halfWidthStart, (int) oneDp*4,
                halfWidthEnd, (int) (canvas.getHeight() - oneDp*4));
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(255);
        canvas.drawRect(rect, paint);
        rect.set((int) (oneDp*4), (int) (canvas.getHeight()/2 - oneDp),
                (int) (canvas.getWidth() - (oneDp*4)), (int) (canvas.getHeight()/2 + oneDp));
        canvas.drawRect(rect, paint);
        return bitmap;
    }

    /**
     * Returns a color from a resource id.
     * @param resId, the resource id.
     * @return the color.
     */
    private int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * Returns a dimension given its resource id.
     * @param resId the resource id.
     * @return a float representing the dimension.
     */
    private float getDimension(int resId) {
        return getResources().getDimension(resId);
    }

    /**
     * Set the button's normal color.
     * @param color the normal color.
     */
    public void setButtonColor(int color) {
        this.mButtonColor = color;
        this.mButtonPaint.setColor(mButtonColor);
        invalidate();
    }

    /**
     * Set the button's pressed color.
     * @param color the color when pressed.
     */
    public void setButtonPressedColor(int color) {
        this.mButtonPressedColor = color;
        this.shadowSelectorGenerator.setPressedColor(mButtonPressedColor);
    }

    /**
     * Return the size of the view.
     * @return the size.
     */
    public float getSize() {
        return mSize;
    }

    /**
     * Set the icon for this view. Default is a plus.
     * @param icon the icon resource id.
     */
    public void setIcon(@DrawableRes int icon) {
        if (mIcon != icon) {
            mIcon = icon;
        }
    }

    /**
     * Flatten the button. This makes the shadow go away.
     */
    public void flatten() {
        shadowRippleGenerator.flatten();
    }

    /**
     * Undo the flattening action and make the shadow reappear.
     */
    public void unflatten() {
        shadowRippleGenerator.unflatten();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        if(!useSelector) shadowRippleGenerator.onDrawShadow(mButtonPaint);
        canvas.drawCircle(cx, cy, buttonSize/2, mButtonPaint);
        canvas.save();
        canvas.rotate(rotation, cx, cy);
        if(iconBitmap != null) canvas.drawBitmap(iconBitmap, bitmapRect, bitmapDrawRect, bitmapPaint);
        canvas.restore();
        if(useSelector) {
            shadowSelectorGenerator.onDraw(mButtonPaint);
        } else {
            shadowRippleGenerator.onDrawRipple(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(useSelector) {
            return shadowSelectorGenerator.onTouchEvent(event);
        } else {
            return shadowRippleGenerator.onTouchEvent(event);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) mSize, (int) mSize);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void setBackgroundCompat(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

}
