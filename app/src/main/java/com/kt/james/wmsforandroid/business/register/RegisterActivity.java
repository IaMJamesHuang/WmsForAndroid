package com.kt.james.wmsforandroid.business.register;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.app.Constants;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.business.utils.WmsSpManager;
import com.kt.james.wmsforandroid.databinding.ActivityRegisterBinding;
import com.kt.james.wmsforandroid.utils.ResourceUtil;

@Route(path = Constants.URI_REGISTER_ACTIVITY, name = "注册页面")
public class RegisterActivity extends BaseActivity<RegisterViewModel, ActivityRegisterBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(ResourceUtil.getString(R.string.register_title));
        showContentView();
        bindingView.setViewModel(viewModel);
    }

    public void register(View view) {
        viewModel.register().observe(this, this::loginCallBack);
    }

    public void loginCallBack(Boolean result) {
        if (Boolean.TRUE.equals(result)) {
            WmsSpManager.setIsLogin(true);
            ARouter.getInstance().build(Constants.URI_MAIN_ACTIVITY)
                    .withTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
                    .navigation(this);
            finish();
        }
    }

}
