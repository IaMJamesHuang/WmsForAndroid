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

    private MainPageAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(ResourceUtil.getString(R.string.main_title));
        initView();
        initMenu();
        showContentView();
        bindingView.setViewModel(viewModel);
    }

    private void initView() {
        mPageAdapter = new MainPageAdapter(this);
        bindingView.rvMainPage.setAdapter(mPageAdapter);
        bindingView.rvMainPage.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void initMenu() {
        List<MainPageBean> list = new ArrayList<>();
        list.add(new MainPageBean(R.drawable.icon_shelf,
                ResourceUtil.getString(R.string.main_module_shelf)));
        list.add(new MainPageBean(R.drawable.icon_down,
                ResourceUtil.getString(R.string.main_module_down)));
        list.add(new MainPageBean(R.drawable.icon_replenish,
                ResourceUtil.getString(R.string.main_module_report)));
        list.add(new MainPageBean(R.drawable.icon_report,
                ResourceUtil.getString(R.string.main_module_report)));
        list.add(new MainPageBean(R.drawable.icon_input,
                ResourceUtil.getString(R.string.main_module_input)));
        mPageAdapter.addAll(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WmsSpManager.setIsLogin(false);
    }
}
