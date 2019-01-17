package com.kt.james.wmsforandroid.net;

import com.kt.james.wmsforandroid.business.login.LoginDto;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpClient {

    public static final int CODE_SUCCESS = 200;

    class Builder {

        public static HttpClient getWmsService() {
            return HttpClientBuildFactory.getInstance().create(HttpClient.class, HttpClientType.WMSClient);
        }

    }

    /**
     * 玩安卓登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("login")
    Observable<LoginDto> login(@Field("username") String username, @Field("password") String password);

}
