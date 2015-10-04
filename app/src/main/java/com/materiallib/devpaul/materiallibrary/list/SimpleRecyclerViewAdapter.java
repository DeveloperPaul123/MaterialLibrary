package com.materiallib.devpaul.materiallibrary.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devpaul.materiallibrary.views.CircularTextView;
import com.materiallib.devpaul.materiallibrary.R;

import java.util.List;

/**
 * Created by Paul on 7/9/2015.
 */
public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter {

    public static class Viewholder extends RecyclerView.ViewHolder{
        public TextView textView;
        public CircularTextView circularTextView;

        public Viewholder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.mat_lib_demo_text_view);
            circularTextView = (CircularTextView) itemView.findViewById(R.id.mat_lib_demo_circle_text);
        }
    }

    private List<ListItem> items;
    private Context context;

    public SimpleRecyclerViewAdapter(Context context, List<ListItem> items) {
        this.items = items;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ListItem item = getItem(i);
        Viewholder holder = (Viewholder) viewHolder;
        holder.textView.setText(item.getText());
        holder.circularTextView.setText("" + i);
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ListItem getItem(int position) {
        return items.get(position);
    }

}
