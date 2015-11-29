package com.materiallib.devpaul.materiallibrary;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.devpaul.materiallibrary.abstracts.activities.BaseRecyclerFabActivity;
import com.materiallib.devpaul.materiallibrary.list.ListItem;
import com.materiallib.devpaul.materiallibrary.list.SimpleRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 10/11/2015.
 */
public class ToolbarRecyclerActivity extends BaseRecyclerFabActivity {
    @Override
    public void onActionButtonClicked() {
        Snackbar.make(getCoordinatorLayout(), "Button Clicked", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void init() {
        //just make some simple list items.
        List<ListItem> items = new ArrayList<>();
        for(int i = 0; i < 45; i++) {
            items.add(new ListItem("Item " + i));
        }
        SimpleRecyclerViewAdapter adapter = new SimpleRecyclerViewAdapter(this, items);
        getRecyclerView().setAdapter(adapter);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }
}
