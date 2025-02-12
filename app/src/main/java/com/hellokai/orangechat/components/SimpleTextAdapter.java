package com.hellokai.orangechat.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;

    // Constructor
    public SimpleTextAdapter(List<String> data, Context context) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }

    // ViewHolder class
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = mData.get(position);
        holder.textView.setText(item);
        android.util.Log.i("hellokaisimple", "onBindViewHolder: " + item);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

