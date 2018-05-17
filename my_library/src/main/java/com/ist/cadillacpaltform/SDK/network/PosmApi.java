package com.ist.cadillacpaltform.SDK.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ist.cadillacpaltform.SDK.bean.Authorization;
import com.ist.cadillacpaltform.SDK.bean.Posm.Question;
import com.ist.cadillacpaltform.SDK.bean.Posm.Rectification;
import com.ist.cadillacpaltform.SDK.response.Posm.AbstractGradeDetailResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.AbstractMarkResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.DealerGradeResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.GradeDetailResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.GradeResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.ModuleGradeResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.PosmZoneResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.QuarterResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.QuestionResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.RectificationListResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.RectificationResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.SimpleQuestionListResponse;
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
 * @Description POSM部分网络请求的接口类
 * 使用retrofit网络框架
 * 使用rxJava框架实现异步操作
 * 通过线程控制实现单例
 */
public class PosmApi {

//    public static final String BASEURL = "http://115.28.28.219:8080/POSMPlatform/";
    //public static final String BASEURL = "http://115.159.78.97:8080/POSMPlatform/";

//    public static final String BASEURL = "http://121.43.34.185:7522/POSMPlatform/";//正式

//    public static final String BASEURL = "http://192.168.2.101:8080/noneLiveWeb/";//朱力
//        public static final String BASEURL ="http://192.168.2.100:8080/noneLiveWeb/";//开发服务无线

//    public static final String BASEURL ="http://121.43.34.185:6069/noneLiveWeb/";//远程

//    public static final String BASEURL ="http://47.97.11.213:8080/noneLiveWeb/";//正式

//    public static final String BASEURL ="http://47.97.11.213:8080/cadillac/";//后来正式环境

    public static final String BASEURL ="http://192.168.2.108:8080/cadillac/";
    private static volatile PosmApi mPosmApi;

    private static Retrofit mRetrofit;
    private static PosmService mService;

    private PosmApi() {
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
            mService = mRetrofit.create(PosmService.class);
        }
    }

    public static PosmApi getInstance() {
        if (mPosmApi == null) {
            // 保证单例
            synchronized (PosmApi.class) {
                if (mPosmApi == null) {
                    mPosmApi = new PosmApi();
                }
            }
        }

        return mPosmApi;
    }

    /**
     * 根据年份季度获取试卷
     *
     * @param year
     * @param quarter
     */
    public void getPaperByYearAndQuarter(Subscriber<SimpleQuestionListResponse> subscriber, int year, int quarter) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;
        mService.getPaperByYearAndQuarter(auth, year, quarter)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 根据id获取问题信息
     *
     * @param subscriber
     * @param id
     */
    public void getQuestionById(Subscriber<QuestionResponse> subscriber, long id) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;
        mService.getQuestionById(auth, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 查看获得分数细节
     *
     * @param gradeId  打分Id
     * @param showType 显示类型（1: 仅显示未得满分项目 0: 显示全部项目）
     */
    public void getAbstractGradeDetails(Subscriber<AbstractGradeDetailResponse> subscriber, long gradeId, int showType) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.getAbstractGradeDetails(auth, gradeId, showType)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改gradeDetail
     *
     * @param subscriber
     * @param gradeDetailId
     * @param gradeDetail
     */
    public void modifyGradeDetails(Subscriber<GradeDetailResponse> subscriber, long gradeDetailId, JsonObject gradeDetail) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;
        mService.modifyGradeDetail(auth, gradeDetailId, gradeDetail)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获得gradeDetail
     *
     * @param subscriber
     * @param gradeDetailId
     */
    public void getGradeDetails(Subscriber<GradeDetailResponse> subscriber, long gradeDetailId) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;
        mService.getGradeDetail(auth, gradeDetailId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 根据年份、季度、经销商Id获取前lastN个季度(不包括当前)的总分情况
     *
     * @param dealerId 经销商Id
     * @param year     年份
     * @param quarter  季度
     */
    public void getDealerScoreByTime(Subscriber<AbstractMarkResponse> subscriber, long dealerId, int year, int quarter) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.getDealerScoreByTime(auth, dealerId, year, quarter)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获得最近四个季度的总分情况
     * @param dealerId  经销商id
     */
    public void getRecentScoreByDealer(Subscriber<AbstractMarkResponse> subscriber, long dealerId) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.getRecentScoreByDealer(auth, dealerId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 根据模块获取分数信息
     *
     * @param gradeId 打分id
     */
    public void getModuleGrade(Subscriber<ModuleGradeResponse> subscriber, long gradeId) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.getModuleGrade(auth, gradeId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取Posm主页面中的dealer和grade信息
     *
     * @param year    年
     * @param quarter 季度
     * @param map     参数
     */
    public void getDealerGradeList(Subscriber<DealerGradeResponse> subscriber, int year, int quarter, Map<String, Object> map) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;

        mService.getDealerGradeList(auth, year, quarter, map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取最近的三个季度
     */
    public void getQuarterList(Subscriber<QuarterResponse> subscriber) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;
        mService.getQuarterList(auth)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 根据身份获取大区
     */
    public void getZones(Subscriber<PosmZoneResponse> subscriber) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;
        Log.i("dong", auth);

        mService.getZones(auth)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 新增打分
     *
     * @param subscriber
     */
    public void postGrade(Subscriber<GradeResponse> subscriber, long dealerId) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;
        Log.i("dong", auth);

        JsonObject object = new JsonObject();
        Gson gson = new Gson();
        object.add("dealerid", gson.toJsonTree(dealerId));

        JsonObject object2 = new JsonObject();
        object2.add("data", object);

        mService.postGrade(auth, object2)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getRectificationByGradeDetailId(Subscriber<RectificationResponse> subscriber, long gradeDetailId) {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;
        mService.getRectificationByGradeDetailId(auth, gradeDetailId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void postRectification(Subscriber<RectificationListResponse> subscriber, Rectification rectification) {
        JsonObject GradeDetailObject = new JsonObject();
        GradeDetailObject.addProperty("id", rectification.getGradeDetail().getId());
        JsonObject object1 = new JsonObject();
        object1.add("detail", GradeDetailObject);
        object1.addProperty("description", rectification.getDescription());
        object1.addProperty("picurl1", rectification.getPicurl1());
        object1.addProperty("picurl2", rectification.getPicurl2());
        object1.addProperty("picurl3", rectification.getPicurl3());
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(object1);
        JsonObject submitObject = new JsonObject();
        submitObject.add("data", jsonArray);
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;
        mService.postRectification(auth, submitObject)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void patchRectification(Subscriber<RectificationResponse> subscriber, long rectificationId, Rectification rectification) {
        JsonObject object1 = new JsonObject();
        object1.addProperty("description", rectification.getDescription());
        object1.addProperty("picurl1", rectification.getPicurl1());
        object1.addProperty("picurl2", rectification.getPicurl2());
        object1.addProperty("picurl3", rectification.getPicurl3());
        JsonObject submitObject = new JsonObject();
        submitObject.add("data", object1);
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        String auth = authorization.authorization;
        mService.patchRectification(auth, rectificationId, submitObject)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
