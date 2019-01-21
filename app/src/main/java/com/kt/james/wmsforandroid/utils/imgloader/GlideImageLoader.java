package com.kt.james.wmsforandroid.utils.imgloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kt.james.wmsforandroid.R;

public class GlideImageLoader implements ImageLoaderInterface<ImageView>{

    @Override
    public void displayImage(Context context, Object uri, ImageView imageView) {
        Glide.with(context).load(uri)
                .placeholder(R.drawable.shape_bg_loading)
                .error(R.drawable.shape_bg_loading)
                .crossFade(1000)
                .into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        return imageView;
    }
}
