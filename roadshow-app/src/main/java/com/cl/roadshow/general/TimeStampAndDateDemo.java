package com.cl.roadshow.general;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期和时间戳转换功能演示
 * 
 */
public class TimeStampAndDateDemo {

    public static void main(String[] args) {
        Long timeStampSource = timeStamp();
        System.out.println("timeStampFrom=" + timeStampSource);

        String date = timeStamp2Date(timeStampSource, "yyyy-MM-dd HH:mm:ss");
        System.out.println("date=" + date);
        
        String dateMilli = timeStamp2Date(timeStampSource, "yyyy-MM-dd HH:mm:ss.FFF");
        System.out.println("dateMilli=" + dateMilli);

        Long timeStampTarget = date2TimeStamp(dateMilli, "yyyy-MM-dd HH:mm:ss.FFF");
        System.out.println("timeStampTarget=" + timeStampTarget);
    }

    /**
     * 时间戳转换成到日期格式字符串
     * 
     * @param milliSeconds
     *            精确到毫秒的字符串
     * @param formatStr
     * @return
     */
    public static String timeStamp2Date(long milliSeconds, String format) {
        if (format == null || format.isEmpty())
            format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(milliSeconds));
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
    public static Long date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            //TODO：这里没有毫秒了，有空看看
            Date d = sdf.parse(date_str);
            return d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 取得当前时间戳（精确到毫秒）
     * 
     * @return
     */
    public static Long timeStamp() {
        long time = System.currentTimeMillis();
        return time;
    }

}
