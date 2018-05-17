package cadillac.example.com.cadillac.http.Manage;

import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.bean.JpUloadBean;
import cadillac.example.com.cadillac.bean.ListBean;
import cadillac.example.com.cadillac.bean.LoadBean;
import cadillac.example.com.cadillac.bean.RetailfoBean;
import cadillac.example.com.cadillac.bean.UpLoadBean;
import cadillac.example.com.cadillac.bean.UpjxsLoadBean;
import cadillac.example.com.cadillac.http.CadillacUrl;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.DateAndTimeUtils;
import cadillac.example.com.cadillac.utils.GsonUtils;
import cadillac.example.com.cadillac.utils.SpUtils;

/**
 * 用户
 * Created by bitch-1 on 2017/3/28.
 */

public class UserManager {
    private static UserManager userManager;

    public static UserManager getUserManager() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    /**登录接口
     * @param
     * @param
     * @param
     * @param
     * @param resultCallback
     */
    public void toLogin(String userName,String password,ResultCallback resultCallback){
        Map<String, String> params = new HashMap<>();
        params.put("userName",userName);
        params.put("password",password);

        OkHttpManager.getInstance().doPostForjson(CadillacUrl.LOGIN_URL,params,resultCallback);
    }

    /**
     * 首页获取当天数据
     * @param dealerCode
     * @param dateStr
     */
    public void getDayinfo(String dealerCode,String dateStr,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("dealerCode",dealerCode);
        params.put("dateStr",dateStr);

        OkHttpManager.getInstance().doPostForjson(CadillacUrl.DAYINFO_URL,params,resultCallback);
    }


    /**
     * 获取全部车型
     */
    public void getCarModle(ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();

        OkHttpManager.getInstance().doPostForjson(CadillacUrl.ALLCARMODLE_URL,params,resultCallback);
    }


    /**报表获取日的数据
     * @param dateStr
     * @param queryType 0-日数据,1-月数据,2-年数据
     * @param carModelId 车系Id过滤
     * @param compareData 比较数据选择  0-新增来店数,1-订单数
     * @param resultCallback
     * @param compareType 如果是查询余额数据，可以做比较，0-同比,1-环比
     *  {"dateStr":"2017-12-15","queryType":1,"compareType":1,"compareData":0}
     */
    public void getdaydate(String dateStr,String queryType,String carModelId,String compareType,String compareData,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("dateStr",dateStr+"-01");
        params.put("queryType",queryType);
        if(!TextUtils.isEmpty(carModelId)) {
            params.put("carModelId", carModelId);
        }
        if(!TextUtils.isEmpty(compareType)) {
            params.put("compareType", compareType);
        }
        if(!TextUtils.isEmpty(compareData)) {
            params.put("compareData", compareData);//新增来电订单数字
        }
        OkHttpManager.getInstance().doPostForjson(CadillacUrl.KLINEMONTH_URL,params,resultCallback);

    }



    /**报表获取月的数据
     * @param dateStr
     * @param queryType 0-日数据,1-月数据,2-年数据
     * @param carModelId 车系Id过滤
     * @param compareData 客流0订单1
     * @param resultCallback
     * @param compareType 如果是查询余额数据，可以做比较，0-同比,1-环比
     *  {"dateStr":"2017-12-15","queryType":1,"compareType":1,"compareData":0}
     */
    public void getmonthdate(String dateStr,String queryType,String carModelId,String compareType,String compareData,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("dateStr",dateStr);
        params.put("queryType",queryType);
        if(!TextUtils.isEmpty(carModelId)) {
            params.put("carModelId", carModelId);
        }
        if(!TextUtils.isEmpty(compareType)) {
            params.put("compareType", compareType);
        }
        if(!TextUtils.isEmpty(compareData)) {
            params.put("compareData", compareData);
        }
        OkHttpManager.getInstance().doPostForjson(CadillacUrl.KLINEMONTH_URL,params,resultCallback);
    }


