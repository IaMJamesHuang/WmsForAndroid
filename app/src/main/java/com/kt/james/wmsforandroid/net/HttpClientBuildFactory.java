package com.kt.james.wmsforandroid.net;

public class HttpClientBuildFactory {

    private static HttpClientBuildFactory instance;

    private Object wmsClient;

    public static HttpClientBuildFactory getInstance() {
        if (instance == null) {
            synchronized (HttpClientBuildFactory.class) {
                if (instance == null) {
                    instance = new HttpClientBuildFactory();
                }
            }
        }
        return instance;
    }

    public <T> T create(Class<T> target, HttpClientType type) {
        switch (type) {
            case WMSClient:
                if (wmsClient == null) {
                    synchronized (this) {
                        if (wmsClient == null) {
                            wmsClient = HttpUtils.getInstance().getRetrofitBuilder(type.getValue()).build().create(target);
                        }
                    }
                }
                return (T) wmsClient;
            default:
                if (wmsClient == null) {
                    synchronized (this) {
                        if (wmsClient == null) {
                            wmsClient = HttpUtils.getInstance().getRetrofitBuilder(type.getValue()).build().create(target);
                        }
                    }
                }
                return (T) wmsClient;
        }
    }

}
