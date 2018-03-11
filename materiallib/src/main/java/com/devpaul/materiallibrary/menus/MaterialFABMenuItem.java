package com.devpaul.materiallibrary.menus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.devpaul.materiallibrary.R;
import com.devpaul.materiallibrary.utils.ColorUtils;

/**
 * Created by Pauly D on 2/21/2015.
 */
public class MaterialFABMenuItem extends ImageButton {

    private int color, pressedColor, disabledColor;
    private int mDrawableSize;
    private int mIcon;
    private Drawable backgroundDrawable;

    public MaterialFABMenuItem(Context context) {
        super(context);
        init(context, null);
    }

    public MaterialFABMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MaterialFABMenuItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MaterialFABMenuItem);
        mIcon = array.getResourceId(R.styleable.MaterialFABMenuItem_mat_fab_item_icon, 0);
        array.recycle();

        color = getResources().getColor(android.R.color.holo_blue_dark);
        pressedColor = ColorUtils.getDarkerColor(color);
        disabledColor = ColorUtils.getLigherColor(color);
        mDrawableSize = (int) getResources().getDimension(R.dimen.material_item_size);
        updateDrawable();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mDrawableSize, mDrawableSize);
    }


    private void updateDrawable() {
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{
                getFillDrawable(),
                getIconDrawable()
        });

        int iconoffset = (int) (mDrawableSize  - getResources().getDimension(R.dimen.material_item_icon_size))/2;
        layerDrawable.setLayerInset(1, iconoffset, iconoffset, iconoffset, iconoffset);

        setBackgroundCompat(layerDrawable);
    }

    private void setBackgroundCompat(Drawable drawable) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }
    private Drawable getShapeDrawable(int color) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        final Paint fillPaint = shapeDrawable.getPaint();
        fillPaint.setAntiAlias(true);
        fillPaint.setColor(color);

        return shapeDrawable;
    }

    private Drawable getFillDrawable() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[] {-android.R.attr.state_enabled}, getShapeDrawable(disabledColor));
        stateListDrawable.addState(new int[] {android.R.attr.state_pressed}, getShapeDrawable(pressedColor));
        stateListDrawable.addState(new int[] {}, getShapeDrawable(color));

        return stateListDrawable;
    }

    public Drawable getIconDrawable() {
        if(backgroundDrawable != null) {
            return backgroundDrawable;
        } else if(mIcon != 0) {
            return getResources().getDrawable(mIcon);
        } else {
            return new ColorDrawable(Color.TRANSPARENT);
        }
    }
}
