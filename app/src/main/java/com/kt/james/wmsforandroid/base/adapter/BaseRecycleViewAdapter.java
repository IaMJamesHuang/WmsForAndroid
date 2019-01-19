package com.kt.james.wmsforandroid.base.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<BaseRecycleViewHolder> {

    protected List<T> data = new ArrayList<>();
    protected OnItemClickListener<T> listener;
    protected OnItemLongClickListener onItemLongClickListener;

    @Override
    public void onBindViewHolder(@NonNull BaseRecycleViewHolder baseRecycleViewHolder, int i) {
        baseRecycleViewHolder.onBindViewHolder(data.get(i), i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<T> getData() {
        return data;
    }

    public void addAll(List<T> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void add(T object) {
        data.add(object);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void remove(T object) {
        data.remove(object);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    public void removeAll(List<T> data) {
        this.data.retainAll(data);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

}
