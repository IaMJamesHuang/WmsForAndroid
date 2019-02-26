package com.kt.james.wmsforandroid.app.replenish;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.adapter.BaseRecycleViewAdapter;
import com.kt.james.wmsforandroid.base.adapter.BaseRecycleViewHolder;
import com.kt.james.wmsforandroid.databinding.ItemReplenishBinding;

public class ReplenishAdapter extends BaseRecycleViewAdapter<ReplenishBean.ReplenishItem> {

    private Context context;

    public ReplenishAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BaseRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup, R.layout.item_replenish);
    }

    public class ViewHolder extends BaseRecycleViewHolder<ReplenishBean.ReplenishItem, ItemReplenishBinding> {

        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(ReplenishBean.ReplenishItem object, int position) {
            binding.setBean(object);
        }
    }

}
