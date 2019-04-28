package com.kt.james.wmsforandroid.app;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kt.james.wmsforandroid.BuildConfig;
import com.kt.james.wmsforandroid.net.HttpUtils;
import com.kt.james.wmsforandroid.utils.crash.JLogCrashHandler;
import com.limpoxe.fairy.core.FairyGlobal;
import com.limpoxe.fairy.core.PluginLoader;
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
        //框架日志开关, 默认false
        FairyGlobal.setLogEnable(isDebug());

        //首次加载插件会创建插件对象，比较耗时，通过弹出loading页来过渡。
        //这个方法是设置首次加载插件时, 定制loading页面的UI, 不传即默认没有loading页
        //在宿主中创建任意一个layout传进去即可
        //注意：首次唤起插件组件时，如果是通过startActivityForResult唤起的，如果配置了loading页，
        //则实际是先打开了loading页，再转到目标页面，此时会忽略ForResult的结果。这种情况下应该禁用loading页配置
//        FairyGlobal.setLoadingResId(R.layout.loading);

        //是否支持插件中使用本地html, 默认false
        FairyGlobal.setLocalHtmlenable(false);

        //初始化框架
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

    @Override
    public Context getBaseContext() {
        return PluginLoader.fixBaseContextForReceiver(super.getBaseContext());
    }
}
