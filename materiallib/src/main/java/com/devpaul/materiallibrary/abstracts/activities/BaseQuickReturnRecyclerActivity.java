package com.devpaul.materiallibrary.abstracts.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.devpaul.materialfabmenu.R;

/**
 * Created by Paul on 7/9/2015.
 */
public abstract class BaseQuickReturnRecyclerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_library_base_quick_return_toolbar_recycler_activity);
        toolbar = (Toolbar) findViewById(R.id.material_library_base_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.material_library_recycler_view);
        setSupportActionBar(toolbar);
        init();
    }

    /**
     * Returns the recycler view for this activity.
     * @return the recycler view.
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * Returns the current toolbar for this activity that is acting as the action bar.
     * @return the toolbar for this activity.
     */
    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * Perform your initialization here. This is called right after onCreate.
     */
    public abstract void init();
}

