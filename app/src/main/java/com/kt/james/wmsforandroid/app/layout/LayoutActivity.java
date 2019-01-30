package com.kt.james.wmsforandroid.app.layout;

import android.os.Bundle;

import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.databinding.ActivityLayoutBinding;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

public class LayoutActivity extends BaseActivity<LayoutViewModel, ActivityLayoutBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        setTitle(ResourceUtil.getString(R.string.layout_title));
        LayoutAdapter<LayoutSurfaceView> layoutAdapter = new LayoutAdapter<>(bindingView.surfaceLayout);
        showContentView();
    }
}
