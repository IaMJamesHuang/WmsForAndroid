package com.kt.james.wmsforandroid.business.utils;

import com.kt.james.wmsforandroid.utils.sp.SPUtil;

public class WmsSpManager{

    private static final String IS_LOGIN = "is_login";

    public static boolean getIsLogin() {
        return SPUtil.getBoolean(IS_LOGIN, false);
    }

    public static void setIsLogin(boolean login) {
        SPUtil.putBoolean(IS_LOGIN, login);
    }

}
