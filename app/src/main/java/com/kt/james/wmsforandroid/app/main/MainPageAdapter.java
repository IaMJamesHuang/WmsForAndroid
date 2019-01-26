package com.kt.james.wmsforandroid.app.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.adapter.BaseRecycleViewAdapter;
import com.kt.james.wmsforandroid.base.adapter.BaseRecycleViewHolder;
import com.kt.james.wmsforandroid.databinding.ItemMainPageBinding;
import com.kt.james.wmsforandroid.utils.imgloader.ImageLoader;

public class MainPageAdapter extends BaseRecycleViewAdapter<MainPageBean> {

    private Context context;

    public MainPageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BaseRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(viewGroup, R.layout.item_main_page);
    }

    private class ViewHolder extends BaseRecycleViewHolder<MainPageBean, ItemMainPageBinding> {

        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(MainPageBean object, int position) {
            binding.setBean(object);
            if (listener != null ) {
                binding.getRoot().setOnClickListener((v)->{
                    listener.onClick(object, position);
                });
            }
            if (object != null) {
                ImageLoader.getInstance().displayImage(context, object.getIconImgId(), binding.ivIcon);
            }
        }
    }

}
