package com.kt.james.wmsforandroid.net.interceptor;

import com.kt.james.wmsforandroid.app.App;
import com.kt.james.wmsforandroid.app.utils.WmsSpManager;
import com.kt.james.wmsforandroid.utils.net.NetworkUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHeadInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Accept", "application/json;versions=1");
        builder.addHeader("User-Agent", "WmsForAndroid");
        builder.addHeader("company_id", WmsSpManager.getCompanyId());
        if (NetworkUtil.isNetworkConnected(App.getAppContext())) {
            int maxAge = 60;
            builder.addHeader("Cache-Control", "public, max-age=" + maxAge);
        } else {
            int maxStale = 60 * 60 * 24 * 28;
            builder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
        }
        return chain.proceed(builder.build());
    }
}
