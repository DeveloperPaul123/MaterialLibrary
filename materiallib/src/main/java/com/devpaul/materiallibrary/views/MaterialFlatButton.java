package com.devpaul.materiallibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devpaul.materialfabmenu.R;
import com.devpaul.materiallibrary.abstracts.BaseViewGroup;
import com.devpaul.materiallibrary.utils.ColorUtils;
import com.devpaul.materiallibrary.utils.ShadowRippleGenerator;

/**
 * Created by Paul on 6/30/2015.
 *
 * This is a material flat button. Can be raised or flat.
 */
public class MaterialFlatButton extends BaseViewGroup {

    /**
     * Measurement holders.
     */
    private float padding;
    private float minWidth;
    private float buttonHeight;
    private float viewHeight;
    private float clipRadius;
    private float width;

    /**
     * Button color.
     */
    private int color;

    /**
     * Set if the button should be flat or not.
     */
    private boolean isFlat;

    /**
     * Text view that holds the text shown.
     */
    private TextView textView;

    /**
     * Holder for the current text.
     */
    private String text;

    /**
     * Generates the ripples and shadow.
     */
    private ShadowRippleGenerator shadowRippleGenerator;

    /**
     * Main paint for drawing the view.
     */
    private Paint mainPaint;

    /**
     * The rectagle that is drawn for the button.
     */
    private RectF drawRect;

    /**
     * This is a flat button based on Material Design guidelines. It can be made to have a shadow
     * or to lay flat and has a ripple effect.
     * @param context the context from the parent.
     */
    public MaterialFlatButton(Context context) {
            super(context);
    }

    public MaterialFlatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialFlatButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void init(Context context, AttributeSet attrs) {

        String text = "Button";
        TypedArray a = null;
        if(attrs != null) {
            try {
                a = context.obtainStyledAttributes(attrs, R.styleable.MaterialFlatButton);
                String temp = a.getString(R.styleable.MaterialFlatButton_mat_flat_button_text);
                isFlat = a.getBoolean(R.styleable.MaterialFlatButton_mat_flat_button_is_flat, false);
                color = a.getColor(R.styleable.MaterialFlatButton_mat_flat_button_color, getColor(R.color.material_deep_teal_500));
                if(temp != null) text = temp;
            } finally {
                if(a != null) {
                    a.recycle();
                }
            }
        } else {
            isFlat = false;
            color = getColor(R.color.material_deep_teal_500);
        }

        padding = getDimension(R.dimen.material_flat_button_padding);
        buttonHeight = getDimension(R.dimen.material_flat_button_draw_height);
        viewHeight = getDimension(R.dimen.material_flat_button_height) + padding;
        minWidth = getDimension(R.dimen.material_flat_button_min_width);
        clipRadius = getDimension(R.dimen.material_flat_button_clip_radius); //2dp

        drawRect = new RectF(padding, padding/2, minWidth+padding, buttonHeight + padding/2);

        mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainPaint.setColor(color);
        mainPaint.setStyle(Paint.Style.FILL);

        shadowRippleGenerator = new ShadowRippleGenerator(this, mainPaint);
        shadowRippleGenerator.setAnimationDuration(200);
        shadowRippleGenerator.setBoundingRect(drawRect);
        shadowRippleGenerator.setClipRadius(clipRadius);
        shadowRippleGenerator.setRippleColor(ColorUtils.getDarkerColor(color));
        shadowRippleGenerator.setMaxRippleRadius(minWidth / 2);
        shadowRippleGenerator.setMaxShadowOffset(clipRadius / 2);
        shadowRippleGenerator.setMaxShadowSize(clipRadius*2);
        createMainTextView(context, text);

        setWillNotDraw(false);
    }

    /**
     * Creates the main text view for this view.
     * @param context the context.
     * @param text text for the textview.
     */
    public void createMainTextView(Context context, String text) {
        textView = new TextView(context);
        ViewGroup.LayoutParams params = generateDefaultLayoutParams();
        params.height = LayoutParams.WRAP_CONTENT;
        params.width = LayoutParams.WRAP_CONTENT;
        textView.setLayoutParams(params);
        textView.setAllCaps(true);
        textView.setText(text);
        textView.setTextColor(ColorUtils.getContrastColor(color));
        addView(textView, this.generateDefaultLayoutParams());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //layout the text view.
        float cx = width/2;
        float cy = viewHeight/2;
        int childCount = getChildCount();
        if(childCount > 1) {
            throw new IllegalStateException("This is not meant to be used as a layout, do not put" +
                    "any children in this view group");
        }

        for(int i = 0; i < childCount; i++) {
            final View v = getChildAt(i);
            final float childWidth = v.getMeasuredWidth();
            final float childHeight = v.getMeasuredHeight();
            final float halfWidth = childWidth/2;
            final float halfHeight = childHeight/2;
            v.layout((int) (cx - halfWidth), (int) (cy - halfHeight),
                    (int) (cx + halfWidth), (int) (cy + halfHeight));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        float requiredWidth = padding*4 + textView.getWidth();
        float testWidth = minWidth + (2*padding);
        width = requiredWidth > testWidth ? requiredWidth : testWidth;
        float dif = Math.abs(buttonHeight - viewHeight)/2;
        drawRect.set(padding, dif, width-padding, buttonHeight+dif);
        setMeasuredDimension((int) width, (int) viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!isFlat)shadowRippleGenerator.onDrawShadow(mainPaint);
        canvas.drawRoundRect(drawRect, clipRadius, clipRadius, mainPaint);
        shadowRippleGenerator.onDrawRipple(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return shadowRippleGenerator.onTouchEvent(event);
    }

    /**
     * Set the text of this button.
     * @param text the new text.
     */
    public void setText(String text) {
        this.text = text;
        this.textView.setText(text);
        invalidate();
    }

    /**
     * Get the current text of this button.
     * @return String the current text.
     */
    public String getText() {
        return text;
    }
}
