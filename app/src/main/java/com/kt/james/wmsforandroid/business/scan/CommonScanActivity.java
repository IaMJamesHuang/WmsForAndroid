package com.kt.james.wmsforandroid.business.scan;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.databinding.ActivityCommonScanBinding;
import com.kt.james.wmsforandroid.utils.ResourceUtil;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

@Route(path = "/scan/common_scan_activity", name = "扫码页面")
public class CommonScanActivity extends BaseActivity<CommonScanViewModel, ActivityCommonScanBinding> {

    public static final int ITEM = 0x00;
    public static final int LOC = 0x01;

    @Autowired
    public int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_scan);
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(codeCallBack);
        getSupportFragmentManager().beginTransaction().replace(R.id.scan_container, captureFragment).commit();
        setTitle(ResourceUtil.getString(R.string.scan_title));
        viewModel.isItem.set(from == ITEM);
        bindingView.setViewModel(viewModel);
        showContentView();
    }

    CodeUtils.AnalyzeCallback codeCallBack = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

        }

        @Override
        public void onAnalyzeFailed() {

        }
    };

}
