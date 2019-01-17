package com.kt.james.wmsforandroid.net;

import static com.kt.james.wmsforandroid.app.Constants.WMS_BASE_URL;

public enum HttpClientType {

    WMSClient(WMS_BASE_URL);

    private String value;

    private HttpClientType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