    /**报表获取年的数据（日月年的接口其实只有一个，等参数确定后在换成一个即可）
     * @param dateStr
     * @param queryType 0-日数据,1-月数据,2-年数据
     * @param carModelId 车系Id过滤
     * @param compareData 比较数据选择  0-新增来店数,1-订单数
     * @param resultCallback
     * @param compareType 如果是查询余额数据，可以做比较，0-同比,1-环比
     *  {"dateStr":"2017-12-15","queryType":1,"compareType":1,"compareData":0}
     */
    public void getyeardate(String dateStr,String queryType,String carModelId,String compareType,String compareData,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("dateStr",dateStr);
        params.put("queryType",queryType);
        if(!TextUtils.isEmpty(carModelId)) {
            params.put("carModelId", carModelId);
        }
        if(!TextUtils.isEmpty(compareType)) {
            params.put("compareType", compareType);
        }
        if(!TextUtils.isEmpty(compareData)) {
            params.put("compareData", compareData);//新增来电订单数字
        }
        OkHttpManager.getInstance().doPostForjson(CadillacUrl.KLINEMONTH_URL,params,resultCallback);

    }


    /**
     * 获取上传数据接口
     * @param dateStr
     * @param dealerId
     * @param resultCallback
     */
    public void getLoadDaily(String dateStr,String dealerId,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("dateStr", dateStr);
        params.put("dealerId",dealerId);
        OkHttpManager.getInstance().doPostForjson(CadillacUrl.LOADDAILYDATA_URL,params,resultCallback);


    }


