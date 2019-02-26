package com.kt.james.wmsforandroid.app.replenish;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.app.register.RegisterViewModel;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.databinding.ActivityReplenishBinding;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

@Route(path = "/replenish/replenish_activity", name = "补货页面")
public class ReplenishActivity extends BaseActivity<ReplenishViewModel, ActivityReplenishBinding> {

    private ReplenishAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replenish);
        setTitle(ResourceUtil.getString(R.string.replenish_title));
        initRecycleView();
        showLoading();
        viewModel.getReplenishInfos().observe(this, this::getInfosCallback);
    }

    private void initRecycleView() {
        mAdapter = new ReplenishAdapter(this);
        bindingView.rvReplenishInfo.setLayoutManager(new LinearLayoutManager(this));
        bindingView.rvReplenishInfo.setAdapter(mAdapter);
    }

    public void getInfosCallback(ReplenishBean bean) {
        if (bean != null) {
            mAdapter.addAll(bean.getItems());
            showContentView();
        } else {
            showError();
        }
    }

    @Override
    protected void onRefresh() {
        showLoading();
        viewModel.getReplenishInfos().observe(this, this::getInfosCallback);
    }

    public void onSubmit(View view) {

    }

    public void onQuickSubmit(View view) {

    }

}
