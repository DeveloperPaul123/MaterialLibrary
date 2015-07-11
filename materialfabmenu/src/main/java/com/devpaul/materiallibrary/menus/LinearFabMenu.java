package com.devpaul.materiallibrary.menus;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.devpaul.materialfabmenu.R;
import com.devpaul.materiallibrary.utils.ColorUtils;
import com.devpaul.materiallibrary.utils.RippleGenerator;
import com.devpaul.materiallibrary.utils.ScreenUtil;
import com.devpaul.materiallibrary.utils.ShadowGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pauly D on 4/28/2015.
 */
public class LinearFabMenu extends ViewGroup {

    /*
    Expand directions
     */
    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    /*
    Alignment options
     */
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    public static final int BOTTOM = 3;

    /*
    Size options
     */
    public static final int NORMAL = 0;
    public static final int MINI = 1;

    private Paint mainPaint;
    private RectF mainRectangle;
    private int mColor;

    private int mSize;
    private int expandDir;
    private int alignment;
    private int maxChildrenCount;

    private float rectWidth;
    private float MAX_WIDTH;
    private float MIN_WIDTH;
    private float MAX_HEIGHT;
    private float padding;
    private float minimumMenuSize;

    private boolean isExpanded;
    private boolean isExpanding;
    private boolean shouldCollapse;

    /*
    Animation stuff.
     */
    List<Animator> expands;
    List<Animator> collapses;
    private AnimatorSet showChildrenSet;
    private AnimatorSet hideChildrenSet;
    private ObjectAnimator widthAnimator;
    private static OvershootInterpolator overshootInterpolator = new OvershootInterpolator();

    /**
     * Generates a shadow for this view.
     */
    private ShadowGenerator shadowGenerator;

    /**
     * Private class that acts as the main button for the menu.
     */
    private RotatingPlusButton mainButton;

    private Rect homeRect;

    /**
     * Debug log tag.
     */
    private static final String LOGTAG = "LinearFabMenu";

    /**
     * Listener for menu item clicks.
     */
    private OnLinearMenuItemClickListener onLinearMenuItemClickListener;

    /**
     * On click listener for the menu items.
     */
    public static interface OnLinearMenuItemClickListener {
        public void onItemClick(int position, int id, LinearFabMenuItem item);
    }

    /**
     * Default constructor for a Linear Floating Action Button Menu.
     * @param context the context for the menue.
     */
    public LinearFabMenu(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * Constructor from XML
     * @param context the context for the menu.
     * @param attrs attribute set from XML.
     */
    public LinearFabMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * Initialize various things for the view.
     * @param context the context for the menu.
     * @param attrs attribute set from XML.
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray array = null;
        try {
            array = context.obtainStyledAttributes(attrs, R.styleable.LinearFabMenu);
            mColor = array.getColor(R.styleable.LinearFabMenu_lin_fab_color_normal, getColor(android.R.color.holo_blue_dark));
            expandDir = array.getInt(R.styleable.LinearFabMenu_lin_fab_exp_dir, HORIZONTAL);
            padding = array.getDimension(R.styleable.LinearFabMenu_lin_fab_item_padding, getDimension(R.dimen.lin_fab_menu_padding));
            if(expandDir == HORIZONTAL) {
                alignment = array.getInt(R.styleable.LinearFabMenu_lin_fab_align, RIGHT);
            } else {
                alignment = array.getInt(R.styleable.LinearFabMenu_lin_fab_align, BOTTOM);
            }

            mSize = array.getInt(R.styleable.LinearFabMenu_lin_fab_size, NORMAL);
        } finally {
            if(array != null) {
                array.recycle();
            }
        }
        expands = new ArrayList<>();
        collapses = new ArrayList<>();

        MIN_WIDTH = mSize == NORMAL ? getDimension(R.dimen.lin_fab_menu_size):getDimension(R.dimen.lin_fab_menu_mini_size);

        rectWidth = MIN_WIDTH;
        isExpanded = isInEditMode();
        isExpanding = false;

        widthAnimator = ObjectAnimator.ofFloat(this, "rectWidth", MAX_WIDTH);
        showChildrenSet = new AnimatorSet();
        hideChildrenSet = new AnimatorSet();

        mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainPaint.setStyle(Paint.Style.FILL);
        mainPaint.setColor(mColor);

        shadowGenerator = new ShadowGenerator(this, mainPaint);
        shadowGenerator.setAnimationDuration(200);
        shadowGenerator.setShouldAnimate(false);

        mainRectangle = new RectF(0.0f, 0.0f, MIN_WIDTH, MIN_WIDTH);
        homeRect = new Rect();

        shouldCollapse = true;

        setWillNotDraw(false);
        setClickable(false);
        setLongClickable(false);
        createMainButton(context);
    }

