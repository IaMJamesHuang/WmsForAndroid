package com.kt.james.wmsforandroid.databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kt.james.wmsforandroid.R;

public class BindingAdapterUtil {

    @BindingAdapter("android:displayCircle")
    public static void displayCircle(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .error(R.drawable.ic_avatar_default)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }

}
