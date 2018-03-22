package com.devpaul.materiallibrary.abstracts.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.devpaul.materiallibrary.R;

/**
 * Created by Paul on 6/20/2015.
 *
 * Abstract activity that contains a toolbar and a list view.
 */
public abstract class BaseToolbarListActivity extends BaseToolbarActivity {

    /**
     * List view for this activity.
     */
    private ListView listView;

    /**
     * Toolbar of the activity.
     */
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = (ListView) findViewInContentById(R.id.material_library_base_list_view);
    }

    @Override
    public int getLayoutContentId() {
        return R.layout.material_library_list_view;
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
