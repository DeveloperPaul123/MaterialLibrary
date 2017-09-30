package com.devpaul.materiallibrary.abstracts.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.devpaul.materialfabmenu.R;

/**
 * Created by Paul on 6/13/2015.
 */
public abstract class BaseToolbarActivity extends AppCompatActivity {

    /**
     *  The home view provided by the developer.
     */
    private View homeView;

    /**
     * The toolbar for this activity.
     */
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_library_base_toolbar_activity);
        toolbar = (Toolbar) findViewById(R.id.material_library_base_toolbar);
        setSupportActionBar(toolbar);
        FrameLayout layout = (FrameLayout) findViewById(R.id.material_library_base_activity_content);
        homeView = getLayoutInflater().inflate(getLayoutContentId(), layout, false);
        layout.addView(homeView);
        init();
    }

    /**
     * Return the id of the layout that is the content of your activity.
     * @return the id of your layout.
     */
    public abstract int getLayoutContentId();

    /**
     * Perform you initialization here. This is called after on create.
     */
    public abstract void init();

    /**
     * Use this instead of findViewById. This method will search the layout resource file that you
     * provide in {@code getLayoutContentId}
     * @param id the id of the view to search for.
     * @return the found view if it exists.
     */
    public View findViewInContentById(int id) {
        return homeView.findViewById(id);
    }

    /**
     * Returns the current toolbar.
     * @return the current toolbar.
     */
    public Toolbar getToolbar() {
        return this.toolbar;
    }

}
