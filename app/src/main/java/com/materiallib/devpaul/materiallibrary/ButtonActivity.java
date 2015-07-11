package com.materiallib.devpaul.materiallibrary;

import com.devpaul.materiallibrary.abstracts.activities.BaseToolbarActivity;
import com.devpaul.materiallibrary.views.MaterialFlatButton;

/**
 * Created by Paul on 7/1/2015.
 */
public class ButtonActivity extends BaseToolbarActivity {

    MaterialFlatButton flatButton;
    @Override
    public int getLayoutContentId() {
        return R.layout.activity_button;
    }

    @Override
    public void init() {

    }
}
