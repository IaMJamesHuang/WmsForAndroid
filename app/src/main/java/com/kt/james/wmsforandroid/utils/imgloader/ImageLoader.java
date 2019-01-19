package com.kt.james.wmsforandroid.utils.imgloader;

import android.content.Context;
import android.view.View;

public class ImageLoader implements ImageLoaderInterface{

    private static ImageLoader mInstance;

    private ImageLoaderInterface realLoader;

    private ImageLoader() {
        realLoader = new GlideImageLoader();
    }

    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void displayImage(Context context, Object uri, View imageView) {
        realLoader.displayImage(context, uri, imageView);
    }

    @Override
    public View createImageView(Context context) {
        return realLoader.createImageView(context);
    }
}
