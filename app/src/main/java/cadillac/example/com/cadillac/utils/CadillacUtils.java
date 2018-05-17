package cadillac.example.com.cadillac.utils;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cadillac.example.com.cadillac.MyApplication;
import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.SplashAct;
import cadillac.example.com.cadillac.bean.LoginBean;
import cadillac.example.com.cadillac.view.xrecyclerview.ProgressStyle;
import cadillac.example.com.cadillac.view.xrecyclerview.XRecyclerView;

/**
 * Created by bitch-1 on 2017/3/17.
 */
public class CadillacUtils {

    /**
     * 获取测试List
     *
     * @param length
     * @return
     */
    public static List<String> getTestDatas(int length) {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            datas.add(i + "");
        }
        return datas;
    }

    /**
     * 初始化下拉刷新
     *
     * @param context
     * @return
     */
    public static XRecyclerView getLinearRecyclerView(XRecyclerView rcv, Context context, boolean isCanLoadMore) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rcv.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        rcv.setArrowImageView(R.drawable.iconfont_downgrey);
        rcv.setLoadingMoreEnabled(isCanLoadMore);//设置上拉加载false为加载
        rcv.setLayoutManager(linearLayoutManager);
        return rcv;
    }


    /**
     * 获取设备id
     *
     * @param context
     * @return
     */
    public static String getUid(Context context) {

        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /**
     * 获取手机屏幕宽
     *
     * @param activity
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // float density = metrics.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        // int densityDpi = metrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        return metrics.widthPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取当前登录用户
     */
    public static LoginBean getCurruser() {
        List<LoginBean> list = MyApplication.getInstances().getDaoSession().getLoginBeanDao().loadAll();
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }


    /**
     * 将2017-11 转换成 十一月 2017
     *
     * @param selecttime
     * @return
     */
    public static String forMatetime(String selecttime) {
        String yy = selecttime.split("-")[0];
        String month = selecttime.split("-")[1];
        if (month.equals("01") || month.equals("1")) {
            return "一月  " + yy;
        }
        if (month.equals("02") || month.equals("2")) {
            return "二月  " + yy;
        }
        if (month.equals("03") || month.equals("3")) {
            return "三月  " + yy;
        }
        if (month.equals("04") || month.equals("4")) {
            return "四月  " + yy;
        }
        if (month.equals("05") || month.equals("5")) {
            return "五月  " + yy;
        }
        if (month.equals("06") || month.equals("6")) {
            return "六月  " + yy;
        }
        if (month.equals("07") || month.equals("7")) {
            return "七月  " + yy;
        }
        if (month.equals("08") || month.equals("8")) {
            return "八月  " + yy;
        }
        if (month.equals("09") || month.equals("9")) {
            return "九月  " + yy;
        }
        if (month.equals("10") || month.equals("10")) {
            return "十月  " + yy;
        }
        if (month.equals("11") || month.equals("11")) {
            return "十一月  " + yy;
        }
        if (month.equals("12") || month.equals("12")) {
            return "十二月  " + yy;
        }

        return "";
    }


    /**
     * 将01转成1
     *
     * @param dateKey
     * @return
     */
    public static String numFormart(String dateKey) {
        if (dateKey.equals("01")) {
            return "1";
        }
        if (dateKey.equals("02")) {
            return "2";
        }
        if (dateKey.equals("03")) {
            return "3";
        }
        if (dateKey.equals("04")) {
            return "4";
        }
        if (dateKey.equals("05")) {
            return "5";
        }
        if (dateKey.equals("06")) {
            return "6";
        }
        if (dateKey.equals("07")) {
            return "7";
        }
        if (dateKey.equals("08")) {
            return "8";
        }
        if (dateKey.equals("09")) {
            return "9";
        }

        return dateKey;
    }

    /**
     * "Jan","Feb","Mar","Q1","Apr","May","Jun","Q2","Jul","Aug","Sep","Q3","Oct","Nov","Dec","Q4"
     *
     * @param dateKey
     * @return
     */
    public static String monthFromat(String dateKey) {

        if (dateKey.equals("01") || dateKey.equals("1")) {
            return "Jan";
        } else if (dateKey.equals("02") || dateKey.equals("2")) {
            return "Feb";
        } else if (dateKey.equals("03") || dateKey.equals("3")) {
            return "Mar";
        } else if (dateKey.equals("04") || dateKey.equals("4")) {
            return "Apr";
        } else if (dateKey.equals("05") || dateKey.equals("5")) {
            return "May";
        } else if (dateKey.equals("06") || dateKey.equals("6")) {
            return "Jun";
        } else if (dateKey.equals("07") || dateKey.equals("7")) {
            return "Jul";
        } else if (dateKey.equals("08") || dateKey.equals("8")) {
            return "Aug";
        } else if (dateKey.equals("09") || dateKey.equals("9")) {
            return "Sep";
        } else if (dateKey.equals("10") || dateKey.equals("10")) {
            return "Oct";
        } else if (dateKey.equals("11") || dateKey.equals("11")) {
            return "Nov";
        } else if (dateKey.equals("12") || dateKey.equals("12")) {
            return "Dec";
        }

        return dateKey;

    }


    /**
     * 手机号码验证,11位，不知道详细的手机号码段，只是验证开头必须是1和位数
     */
    public static boolean checkCellPhone(String cellPhoneNr) {
        String reg = "^[1][\\d]{10}";//只判断第一位和11位数
//        String reg = "^[1][34578][\\d]{9}";
        return startCheck(reg, cellPhoneNr);

    }

    public static boolean startCheck(String reg, String string) {
        boolean tem = false;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(string);

        tem = matcher.matches();
        return tem;
    }
}
