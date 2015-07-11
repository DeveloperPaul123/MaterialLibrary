package com.materiallib.devpaul.materiallibrary;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devpaul.materiallibrary.abstracts.activities.BaseToolbarActivity;
import com.devpaul.materiallibrary.menus.LinearFabMenu;
import com.devpaul.materiallibrary.menus.LinearFabMenuItem;
import com.devpaul.materiallibrary.menus.MaterialFloatingActionButtonMenu;

/**
 * Created by Paul on 6/26/2015.
 */
public class MenuActivity extends BaseToolbarActivity {

    @Override
    public int getLayoutContentId() {
        return R.layout.activity_menu;
    }

    @Override
    public void init() {
        getToolbar().setTitleTextColor(getResources().getColor(android.R.color.white));

        LinearFabMenu linearFabMenu = (LinearFabMenu) findViewInContentById(R.id.linearFabMenu);
        linearFabMenu.setOnLinearMenuItemClickListener(new LinearFabMenu.OnLinearMenuItemClickListener() {
            @Override
            public void onItemClick(int position, int id, LinearFabMenuItem item) {
                switch(id) {
                    case R.id.linear_menu_item_add:
                        Toast.makeText(MenuActivity.this, "Add", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.linear_menu_item_delete:
                        Toast.makeText(MenuActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.linear_menu_item_info:
                        Toast.makeText(MenuActivity.this, "Info", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        MaterialFloatingActionButtonMenu materialFloatingActionButtonMenu =
                (MaterialFloatingActionButtonMenu) findViewInContentById(R.id.material_float_action_button_menu);
        materialFloatingActionButtonMenu.setMenuListener(new MaterialFloatingActionButtonMenu.MaterialFABMenuListener() {
            @Override
            public void onChildClicked(int position, int id, View child) {
                Log.i("Main", "Child clicked");
                switch (id) {
                    case R.id.mat_fab_disk:
                        Toast.makeText(MenuActivity.this, "Disk", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.mat_fab_item_delete:
                        Toast.makeText(MenuActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.mat_fab_item_play:
                        Toast.makeText(MenuActivity.this, "Play", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onExpanded() {
                Toast.makeText(MenuActivity.this, "Opened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCollapsed() {
                Toast.makeText(MenuActivity.this, "Closed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
