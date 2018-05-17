package cadillac.example.com.cadillac.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import java.util.HashMap;
import java.util.Map;

import cadillac.example.com.cadillac.activity.SmallHomeAct;
import cadillac.example.com.cadillac.bean.UserBean;

/**
 * Created by wangbin on 17/2/7.
 */
public class SpUtils {

    private final static String CADILLAC_SP = "cadi_sp";
    private static final String COOKIE = "cookie";
    private final static String SCREEN_WITH = "screen_with";


    public static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(CADILLAC_SP, Context.MODE_PRIVATE);
    }

    /**
     * 保存cookie
     *
     * @param context
     * @param cookie
     */
    public static void setCooike(Context context, String cookie) {
        getSp(context).edit().putString(COOKIE, cookie).commit();
    }

    /**
     * 获取cookie
     *
     * @param context
     * @return
     */
    public static String getCookie(Context context) {
        return getSp(context).getString(COOKIE, "");
    }

    /**
     * 清除sp
     *
     * @param context
     */
    public static void clearSp(Context context) {
        getSp(context).edit().clear().commit();
    }

    public static void setScreenWith(Context context, int screenWith) {
        getSp(context).edit().putInt(SCREEN_WITH, screenWith).commit();
    }

    public static int getScreenWith(Context context) {
        return getSp(context).getInt(SCREEN_WITH, 0);
    }

    /**
     * 保存登录参数值用户名和密码
     */
    public static void savenameandpwd(Context context, String user, String pswd) {
        getSp(context).edit().putString("user", user).putString("pswd", pswd).commit();
    }

    // 获取登录的数据
    public static Map<String, String> getnameandpwd(Context context) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user", getSp(context).getString("user", ""));//登录名
        params.put("pswd", getSp(context).getString("pswd", ""));//密码

        return params;
    }


    /**
     * 保存职务
     *
     * @param context
     * @param role
     */
    public static void saverole(Context context, String role) {
        getSp(context).edit().putString("role", role).commit();
    }


    /**
     * 获取职务
     *
     * @param context
     * @return
     */
    public static String getrole(Context context) {
        return getSp(context).getString("role", "");
    }


    /**
     * 设置kpidilog弹出的样式
     *
     * @param context
     * @param type
     */
    public static void setKpitype(Context context, String type) {
        getSp(context).edit().putString("kpitype", type).commit();
    }

    public static String getKpitype(Context context) {
      return getSp(context).getString("kpitype", "0");//默认为0
    }


    /**
     * 设置报表日的时间
     * @param context
     * @param time
     */
    public static void setOneTime(Context context,String time) {
        getSp(context).edit().putString("onetime", time).commit();
    }
    /**
     * 获取报表日的时间
     * @param context
     * @return
     */
    public static String getOneTime(Context context) {
        return getSp(context).getString("onetime",DateAndTimeUtils.getCurrentTime("yyyy-MM"));
    }


    /**
     * 设置报表日的时间
     * @param context
     * @param time
     */
    public static void setTwoTime(Context context,String time) {
        getSp(context).edit().putString("twotime", time).commit();
    }
    /**
     * 获取报表日的时间
     * @param context
     * @return
     */
    public static String getTwoTime(Context context) {
        return getSp(context).getString("twotime",DateAndTimeUtils.getCurrentTime("yyyy"));
    }




    /**
     * 设置报表年的时间
     * @param context
     * @param time
     */
    public static void setThreeTime(Context context,String time) {
        getSp(context).edit().putString("ttime",time).commit();
    }
    /**
     * 获取报表年的时间
     * @param context
     * @return
     */
    public static String getThreeTime(Context context) {
        return getSp(context).getString("ttime",DateAndTimeUtils.getCurrentTime("yyyy")+"");
    }


    /**
     * 设置报车型
     * @param context
     * @param
     */
    public static void setCarModleid(Context context,String carid) {
        getSp(context).edit().putString("carid", carid).commit();
    }
    /**
     * 获取车型（默认全部）
     * @param context
     * @return
     */
    public static String getCarModleid(Context context) {
        return getSp(context).getString("carid","");
    }

    /**设置同比
     * @param context
     */
    public static void setTbOrHb(Context context,String type) {
         getSp(context).edit().putString("tb",type).commit();
    }

    /**获取环比0，同比 1，环比
     * @param context
     * @return
     */
    public static String getTbOrHb(Context context) {
        return getSp(context).getString("tb","-1");
    }

    /**设置客流或者订单
     * @param context
     */
    public static void setKlOrDd(Context context,String type) {
        getSp(context).edit().putString("kl",type).commit();
    }
    /**
     * 获取是客流还是订单0客流，1是订单
     * @param context
     * @return
     */
    public static String getKlOrDd(Context context) {
        return getSp(context).getString("kl","0");
    }

    /**
     * 设置车型名字
     * @param context
     * @param name
     */
    public static void setCarName(Context context,String name) {
        getSp(context).edit().putString("name",name).commit();
    }


    /**
     * 设置车型名字
     * @param context
     * @param
     */
    public static String getCarName(Context context) {
        return getSp(context).getString("name","全部");
    }


    /**
     * 设置详情里面的kpi类型
     * @param context
     * @param s
     */
    public static void setKpiStat(Context context, String s) {
        getSp(context).edit().putString("kpistat",s).commit();
    }

    /**
     * 获取详情里面的kpi类型，默认给0
     * @param context
     * @return
     */
    public static String getKpiStat(Context context) {
       return getSp(context).getString("kpistat","0");
    }


    /**
     * 设置详情里面的kpi的type
     * @param context
     * @param s
     */
    public static void setKpiinfo(Context context, String s) {
        getSp(context).edit().putString("kpiinfo",s).commit();
    }

    /**
     * 获取详情里面的kpi的type
     * @param context
     * @return
     */
    public static String getKpiinfo(Context context) {
        return getSp(context).getString("kpiinfo","0");
    }

    /**
     * 保存inputtimeid
     * @param context
     * @param s
     */
    public static void setInputTimeId(Context context,String s) {
        getSp(context).edit().putString("inputtimeid",s).commit();
    }

    /**
     * 获取inputtimeid
     * @param context
     * @return
     */
    public static String getInputTimeId(Context context){
        return getSp(context).getString("inputtimeid","");
    }


    /**
     * 保存dateid
     * @param context
     * @param s
     */
    public static void setDateId(Context context,String s) {
        getSp(context).edit().putString("dateid",s).commit();
    }

    /**
     * 获取dateid
     * @param context
     * @return
     */
    public static String getDateId(Context context){
        return getSp(context).getString("dateid","");
    }


    /**
     * 保存用户名
     * @param context
     * @param s
     */
    public static void setAcount(Context context,String s) {
        getSp(context).edit().putString("acount",s).commit();

    }

    /**
     * 获取用户名
     * @param context
     * @return
     */
    public static String getAcount(Context context){
        return getSp(context).getString("acount","");
    }


    /**
     * 保存密码
     * @param context
     * @param s
     */
    public static void setPwd(Context context,String s) {
        getSp(context).edit().putString("password",s).commit();

    }

    /**
     * 保存密码
     * @param context
     * @param
     */
    public static String getPwd(Context context) {
        return getSp(context).getString("password","");

    }

}