    /**
     * Returns a color given a resource id.
     * @param resId the resource ID of the color.
     * @return an int representing the color
     */
    private int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * Returns a dimension givien a resource ID.
     * @param resId the resource ID of the dimension.
     * @return a float representing the dimension.
     */
    private float getDimension(int resId) {
        return getResources().getDimension(resId);
    }

    /**
     * Set a menu item click listener for the menu.
     * @param listener the {@code OnLinearMenuItemClickListener} for this view.
     */
    public void setOnLinearMenuItemClickListener(OnLinearMenuItemClickListener listener) {
        this.onLinearMenuItemClickListener = listener;
        setChildClickListeners();
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        throw new IllegalStateException("The menu is not clickable. Please use a " +
                "OnLinearMenuItemClickListener to listen for child events.");
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        throw  new IllegalStateException("The menu is not long clickable. Please use a " +
                "OnLinearMenuItemClickListener to listen for child events.");
    }

    /**
     * Set whether or not the menu should collapse when a child is clicked. If this is set to false
     * it is your responsibility to close the menu by calling {@code toggle()}
     * @param shouldCollapse, should or shouldn't collapse. True by default.
     */
    public void setCollapseOnChildClick(boolean shouldCollapse) {
        this.shouldCollapse = shouldCollapse;
    }

