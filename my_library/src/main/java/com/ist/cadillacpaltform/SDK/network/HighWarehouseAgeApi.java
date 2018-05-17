package com.ist.cadillacpaltform.SDK.network;

import android.util.Log;

import com.google.gson.Gson;
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
import com.ist.cadillacpaltform.SDK.util.SQLiteHelper;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dearlhd on 2016/12/15.
 *
 * @Description 高库龄部分网络请求的接口类
 * 使用retrofit网络框架
 * 使用rxJava框架实现异步操作
 * 通过线程控制实现单例
 */
public class HighWarehouseAgeApi {

//    public static final String BASEURL = "http://115.159.78.97:8080/";//测试

//    public static final String BASEURL = "http://121.43.34.185:7522/";//正式

//    public static final String BASEURL = "http://192.168.2.101:8080/noneLiveWeb/";//朱力

//    public static final String BASEURL ="http://192.168.2.100:8080/noneLiveWeb/";//开发服务无线
//    public static final String BASEURL ="http://121.43.34.185:6069/noneLiveWeb/";//远程

//    public static final String BASEURL ="http://47.97.11.213:8080/noneLiveWeb/";//正式

//    public static final String BASEURL ="http://47.97.11.213:8080/cadillac/";//后来正式环境
      public static final String BASEURL ="http://192.168.2.108:8080/cadillac/";//朱莉
    private static volatile HighWarehouseAgeApi mHighWarehouseAgeApi;

    private static Retrofit mRetrofit;
    private static HighWarehouseAgeService mService;


    private HighWarehouseAgeApi() {
        if (mService == null) {
            if (mRetrofit == null) {
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                        .build();

                mRetrofit = new Retrofit.Builder()
                        .baseUrl(BASEURL)
                        .client(okHttpClient)
                        .addConverterFactory(NoBodyConvertFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
            }
            mService = mRetrofit.create(HighWarehouseAgeService.class);

        }
    }

    public static HighWarehouseAgeApi getInstance() {
        if (mHighWarehouseAgeApi == null) {
            // 保证单例
            synchronized (HighWarehouseAgeApi.class) {
                if (mHighWarehouseAgeApi == null) {
                    mHighWarehouseAgeApi = new HighWarehouseAgeApi();
                }
            }
        }

        return mHighWarehouseAgeApi;
    }


    /**
     * 登录
     *
     * @param subscriber 监听者对象
     */
    public void login(Subscriber<Authorization> subscriber, Account account) {

        mService.login(account)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * 获取所有车系
     *
     * @param subscriber 监听者对象
     */
    public void getAllLines(Subscriber<CarLineListResponse> subscriber) {
        mService.getAllLines()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 根据车系获取配置
     *
     * @param subscriber 监听者对象
     * @param lineId     车系
     */
    public void getConfigByLine(Subscriber<CarConfigListResponse> subscriber, long lineId) {
        mService.getConfigByLine(lineId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取某个配置的所有颜色
     *
     * @param subscriber 监听者对象
     * @param configId   车系
     */
    public void getColorByConfig(Subscriber<CarColorListResponse> subscriber, long configId) {
        mService.getColorByConfig(configId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 根据条件查询、筛选车辆
     * <p/>
     * params:
     * page			// 查询的页数 默认为1
     * size			// 每页显示的数据条数	默认为10
     * dealer 		// 经销商id 默认为0  当通过搜索经销商查看车辆时带上即可， 否则的话不传或传0即可
     * line 			// 车系id  默认为0
     * configuration 	// 配置id  默认为0
     * color 			// 颜色 字符串 默认为"" i.e: 红
     * location		// 省市信息  字符串  默认为"" i.e： 1.上海市闵行区  	2.上海市（市不限时）
     * zone 			// 分区信息  字符串  默认为"" i.e： 1. 一区MAC1		2. 一区(子分区不限时)
     * order_by		// 排序方式  字符串  1. "price": 按价格升序	2. "distance": 按距离（待实现） 3. 其余任何形式及默认都为按发布时间降序
     */

    public void searchCars(Subscriber<CarInfoListResponse> subscriber, Map<String, Object> options) {

        mService.searchCarInfo(options)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 根据ID获取车辆信息
     */
    public void getCarById(Subscriber<CarInfoResponse> subscriber, long carId) {
        mService.getCarById(carId).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取所有大区
     */
    public void getZones(Subscriber<ZoneListResponse> subscriber) {
        mService.getZones()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 根据大区获取所有的子分区
     */
    public void getSubzonesByZone(Subscriber<SubZoneListResponse> subscriber, long zoneId) {
        mService.getSubzoneByZone(zoneId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 根据名称搜索经销商
     */
    public void getDealerByName(Subscriber<DealerResponse> subscriber, String name) {

        mService.getDealerByName(name)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 根据经销商获取车辆
     */
    public void getCarByDealer(Subscriber<CarInfoListResponse> subscriber, Map<String, Object> options) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.getCarsByDealer(auth, options)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取经销商所有被请求的车辆， 不分页
     * 实际返回的是所有被请求车辆的请求订单信息，包括了请求车辆的经销商信息、被请求车辆的具体信息
     * 该订单类的id用于后续的交易确认或取消的请求
     */
    public void getRequestedCars(Subscriber<OrderResponse> subscriber) {

        // 获得身份认证信息
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;
        mService.getRequestedCars(auth)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发出车辆的购买请求
     *
     * @param subscriber
     * @param carId
     */
    public void purchaseCar(Subscriber<PurchaseCar> subscriber, long carId) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.purchaseCar(auth, carId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 同意购车请求
     *
     * @param subscriber
     * @param orderId
     */
    public void ConfirmTrade(Subscriber<NoBodyEntity> subscriber, long orderId) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.confirmTrade(auth, orderId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 拒绝购车请求
     *
     * @param subscriber
     * @param orderId
     */
    public void denyTrade(Subscriber<NoBodyEntity> subscriber, long orderId) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.denyTrade(auth, orderId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 删除车辆
     */
    public void deleteCar(Subscriber<NoBodyEntity> subscriber, long carId) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.deleteCar(auth, carId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改车辆信息
     */
    public void saveCar(Subscriber<CarInfoResponse> subscriber, long carId, JsonObject carInfo) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.saveCar(auth, carId, carInfo)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 增加车辆信息
     */
    public void addCar(Subscriber<CarInfoResponse> subscriber, JsonObject carInfo) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.addCar(auth, carInfo)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 根据订单ID查找订单
     */
    public void getOrderById(Subscriber<OrderInfoResponse> subscriber, long orderId) {
        mService.getOrderById(orderId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 分页获取mac管理的所有车辆
     */
    public void getCarsByMac(Subscriber<CarInfoListResponse> subscriber, Map<String, Object> args) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.getCarsByMac(auth, args)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * MAC获取所有经销商
     */
    public void getDealerByMac(Subscriber<DealerResponse> subscriber, Map<String, Object> args) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.getDealerByMac(auth, args)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 更改经销商状态
     */
    public void changeDealerStatus(Subscriber<DealerResponse> subscriber, long dealerId) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.changeDealerStatus(auth, dealerId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
