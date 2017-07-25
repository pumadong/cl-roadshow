package com.cl.roadshow.joda;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

/**
 * org.joda.time.DateTime demo
 * @author dongyongjin
 *
 */
public class DateTimeDemo {

	public static void main(String[] args) {
		
		// 日期和字符串转换
		DateTime dt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime("2017-07-25 10:49:50");
		System.out.println(dt.toString("MM/dd/yyyy hh:mm:ss.SSSa"));
		System.out.println(dt.toString("dd-MM-yyyy HH:mm:ss"));
		System.out.println(dt.toString("EEEE dd MMMM, yyyy HH:mm:ssa"));
		System.out.println(dt.toString("MM/dd/yyyy HH:mm ZZZZ"));
		System.out.println(dt.toString("MM/dd/yyyy HH:mm Z"));
		System.out.println(dt.toString("yyyy/MM/dd HH:mm:ss EE"));
		
		System.out.println("\n");
		
		// 日期数字
		System.out.println("dt.getYear() : " + dt.getYear());
		System.out.println("dt.getYearOfCentury() : " + dt.getYearOfCentury());
		System.out.println("dt.getYearOfEra() : " + dt.getYearOfEra());
		System.out.println("dt.getMonthOfYear() : " + dt.getMonthOfYear());
		System.out.println("dt.getDayOfMonth() : " + dt.getDayOfMonth());
		System.out.println("dt.getDayOfWeek() : " + dt.getDayOfWeek());
		System.out.println("dt.getDayOfYear() : " + dt.getDayOfYear());
		System.out.println("dt.getMinuteOfHour() : " + dt.getMinuteOfHour());
		
		// 时间增减
		dt = dt.plusMinutes(1);
		dt = dt.plusHours(-1);
		System.out.println(dt.toString("yyyy/MM/dd HH:mm:ss EE"));
		
		System.out.println("\n");
		
		System.out.println("\n");
		//计算两个日期间隔的天数  
        LocalDate start = new LocalDate(2017, 7, 28);
        LocalDate end = new LocalDate(2017, 7, 29);    
        int days = Days.daysBetween(start, end).getDays();
        System.out.println("days : " + days);
        
        DateTime dt1 = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime("20170725104901");
        DateTime dt2 = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime("20170725114959");
        // 计算区间毫秒数
        Duration d = new Duration(dt1, dt2);
		System.out.println("d.getMillis() : " + d.getMillis());
		System.out.println("d.getStandardSeconds() : " + d.getStandardSeconds());
		System.out.println("d.getStandardMinutes() : " + d.getStandardMinutes());
		System.out.println("d.getStandardHours() : " + d.getStandardHours());
		System.out.println("d.getStandardDays() : " + d.getStandardDays());
		// 分别按照某个时间刻度（比如小时、分钟刻度）计算差值
		System.out.println("\n");
		Period p = new Period(dt1, dt2);
		System.out.println("p.getMillis() : " + p.getMillis());
		System.out.println("p.getSeconds() : " + p.getSeconds());
		System.out.println("p.getMinutes() : " + p.getMinutes());
		System.out.println("p.getHours() : " + p.getHours());
		System.out.println("p.getDays() : " + p.getDays());
		// 计算特定日期是否在该区间内
		System.out.println("\n");
		Interval interval = new Interval(dt1, dt2);  
		boolean contained = interval.contains(new DateTime(dt1.getMillis())); 
		System.out.println("contained : " + contained);
	}
	
}
