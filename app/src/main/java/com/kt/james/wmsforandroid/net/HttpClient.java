package com.kt.james.wmsforandroid.net;

import com.kt.james.wmsforandroid.app.input.dto.AddItemDto;
import com.kt.james.wmsforandroid.app.input.dto.CheckItemBarcodeDto;
import com.kt.james.wmsforandroid.app.input.dto.CheckLocDto;
import com.kt.james.wmsforandroid.app.login.LoginDto;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpClient {

    int CODE_SUCCESS = 200;

    class Builder {

        public static HttpClient getWmsService() {
            return HttpClientBuildFactory.getInstance().create(HttpClient.class, HttpClientType.WMSClient);
        }

    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("login")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<LoginDto> login(@Field("username") String username, @Field("password") String password);

    /**
     * 登录
     * @param companyName 公司名称
     * @param account 账号名称
     * @param nick 昵称
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("register")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<LoginDto> register(@Field("companyName") String companyName, @Field("account") String account,
                                  @Field("nick") String nick, @Field("password") String password);

    /**
     * 校验商品条码
     * @param barcode 条码
     * @param company_id 公司ID
     */
    @GET("checkItemBarcode")
    Observable<CheckItemBarcodeDto> checkItemBarcode(@Query("barcode") String barcode,
                                                     @Query("company_id") String company_id);

    /**
     * 校验库位
     * @param loc 库位
     * @param company_id 公司ID
     */
    @GET("checkLoc")
    Observable<CheckLocDto> checkLoc(@Query("locCode") String loc,
                                     @Query("company_id") String company_id);

    /**
     * 录入商品
     * @param barcode 条码
     * @param company_id 公司ID
     * @param amount 数量
     * @param loc 库位
     */
    @FormUrlEncoded
    @POST("addItem")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<AddItemDto> addItem(@Field("barcode") String barcode,
                                   @Field("company_id") String company_id,
                                   @Field("amount") float amount, @Field("loc") String loc);
}
