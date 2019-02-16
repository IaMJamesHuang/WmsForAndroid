package com.kt.james.wmsforandroid.app.report;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.adapter.BaseRecycleViewAdapter;
import com.kt.james.wmsforandroid.base.adapter.BaseRecycleViewHolder;
import com.kt.james.wmsforandroid.databinding.ItemReportAllBinding;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

public class ReportAdapter extends BaseRecycleViewAdapter<ItemInfoBean> {

    private Context context;

    private int selectPos;

    public ReportAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BaseRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup, R.layout.item_report_all);
    }

    private class ViewHolder extends BaseRecycleViewHolder<ItemInfoBean, ItemReportAllBinding> {

        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(ItemInfoBean object, int position) {
            binding.setBean(object);
            if(position == selectPos) {
                binding.tvItemName.setTextColor(ResourceUtil.getColor(R.color.orange));
            } else {
                binding.tvItemName.setTextColor(ResourceUtil.getColor(R.color.colorTheme));
            }
            if (listener != null) {
                binding.getRoot().setOnClickListener((v) -> {
                    listener.onClick(object, position);
                });
            }
        }
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
        notifyDataSetChanged();
    }
}
