package com.kt.james.wmsforandroid.business.interceptor;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kt.james.wmsforandroid.app.Constants;
import com.kt.james.wmsforandroid.business.utils.WmsSpManager;

import java.util.HashSet;
import java.util.Set;

@Interceptor(priority = 100)
public class LoginInterceptor implements IInterceptor {

    private static final String TAG = "LoginInterceptor";
    private Set<String> interceptorActivity;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Log.d(TAG, "process: process");
        if (interceptorActivity.contains(postcard.getPath()) && !WmsSpManager.getIsLogin()) {
                Log.d(TAG, "process: onInterrupt");
            callback.onInterrupt(null);
            ARouter.getInstance().build(Constants.URI_LOGIN_ACTIVITY).navigation();
        } else {
            Log.d(TAG, "process: onContinue");
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        interceptorActivity = new HashSet<>(5);
        interceptorActivity.add(Constants.URI_MAIN_ACTIVITY);
    }
}
