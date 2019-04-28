package com.kt.james.pluginoff;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kt.james.wmsforandroid.app.Constants;
import com.kt.james.wmsforandroid.app.scan.CommonScanActivity;
import com.kt.james.wmsforandroid.app.scan.dto.CheckLocBean;
import com.kt.james.wmsforandroid.app.scan.dto.ItemBarcodeBean;
import com.kt.james.wmsforandroid.base.BaseMVPActivity;
import com.kt.james.wmsforandroid.utils.ARouterUtil;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

import static com.kt.james.wmsforandroid.app.scan.CommonScanActivity.RESULT_TAG;

public class OffShelfActivity extends BaseMVPActivity<OffShelfViewModel> implements View.OnClickListener, OffShelfView {

    private static final int REQUEST_SCAN_ITEM = 0x00;
    private static final int REQUEST_SCAN_LOC = 0x01;

    private EditText etOffShelfLoc;
    private EditText etItemBarcode;
    private EditText etItemName;
    private EditText etSpec;
    private EditText etBrand;
    private EditText etOffShelfNum;

    private Button btScanLoc;
    private Button btScanItem;
    private Button btSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_shelf);
        setBarTitle(ResourceUtil.getString(com.kt.james.wmsforandroid.R.string.off_shelf_title));
        initView();
        showContentView();
        viewModel.setView(this);
        initListener();
    }

    private void initView() {
        etOffShelfLoc = getView(R.id.et_scan_loc);
        etItemBarcode = getView(R.id.et_scan_barcode);
        etItemName = getView(R.id.et_item_name);
        etSpec = getView(R.id.et_item_spec);
        etBrand = getView(R.id.et_item_brand);
        etOffShelfNum = getView(R.id.et_item_amount);

        btScanItem = getView(R.id.bt_go_scan_item);
        btScanLoc = getView(R.id.bt_go_scan_loc);
        btSubmit = getView(R.id.bt_submit);
    }

    private void initListener() {
        btScanItem.setOnClickListener(this);
        btScanLoc.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_go_scan_item:
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
                    etItemBarcode.setText(result.getBarcode());
                    etItemName.setText(result.getName());
                    etSpec.setText(result.getSpec());
                    etBrand.setText(result.getFactory());
                }
                break;
            case REQUEST_SCAN_LOC:
                CheckLocBean loc = (CheckLocBean) data.getSerializableExtra(RESULT_TAG);
                if (loc != null && !TextUtils.isEmpty(loc.getName())) {
                    etOffShelfLoc.setText(loc.getName());
                }
                break;
        }
    }

    @Override
    public String getItemBarcode() {
        return etItemBarcode.getText().toString();
    }

    @Override
    public String getItemNum() {
        return etOffShelfNum.getText().toString();
    }

    @Override
    public String getItemLoc() {
        return etOffShelfLoc.getText().toString();
    }
}