    /**
     * 上传
     * @param list
     * @param resultCallback
     */
    public void upLoadinfo(List<LoadBean>list,ResultCallback resultCallback) {
        List<LoadBean>jsonlist=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            jsonlist.add(list.get(i));
        }
        OkHttpManager.getInstance().doPostForjsonfromList(CadillacUrl.UPDATEDAILYDATA_URL,jsonlist,resultCallback);
    }


    /**
     * 点击报表获取详情三屏幕
     * @param dateStr
     * @param queryType
     * @param dealerCode
     * @param
     * @param
     * @param resultCallback
     */
    public void getDailyInfo(String dateStr,String queryType,String dealerCode,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("dateStr",dateStr);
        params.put("queryType",queryType);
        params.put("dealerCode",dealerCode);
        OkHttpManager.getInstance().doPostForjson(CadillacUrl.DETAILINFO_URL,params,resultCallback);

    }

    /**
     * 获取上下级
     * @param parentId
     * @param isDelete
     * @param resultCallback
     */
    public void screenRole(String parentId,String isDelete,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("parentId",parentId);
        params.put("isDelete",isDelete);
        OkHttpManager.getInstance().doPostForjson(CadillacUrl.PARENTID_URL,params,resultCallback);
    }


    /**
     * 获取经销商裸车毛利
     * @param userName
     * @param resultCallback
     */
    public void getJxsLm(String userName,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", userName);
        OkHttpManager.getInstance().doPost(CadillacUrl.NEWGETJXSLM_URL,params,resultCallback);
    }

    /**经销商角色获取裸车毛利编辑数据
     * @param userName
     * @param resultCallback
     */
    public void getEditorLm(String userName,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", userName);
        OkHttpManager.getInstance().doPost(CadillacUrl.NEWGETEDITORLM_URL,params,resultCallback);
    }


    /**上传经销商角色的裸车毛利
     * @param upjxsloadbean
     * @param resultCallback
     */
    public void upLoadLuoc(UpjxsLoadBean upjxsloadbean,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("data",GsonUtils.bean2Json(upjxsloadbean));
        OkHttpManager.getInstance().doPost(CadillacUrl.NEWUPLOADLUOC_URL,params,resultCallback);
    }

    /**
     * http://192.168.2.105:8080/noneLiveWeb/dealer/queryDealersByDataType?IS_FOR_TEST=test&dataType=big
     * 注册获取大区
     * @param resultCallback
     */
    public void getQylist(ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("IS_FOR_TEST","test");
        params.put("dataType","big");
        OkHttpManager.getInstance().doGet(CadillacUrl.QUERYDEALER_URL,params,resultCallback);
    }


    /**
     *
     * 注册获取经销商
     * @param resultCallback
     */
    public void getJxsNamelist(String code,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("IS_FOR_TEST","test");
        params.put("code",code);
        OkHttpManager.getInstance().doGet(CadillacUrl.QUERYDEALERJXS_URL,params,resultCallback);
    }


    /**
     *获取职务
     * @param resultCallback
     */
    public void getZhiwulist(ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("IS_FOR_TEST","test");
        params.put("type","dealer");
        OkHttpManager.getInstance().doGet(CadillacUrl.QUERYDEALERZW_URL,params,resultCallback);
    }

    /**
     *注册
     * @param
     */
    public void toRegistUse(String dealerId,String roleId,String userName,String personName,String mobileNo,String loginVerson,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("dealerId",dealerId);
        params.put("roleId",roleId);
        params.put("userName",userName);
        params.put("personName",personName);
        params.put("mobileNo",mobileNo);
        params.put("loginVerson",loginVerson);
        OkHttpManager.getInstance().doPostForjson(CadillacUrl.QUERYDEALERTJ_URL,params,resultCallback);
    }




    /**
     * 获取经销商零售预测数据
     * @param userName
     * @param resultCallback
     */
    public void getRetailfo(String userName,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("userName",userName);
        OkHttpManager.getInstance().doPost(CadillacUrl.SERVLET_URL,params,resultCallback);
    }


    /**保存经销商零售预测
     * @param
     * @param
     * @param
     * @param resultCallback
     */
    public void upLoadRe(UpLoadBean uploadbean, ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
//        params.put("userName",userName);
//        params.put("inputTimeId",inputTimeId);
        params.put("data", GsonUtils.bean2Json(uploadbean));
        OkHttpManager.getInstance().doPost(CadillacUrl.UPDATESERVLET_URL,params,resultCallback);
    }

    /**
     * 获取mac零售预测
     * @param userName
     * @param resultCallback
     */
    public void getMacRetaInfo(String userName,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("userName",userName);
        OkHttpManager.getInstance().doPost(CadillacUrl.MACPRIDICT_URL,params,resultCallback);
    }


    /**点击mac获取下一级
     * @param param
     * @param flag
     * @param resultCallback
     */
    public void getHqMacRegional(String param,String flag,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("param", param);
        params.put("flag", flag);
        OkHttpManager.getInstance().doPost(CadillacUrl.GETREGIONMAC_URL,params,resultCallback);
    }


    /**
     * 裸车毛利获取inputtimeid
     * @param inputTimeId
     * @param timeType
     * @param resultCallback
     */
    public void getInPutTime(String inputTimeId, String timeType,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        if(!TextUtils.isEmpty(inputTimeId)) {
            params.put("inputTimeId", inputTimeId);
        }
        if(!TextUtils.isEmpty(inputTimeId)) {
            params.put("timeType", timeType);
        }
        OkHttpManager.getInstance().doPost(CadillacUrl.INPUTTIMEINFO_URL,params,resultCallback);
    }


    /**分享发送短信
     * @param userName
     * @param dateTime
     * @param resultCallback
     */
    public void dealerCount(String userName,String dateTime,String dateType,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", userName);
        params.put("dateTime", dateTime);
        params.put("dateType",dateType);
        OkHttpManager.getInstance().doGet(CadillacUrl.DEALERCOUNT_URL,params,resultCallback);
    }




    /**竞品价格保存
     * @param uloadbean
     * @param resultCallback
     */
    public void getbaoChun(JpUloadBean uloadbean,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("dataList",GsonUtils.bean2Json(uloadbean));
        OkHttpManager.getInstance().doPost(CadillacUrl.GETBAOCHUN_URL,params,resultCallback);

    }

    /**获取竞品价格省份和车型
     * @param userName
     * @param carBrand
     * @param carClass
     * @param province
     * @param city
     * @param resultCallback
     */
    public void getProbrand(String userName,String carBrand,String carClass,String province,String city,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("userName",userName);
        if(!TextUtils.isEmpty(carBrand)) {
            params.put("carBrand", carBrand);
        }
        if(!TextUtils.isEmpty(carClass)) {
            params.put("carClass", carClass);
        }
        if(!TextUtils.isEmpty(province)) {
            params.put("province", province);
        }
        if(!TextUtils.isEmpty(city)) {
            params.put("city", city);
        }
        OkHttpManager.getInstance().doPost(CadillacUrl.GETPROBRAND_URL,params,resultCallback);
    }

    /**
     * 竞品价格编辑数据
     * @param userName
     * @param carBrand
     * @param carClass
     * @param resultCallback
     */
    public void getJinpinInfo(String userName,String carBrand,String carClass,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("userName",userName);
        if(!TextUtils.isEmpty(carBrand)) {
            params.put("carBrand", carBrand);
        }
        if(!TextUtils.isEmpty(carClass)) {
            params.put("carClass", carClass);
        }
        OkHttpManager.getInstance().doPost(CadillacUrl.GETJINPININFO,params,resultCallback);

    }


    /**
     * 查看申请列表
     * @param
     * @param
     * @param
     * @param resultCallback
     */
    public void getlist(String admin,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("admin",admin);
        OkHttpManager.getInstance().doPost(CadillacUrl.GETLIST_URL,params,resultCallback);
    }


    /**
     * 查看申请同意拒绝按钮
     * @param id
     * @param state
     * @param resultCallback
     */
    public void through(String id,String state,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("id",id);
        params.put("state",state);
        OkHttpManager.getInstance().doPostForjson(CadillacUrl.CHECK_URL,params,resultCallback);
    }



    /**
     * 裸车毛利趋势图
     * @param userName
     * @param carType
     * @param month
     * @param inputTimeId
     * @param resultCallback
     */
    public void getQstData(String userName,String carType,String month,String inputTimeId,String dateId,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("userName",userName);
        if(!carType.equals("全部")) {
            params.put("carType", carType);
        }
        params.put("month", month);
        params.put("dateId",dateId);
        params.put("inputTimeId",inputTimeId);
        OkHttpManager.getInstance().doGet(CadillacUrl.GETQSTDATA_URL,params,resultCallback);
    }















    /**--------------------------------------------------------------*/




    /**
     * 获取职位现参数都是写死的,获取区域也是这个接口知识参数不同
     * @param op
     * @param obj
     * @param resultCallback
     */
    public void findJob(String op,String obj,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("op",op);
        params.put("obj",obj);
        OkHttpManager.getInstance().doPost(CadillacUrl.NEWUSER_URL,params,resultCallback);
    }


    /**获取经销商名称
     * @param op
     * @param obj
     * @param district Cadillac一区
     * @param resultCallback
     */
    public void findDealer(String op,String obj,String district,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("op",op);
        params.put("obj",obj);
        params.put("district",district);
        OkHttpManager.getInstance().doPost(CadillacUrl.NEWUSER_URL,params,resultCallback);
    }

    /**
     * 注册
     * @param op register
     * @param obj ToBeApproved
     * @param username nousername
     * @param name 名字
     * @param Telephone 手机
     * @param position 经销商销售经理
     * @param dealer 福建中凯
     * @param dealergroup “”
     * @param district Cadillac一区
     */
    public void toRegist(String op,String obj,String username,String name,String Telephone,String position,String dealer,String dealergroup,String district,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("op",op);
        params.put("obj",obj);
        params.put("username",username);
        params.put("name",name);
        params.put("Telephone",Telephone);
        params.put("position",position);
        params.put("dealer",dealer);
        params.put("dealergroup",dealergroup);
        params.put("district",district);
        OkHttpManager.getInstance().doPost(CadillacUrl.NEWUSER_URL,params,resultCallback);
    }

    /**
     * @param
     * @param
     * @param
     * @param
     * @param resultCallback
     */
    public void findPwd(String userName,String mobileNo,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("userName",userName);
        params.put("mobileNo",mobileNo);
        OkHttpManager.getInstance().doPostForjson(CadillacUrl.NEWUSER_URL,params,resultCallback);
    }

    /**
     * 获取首页时间
     */
    public void getTime(ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        OkHttpManager.getInstance().doPost(CadillacUrl.DOWNSETTING_URL,params,resultCallback);
    }

    /**
     * 展厅数据 show 改成否 就是车展数据
     * @param date 2017-3-30
     * @param store 测试经销商(经销商name)
     * @param role 经销商销售经理(职务)
     * @param show 是
     */
    public void getZtData(String date,String store,String role,String show,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("date",date);
        params.put("store",store);
        params.put("role",role);
        params.put("show",show);
        OkHttpManager.getInstance().doPost(CadillacUrl.SALESDATA_URL,params,resultCallback);
    }

    /**
     * @param date 2017-3-31
     * @param store 测试经销商
     * @param secRoom 二次进店
     * @param room 进店
     * @param order 订单
     * @param retail 交车
     * @param total 月累计
     * @param retain 留存
     * @param model 车型
     * @param is 是（表示展厅）
     * @param unsubscribe 退订
     * @param resultCallback
     */
    public void pullDate(String date,String store,String secRoom,String room,String order,String retail,String total,String retain,String model,String is,String unsubscribe,ResultCallback resultCallback ) {
        Map<String, String> params = new HashMap<>();
        params.put("date",date);
        params.put("store",store);
        if(TextUtils.isEmpty(secRoom)){params.put("secRoom","");}else {params.put("secRoom", secRoom);}
        if(TextUtils.isEmpty(room)){params.put("room","");}else {params.put("room",room);}
        if(TextUtils.isEmpty(order)) {params.put("order","");}else {params.put("order",order);}
        if(!TextUtils.isEmpty(retail)) {params.put("retail",retail);}else {params.put("retail","");}
        if(!TextUtils.isEmpty(total)) {params.put("total",total);}else {params.put("total","");}
        if(!TextUtils.isEmpty(retain)){params.put("retain",retain);}else {params.put("retain","");}
        if(!TextUtils.isEmpty(model)){params.put("model",model);}else {params.put("model","");}
        params.put("is",is);
        if(!TextUtils.isEmpty(model)){ params.put("unsubscribe",unsubscribe);}else {params.put("unsubscribe","");}
        OkHttpManager.getInstance().doPost(CadillacUrl.UPSALESDAILY_URL,params,resultCallback);
    }

    /**
     * 判断用户是否在线
     * @param user 登录名
     * @param uid 设备id
     * @param resultCallback
     */
    public void Judgonline(String user,String uid,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("user",user);
        params.put("uid",uid);
        OkHttpManager.getInstance().doPost(CadillacUrl.UCHECKALIVE_URL,params,resultCallback);
    }

    /**
     *管理员获取详情
     * @param name 经销商名字
     * @param role 职务
     * @param date 时间
     * @param resultCallback
     */
    public void getinfo(String name,String role,String date,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("name",name);
        params.put("role",role);
        params.put("date",date);
        OkHttpManager.getInstance().doPost(CadillacUrl.REPORTDETAIL_URL,params,resultCallback);
    }

    /**发送短信
     * @param op sendsms
     * @param obj message
     * @param telephone 电话
     * @param content  内容
     */
    public void sendMessage(String op,String obj,String telephone,String content, ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("op",op);
        params.put("obj",obj);
        params.put("telephone",telephone);
        params.put("content",content);
        OkHttpManager.getInstance().doPost(CadillacUrl.SENDSMS_URL,params,resultCallback);
    }








    /**点击报表（天）后的详情
     * @param date
     * @param store
     * @param role
     * @param which
     */
    public void dayinfo(String date, String store, String role, String which,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("date",date);
        params.put("store",store);
        params.put("role",role);
        params.put("which",which);
        OkHttpManager.getInstance().doPost(CadillacUrl.REPORTDAY_URL,params,resultCallback);
    }

    /**击报表（月）后的详情
     * @param date 时间
     * @param store
     * @param role
     * @param which
     * @param tthis
     * @param resultCallback
     */
    public void monthinfo(String date,String store,String role,String which,String tthis,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("date",date);
        params.put("store",store);
        params.put("role",role);
        params.put("which",which);
        params.put("this",tthis);
        OkHttpManager.getInstance().doPost(CadillacUrl.REPORTMON_URL,params,resultCallback);
    }

    /**击报表（季度）后的详情
     * @param date 时间
     * @param store
     * @param role
     * @param which
     * @param tthis
     * @param resultCallback
     */
    public void quarterinfo(String date,String store,String role,String which,String tthis,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("date",date);
        params.put("store",store);
        params.put("role",role);
        params.put("which",which);
        params.put("this",tthis);
        OkHttpManager.getInstance().doPost(CadillacUrl.REPORTQTR_URL,params,resultCallback);
    }

    /**点击报表（年）后的详情
     * @param date
     * @param store
     * @param role
     * @param which
     * @param tthis
     */
    public void yearinfo(String date, String store, String role, String which, String tthis,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("date",date);
        params.put("store",store);
        params.put("role",role);
        params.put("which",which);
        params.put("this",tthis);
        OkHttpManager.getInstance().doPost(CadillacUrl.REPORTYEA_URL,params,resultCallback);
    }

    /**更新信息
     * @param
     * @param
     * @param
     * @param
     * @param
     * @param resultCallback
     */
    public void updateinfo(String userName,String newMobileNo,String mobileNo,String personName,String newPersonName,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("userName",userName);
        params.put("newMobileNo",newMobileNo);
        params.put("mobileNo",mobileNo);
        params.put("personName",personName);
        params.put("newPersonName",newPersonName);
        OkHttpManager.getInstance().doPostForjson(CadillacUrl.UPDATE_URL,params,resultCallback);
    }



    /**修改密码
     * @param
     * @param
     * @param
     * @param
     * @param
     * @param resultCallback
     */
    public void updatePwd(String userId,String userOldPassWord,String userNewPassWord,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("userId",userId);
        params.put("userOldPassWord",userOldPassWord);
        params.put("userNewPassWord",userNewPassWord);
        OkHttpManager.getInstance().doPostForjson(CadillacUrl.UPDATEPWD_URL,params,resultCallback);
    }

    /**获取经销商个数和精品个数（传递参数不一样）
     * @param op
     * @param obj
     * @param username
     * @param resultCallback
     */
    public void findNum(String op,String obj,String username,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("op",op);
        params.put("obj",obj);
        params.put("username",username);
        OkHttpManager.getInstance().doPost(CadillacUrl.FINDNUM_URL,params,resultCallback);
    }

    /**获取车型简称
     * @param all
     * @param resultCallback
     */
    public void findChexin(String all,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("all",all);
        OkHttpManager.getInstance().doPost(CadillacUrl.GETADD_URL,params,resultCallback);
    }



    /**查询点击对话通过后的对话框
     * @param op
     * @param obj
     * @param username
     * @param telephone
     * @param resultCallback
     */
    public void checkDialog(String op,String obj,String username,String telephone,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("op",op);
        params.put("obj",obj);
        params.put("username",username);
        params.put("telephone",telephone);
        OkHttpManager.getInstance().doPost(CadillacUrl.CHECK_URL,params,resultCallback);
    }





    /**
     * mac(大区)获取经销商零售预测大区数据，小区数据等
     */
    public void getRegional(String param,String flag,ResultCallback resultCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("param", param);
        params.put("flag", flag);
        OkHttpManager.getInstance().doPost(CadillacUrl.GETREGION_URL,params,resultCallback);
    }










    /**角色为Mac大区总部的裸车毛利
     * @param userName
     * @param dataType
     * @param inputTimeId
     * @param
     * @param resultCallback
     */
    public void getMacluoc(String userName,String dataType,String inputTimeId,String dateId,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("userName",userName);
        params.put("dataType",dataType);
        params.put("inputTimeId",inputTimeId);
        params.put("dateId",dateId);
        OkHttpManager.getInstance().doPost(CadillacUrl.NEWMACLUOC_URL,params,resultCallback);
    }

    /**角色为大区和mac的裸车毛利获取大区list
     * @param flag
     * @param param
     * @param inputTimeId
     * @param dataType
     * @param resultCallback
     */
    public void getMacluocDqlist(String flag,String param,String inputTimeId,String dataType,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("flag",flag);
        params.put("param",param);
        params.put("inputTimeId",inputTimeId);
        params.put("dataType",dataType);
        OkHttpManager.getInstance().doPost(CadillacUrl.NEWGETMACLUOCDQLIST_URL,params,resultCallback);
    }



    /**通过省份查询下面城市
     * @param province
     * @param resultCallback
     */
    public void getCityList(String province,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("province",province);
        OkHttpManager.getInstance().doPost(CadillacUrl.GETCITYLIST,params,resultCallback);


    }







    /**裸车毛利和经销商零售预测
     * @param userName
     * @param inputTimeId
     * @param inputType
     * @param resultCallback
     */
    public void getUnInput(String userName,String inputTimeId,String inputType,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("userName",userName);
        params.put("inputTimeId",inputTimeId);
        params.put("inputType",inputType);
        OkHttpManager.getInstance().doPost(CadillacUrl.GETUNINPUT_URL,params,resultCallback);

    }

    /**
     * 筛选角色查看（总部人员获取大区数据，大区经理获取小区）
     * @param role
     * @param region
     */
    public void shaiXuan(String role,String region,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("role",role);
        if(!TextUtils.isEmpty(region)) {
            params.put("region", region);
        }
        OkHttpManager.getInstance().doPost(CadillacUrl.ALLREGION_URL,params,resultCallback);
    }

    /**
     * 筛选角色查看（总部人员获取下面的小区）
     * @param
     * @param region
     */
    public void dqXiaoqu(String region,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("region", region);
        OkHttpManager.getInstance().doPost(CadillacUrl.ALLAREABYREGION_URL,params,resultCallback);
    }

    /**点击计算时候需要的请求
     * @param date
     * @param store
     * @param role
     * @param show
     * @param resultCallback
     */
    public void getCalculate(String date,String store,String role,String show,ResultCallback resultCallback) {
        Map<String,String>params=new HashMap<>();
        params.put("date",date);
        params.put("store",store);
        params.put("role",role);
        params.put("show",show);
        OkHttpManager.getInstance().doPost(CadillacUrl.GETCALCULATEDATA_URL,params,resultCallback);
    }



}
