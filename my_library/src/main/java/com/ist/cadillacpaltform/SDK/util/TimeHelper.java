package com.ist.cadillacpaltform.SDK.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by czh on 2017/1/7.
 */

public class TimeHelper {
    static public String showCreateTime(String createTime) {//"2016-08-30 15:02:46"转化成2016年8月30日
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(createTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)+1;
            int day = calendar.get(Calendar.DATE);
            return year + "年" + month + "月" + day + "日";
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    static public String showAddTime(String createTime) {//"2016-08-30"转化成2016年8月30日
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(createTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)+1;
            int day = calendar.get(Calendar.DATE);
            return year + "年" + month + "月" + day + "日";
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    static public String ToAddTime(String time) {//2016年8月30日转化成"2016-08-30"
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd");
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)+1;
            int day = calendar.get(Calendar.DATE);
            return year + "-" + month + "-" + day;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    static public String calculateAge(String addTime) {// 计算库龄
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = null;
            Date d2 = new Date();
            d1 = sdf1.parse(addTime);
            return String.valueOf((int) ((d2.getTime() - d1.getTime()) / 1000 / 60 / 60 / 24))+"天";
        } catch (ParseException e) {
            e.printStackTrace();
            return "未知";
        }
    }

    static public String getCurrentTime () {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = 1 + c.get(Calendar.MONTH);  // 系统月份从0开始计算
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        String currentTime = year + "年" + month + "月" + day + "日 "
                + hour + ":" + minute + ":" + second;
        return currentTime;
    }

    static public boolean isLessThan48Hours (String time) {
        if (time == null) {
            return true;
        }

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = 1 + c.get(Calendar.MONTH);  // 系统月份从0开始计算
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date commitTime = sdf.parse(time);
            Date now = sdf.parse(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);

            long between = (now.getTime() - commitTime.getTime()) / 1000; //换算成秒
            int hourDiff = (int)between / 3600;
            if (hourDiff < 48) {
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    static public boolean isCurrentQuarter (int yy, int qq) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = 1 + c.get(Calendar.MONTH);  // 系统月份从0开始计算

        return yy == year && qq == month / 4 + 1;
    }
}
