package com.devpaul.materiallibrary.abstracts.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.devpaul.materialfabmenu.R;

/**
 * Created by Paul on 6/20/2015.
 */
public abstract class BaseToolbarListActivity extends AppCompatActivity {

    /**
     * List view for this activity.
     */
    private ListView listView;

    /**
     * Toolbar of the activity.
     */
    private Toolbar toolbar;

    /**
     * Content container.
     */
    private FrameLayout homeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_library_base_toolbar_activity);
        toolbar = (Toolbar) findViewById(R.id.material_library_base_toolbar);
        setSupportActionBar(toolbar);
        homeLayout = (FrameLayout) findViewById(R.id.material_library_base_activity_content);
        listView = new ListView(this);
        listView.setId(android.R.id.list);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(params);
        homeLayout.addView(listView);
        init();
    }


    /**
     * Perform you initialization here. This is called after on create.
     */
    public abstract void init();

    /**
     * Gets the home layout of this activity.
     * @return The home FrameLayout of the content.
     */
    public FrameLayout getHomeLayout() {
        return this.homeLayout;
    }

    /**
     * Gets the current list view.
     * @return the current list view.
     */
    public ListView getListView() {
        return this.listView;
    }

    /**
     * Returns the current toolbar.
     * @return the Toolbar.
     */
    public Toolbar getToolbar() {
        return this.toolbar;
    }
}
