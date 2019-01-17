package com.kt.james.wmsforandroid.utils.crash;

import java.util.concurrent.ConcurrentHashMap;

public interface CrashUploader {

    void uploadCrashMessage(ConcurrentHashMap<String, Object> info);

}
