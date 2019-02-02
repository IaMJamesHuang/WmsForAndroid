package com.kt.james.wmsforandroid.net;

import com.kt.james.wmsforandroid.app.input.AddItemDto;
import com.kt.james.wmsforandroid.app.layout.GetLayoutsDto;
import com.kt.james.wmsforandroid.app.layout.PostLayoutsDto;
import com.kt.james.wmsforandroid.app.login.LoginDto;
import com.kt.james.wmsforandroid.app.offshelf.OffShelfDto;
import com.kt.james.wmsforandroid.app.scan.dto.CheckItemBarcodeDto;
import com.kt.james.wmsforandroid.app.scan.dto.CheckLocDto;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
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

    /**
     * 商品下架
     * @param barcode 条码
     * @param company_id 公司ID
     * @param amount 数量
     * @param loc 库位
     */
    @FormUrlEncoded
    @POST("offShelf")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<OffShelfDto> offShelfItem(@Field("barcode") String barcode,
                                         @Field("company_id") String company_id,
                                         @Field("amount") float amount, @Field("loc") String loc);

    /**
     * 获取本公司对应的库位
     * @param company_id 公司ID
     */
    @GET("layoutAJust")
    Observable<GetLayoutsDto> getLayoutInfos(@Query("company_id") String company_id);

    /**
     * 提交布局信息
     * @param jsonData 布局信息
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST ("layoutAJust")
    Observable<PostLayoutsDto> postLayoutInfos(@Body RequestBody jsonData);

}
