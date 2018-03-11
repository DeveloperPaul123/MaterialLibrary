package com.devpaul.materiallibrary.menus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.devpaul.materiallibrary.R;
import com.devpaul.materiallibrary.abstracts.views.BaseCustomView;
import com.devpaul.materiallibrary.utils.ColorUtils;
import com.devpaul.materiallibrary.utils.SelectorGenerator;

/**
 * Menu item for this menu.
 */
public class LinearFabMenuItem extends BaseCustomView {

    /**
     * Icon resource id.
     */
    private int icon;

    /**
     * Bitmap that is drawn.
     */
    private Bitmap bitmap;

    /**
     * Color of the view.
     */
    private int mColor;

    /**
     * Pressed color of the view.
     */
    private int mPressedColor;

    /**
     * Diameter of the view.
     */
    private float diameter;

    /**
     * Drawing size of the icon.
     */
    private float iconSize;

    /**
     * Main background paint.
     */
    private Paint mainPaint;

    /**
     * Main bitmap paint.
     */
    private Paint bitmapPaint;

    /**
     * Rectangle containing the bitmap.
     */
    Rect bitmapRect;

    /**
     * Rectangle that is the drawing size of the bitmap.
     */
    RectF bitmapDrawRect;

    /**
     * Selector generator.
     */
    SelectorGenerator selectorGenerator;

    public LinearFabMenuItem(Context context) {
        super(context);
    }

    public LinearFabMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearFabMenuItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(Context context, AttributeSet attrs) {

        TypedArray array = null;
        try {
            array = context.obtainStyledAttributes(attrs, R.styleable.LinearFabMenuItem);
            icon = array.getResourceId(R.styleable.LinearFabMenuItem_lin_fab_item_icon, -1);
            mColor = array.getInt(R.styleable.LinearFabMenuItem_lin_fab_item_color, getColor(android.R.color.holo_blue_dark));
            mPressedColor = array.getInt(R.styleable.LinearFabMenuItem_lin_fab_item_pressed_color, ColorUtils.getDarkerColor(mColor));
        } finally {
            if(array != null) {
                array.recycle();
            }
        }

        diameter = getDimension(R.dimen.lin_fab_menu_item_diameter);
        iconSize = getDimension(R.dimen.lin_fab_menu_item_icon_size);

        if(icon != -1) {
            Drawable drawable = context.getResources().getDrawable(icon);
            if(drawable != null) {
                bitmap = Bitmap.createBitmap((int) iconSize, (int) iconSize, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                bitmapRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                bitmapDrawRect = new RectF(diameter/2 - iconSize/2, diameter/2 - iconSize/2,
                        diameter/2 + iconSize/2, diameter/2 + iconSize/2);
            }
        }
        mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainPaint.setStyle(Paint.Style.FILL);
        mainPaint.setColor(mColor);

        selectorGenerator = new SelectorGenerator(this);
        selectorGenerator.setNormalColor(mColor);
        selectorGenerator.setPressedColor(mPressedColor);

        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setFilterBitmap(true);
        bitmapPaint.setDither(true);
    }

    public float getDiameter() {
        return diameter;
    }
    public int getColor() {
        return this.mColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        selectorGenerator.onDraw(mainPaint);
        canvas.drawCircle(diameter/2, diameter/2, diameter/2, mainPaint);
        if(bitmap != null) {
            canvas.drawBitmap(bitmap, bitmapRect, bitmapDrawRect, bitmapPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) diameter, (int) diameter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return selectorGenerator.onTouchEvent(event);
    }

    /**
     * Sets the icon for this view.
     * @param iconId the id of the icon.
     */
    public void setIcon(int iconId) {
        icon = iconId;
        bitmap = BitmapFactory.decodeResource(getResources(), iconId);
        invalidate();
    }

    /**
     * Sets the color of the view.
     * @param newColor the new color.
     */
    public void setColor(int newColor) {
        this.mColor = newColor;
        this.mainPaint.setColor(mColor);
        this.selectorGenerator.setNormalColor(newColor);
        this.selectorGenerator.setPressedColor(ColorUtils.getDarkerColor(newColor));
        invalidate();
    }

    /**
     * Set the pressed color of the view.
     * @param newColor the new color.
     */
    public void setPressedColor(int newColor) {
        this.mPressedColor = newColor;
        this.selectorGenerator.setPressedColor(mPressedColor);
    }
}