    /**
     * Sets the on click listeners for all the children except the main button.
     */
    public void setChildClickListeners() {
        final int count = getChildCount();
        for(int i = 0; i < count; i++) {
            final View v = getChildAt(i);
            if(v instanceof RotatingPlusButton) {
                continue;
            } else if(v instanceof LinearFabMenuItem) {
                v.setTag(i);
                v.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isOpen()) {
                            if(onLinearMenuItemClickListener != null) {
                                onLinearMenuItemClickListener.onItemClick((int) view.getTag(),
                                        view.getId(), (LinearFabMenuItem) view);
                                if(shouldCollapse) {
                                    if(mainButton != null) {
                                        mainButton.toggle();
                                    }
                                    toggle();
                                }
                            }
                        }
                    }
                });
            } else{
                throw new IllegalStateException("Children can only be LinearFabMenuItem objects.");
            }

        }
    }
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    /**
     * Creates the main button.
     * @param context the context of the menu.
     */
    private void createMainButton(Context context) {
        mainButton = new RotatingPlusButton(context);
        mainButton.setColor(LinearFabMenu.this.mColor);
        mainButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
                ((RotatingPlusButton) view).toggle();
            }
        });
        addView(mainButton, super.generateDefaultLayoutParams());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        shadowGenerator.onDraw(mainPaint);
        measureAndSetRectangle();
        canvas.drawRoundRect(mainRectangle, MIN_WIDTH/2, MIN_WIDTH/2, mainPaint);

    }

    /**
     * Setter for the main menu animator.
     * @param newWidth the new width of the rectangle.
     */
    public void setRectWidth(float newWidth) {
        this.rectWidth = newWidth;
        invalidate();
    }

    /**
     * Getter for the main menu animator.
     * @return the current rectWidth.
     */
    public float getRectWidth() {
        return this.rectWidth;
    }

    /**
     * Sets up child animations.
     */
    private void setUpAnimations() {
        if(expands.size() > 0 && collapses.size() > 0) {
            showChildrenSet.playSequentially(expands);
            hideChildrenSet.playSequentially(collapses);
        }
    }

    /**
     * Toggles the menu open and closed. It handles checking its current state so you don't!
     */
    public void toggle() {
        if(!isExpanding) {
            clearAnimation();
            if(isExpanded) {
                switch (expandDir) {
                    case HORIZONTAL:
                        widthAnimator.setFloatValues(MIN_WIDTH);
                        break;
                    case VERTICAL:
                        widthAnimator.setFloatValues(MIN_WIDTH);
                        break;
                }
                widthAnimator.setDuration(200);
                widthAnimator.removeAllListeners();
                widthAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isExpanded = false;
                        isExpanding = false;
                        requestLayout();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                hideChildrenSet.removeAllListeners();
                hideChildrenSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        widthAnimator.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                hideChildrenSet.start();

            } else {
                switch (expandDir) {
                    case HORIZONTAL:
                        widthAnimator.setFloatValues(getWidth()-2*padding);
                        break;
                    case VERTICAL:
                        widthAnimator.setFloatValues(getHeight()-2*padding);
                        break;
                }
                widthAnimator.setDuration(200);
                widthAnimator.removeAllListeners();
                widthAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isExpanding = false;
                        isExpanded = true;
                        showChildrenSet.removeAllListeners();
                        showChildrenSet.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                requestLayout();
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                        showChildrenSet.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                widthAnimator.start();
            }
            isExpanding = true;
        }
    }

    /**
     * Sets the main rectangle dimensions.
     */
    private void measureAndSetRectangle() {
        switch (expandDir) {
            case HORIZONTAL:
                switch (alignment) {
                    case LEFT:
                        mainRectangle.set(padding, padding, rectWidth+padding, MIN_WIDTH+padding);
                        break;
                    case RIGHT:
                        mainRectangle.set(getWidth()- rectWidth - padding, padding, getWidth()-padding, padding + MIN_WIDTH);
                        break;
                    default:
                        break;
                }
                break;
            case VERTICAL:
                switch (alignment) {
                    case TOP:
                        mainRectangle.set(padding, padding, getWidth()-padding, rectWidth + padding);
                        break;
                    case BOTTOM:
                        mainRectangle.set(padding, getHeight()-rectWidth - padding, getWidth()-padding, (float) getHeight() - padding);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        expands.clear();
        collapses.clear();

        final int childCount = getChildCount();
        int curX = 0;
        int curY = 0;
        for(int i = 0; i < childCount; i++) {
            final View v = getChildAt(i);
            final int childWidth = v.getMeasuredWidth();
            final int childHeight = v.getMeasuredHeight();
            if(v instanceof RotatingPlusButton) {
                v.layout(homeRect.left, homeRect.top, homeRect.right, homeRect.bottom);
            } else {
                switch (alignment) {
                    case RIGHT:
                        //figure out the start position.
                        curX = (int) padding + ((int) padding + childWidth) * i;
                        int right = curX + childWidth;
                        int top = (int) padding;
                        int bottom = getHeight() - (int) padding;
                        if(i < maxChildrenCount)
                        v.layout(curX, top, right, bottom);
                        break;
                    case LEFT:
                        //figure out starting position, children should go right to left.
                        curX = getWidth() - (int) padding - ((int) padding + childWidth) * i;
                        int left =  curX - childWidth;
                        int topTwo = (int) padding;
                        int bottomTwo = getHeight() - (int) padding;
                        if(i < maxChildrenCount)
                        v.layout(left, topTwo, curX, bottomTwo);
                        break;
                    case TOP:
                        //starting position, children laid out bottom to top.
                        curY = getHeight() - (int) padding - ((int) padding + childHeight) *i;
                        top = curY - childHeight;
                        left = (int) padding;
                        right = (int) padding + childWidth;
                        if(i < maxChildrenCount)
                        v.layout(left, top, right, curY);
                        break;
                    case BOTTOM:
                        //starting position , children laid out top to bottom.
                        curY = (int) padding + ((int) padding + childHeight) * i;
                        bottom = curY + childHeight;
                        left = (int) padding;
                        right = (int) padding + childWidth;
                        if(i < maxChildrenCount)
                        v.layout(left, curY, right, bottom);
                        break;
                    default:
                        break;
                }
                if(!isExpanding) {
                    LinearFabMenuLayoutParams layoutParams = (LinearFabMenuLayoutParams) v.getLayoutParams();
                    layoutParams.setViewTarget(v);
                }
                v.setScaleX(isOpen() ? 1:0);
                v.setScaleY(isOpen() ? 1:0);
            }
        }
        setUpAnimations();
        invalidate();
    }

    /**
     * Checks whether the menu is open or not.
     * @return a boolean, true if it is open.
     */
    public boolean isOpen() {
        return (isExpanded && !isExpanding);
    }

    @Override
    protected void onFinishInflate() {
        bringChildToFront(mainButton);
        super.onFinishInflate();
    }

    /**
     * Returns the bounding rectangle of the main button.
     * @return the bounding rectangle.
     */
    private Rect getHomeRect() {
        return homeRect;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View parent = getRootView();
        MAX_WIDTH = Math.min(ScreenUtil.getScreenWidthInPixels(getContext()), parent.getMeasuredWidth());
        MAX_HEIGHT = Math.min(ScreenUtil.getScreenHeightInPixels(getContext()),
                ScreenUtil.getRootLayoutHeightInPixels(getContext()));

        //measure children.
        final int count = getChildCount();
        for(int i = 0; i < count; i++) {
            final View v = getChildAt(i);
            measureChild(v, widthMeasureSpec, heightMeasureSpec);
        }
        //figure out size.
        calculateMaxChildrenAndMinimumSize();
        int height = 0;
        int width = 0;
        switch (expandDir) {
            case HORIZONTAL:
                width  = (int) Math.min(MAX_WIDTH, minimumMenuSize);
                height = (int) (MIN_WIDTH + 2 * padding);
                break;
            case VERTICAL:
                width  = (int) (MIN_WIDTH + 2*padding);
                height = (int) Math.min(MAX_HEIGHT, minimumMenuSize);
                break;
        }
        //figure out where the main button goes.
        switch (alignment) {
            case TOP:
                homeRect.set((int) padding, (int) padding, width - (int) padding, (int) padding + (int) MIN_WIDTH);
                break;
            case BOTTOM:
                homeRect.set((int) padding, height-(int) padding-(int) MIN_WIDTH, width - (int) padding, height - (int) padding);
                break;
            case RIGHT:
                homeRect.set(width - (int) padding - (int) MIN_WIDTH, (int) padding, width-(int) padding, height-(int) padding);
                break;
            case LEFT:
                homeRect.set((int) padding, (int) padding, (int) padding + (int) MIN_WIDTH, (int) padding + (int) MIN_WIDTH);
                break;
        }
        setMeasuredDimension(width, height);
    }

    /**
     * Calculates the maximum number of children for the view and the minumum size needed to fit
     * all the children appropriately.
     */
    private void calculateMaxChildrenAndMinimumSize() {
        switch (expandDir) {
            case HORIZONTAL:
                float maxMenuWidth = MAX_WIDTH-2.0f*padding;
                maxChildrenCount = (int) (maxMenuWidth/(MIN_WIDTH+padding));
                break;
            case VERTICAL:
                float maxMenuHeight = MAX_HEIGHT-2.0f*padding;
                maxChildrenCount = (int) (maxMenuHeight/(MIN_WIDTH+padding));
                break;
        }
        //account for one child being the main button.
        maxChildrenCount -=1;

        minimumMenuSize =0;
        minimumMenuSize += 2*padding;
        final int children = getChildCount();
        for(int i = 0; i < children; i++) {
            switch (expandDir) {
                case HORIZONTAL:
                    minimumMenuSize += (getChildAt(i).getWidth()+padding);
                    break;
                case VERTICAL:
                    minimumMenuSize += (getChildAt(i).getHeight()+padding);
                    break;
            }
        }
        minimumMenuSize+=mainButton.getHeight();
    }

    /**
     * Interpolator.
     */
    private static OvershootInterpolator interpolator = new OvershootInterpolator();

    /**
     * Custom layout params for this menu.
     */
    public class LinearFabMenuLayoutParams extends LayoutParams {

        private ObjectAnimator expandAnimator;
        private ObjectAnimator collapseAnimator;

        private PropertyValuesHolder xScaleIn = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f);
        private PropertyValuesHolder yScaleIn = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f);
        private PropertyValuesHolder xScaleOut = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.0f);
        private PropertyValuesHolder yScaleOut = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.0f);

        private boolean animationsSet;

        public LinearFabMenuLayoutParams(LayoutParams source) {
            super(source);

        }

        public void setViewTarget(View view) {
            expandAnimator = ObjectAnimator.ofPropertyValuesHolder(view, xScaleIn, yScaleIn);
            collapseAnimator = ObjectAnimator.ofPropertyValuesHolder(view, xScaleOut, yScaleOut);
            expandAnimator.setDuration(120);
            collapseAnimator.setDuration(120);
            if(!animationsSet) {
                expands.add(expandAnimator);
                collapses.add(collapseAnimator);

                animationsSet = true;
            }
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LinearFabMenuLayoutParams(super.generateDefaultLayoutParams());
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new LinearFabMenuLayoutParams(super.generateLayoutParams(p));
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LinearFabMenuLayoutParams(super.generateLayoutParams(attrs));
    }

    /**
     * Main button for the menu.
     */
    private class RotatingPlusButton extends LinearFabMenuItem {

        private Paint circlePaint;
        private Paint bitmapPaint;
        private Bitmap bitmap;

        private float iconSize;
        private float rotation;
        private boolean isRotated;
        private ObjectAnimator rotationAnimator;

        private Rect bitmapRect;
        private RectF bitmapDrawRect;

        private static final float MAX_ROTATION = -45.0f;
        private RippleGenerator rippleGenerator;

        public RotatingPlusButton(Context context) {
            super(context);
        }

        public RotatingPlusButton(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public RotatingPlusButton(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public void setRotation(float rotation) {
            this.rotation = rotation;
            invalidate();
        }

        public float getRotation() {
            return this.rotation;
        }

        @Override
        public void init(Context context, AttributeSet attrs) {
            super.init(context, attrs);
            circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            circlePaint.setColor(getColor(android.R.color.holo_blue_dark));
            circlePaint.setStyle(Paint.Style.FILL);
            circlePaint.setAlpha(255);

            bitmapPaint = new Paint();
            bitmapPaint.setAntiAlias(true);
            bitmapPaint.setFilterBitmap(true);
            bitmapPaint.setDither(true);

            rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", MAX_ROTATION);
            rotationAnimator.setInterpolator(overshootInterpolator);

            rotation = 0.0f;
            iconSize = getDimension(R.dimen.lin_fab_menu_item_icon_size);
            bitmap = getDefaulBitmap();
            bitmapRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            bitmapDrawRect = new RectF(0.0f, 0.0f, iconSize, iconSize);

            rippleGenerator = new RippleGenerator(this);
            rippleGenerator.setRippleColor(ColorUtils.getDarkerColor(mColor));
            rippleGenerator.setBoundingRect(new RectF(0.0f, 0.0f, getDiameter(), getDiameter()));
            rippleGenerator.setClipRadius((int) getDiameter()/2);
            rippleGenerator.setMaxRippleRadius((int) (0.75 * getDiameter()/2));

        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            float halfBitmapSize = iconSize/2;
            float cx = getDiameter()/2;
            float cy = getDiameter()/2;
            bitmapDrawRect.set(cx - halfBitmapSize, cy - halfBitmapSize, cx + halfBitmapSize, cy + halfBitmapSize);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(getDiameter()/2, getDiameter()/2, getDiameter()/2, circlePaint);
            canvas.save();
            canvas.rotate(rotation, getDiameter()/2, getDiameter()/2);
            canvas.drawBitmap(bitmap, bitmapRect, bitmapDrawRect, bitmapPaint);
            canvas.restore();
            rippleGenerator.onDraw(canvas);
        }

        /**
         * Returns a default bitmap for the button. This is a plus.
         * @return the default bitmap.
         */
        private Bitmap getDefaulBitmap() {
            Bitmap bitmap = Bitmap.createBitmap((int) iconSize, (int) iconSize, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.TRANSPARENT);
            Canvas canvas = new Canvas(bitmap);
            float oneDp = getDimension(R.dimen.mat_fab_single_dp);
            int halfWidthStart = (int) (canvas.getWidth()/2 - oneDp);
            int halfWidthEnd = (int) (canvas.getWidth()/2 + oneDp);
            Rect rect = new Rect(halfWidthStart, (int) oneDp*3,
                    halfWidthEnd, (int) (canvas.getHeight() - oneDp*3));
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha(255);
            canvas.drawRect(rect, paint);
            rect.set((int) (oneDp*3), (int) (canvas.getHeight()/2 - oneDp),
                    (int) (canvas.getWidth() - (oneDp*3)), (int) (canvas.getHeight()/2 + oneDp));
            canvas.drawRect(rect, paint);
            return bitmap;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            return rippleGenerator.onTouchEvent(event);
        }

        /**
         * Toggle the button. This rotates the plus.
         */
        public void toggle() {
            if(isRotated) {
                clearAnimation();
                rotationAnimator.cancel();
                rotationAnimator.setFloatValues(0.0f);
                rotationAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isRotated = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                rotationAnimator.start();
            } else {
                clearAnimation();
                rotationAnimator.cancel();
                rotationAnimator.setFloatValues(MAX_ROTATION);
                rotationAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isRotated = true;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                rotationAnimator.start();
            }
        }
    }

}
