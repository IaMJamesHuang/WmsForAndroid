package com.kt.james.wmsforandroid.utils.imgloader;

import android.content.Context;
import android.view.View;

import java.io.Serializable;

public interface ImageLoaderInterface<T extends View> extends Serializable {

    void displayImage(Context context, Object uri, T imageView);

    T createImageView(Context context);

}
