package com.kt.james.wmsforandroid.app.utils;

import android.content.res.AssetManager;
import android.util.Log;

import com.kt.james.wmsforandroid.app.App;
import com.limpoxe.fairy.manager.PluginManagerProviderClient;
import com.limpoxe.fairy.util.FileUtil;

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
                int result = PluginManagerProviderClient.install(dest);
                Log.e("AppPluginLoader", "install: " + result);
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
