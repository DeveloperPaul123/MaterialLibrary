package com.materiallib.devpaul.materiallibrary;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.devpaul.materiallibrary.abstracts.activities.BaseCollapsingToolbarActivity;
import com.materiallib.devpaul.materiallibrary.list.ListItem;
import com.materiallib.devpaul.materiallibrary.list.SimpleRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 7/9/2015.
 */
public class CollapsingToolbarRecycler extends BaseCollapsingToolbarActivity {
    @Override
    public void init() {
        List<ListItem> items = new ArrayList<>();
        for(int i = 0; i < 45; i++) {
            items.add(new ListItem("Item " + i));
        }
        getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        SimpleRecyclerViewAdapter adapter = new SimpleRecyclerViewAdapter(this, items);
        getRecyclerView().setAdapter(adapter);
        getCollapsingToolbarLayout().setTitle(getToolbar().getTitle());
        getCollapsingToolbarLayout().setExpandedTitleColor(getResources().getColor(android.R.color.white));
    }
}
