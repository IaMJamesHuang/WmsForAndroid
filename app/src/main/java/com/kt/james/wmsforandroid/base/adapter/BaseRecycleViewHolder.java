package com.kt.james.wmsforandroid.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class BaseRecycleViewHolder<T, D extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public D binding;

    public BaseRecycleViewHolder(ViewGroup viewGroup, int layoutId) {
        super(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                layoutId, viewGroup, false).getRoot());
        binding = DataBindingUtil.getBinding(this.itemView);
    }

    /**
     * @param object   the data of bind
     * @param position the item position of recyclerView
     */
    public abstract void onBindViewHolder(T object, final int position);

    /**
     * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
     */
    void onBaseBindViewHolder(T object, final int position) {
        onBindViewHolder(object, position);
        binding.executePendingBindings();
    }

}
