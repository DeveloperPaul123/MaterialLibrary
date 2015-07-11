package com.materiallib.devpaul.materiallibrary.list;

/**
 * Created by Paul on 6/26/2015.
 */
public class ListItem {
    public ListItem() {

    }

    public ListItem(String text) {
        this.text = text;
    }
    private String text;
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return this.text;
    }
}
