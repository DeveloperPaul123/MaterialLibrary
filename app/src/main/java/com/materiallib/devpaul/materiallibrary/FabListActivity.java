package com.materiallib.devpaul.materiallibrary;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.devpaul.materiallibrary.abstracts.activities.BaseFabListActivity;
import com.materiallib.devpaul.materiallibrary.list.ListItem;
import com.materiallib.devpaul.materiallibrary.list.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 6/29/2015.
 */
public class FabListActivity extends BaseFabListActivity {


    @Override
    public void init() {
        List<ListItem> items = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            items.add(new ListItem("Item " + i));
        }

        MyAdapter adapter = new MyAdapter(this, items);
        getListView().setAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FabListActivity.this, "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActionButtonClicked() {
        Snackbar.make(getCoordinatorLayout(), "Button Clicked", Snackbar.LENGTH_LONG)
                .show();
    }
}
