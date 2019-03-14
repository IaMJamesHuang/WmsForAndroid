package com.kt.james.wmsforandroid.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.kt.james.wmsforandroid.app.App;

public class PackageUtils {

    public static String getAppMeta(String key) {
        String result = "";
        try {
            ApplicationInfo applicationInfo = App.getAppContext()
                    .getPackageManager()
                    .getApplicationInfo(App.getAppContext().getPackageName(), PackageManager.GET_META_DATA);
            result = applicationInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

}
