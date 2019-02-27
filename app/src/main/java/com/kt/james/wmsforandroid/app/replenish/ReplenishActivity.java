package com.kt.james.wmsforandroid.app.replenish;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.databinding.ActivityReplenishBinding;
import com.kt.james.wmsforandroid.utils.MathUtil;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

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
        submitReplenish(false);
    }

    public void onQuickSubmit(View view) {
        submitReplenish(true);
    }

    private void submitReplenish(boolean isQuick) {
        List<ReplenishBean.ReplenishItem> data = mAdapter.getData();
        List<SubmitReplenishBean.SubmitItem> submitData = new ArrayList<>();
        for (ReplenishBean.ReplenishItem item : data) {
            if (!isQuick) {
                if (Boolean.TRUE.equals(item.check.get()) && MathUtil.isFloatRight(item.replenishNum.get())) {
                    float replenishNum = MathUtil.tryFormatFloat(item.replenishNum.get(),0);
                    if (replenishNum > 0) {
                        SubmitReplenishBean.SubmitItem submitItem = new SubmitReplenishBean.SubmitItem();
                        submitItem.setItemId(item.getItemId());
                        submitItem.setReplenishCount(replenishNum);
                        submitData.add(submitItem);
                    }
                }
            } else {
                if (MathUtil.isFloatRight(item.getRecommend())) {
                    float replenishNum = MathUtil.tryFormatFloat(item.getRecommend(),0);
                    if (replenishNum > 0) {
                        SubmitReplenishBean.SubmitItem submitItem = new SubmitReplenishBean.SubmitItem();
                        submitItem.setItemId(item.getItemId());
                        submitItem.setReplenishCount(replenishNum);
                        submitData.add(submitItem);
                    }
                }
            }
        }
        viewModel.submitReplenishInfos(submitData).observe(this, this::submitCallback);
    }

    public void submitCallback(Boolean result) {
        if (Boolean.TRUE.equals(result)) {
            finish();
        }
    }

}
