package com.ist.cadillacpaltform.SDK.network;

import com.google.gson.JsonObject;
import com.ist.cadillacpaltform.SDK.bean.Account;
import com.ist.cadillacpaltform.SDK.bean.Authorization;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.PurchaseCar;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarColorListResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarConfigListResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarInfoListResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarInfoResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarLineListResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.DealerResponse;
import com.ist.cadillacpaltform.SDK.response.NoBodyEntity;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.OrderInfoResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.OrderResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.SubZoneListResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.ZoneListResponse;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by dearlhd on 2016/12/15.
 */
public interface HighWarehouseAgeService {

//    CarPlatform

    /**
     * 登录
     */
    @Headers({"Content-type:application/json"})
    @POST("account/login")
    Observable<Authorization> login(@Body Account account);

    /**
     * 获取所有车系
     */
    @GET("lines?IS_FOR_TEST=test")
    Observable<CarLineListResponse> getAllLines();

    /**
     * 根据车系获取配置
     */
    @GET("configurations/byline?IS_FOR_TEST=test")
    Observable<CarConfigListResponse> getConfigByLine(@Query("line") long lineId);

    /**
     * 获取某个配置的所有颜色
     */
    @GET("configurations/{id}/colors?IS_FOR_TEST=test")
    Observable<CarColorListResponse> getColorByConfig(@Path("id") long configId);

    /**
     * 根据条件查询、筛选车辆
     *
     * @param options 参数集
     */
    @GET("cars/search?IS_FOR_TEST=test")
    Observable<CarInfoListResponse> searchCarInfo(@QueryMap Map<String, Object> options);

    /**
     * 根据ID获取车辆信息,return信息多于CarInfoResponse不会有问题
     */
    @GET("cars/{id}?IS_FOR_TEST=test")
    Observable<CarInfoResponse> getCarById(@Path("id") long carId);

    /**
     * 获取所有大区
     */
    @GET("dealer/largeArea?IS_FOR_TEST=test")
    Observable<ZoneListResponse> getZones();

    /**
     * 根据大区获取所有的子分区
     *
     * @param zoneId 大区
     */
    @GET("dealer/smallArea?IS_FOR_TEST=test")
    Observable<SubZoneListResponse> getSubzoneByZone(@Query("zone") long zoneId);

    /**
     * 根据名称搜索经销商
     */
    @GET("dealer/search?IS_FOR_TEST=test")
    Observable<DealerResponse> getDealerByName(@Query("name") String name);

    /**
     * 根据经销商获取车辆
     */
    @GET("cars/bydealer?IS_FOR_TEST=test")
    Observable<CarInfoListResponse> getCarsByDealer(@Header("Authorization") String auth, @QueryMap Map<String, Object> options);

    /**
     * 获取经销商所有被请求的车辆， 不分页
     * 实际返回的是所有被请求车辆的请求订单信息，包括了请求车辆的经销商信息、被请求车辆的具体信息
     * 该订单类的id用于后续的交易确认或取消的请求
     */
    @GET("cars/requested?IS_FOR_TEST=test")
    Observable<OrderResponse> getRequestedCars(@Header("Authorization") String auth);

    /**
     * 发出车辆的购买请求
     *
     * @param carId 汽车Id
     * @return
     */
    @POST("cars/{id}/purchase?IS_FOR_TEST=test")
    Observable<PurchaseCar> purchaseCar(@Header("Authorization") String auth, @Path("id") long carId);

    /**
     * 同意购车请求
     */
    @POST("orders/{id}/confirm?IS_FOR_TEST=test")
    Observable<NoBodyEntity> confirmTrade(@Header("Authorization") String auth, @Path("id") long orderId);

    /**
     * 拒绝购车请求
     */
    @POST("orders/{id}/refuse?IS_FOR_TEST=test")
    Observable<NoBodyEntity> denyTrade(@Header("Authorization") String auth, @Path("id") long orderId);

    /**
     * 删除车辆信息
     */
    @DELETE("cars/{id}?IS_FOR_TEST=test")
    Observable<NoBodyEntity> deleteCar(@Header("Authorization") String auth, @Path("id") long carId);

    /**
     * 修改车辆信息
     */
    @Headers({"Content-type:application/json"})
    @PATCH("cars/{id}?IS_FOR_TEST=test")
    Observable<CarInfoResponse> saveCar(@Header("Authorization") String auth, @Path("id") long carId, @Body JsonObject carInfo);

    /**
     * 增加车辆信息,给carInfo有多余信息不会造成错误
     */
    @Headers({"Content-type:application/json"})
    @POST("cars?IS_FOR_TEST=test")
    Observable<CarInfoResponse> addCar(@Header("Authorization") String auth, @Body JsonObject carInfo);

    /**
     * 根据订单ID查找订单
     */
    @GET("orders/{id}?IS_FOR_TEST=test")
    Observable<OrderInfoResponse> getOrderById(@Path("id") long orderId);

    /**
     * 分页获取mac管理的所有车辆
     */
    @GET("cars/bymac?IS_FOR_TEST=test")
    Observable<CarInfoListResponse> getCarsByMac (@Header("Authorization") String auth, @QueryMap Map<String, Object> args);

    /**
     * MAC获取所有经销商
     */
    @GET("dealers/bymac?IS_FOR_TEST=test")
    Observable<DealerResponse> getDealerByMac (@Header("Authorization") String auth, @QueryMap Map<String, Object> args);

    /**
     * 更改经销商状态
     */
    @PATCH("dealers/{id}/flag?IS_FOR_TEST=test")
    Observable<DealerResponse> changeDealerStatus (@Header("Authorization") String auth, @Path("id") long dealerId);
}
