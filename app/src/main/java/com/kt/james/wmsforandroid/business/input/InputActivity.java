package com.kt.james.wmsforandroid.business.input;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.databinding.ActivityItemInputBinding;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

@Route(path = "/input/input_activity", name = "录入页面")
public class InputActivity extends BaseActivity<InputViewModel, ActivityItemInputBinding> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_input);
        setTitle(ResourceUtil.getString(R.string.input_item_title));
        showContentView();
        bindingView.setViewModel(viewModel);
        initListener();
    }

    private void initListener() {
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_go_scan_item:
                break;
        }
    }
}
