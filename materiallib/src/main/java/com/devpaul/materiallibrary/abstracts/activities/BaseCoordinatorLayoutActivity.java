package com.devpaul.materiallibrary.abstracts.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;

import com.devpaul.materialfabmenu.R;

/**
 * Created by Paul on 7/9/2015.
 */
public abstract class BaseCoordinatorLayoutActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_library_base_coordinator_layout);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.material_library_base_coordinator);
    }

    /**
     * Perform initialization here. Note that this activity does not call init(). Needs to be called
     * at the end of onCreate in classes that inherit from this one.
     */
    public abstract void init();

    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }
}
