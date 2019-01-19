package com.kt.james.wmsforandroid.business.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.business.utils.WmsSpManager;
import com.kt.james.wmsforandroid.databinding.ActivityMainBinding;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/main/main_activity", name = "主页面")
public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(ResourceUtil.getString(R.string.main_title));
        initView();
        showContentView();
        bindingView.setViewModel(viewModel);
    }

    private void initView() {
        MainPageAdapter adapter = new MainPageAdapter(this);
        bindingView.rvMainPage.setAdapter(adapter);
        bindingView.rvMainPage.setLayoutManager(new GridLayoutManager(this, 3));
        MainPageBean bean;
        List<MainPageBean> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            bean = new MainPageBean(R.drawable.icon_shelf, "上架");
            list.add(bean);
        }
        adapter.addAll(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WmsSpManager.setIsLogin(false);
    }
}
