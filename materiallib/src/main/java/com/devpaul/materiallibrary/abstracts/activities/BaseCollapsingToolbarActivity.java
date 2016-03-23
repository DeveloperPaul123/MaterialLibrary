package com.devpaul.materiallibrary.abstracts.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.devpaul.materialfabmenu.R;

/**
 * Created by Paul on 7/9/2015.
 */
public abstract class BaseCollapsingToolbarActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_library_base_collapsing_toolbar_activity);
        toolbar = (Toolbar) findViewById(R.id.material_library_base_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.material_library_recycler_view);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.material_library_collapsing_toolbar);
        setSupportActionBar(toolbar);
        init();
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
}
