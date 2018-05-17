package cadillac.example.com.cadillac.http;

/**
 * Created by bitch-1 on 2017/3/27.
 */

public class CadillacUrl {


//    public static final String BASE_URL ="http://192.168.1.159:8080/noneLiveWeb/";//庄老师
//      public static final String BASE_URL ="http://192.168.91.186:8080/noneLiveWeb/";//开发服务有线
//      public static final String BASE_URL ="http://192.168.2.100:8080/noneLiveWeb/";//开发服务无线
//      public static final String BASE_URL ="http://121.43.34.185:6069/noneLiveWeb/";//远程
//      public static final String BASE_URL ="http://192.168.2.101:8080/noneLiveWeb/";//朱莉
//        public static final String BASE_URL ="http://192.168.2.100:8080/";//顾老师

//    public static final String BASE_URL ="http://192.168.2.100:8080/noneLiveWeb/";//顾老师

//    public static final String BASE_URL ="http://47.97.11.213:8080/noneLiveWeb/";//正式


//    public static final String BASE_URL ="http://47.97.11.213:8080/cadillac/";//后来正式环境

//    public static final String BASE_URL ="http://192.168.2.105:8080/cadillac/";//庄老师
    public static final String BASE_URL ="http://192.168.2.108:8080/cadillac/";//朱立

//    public static final String BASE_URL ="http://192.168.2.101:8080/cadillac/";//朱立

    /*-----------------------------------------------------------------------------*/
    /*登录*/
    public static final String LOGIN_URL = BASE_URL + "login/appLogin";
    /*首页获取数据三屏*/
    public static final String DAYINFO_URL = BASE_URL + "dealerDailyDataApp/dataHome";

    /*获取全部车型*/
    public static final String ALLCARMODLE_URL = BASE_URL + "carModel/queryAllCarModel";

    /*获取日的数据*/
    public static final String KLINEMONTH_URL=BASE_URL+"dealerDailyDataApp/reportHome";

    /*获取上传的数据*/
    public static final String LOADDAILYDATA_URL=BASE_URL+"dealerDailyDataApp/loadDailyData";


    /*保存上传*/
    public static final String UPDATEDAILYDATA_URL=BASE_URL+"dealerDailyDataApp/updateDailyData";



    /*点击报表详情*/
    public static final String DETAILINFO_URL=BASE_URL+"dealerDailyDataApp/reportDetail";

    /*获取上下级*/
//    public static final String PARENTID_URL=BASE_URL+"dealer/queryDealersByParentId";
    public static final String PARENTID_URL=BASE_URL+"dealer/queryDealersByParentIdWithUnsubmitCnt";




    /*获取竞品价格的省份和车型*/
    public static final String GETPROBRAND_URL =BASE_URL+"competCar/getRegionCompete";

    /*角色为Mac大区总部的裸车毛利*/
    public static final String MACLUOC_URL =BASE_URL+"competCar/getDealerProfit";
    public static final String NEWMACLUOC_URL =BASE_URL+"newCompetCar/getDealerProfit";


    /*角色为Mac大区总部的裸车毛利大区集合*/
    public static final String GETMACLUOCDQLIST_URL =BASE_URL+"competCar/getRegionDealerProfit";

    public static final String NEWGETMACLUOCDQLIST_URL =BASE_URL+"newCompetCar/getRegionDealerProfit";

    /*获取经销商裸车毛利界面*/
    public static final String GETJXSLM_URL =BASE_URL+"competCar/getDealerProfit";

    public static final String NEWGETJXSLM_URL =BASE_URL+"newCompetCar/getDealerProfit";



    /*获取经销商裸车毛利编辑界面*/
    public static final String GETEDITORLM_URL =BASE_URL+"competCar/editDealerProfit";

    public static final String NEWGETEDITORLM_URL =BASE_URL+"newCompetCar/editDealerProfit";

    /*保存经销商角色裸车毛利*/
    public static final String UPLOADLUOC_URL =BASE_URL+"competCar/updateDealerProfit";

    public static final String NEWUPLOADLUOC_URL =BASE_URL+"newCompetCar/updateDealerProfit";

    /*获取mac零售预测*/
    public static final String MACPRIDICT_URL =BASE_URL+"macSale/macPridict";


    /*注册获取大区*/
    public static final String QUERYDEALER_URL =BASE_URL+"dealer/queryDealersByDataType";

    /*注册获取经销商*/
    public static final String QUERYDEALERJXS_URL =BASE_URL+"dealer/queryDealersByBigAreaCode";


    /*注册获取职务*/
    public static final String QUERYDEALERZW_URL =BASE_URL+"role/queryRolesByType";

    /*注册提交按钮*/
    public static final String QUERYDEALERTJ_URL =BASE_URL+"loginUser/registerLoginUser?IS_FOR_TEST=test";

