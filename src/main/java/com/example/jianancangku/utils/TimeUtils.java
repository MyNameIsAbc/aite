package com.example.jianancangku.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by l on 2018/3/6.
 */

public class TimeUtils {
//    int year= Integer.parseInt(getCurrentYY());
//    int month= Integer.parseInt(getCurrentMM());
//    int day= Integer.parseInt(getCurrentDD());



    //毫秒 获取当前时间0点
    public static long getTime000(long time) {
        return time / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
    }
    //秒 获取当前时间0点
    @SuppressLint("DefaultLocale")
    public static long getTime(long time) {
        return getTime000(time)/1000;
    }
    public static String getCurrentHHSS() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }
    public static String getCurrentYYMMDD() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }
    public static String getCurrentYY() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }
    public static String getCurrentMM() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }
    public static String getCurrentDD() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static String getMongoDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
        String s = format.format(new Date());
        return s;
    }

    public static String utcTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd'T'kk':'mm':'ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("utc"));
        String gmtTime = sdf.format(new Date());
        return gmtTime;
    }

    public static Date utc2local(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("utc"));
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            LogUtils.e(e.getCause() + e.getMessage());
            e.printStackTrace();
        }
        return date;
    }

    public static String getUTCCurrHours() {
        Calendar zoreDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        int i = zoreDate.get(Calendar.HOUR_OF_DAY);
        return String.valueOf(i);
    }

    public static String getUTCCurrMinute() {
        Calendar zoreDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        int i = zoreDate.get(Calendar.MINUTE);
        return String.valueOf(i);
    }

    public static String getUTCCurrSecond() {
        Calendar zoreDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        int i = zoreDate.get(Calendar.SECOND);
        return String.valueOf(i);
    }

    public static long getZoreTimeMs() {
        Calendar zoreDate = Calendar.getInstance();
        zoreDate.set(Calendar.HOUR_OF_DAY, 0);
        zoreDate.set(Calendar.MINUTE, 0);
        zoreDate.set(Calendar.SECOND, 0);
        zoreDate.set(Calendar.MILLISECOND, 0);
        return zoreDate.getTimeInMillis();
    }

    public static long getUTCZoreTimeMs() {
        Calendar zoreDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        //zoreDate.setTimeZone();
        //zoreDate.setTimeInMillis(System.currentTimeMillis());
        zoreDate.set(Calendar.HOUR_OF_DAY, 0);
        zoreDate.set(Calendar.MINUTE, 0);
        zoreDate.set(Calendar.SECOND, 0);
        zoreDate.set(Calendar.MILLISECOND, 0);
        return zoreDate.getTimeInMillis();
    }

    public static long getUTCCurrMs() {
        Calendar zoreDate = Calendar.getInstance();
        zoreDate.setTimeZone(TimeZone.getTimeZone("utc"));
        return zoreDate.getTimeInMillis();
    }

    public static String getCurrTimeZone() {
        return TimeZone.getDefault().getDisplayName(true, TimeZone.SHORT);
    }

    public static String getCurrTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        return time;
    }

    public static void setTime(Context context, int y, int month, int d, int h, int m, int s, int mill) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, d);
        c.set(Calendar.HOUR_OF_DAY, h);
        c.set(Calendar.MINUTE, m);
        c.set(Calendar.SECOND, s);
        c.set(Calendar.MILLISECOND, mill);

        long when = c.getTimeInMillis();
        if (when / 1000 < Integer.MAX_VALUE) {
            try {
                ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(when);
            } catch (Exception e) {
                LogUtils.e(e.getMessage() + e.getCause());
            }
        }
    }

    /**
     * @param context
     * @param zone    GMT+8:00
     */
    public static void setTimeZone(Context context, String zone) {
        try {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTimeZone(zone);
        } catch (Exception e) {
            LogUtils.e(e.getMessage() + e.getCause());
        }
    }
    /*
     * 将时间转换为时间戳
     */
    public String dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long timeMillis){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }
}
