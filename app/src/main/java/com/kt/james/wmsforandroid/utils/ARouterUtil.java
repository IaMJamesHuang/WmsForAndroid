package com.kt.james.wmsforandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kt.james.wmsforandroid.R;

public class ARouterUtil {

    public static void nav(Context context, String uri) {
        ARouter.getInstance().build(uri)
                .withTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
                .navigation(context);
    }

    public static void nav(Context context, String uri, Bundle bundle) {
        ARouter.getInstance().build(uri)
                .with(bundle)
                .withTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
                .navigation(context);
    }

    public static void navForResult(Activity context, String uri, int requestCode) {
        ARouter.getInstance().build(uri)
                .withTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
                .navigation(context, requestCode);
    }

    public static void navForResult(Activity context, String uri, Bundle bundle, int requestCode) {
        ARouter.getInstance().build(uri)
                .with(bundle)
                .withTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
                .navigation(context, requestCode);
    }

}
