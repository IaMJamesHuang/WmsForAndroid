package com.kt.james.pluginshelf.upshelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kt.james.pluginshelf.R;
import com.kt.james.wmsforandroid.app.Constants;
import com.kt.james.wmsforandroid.app.scan.CommonScanActivity;
import com.kt.james.wmsforandroid.app.scan.dto.CheckLocBean;
import com.kt.james.wmsforandroid.app.scan.dto.ItemBarcodeBean;
import com.kt.james.wmsforandroid.app.upshelf.UpShelfBean;
import com.kt.james.wmsforandroid.app.upshelf.UpShelfDto;
import com.kt.james.wmsforandroid.base.BaseMVPActivity;
import com.kt.james.wmsforandroid.utils.ARouterUtil;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

public class UpShelfItemActivity extends BaseMVPActivity<UpShelfItemViewModel> implements UpShelfItemView{

    public static final int REQUEST_ITEM = 0x00;
    public static final int REQUEST_LOC = 0x01;

    public UpShelfBean data;

    private TextView tvItemName;
    private TextView tvItemBarcode;
    private TextView tvSpec;
    private TextView tvBrand;
    private TextView tvLoc;
    private TextView tvShelfNum;

    private EditText etItemBarcode;
    private EditText etShelfLoc;
    private EditText etShelfNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_shelf_item);
        initView();
        showContentView();
        setBarTitle(ResourceUtil.getString(com.kt.james.wmsforandroid.R.string.up_shelf_title));
        data = (UpShelfBean) getIntent().getSerializableExtra("data");
        viewModel.setView(this);
        viewModel.setUpShelfInfo(data);
    }

    private void initView() {
        tvItemName = getView(R.id.tv_item_name);
        tvItemBarcode = getView(R.id.tv_item_barcode);
        tvSpec = getView(R.id.tv_spec);
        tvBrand = getView(R.id.tv_brand);
        tvLoc = getView(R.id.tv_loc);
        tvShelfNum = getView(R.id.tv_shelf_num);

        etItemBarcode = getView(R.id.et_item_barcode);
        etShelfLoc = getView(R.id.et_scan_loc);
        etShelfNum = getView(R.id.et_scan_barcode);
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

    @Override
    public void setShelfView(UpShelfBean data) {
        tvItemName.setText(data.getItemName());
        tvItemBarcode.setText(data.getItemBarcode());
        tvSpec.setText(data.getItemSpec());
        tvBrand.setText(data.getItemFactory());
        tvLoc.setText(data.getLocName());
        tvShelfNum.setText(String.valueOf(data.getNum()));
    }

    @Override
    public void cleanView() {
        etShelfNum.setText("");
        etShelfLoc.setText("");
        etItemBarcode.setText("");
    }

    @Override
    public String getInputItem() {
        return etItemBarcode.getText().toString();
    }

    @Override
    public String getShelfLoc() {
        return etShelfLoc.getText().toString();
    }

    @Override
    public String getShelfNum() {
        return etShelfNum.getText().toString();
    }

    @Override
    public void setInputItem(String name) {
        etItemBarcode.setText(name);
    }

    @Override
    public void setShelfLoc(String loc) {
        etShelfLoc.setText(loc);
    }

    @Override
    public void setShelfNum(String shelfNum) {
        etShelfNum.setText(shelfNum);
    }
}
