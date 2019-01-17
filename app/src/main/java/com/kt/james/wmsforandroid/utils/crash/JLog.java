package com.kt.james.wmsforandroid.utils.crash;

import android.util.Log;

public class JLog {

    public static void d(String TAG, String msg) {
        Log.d(TAG, msg);
    }

    public static void e(String TAG, String msg) {
        Log.e(TAG, msg);
    }

}