    /*经销商角色的经销商零售预测*/
    public static final String SERVLET_URL =BASE_URL+"dealerSale/getDealerPridict";

    /*角色为非经销商的经销商零售预测点击获取大区小区*/
    public static final String GETREGION_URL =BASE_URL+"dealerSale/getRegionDealerPridict";

    /*保存零售预测*/
    public static final String UPDATESERVLET_URL =BASE_URL+"dealerSale/updatePridict";

    /*mac零售预测点击获取下一级别*/
    public static final String GETREGIONMAC_URL =BASE_URL+"macSale/getRegionMacPridict";

    /*竞品价格保存数据*/
    public static final String GETBAOCHUN_URL =BASE_URL+"competCar/editDealerCompete";

    /*裸车毛利和经销商零售预测未录入数据*/
    public static final String GETUNINPUT_URL =BASE_URL+"dealerSale/getNotEnteredDealers";

    /*通过省份查询城市*/
    public static final String GETCITYLIST =BASE_URL+"dealer/getCityByProvince";

    /*裸车毛利获取inputtimeid*/
    public static final String INPUTTIMEINFO_URL =BASE_URL+"dealerProfit/getInputTimeInfo";

    /*裸车毛利获取趋势图*/
    public static final String GETQSTDATA_URL =BASE_URL+"dealerProfit/dealerProfitTrend";

    /*发送短信*/
    public static final String DEALERCOUNT_URL =BASE_URL+"dealerSale/dealerCount";


    /*更新名字和手机*/
    public static final String UPDATE_URL =BASE_URL+"loginUser/updateMobile";

    /*更新密码*/
    public static final String UPDATEPWD_URL =BASE_URL+"loginUser/updatePassword";

    /*竞品价格编辑数据*/
    public static final String GETJINPININFO =BASE_URL+"competCar/getDealerCompete";


    /*查看申请列表*/
    public static final String GETLIST_URL =BASE_URL+"loginUser/applicationRecord";


    /*点击通过后的对话框内容,通过，拒绝*/
    public static final String CHECK_URL =BASE_URL+"loginUser/approve";


    /*忘记密码*/
    public static final String NEWUSER_URL =BASE_URL+"noneLiveWeb/sms/getUserPassword?IS_FOR_TEST=test";
















    /*------------------------------------以上是cadillac新的请求地址------------------------------------*/

    /*获取时间*/
    public static final String DOWNSETTING_URL =BASE_URL+"DownSetting";
    /*首页展厅数据，车展数据*/
    public static final String SALESDATA_URL=BASE_URL+"DownSalesData";
    /*上传数据*/
    public static final String UPSALESDAILY_URL=BASE_URL+"UpSalesDailyData";
    /*检查是否在线*/
    public static final String UCHECKALIVE_URL=BASE_URL+"CheckAlive";
    /*获取详情*/
    public static final String REPORTDETAIL_URL=BASE_URL+"ReportDetail";
    /*发送短信*/
    public static final String SENDSMS_URL="http://121.43.34.185/UserServer/NewUser.aspx";



    /*获取月的数据*/
    public static final String KLINEYEAR_URL=BASE_URL+"V3DownSalesKLineYear";

    /*获取年的数据*/
    public static final String KLINE9YEAR_URL=BASE_URL+"V3DownSalesKLine9Year";


    /*点击报表后获取季度的详细数据*/
    public static final String REPORTQTR_URL=BASE_URL+"V2DownSalesReportQtr";

    /*点击报表后获取月的详细数据*/
    public static final String REPORTMON_URL=BASE_URL+"V2DownSalesReportMon";

    /*点击报表(天)后获取详细数据*/
    public static final String REPORTDAY_URL=BASE_URL+"V2DownSalesReport";



    /*点击报表(年)后获取详细数据*/
    public static final String REPORTYEA_URL=BASE_URL+"V2DownSalesReportYea";


    /*获取经销商个数*/
    public static final String FINDNUM_URL ="http://121.43.34.185/UserServer/NewUser.aspx";
    /*获取车型简称*/
    public static final String GETADD_URL =BASE_URL+"GetAdd";

























    /*下载apk路径*/
    public static final String DOWNAPK ="http://121.43.34.185:7522/Cadillac_app/cadillac.apk";



    /*筛选不同角色查看（只有总部角色，大区经理查看）*/
    public static final String ALLREGION_URL =BASE_URL+"getAllRegion";

    /*总部人员筛选到小区*/
    public static final String ALLAREABYREGION_URL =BASE_URL+"getAllAreaByRegion";

    /*点击计算时候*/
    public static final String GETCALCULATEDATA_URL =BASE_URL+"GetCalculateData";


}
