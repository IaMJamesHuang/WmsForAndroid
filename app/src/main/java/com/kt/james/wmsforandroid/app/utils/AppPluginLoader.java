package com.kt.james.wmsforandroid.app.utils;

import android.content.res.AssetManager;

import com.kt.james.beplugincore.content.InstallResult;
import com.kt.james.beplugincore.manager.PluginManagerProviderClient;
import com.kt.james.beplugincore.util.FileUtil;
import com.kt.james.beplugincore.util.LogUtil;
import com.kt.james.wmsforandroid.app.App;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * author: James
 * 2019/4/26 10:40
 * version: 1.0
 * desc: 加载内置插件的工具类
 */
public class AppPluginLoader {

    public static void loadPlugins() {
        //加载、启动插件
        AssetManager assetManager = App.getInstance().getAssets();
        try {
            String[] fileList = assetManager.list("");
            if (fileList == null) {
                return;
            }
            for (String file : fileList) {
                if (file.endsWith(".apk")) {
                    install(file);
                }
            }
            PluginManagerProviderClient.wakeupAllPlugins();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void install(String name) {
        InputStream inputStream = null;
        try {
            inputStream = App.getInstance().getAssets().open(name);
            File file = App.getInstance().getExternalFilesDir(null);
            if (file == null) {
                return;
            }
            String dest = file.getAbsolutePath() + "/" + name;
            if (FileUtil.copyFile(inputStream, dest)) {
                InstallResult result = PluginManagerProviderClient.installPlugin(dest);
                if (result != null) {
                    LogUtil.d(result.getResult());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
