package com.kt.james.wmsforandroid.business.login;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.app.Constants;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.business.utils.WmsSpManager;
import com.kt.james.wmsforandroid.databinding.ActivityLoginBinding;
import com.kt.james.wmsforandroid.utils.ResourceUtil;
import com.kt.james.wmsforandroid.utils.ToastUtil;

@Route(path = "/login/login_activity", name = "登陆界面")
public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(ResourceUtil.getString(R.string.login_title));
        showContentView();
        bindingView.setViewModel(viewModel);
    }

    public void register(View view) {

    }

    public void login(View view) {
        viewModel.login().observe(this, this::loginCallback);
    }

    public void loginCallback(Boolean result) {
        if (result) {
            WmsSpManager.setIsLogin(true);
            ARouter.getInstance().build(Constants.URI_MAIN_ACTIVITY)
                    .withTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
                    .navigation(this);
            finish();
        }
    }

}
