package com.devpaul.materiallibrary.abstracts.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;

import com.devpaul.materialfabmenu.R;
import com.devpaul.materiallibrary.views.MaterialFloatingActionButton;

/**
 * Created by Paul on 10/4/2015.
 */
public abstract class BaseCollapsingToolbarActivityWithFAB extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private static OvershootInterpolator interpolator = new OvershootInterpolator();
    private FloatingActionButton matFab;
    private boolean isShowing;
    private AppBarLayout appBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_library_base_collapsing_toolbar_layout_with_fab);
        toolbar = (Toolbar) findViewById(R.id.material_library_base_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.material_library_recycler_view);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.material_library_collapsing_toolbar);
        matFab = (FloatingActionButton) findViewById(R.id.material_library_floating_action_button);
        appBarLayout = (AppBarLayout) findViewById(R.id.material_library_app_bar_layout);
        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float toolbarHeight = BaseCollapsingToolbarActivityWithFAB.this.getResources()
                        .getDimension(R.dimen.material_library_collapsing_toolbar_height);
                float matfabHeight = matFab.getHeight() * 2;
                if (Math.abs(verticalOffset) < Math.abs(toolbarHeight - matfabHeight) && isShowing) {
                    toggleButton(false);
                } else if (!isShowing && Math.abs(verticalOffset) > Math.abs(toolbarHeight - matfabHeight)) {
                    toggleButton(true);
                }
            }
        });
        isShowing = true;

        matFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionButtonClicked();
            }
        });

        init();
    }

    public void setFloatingActionButtonAlignLeft(boolean alignLeft) {
        if(alignLeft) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) matFab.getLayoutParams();
            params.anchorGravity = Gravity.BOTTOM|Gravity.LEFT|Gravity.START;
            matFab.setLayoutParams(params);
        } else {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) matFab.getLayoutParams();
            params.anchorGravity = Gravity.BOTTOM|Gravity.RIGHT|Gravity.END;
            matFab.setLayoutParams(params);
        }
    }
    /**
     * Toggles the material floating action button.
     * @param visible
     */
    private void toggleButton(final boolean visible) {
        if (isShowing != visible) {
            isShowing = visible;
            int height = matFab.getHeight();
            if (height == 0) {
                ViewTreeObserver vto = matFab.getViewTreeObserver();
                if (vto.isAlive()) {
                    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ViewTreeObserver currentVto = matFab.getViewTreeObserver();
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
            int scale = visible ? 0 : 1;
            matFab.animate()
                    .setDuration(200)
                    .scaleX(scale)
                    .scaleY(scale);

            // On pre-Honeycomb a translated view is still clickable, so we need to disable clicks manually
            matFab.setClickable(visible);
        }
    }

    /**
     * Get the current toolbar that is the action bar.
     * @return the toolbar.
     */
    public Toolbar getToolbar() {
        return this.toolbar;
    }

    /**
     * Returns the recycler view for this activity.
     * @return
     */
    public RecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    /**
     * Returns the collapsing toolbar layout enclosing the toolbar in this activity.
     * @return the collapsing toolbar layout.s
     */
    public CollapsingToolbarLayout getCollapsingToolbarLayout() {
        return this.collapsingToolbarLayout;
    }

    /**
     * Perform your initialization here. This is called right after onCreate
     */
    public abstract void init();

    /**
     * This is called when the floating action button is clicked.
     */
    public abstract void onActionButtonClicked();
}
