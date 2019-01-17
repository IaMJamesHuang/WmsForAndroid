package com.kt.james.wmsforandroid.net;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kt.james.wmsforandroid.net.cookie.SimpleCookieJar;
import com.kt.james.wmsforandroid.net.interceptor.AddCacheInterceptor;
import com.kt.james.wmsforandroid.net.interceptor.HttpHeadInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtils {

    private static HttpUtils instance;
    private Context context;
    private boolean debug;

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    public void init(Context context, boolean debug) {
        this.context = context.getApplicationContext();
        this.debug = debug;
    }

    public Retrofit.Builder getRetrofitBuilder(String apiUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getDefaultOkHttpClient());
        builder.baseUrl(apiUrl);//设置远程地址
        builder.addConverterFactory(GsonConverterFactory.create(getGson()));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder;
    }

    private Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setLenient();
        builder.serializeNulls();
        return builder.create();
    }

    private HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (debug) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // 测试
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE); // 打包
        }
        return interceptor;
    }

    private OkHttpClient getDefaultOkHttpClient() {
        try {
            //cache url
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            // 50 MiB
            int cacheSize = 50 * 1024 * 1024;
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            // Create an ssl socket factory with our all-trusting manager
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(30, TimeUnit.SECONDS);
            okBuilder.connectTimeout(30, TimeUnit.SECONDS);
            okBuilder.writeTimeout(30, TimeUnit.SECONDS);
            //请求头
            okBuilder.addInterceptor(new HttpHeadInterceptor());
            // 添加缓存，无网访问时会拿缓存,只会缓存get请求
            okBuilder.addInterceptor(new AddCacheInterceptor(context));
            okBuilder.addInterceptor(getLoggingInterceptor());
            okBuilder.cookieJar(new SimpleCookieJar());
            okBuilder.cache(cache);

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
