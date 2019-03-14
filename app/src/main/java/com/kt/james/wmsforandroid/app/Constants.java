package com.kt.james.wmsforandroid.app;

import com.kt.james.wmsforandroid.utils.PackageUtils;

public class Constants {

    public static final String APP_NAME = "WmsForAndroid";

    public static final String WMS_BASE_URL = PackageUtils.getAppMeta("BASE_URL");

    //ARouter调转uri
    public static final String URI_MAIN_ACTIVITY = "/main/main_activity";
    public static final String URI_LOGIN_ACTIVITY = "/login/login_activity";
    public static final String URI_REGISTER_ACTIVITY = "/login/register_activity";
    public static final String URI_INPUT_ITEM_ACTIVITY = "/input/input_activity";
    public static final String URI_SCAN_ITEM_ACTIVITY = "/scan/common_scan_activity";
    public static final String URI_ABOUT = "/about/about_activity";
    public static final String URI_OFF_SHELF_ACTIVITY = "/offshelf/off_shelf_activity";
    public static final String URI_LAYOUT_ACTIVITY = "/layout/layout_activity";
    public static final String URI_UP_SHELF_ACTIVITY = "/upShelf/up_shelf_activity";
    public static final String URI_UP_SHELF_ITEM_ACTIVITY = "/upShelf/up_shelf_item_activity";
    public static final String URI_REPORT_ACTIVITY = "/report/report_activity";
    public static final String URI_REPLENISH_ACTIVITY = "/replenish/replenish_activity";

}
