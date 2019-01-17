package com.kt.james.wmsforandroid.net.interceptor;

import android.content.Context;

import com.kt.james.wmsforandroid.utils.net.NetworkUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCacheInterceptor implements Interceptor{

    private Context context;

    public AddCacheInterceptor(Context context) {
        super();
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(365, TimeUnit.DAYS);
        CacheControl cacheControl = cacheBuilder.build();
        Request request = chain.request();
        if (!NetworkUtil.isNetworkConnected(context)) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetworkUtil.isNetworkConnected(context)) {
            // read from cache
            int maxAge = 0;
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            // tolerate 4-weeks stale
            int maxStale = 60 * 60 * 24 * 28;
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }

}
