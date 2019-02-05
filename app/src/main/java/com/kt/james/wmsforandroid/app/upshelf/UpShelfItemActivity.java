package com.kt.james.wmsforandroid.app.upshelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.app.Constants;
import com.kt.james.wmsforandroid.app.scan.CommonScanActivity;
import com.kt.james.wmsforandroid.app.scan.dto.CheckLocBean;
import com.kt.james.wmsforandroid.app.scan.dto.ItemBarcodeBean;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.databinding.ActivityUpShelfItemBinding;
import com.kt.james.wmsforandroid.utils.ARouterUtil;
import com.kt.james.wmsforandroid.utils.ResourceUtil;
import com.kt.james.wmsforandroid.utils.ToastUtil;

@Route(path = "/upShelf/up_shelf_item_activity", name = "上架子页面")
public class UpShelfItemActivity extends BaseActivity<UpShelfItemViewModel, ActivityUpShelfItemBinding> {

    public static final int REQUEST_ITEM = 0x00;
    public static final int REQUEST_LOC = 0x01;

    @Autowired
    public UpShelfBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_up_shelf_item);
        setTitle(ResourceUtil.getString(R.string.up_shelf_title));
        showContentView();
        bindingView.setViewModel(viewModel);
        viewModel.setUpShelfInfo(data);
    }

    public void onSubmit(View view) {
        viewModel.submit().observe(this, this::postUpShelfCallback);
    }

    public void postUpShelfCallback(UpShelfBean info) {
        if (info != null) {
            int state = info.getShelfListState();
            if (state == UpShelfDto.STATE_FINISH) {
                finish();
            } else {
                viewModel.cleanInfo();
                viewModel.setUpShelfInfo(info);
            }
        }
    }

    public void onGoScanLoc(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("from", CommonScanActivity.LOC);
        ARouterUtil.navForResult(this, Constants.URI_SCAN_ITEM_ACTIVITY, bundle, REQUEST_LOC);
    }

    public void onGoScanItem(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("from", CommonScanActivity.ITEM);
        ARouterUtil.navForResult(this, Constants.URI_SCAN_ITEM_ACTIVITY, bundle, REQUEST_ITEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_ITEM:
                ItemBarcodeBean item = (ItemBarcodeBean)
                        data.getSerializableExtra(CommonScanActivity.RESULT_TAG);
                viewModel.setItemInfo(item);
                break;
            case REQUEST_LOC:
                CheckLocBean loc = (CheckLocBean)
                        data.getSerializableExtra(CommonScanActivity.RESULT_TAG);
                viewModel.setLocInfo(loc);
                break;
        }
    }
}
