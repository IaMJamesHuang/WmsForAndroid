package com.kt.james.wmsforandroid.app;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kt.james.beplugincore.PluginLoader;
import com.kt.james.wmsforandroid.BuildConfig;
import com.kt.james.wmsforandroid.net.HttpUtils;
import com.kt.james.wmsforandroid.utils.crash.JLogCrashHandler;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class App extends Application {

    private static App app;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initPlugin();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void init() {
       // initCrashHandler();
        initHttp();
        initARouter();
        initZXing();
    }

    private void initPlugin() {
        PluginLoader.initLoader(this);
    }

    private void initCrashHandler() {
        Intent intent = new Intent();
        intent.setClassName("com.kt.james.wmsforandroid", "com.kt.james.wmsforandroid.business.main.MainActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        JLogCrashHandler.getInstance().init(this, restartIntent);
    }

    private void initHttp() {
        HttpUtils.getInstance().init(this, isDebug());
    }

    private void initARouter() {
        if (isDebug()) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    private void initZXing() {
        ZXingLibrary.initDisplayOpinion(this);
    }

    private boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static Context getAppContext() {
        return app;
    }

    public static App getInstance() {
        return app;
    }

}
