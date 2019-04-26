package com.kt.james.wmsforandroid.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.app.utils.AppPluginLoader;

public class TransitionActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                ARouter.getInstance().build(Constants.URI_MAIN_ACTIVITY)
                        .withTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
                        .navigation(TransitionActivity.this);
                finish();
            }
        }, 2000);
//      在这里进行插件安装看起来是比较适合的
        AppPluginLoader.loadPlugins();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
