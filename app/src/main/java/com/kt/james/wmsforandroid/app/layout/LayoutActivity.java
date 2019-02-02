package com.kt.james.wmsforandroid.app.layout;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.databinding.ActivityLayoutBinding;
import com.kt.james.wmsforandroid.utils.ResourceUtil;
import com.kt.james.wmsforandroid.utils.rx.RxBus;

import java.util.List;

import io.reactivex.functions.Consumer;

@Route(path = "/layout/layout_activity", name = "布局界面")
public class LayoutActivity extends BaseActivity<LayoutViewModel, ActivityLayoutBinding> implements LayoutAdapter.OnLayoutChangeListener{

    private LayoutAdapter<LayoutSurfaceView> mLayoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        setTitle(ResourceUtil.getString(R.string.layout_title));
        initAdapter();
        initBus();
    }

    private void initBus() {
        RxBus.get().toFlowable(LayoutEvent.class).subscribe(new Consumer<LayoutEvent>() {
            @Override
            public void accept(LayoutEvent layoutEvent) throws Exception {
                if (layoutEvent.getWhat() == LayoutEvent.EVENT_SAVE) {
                    mLayoutAdapter.addData(layoutEvent.getLayoutPoint());
                } else {
                    LayoutPoint layoutPoint = layoutEvent.getLayoutPoint();
                    if (layoutPoint.getState() == LayoutSurfaceView.STATE_EDIT) {
                        layoutPoint.setState(LayoutSurfaceView.STATE_EMPTY);
                        mLayoutAdapter.addData(layoutPoint);
                    }
                }
            }
        });
    }

    private void initAdapter() {
        mLayoutAdapter = new LayoutAdapter<>(bindingView.surfaceLayout);
        mLayoutAdapter.setListener(this);
        viewModel.requestLayoutInfo().observe(this, this::requestCallback);
    }

    public void requestCallback(List<LayoutPoint> info) {
        if (info == null) {
            showError();
        } else {
            mLayoutAdapter.addAll(info);
            showContentView();
        }
    }

    public void postLayoutInfo(View view) {
        viewModel.postLayoutInfo(mLayoutAdapter.getData()).observe(this, this::postLayoutCallback);
    }

    public void postLayoutCallback(Boolean result) {
        if (Boolean.TRUE.equals(result)) {
            finish();
        }
    }

    @Override
    protected void onRefresh() {
        viewModel.requestLayoutInfo().observe(this, this::requestCallback);
    }

    @Override
    public void onEmpty(LayoutPoint layoutPoint) {
        LayoutCustomDialog.showLayout(this, layoutPoint);
    }

    @Override
    public void onEditing(LayoutPoint layoutPoint) {

    }

    @Override
    public void onFill(LayoutPoint layoutPoint) {
        LayoutCustomDialog.showLayout(this, layoutPoint);
    }
}
