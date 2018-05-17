package com.ist.cadillacpaltform.SDK.network;

import com.google.gson.JsonObject;
import com.ist.cadillacpaltform.SDK.bean.Posm.Question;
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

import retrofit2.http.Body;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by dearlhd on 2016/12/15.
 */
public interface PosmService {
//    //根据年季度获取题目列表
//    @GET("paper/{year}/{quarter}/questions/?IS_FOR_TEST=test")
//    Observable<SimpleQuestionListResponse> getPaperByYearAndQuarter(@Header("Authorization") String auth, @Path("year") int year, @Path("quarter") int quarter);
//
//    @GET("questions/{id}?IS_FOR_TEST=test")
//    Observable<QuestionResponse> getQuestionById(@Header("Authorization") String auth, @Path("id") long id);
//
////    "grade/detailscore/bygrade/{gradeId}lines?IS_FOR_TEST=test"
//    @GET("grade/detailscore/bygrade/{gradeId}?IS_FOR_TEST=test")
//    Observable<AbstractGradeDetailResponse> getAbstractGradeDetails(@Header("Authorization") String auth, @Path("gradeId") long gradeId, @Query("showtype") int showType);
//
//    //修改gradedetail
//    @PATCH("gradedetails/{id}?IS_FOR_TEST=test")
//    Observable<GradeDetailResponse> modifyGradeDetail(@Header("Authorization") String auth, @Path("id") long gradeDetailId, @Body JsonObject gradeDetail);
//
//    //获取gradeDetail
//    @GET("gradedetails/{id}?IS_FOR_TEST=test")
//    Observable<GradeDetailResponse> getGradeDetail(@Header("Authorization") String auth, @Path("id") long gradeDetailId);
//
//    @GET("grade/totalscore/{dealerId}/{year}/{quarter}/before/3?IS_FOR_TEST=test")
//    Observable<AbstractMarkResponse> getDealerScoreByTime(@Header("Authorization") String auth, @Path("dealerId") long dealerId, @Path("year") int year, @Path("quarter") int quarter);
//
//    @GET("grade/totalscore/latest/{dealerId}?IS_FOR_TEST=test")
//    Observable<AbstractMarkResponse> getRecentScoreByDealer(@Header("Authorization") String auth, @Path("dealerId") long dealerId);
//
//    @GET("grade/modulescore/bygrade/{gradeId}?IS_FOR_TEST=test")
//    Observable<ModuleGradeResponse> getModuleGrade(@Header("Authorization") String auth, @Path("gradeId") long gradeId);
//
//    @GET("grade/totalscore/{year}/{quarter}/bydealer/bypage?IS_FOR_TEST=test")
//    Observable<DealerGradeResponse> getDealerGradeList(@Header("Authorization") String auth, @Path("year") int year, @Path("quarter") int quarter, @QueryMap Map<String, Object> args);
//
//    @GET("grade/quarterlist?IS_FOR_TEST=test")
//    Observable<QuarterResponse> getQuarterList(@Header("Authorization") String auth);
//
//    @GET("zones?IS_FOR_TEST=test")
//    Observable<PosmZoneResponse> getZones(@Header("Authorization") String auth);
//
//    @POST("grade?IS_FOR_TEST=test")
//    Observable<GradeResponse> postGrade(@Header("Authorization") String auth, @Body JsonObject dealerId);
//
//    @GET("rectifications/searchByGradeDetail?IS_FOR_TEST=test")
//    Observable<RectificationResponse> getRectificationByGradeDetailId(@Header("Authorization") String auth, @Query("id") long gradeDetailId);
//
//    @POST("rectifications?IS_FOR_TEST=test")
//    Observable<RectificationListResponse> postRectification(@Header("Authorization") String auth, @Body JsonObject rectification);
//
//    @PATCH("rectifications/{id}?IS_FOR_TEST=test")
//    Observable<RectificationResponse> patchRectification(@Header("Authorization") String auth, @Path("id") long rectificationId, @Body JsonObject rectification);


    //根据年季度获取题目列表
    @GET("paper/{year}/{quarter}/questions/?IS_FOR_TEST=test")
    Observable<SimpleQuestionListResponse> getPaperByYearAndQuarter(@Header("Authorization") String auth, @Path("year") int year, @Path("quarter") int quarter);

    @GET("questions/{id}?IS_FOR_TEST=test")
    Observable<QuestionResponse> getQuestionById(@Header("Authorization") String auth, @Path("id") long id);

    @GET("grade/detailscore/bygrade/{gradeId}?IS_FOR_TEST=test")
    Observable<AbstractGradeDetailResponse> getAbstractGradeDetails(@Header("Authorization") String auth, @Path("gradeId") long gradeId, @Query("showtype") int showType);

    //修改gradedetail
    @PATCH("gradedetails/{id}?IS_FOR_TEST=test")
    Observable<GradeDetailResponse> modifyGradeDetail(@Header("Authorization") String auth, @Path("id") long gradeDetailId, @Body JsonObject gradeDetail);

    //获取gradeDetail
    @GET("gradedetails/{id}?IS_FOR_TEST=test")
    Observable<GradeDetailResponse> getGradeDetail(@Header("Authorization") String auth, @Path("id") long gradeDetailId);

    @GET("grade/totalscore/{dealerId}/{year}/{quarter}/before/3?IS_FOR_TEST=test")
    Observable<AbstractMarkResponse> getDealerScoreByTime(@Header("Authorization") String auth, @Path("dealerId") long dealerId, @Path("year") int year, @Path("quarter") int quarter);

    @GET("grade/totalscore/latest/{dealerId}?IS_FOR_TEST=test")
    Observable<AbstractMarkResponse> getRecentScoreByDealer(@Header("Authorization") String auth, @Path("dealerId") long dealerId);

    @GET("grade/modulescore/bygrade/{gradeId}?IS_FOR_TEST=test")
    Observable<ModuleGradeResponse> getModuleGrade(@Header("Authorization") String auth, @Path("gradeId") long gradeId);

    @GET("grade/totalscore/{year}/{quarter}/bydealer/bypage?IS_FOR_TEST=test")
    Observable<DealerGradeResponse> getDealerGradeList(@Header("Authorization") String auth, @Path("year") int year, @Path("quarter") int quarter, @QueryMap Map<String, Object> args);

    @GET("grade/quarterlist?IS_FOR_TEST=test")
    Observable<QuarterResponse> getQuarterList(@Header("Authorization") String auth);

    /*zones?IS_FOR_TEST=test*/
    @GET("dealer/largeArea?IS_FOR_TEST=test")
    Observable<PosmZoneResponse> getZones(@Header("Authorization") String auth);

    @POST("grade?IS_FOR_TEST=test")
    Observable<GradeResponse> postGrade(@Header("Authorization") String auth, @Body JsonObject dealerId);

    @GET("rectifications/searchByGradeDetail?IS_FOR_TEST=test")
    Observable<RectificationResponse> getRectificationByGradeDetailId(@Header("Authorization") String auth, @Query("id") long gradeDetailId);

    @POST("rectifications?IS_FOR_TEST=test")
    Observable<RectificationListResponse> postRectification(@Header("Authorization") String auth, @Body JsonObject rectification);

    @PATCH("rectifications/{id}?IS_FOR_TEST=test")
    Observable<RectificationResponse> patchRectification(@Header("Authorization") String auth, @Path("id") long rectificationId, @Body JsonObject rectification);
}
