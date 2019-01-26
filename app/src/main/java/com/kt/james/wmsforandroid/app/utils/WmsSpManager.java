package com.kt.james.wmsforandroid.app.utils;

import com.kt.james.wmsforandroid.utils.sp.SPUtil;

public class WmsSpManager{

    private static final String IS_LOGIN = "is_login";

    private static final String COMPANY_ID = "company_id";

    public static boolean getIsLogin() {
        return SPUtil.getBoolean(IS_LOGIN, false);
    }

    public static void setIsLogin(boolean login) {
        SPUtil.putBoolean(IS_LOGIN, login);
    }

    public static String getCompanyId() {
       return SPUtil.getString("company_id", "0");
    }

    public static void setCompanyId(String companyId) {
        SPUtil.putString(COMPANY_ID, companyId);
    }

}
