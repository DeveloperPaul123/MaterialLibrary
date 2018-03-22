package com.materiallib.devpaul.materiallibrary;

import com.devpaul.materiallibrary.abstracts.activities.BaseToolbarListActivity;
import com.materiallib.devpaul.materiallibrary.list.ListItem;
import com.materiallib.devpaul.materiallibrary.list.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 6/29/2015.
 */
public class BaseListActivity extends BaseToolbarListActivity {
    @Override
    public void initialize() {
        List<ListItem> items = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            items.add(new ListItem("Item " + i));
        }
        MyAdapter adapter = new MyAdapter(BaseListActivity.this, items);
        getListView().setAdapter(adapter);
    }
}
