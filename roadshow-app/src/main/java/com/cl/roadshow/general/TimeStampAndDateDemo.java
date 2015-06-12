package com.cl.roadshow.general;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期和时间戳转换功能演示
 * 
 */
public class TimeStampAndDateDemo {

    public static void main(String[] args) {
        String timeStampSource = timeStamp();
        System.out.println("timeStampFrom=" + timeStampSource);

        String date = timeStamp2Date(timeStampSource, "yyyy-MM-dd HH:mm:ss");
        System.out.println("date=" + date);

        String timeStampTarget = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");
        System.out.println("timeStampTarget=" + timeStampTarget);
    }

    /**
     * 时间戳转换成日期格式字符串
     * 
     * @param seconds
     *            精确到秒的字符串
     * @param formatStr
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty())
            format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     * 
     * @param date
     *            字符串日期
     * @param format
     *            如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     * 
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

}
