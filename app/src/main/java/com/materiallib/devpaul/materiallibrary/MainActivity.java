package com.materiallib.devpaul.materiallibrary;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.devpaul.materiallibrary.abstracts.activities.BaseToolbarListActivity;
import com.materiallib.devpaul.materiallibrary.list.ListItem;
import com.materiallib.devpaul.materiallibrary.list.MyAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseToolbarListActivity {

    private static final String[] titles = new String[] {
            "Menus",
            "Buttons",
            "BaseToolbarActivity",
            "BaseToolbarListActivity",
            "BaseFabListActivity",
            "BaseQuickReturn",
            "BaseCollapsingToolbar with Recycler"
    };

    @Override
    public void init() {
        List<ListItem> items = new ArrayList<>();
        for(int i = 0; i < titles.length; i++) {
            ListItem item = new ListItem();
            item.setText(titles[i]);
            items.add(item);
        }

        MyAdapter adapter = new MyAdapter(MainActivity.this, items);
        getListView().setAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            //menus
                            Intent menuActivityIntent = new Intent(MainActivity.this, MenuActivity.class);
                            startActivity(menuActivityIntent);
                            break;
                        case 1:
                            //buttons
                            Intent buttonActivity = new Intent(MainActivity.this, ButtonActivity.class);
                            startActivity(buttonActivity);
                            break;
                        case 2:
                            //base toolbar activity
                            Intent baseToolbar = new Intent(MainActivity.this, BaseToolbarExample.class);
                            startActivity(baseToolbar);
                            break;
                        case 3:
                            //base toolbar list activity
                            Intent baseList = new Intent(MainActivity.this, BaseListActivity.class);
                            startActivity(baseList);
                            break;
                        case 4:
                            //base fab list activity
                            Intent intent = new Intent(MainActivity.this, FabListActivity.class);
                            startActivity(intent);
                            break;
                        case 5:
                            Intent baseQuick = new Intent(MainActivity.this, QuickReturnActivity.class);
                            startActivity(baseQuick);
                            break;
                        case 6:
                            Intent baseCollapse = new Intent(MainActivity.this, CollapsingToolbarRecycler.class);
                            startActivity(baseCollapse);
                            break;
                    }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
