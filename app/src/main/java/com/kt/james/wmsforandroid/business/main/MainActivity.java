package com.kt.james.wmsforandroid.business.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.adapter.OnItemClickListener;
import com.kt.james.wmsforandroid.business.utils.WmsSpManager;
import com.kt.james.wmsforandroid.databinding.ActivityMainBinding;
import com.kt.james.wmsforandroid.databinding.LayoutNavMainBinding;
import com.kt.james.wmsforandroid.utils.ARouterUtil;
import com.kt.james.wmsforandroid.utils.ResourceUtil;
import com.kt.james.wmsforandroid.view.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import static com.kt.james.wmsforandroid.app.Constants.URI_INPUT_ITEM_ACTIVITY;

@Route(path = "/main/main_activity", name = "主页面")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainPageAdapter mPageAdapter;
    private ActivityMainBinding bindingView;
    private LayoutNavMainBinding navBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingView = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initStatusView();
        initDrawerLayout();
        initRecycleView();
        initMenu();
    }

    private void initStatusView() {
        ViewGroup.LayoutParams layoutParams = bindingView.viewStatus.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this);
        bindingView.viewStatus.setLayoutParams(layoutParams);
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, bindingView.drawerLayout,
                ResourceUtil.getColor(R.color.colorTheme));
    }

    private void initDrawerLayout() {
        bindingView.navView.inflateHeaderView(R.layout.layout_nav_main);
        View headerView = bindingView.navView.getHeaderView(0);
        navBinding = DataBindingUtil.bind(headerView);
        navBinding.llNavAbout.setOnClickListener(this);
        navBinding.llNavExit.setOnClickListener(this);
        navBinding.llNavLogin.setOnClickListener(this);
        bindingView.ivTitleMenu.setOnClickListener(this);
    }

    private void initRecycleView() {
        mPageAdapter = new MainPageAdapter(this);
        bindingView.rvMainPage.setAdapter(mPageAdapter);
        bindingView.rvMainPage.setLayoutManager(new GridLayoutManager(this, 3));
        mPageAdapter.setOnItemClickListener(((mainPageBean, position) -> {
            switch (mainPageBean.getTitle()) {
                case "录入":
                    ARouterUtil.nav(MainActivity.this, URI_INPUT_ITEM_ACTIVITY);
                    break;
            }
        }));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_menu:
                // 开启菜单
                bindingView.drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }
}
