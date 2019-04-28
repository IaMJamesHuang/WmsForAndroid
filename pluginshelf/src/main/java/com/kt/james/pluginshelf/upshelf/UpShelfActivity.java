package com.kt.james.pluginshelf.upshelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kt.james.pluginshelf.R;
import com.kt.james.wmsforandroid.app.Constants;
import com.kt.james.wmsforandroid.app.scan.CommonScanActivity;
import com.kt.james.wmsforandroid.app.upshelf.UpShelfDto;
import com.kt.james.wmsforandroid.base.BaseMVPActivity;
import com.kt.james.wmsforandroid.utils.ARouterUtil;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

@Route(path = "/upShelf/up_shelf_activity", name = "上架页面")
public class UpShelfActivity extends BaseMVPActivity<UpShelfViewModel> implements UpShelfView{

    public final static int REQUEST_SCAN_STRING = 0x00;

    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_shelf);
        showContentView();
        setBarTitle(ResourceUtil.getString(com.kt.james.wmsforandroid.R.string.up_shelf_title));
        etInput = getView(R.id.et_up_shelf_order);
        viewModel.setView(this);
    }

    public void onConfirm(View view) {
        viewModel.getUpShelfInfos().observe(this, this::getUpShelfCallback);
    }

    public void getUpShelfCallback(UpShelfDto dto) {
        if (dto != null && dto.getResult() != null) {
            etInput.setText("");
            Intent intent = new Intent(this, UpShelfItemActivity.class);
            intent.putExtra("data", dto.getResult());
            startActivity(intent);
        }
    }

    public void onScan(View view) {
        etInput.setText("");
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
            etInput.setText(result);
            etInput.setSelection(result.length());
        }
    }

    @Override
    public String getInputOrder() {
        return etInput.getText().toString();
    }
}
