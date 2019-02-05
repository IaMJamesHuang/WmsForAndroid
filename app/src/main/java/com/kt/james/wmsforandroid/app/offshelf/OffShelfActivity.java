package com.kt.james.wmsforandroid.app.offshelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.app.Constants;
import com.kt.james.wmsforandroid.app.scan.CommonScanActivity;
import com.kt.james.wmsforandroid.app.scan.dto.CheckLocBean;
import com.kt.james.wmsforandroid.app.scan.dto.ItemBarcodeBean;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.databinding.ActivityOffShelfBinding;
import com.kt.james.wmsforandroid.utils.ARouterUtil;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

import static com.kt.james.wmsforandroid.app.scan.CommonScanActivity.RESULT_TAG;

@Route(path = "/offshelf/off_shelf_activity", name = "下架页面")
public class OffShelfActivity extends BaseActivity<OffShelfViewModel, ActivityOffShelfBinding> implements View.OnClickListener {

    private static final int REQUEST_SCAN_ITEM = 0x00;
    private static final int REQUEST_SCAN_LOC = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_shelf);
        setTitle(ResourceUtil.getString(R.string.off_shelf_title));
        showContentView();
        bindingView.setViewModel(viewModel);
        initListener();
    }

    private void initListener() {
        bindingView.etGoScanItem.setOnClickListener(this);
        bindingView.btGoScanLoc.setOnClickListener(this);
        bindingView.btSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_go_scan_item:
                Bundle bundle = new Bundle();
                bundle.putInt("from", CommonScanActivity.ITEM);
                ARouterUtil.navForResult(this, Constants.URI_SCAN_ITEM_ACTIVITY, bundle, REQUEST_SCAN_ITEM);
                break;
            case R.id.bt_go_scan_loc:
                Bundle locBundle = new Bundle();
                locBundle.putInt("from", CommonScanActivity.LOC);
                ARouterUtil.navForResult(this, Constants.URI_SCAN_ITEM_ACTIVITY, locBundle, REQUEST_SCAN_LOC);
                break;
            case R.id.bt_submit:
                viewModel.submit().observe(this, this::submitCallBack);
                break;
        }
    }

    public void submitCallBack(Boolean result) {
        if (result) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_SCAN_ITEM:
                ItemBarcodeBean result = (ItemBarcodeBean) data.getSerializableExtra(RESULT_TAG);
                if (result != null) {
                    bindingView.etScanBarcode.setText(result.getBarcode());
                    bindingView.etItemName.setText(result.getName());
                    bindingView.etItemSpec.setText(result.getSpec());
                    bindingView.etItemBrand.setText(result.getFactory());
                }
                break;
            case REQUEST_SCAN_LOC:
                CheckLocBean loc = (CheckLocBean) data.getSerializableExtra(RESULT_TAG);
                if (loc != null && !TextUtils.isEmpty(loc.getName())) {
                    bindingView.etScanLoc.setText(loc.getName());
                }
                break;
        }
    }

}
