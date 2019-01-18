package com.kt.james.wmsforandroid.business;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.databinding.ActivityMainBinding;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

@Route(path = "/main/main_activity", name = "主页面")
public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(ResourceUtil.getString(R.string.main_title));
        showContentView();
        bindingView.setViewModel(viewModel);
    }
}
