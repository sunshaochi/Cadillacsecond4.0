package cadillac.example.com.cadillac.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by bitch-1 on 2017/4/1.
 */

public class TimeUtil {


    /**
     * 与当前时间对比
     *
     * @param context
     * @param datess
     * @return
     */
    public static String compareTime(Context context, Long datess) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Long nowss = new Date().getTime();
        Long times = datess - nowss;
        if (times >= 0) {
            return format.format(new Date(datess));
        } else {
            MyToastUtils.showShortToast(context, "时间不得小于当前时间，请重新选择！");
            return "";
        }
    }

    /**
     * 获取当前小时
     * @return
     */
    public static String currTimeHouse(){
        SimpleDateFormat format = new SimpleDateFormat("HH");
        return format.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    /**
     * String 类型时间和当前时间相比较
     */
    public static int comparestringTime(String time)  {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        try {
            c1.setTime(df.parse(time));
            c2.setTime(df.parse(getCurrentTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c1.compareTo(c2);

    }

    /**通过年，月 获取最后一天
     * @param year
     * @param month
     */
    public static String getLastday(String year,String month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();
        Long day=lastDate.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd");
        return format.format(new Date(day));

    }
}
