package com.kt.james.wmsforandroid.app.upshelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.app.Constants;
import com.kt.james.wmsforandroid.app.scan.CommonScanActivity;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.databinding.ActivityUpShelfBinding;
import com.kt.james.wmsforandroid.utils.ARouterUtil;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

@Route(path = "/upShelf/up_shelf_activity", name = "上架页面")
public class UpShelfActivity extends BaseActivity<UpShelfViewModel, ActivityUpShelfBinding> {

    public final static int REQUEST_SCAN_STRING = 0x00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_shelf);
        setTitle(ResourceUtil.getString(R.string.up_shelf_title));
        showContentView();
        bindingView.setViewModel(viewModel);
    }

    public void onConfirm(View view) {
        viewModel.getUpShelfInfos().observe(this, this::getUpShelfCallback);
    }

    public void getUpShelfCallback(UpShelfDto dto) {
        if (dto != null && dto.getResult() != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", dto.getResult());
            ARouterUtil.nav(this, Constants.URI_UP_SHELF_ITEM_ACTIVITY, bundle);
        }
    }

    public void onScan(View view) {
        bindingView.etUpShelfOrder.setText("");
        Bundle bundle = new Bundle();
        bundle.putInt("from", CommonScanActivity.STRING);
        ARouterUtil.navForResult(this, Constants.URI_SCAN_ITEM_ACTIVITY, bundle, REQUEST_SCAN_STRING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_SCAN_STRING) {
            String result = data.getStringExtra(CommonScanActivity.RESULT_TAG);
            bindingView.etUpShelfOrder.setText(result);
            bindingView.etUpShelfOrder.setSelection(result.length());
        }
    }
}
