package com.kt.james.wmsforandroid.base;

import java.io.Serializable;

public class BaseDto implements Serializable {

    private String responseMsg;
    private int responseCode;

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

}
