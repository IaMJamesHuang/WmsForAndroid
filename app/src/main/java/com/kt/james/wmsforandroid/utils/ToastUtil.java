package com.kt.james.wmsforandroid.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.kt.james.wmsforandroid.app.App;

public class ToastUtil {

    public static void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(App.getAppContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToastLong(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(App.getAppContext(), text, Toast.LENGTH_LONG).show();
        }
    }

}
