package com.materiallib.devpaul.materiallibrary.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devpaul.materiallibrary.views.CircularTextView;
import com.materiallib.devpaul.materiallibrary.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ListItem> items;

    public class ViewHolder {
        public CircularTextView circularTextView;
        public TextView textView;
    }

    public MyAdapter(Context context, List<ListItem> itemList) {
        inflater = LayoutInflater.from(context);
        items = itemList;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ListItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.circularTextView = (CircularTextView) convertView.findViewById(R.id.mat_lib_demo_circle_text);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.mat_lib_demo_text_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(items.get(position).getText());
        viewHolder.circularTextView.setText(items.get(position).getText().contains(" ") ? String.valueOf(position):
            items.get(position).getText());
        return convertView;
    }
}