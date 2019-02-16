package com.kt.james.wmsforandroid.app.report;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.databinding.ActivityReportBinding;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

import java.util.List;

@Route(path = "/report/report_activity", name = "报表页面")
public class ReportActivity extends BaseActivity<ReportViewModel, ActivityReportBinding> {

    private ReportAdapter mAdapter;

    private ReportFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setTitle(ResourceUtil.getString(R.string.report_title));
        initRv();
        initFragment();
        initData();
    }

    private void initRv() {
        mAdapter = new ReportAdapter(this);
        bindingView.rvAllItem.setAdapter(mAdapter);
        bindingView.rvAllItem.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(((itemInfoBean, position) -> {
            if (mFragment != null) {
                setAdapterSelect(position);
                mFragment.reLoadData(itemInfoBean.getId());
            }
        }));
    }

    private void initFragment() {
        mFragment = new ReportFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.report_container, mFragment).commit();
    }

    private void initData() {
        viewModel.getAllItem().observe(this, this::getItemCallback);
    }

    private void getItemCallback(List<ItemInfoBean> result) {
        if (result != null && result.size() > 0) {
            mAdapter.addAll(result);
            showContentView();
            setAdapterSelect(0);
            mFragment.loadData(result.get(0).getId());
        } else {
            showError();
        }
    }

    private void setAdapterSelect(int pos) {
        mAdapter.setSelectPos(pos);
    }

}
