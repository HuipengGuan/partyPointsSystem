package com.yocent.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public DateUtil() {
    }
 
    // 格式化日期为字符串 "yyyy-MM-dd   hh:mm"
    public static String formatDateTime(Date basicDate, String strFormat) {
        SimpleDateFormat df = new SimpleDateFormat(strFormat);
        return df.format(basicDate);
    }
 
    // 格式化日期为字符串 "yyyy-MM-dd   hh:mm"
    public static String formatDateTime(String basicDate, String strFormat) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(strFormat);
        Date tmpDate = df.parse(basicDate);
        return df.format(tmpDate);
    }
 
    // 当前日期加减n天后的日期，返回String (yyyy-mm-dd)
    public String nDaysAftertoday(int n) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        // rightNow.add(Calendar.DAY_OF_MONTH,-1);
        rightNow.add(Calendar.DAY_OF_MONTH, +n);
        return df.format(rightNow.getTime());
    }
 
    // 当前日期加减n天后的日期，返回String (yyyy-mm-dd)
    public Date nDaysAfterNowDate(int n) {
        Calendar rightNow = Calendar.getInstance();
        // rightNow.add(Calendar.DAY_OF_MONTH,-1);
        rightNow.add(Calendar.DAY_OF_MONTH, +n);
        return rightNow.getTime();
    }
 
    // 给定一个日期型字符串，返回加减n天后的日期型字符串
    public String nDaysAfterOneDateString(String basicDate, int n) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date tmpDate = null;
        try {
            tmpDate = df.parse(basicDate);
        } catch (Exception e) {
            // 日期型字符串格式错误
        }
        long nDay = (tmpDate.getTime() / (24 * 60 * 60 * 1000) + 1 + n)
                * (24 * 60 * 60 * 1000);
        tmpDate.setTime(nDay);
 
        return df.format(tmpDate);
    }
 
    // 给定一个日期，返回加减n天后的日期
    public Date nDaysAfterOneDate(Date basicDate, int n) {
        long nDay = (basicDate.getTime() / (24 * 60 * 60 * 1000) + 1 + n)
                * (24 * 60 * 60 * 1000);
        basicDate.setTime(nDay);
 
        return basicDate;
    }
 
    // 计算两个日期相隔的天数
    public int nDaysBetweenTwoDate(Date firstDate, Date secondDate) {
        int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
        return nDay;
    }
 
    // 计算两个日期相隔的天数
    public int nDaysBetweenTwoDate(String firstString, String secondString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date firstDate = null;
        Date secondDate = null;
        try {
            firstDate = df.parse(firstString);
            secondDate = df.parse(secondString);
        } catch (Exception e) {
            // 日期型字符串格式错误
        }
 
        int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
        return nDay;
    }
    
    
    public static java.sql.Timestamp getSqlDate(java.util.Date date) {
    	java.sql.Timestamp da = new java.sql.Timestamp(date.getTime());
    	return da;
    }
    
    /**
     * 获取sqldate，用以存入数据库
     * @param basicDate 存入日期
     * @param strFormat 日期格式
     * @return
     */
    public static Timestamp getSqlDate(String basicDate, String strFormat){
    	SimpleDateFormat df = new SimpleDateFormat(strFormat);
    	Timestamp ts = null;
    	try {
			ts = DateUtil.getSqlDate(df.parse(basicDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return ts;
    }
    
    public static Timestamp getSqlDate2(String basicDate, String strFormat) throws ParseException{
    	SimpleDateFormat df = new SimpleDateFormat(strFormat);
    	Timestamp ts = null;
		ts = DateUtil.getSqlDate(df.parse(basicDate));
    	return ts;
    }
    
    public static Date parseDateTime(String basicDate, String strFormat) throws ParseException {
    	SimpleDateFormat df = new SimpleDateFormat(strFormat);
        return df.parse(basicDate);
    }
    
    public static String formatTime(Long basicDate, String strFormat) {
    	SimpleDateFormat df = new SimpleDateFormat(strFormat);
    	return df.format(basicDate);
    }

    /**
     * 获取某月的最大天数
     * @param year
     * @param month
     * @return
     */
	public static int getDaysByYearMonth(int year, int month) {
		Calendar a = Calendar.getInstance(); 
		int maxDay = a.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(maxDay);
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        return a.getActualMaximum(Calendar.DATE);  
	}

	/**
	 * 获取指定日期的本周一
	 * @param unknow 管它是星期几
	 * @return 我只要本周一
	 */
	public static Date getThisWeekMonday(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        // 获得当前日期是一个星期的第几天  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);  
        if (1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        cal.setFirstDayOfWeek(Calendar.MONDAY);  
        // 获得当前日期是一个星期的第几天  
        int day = cal.get(Calendar.DAY_OF_WEEK);  
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);  
        return cal.getTime();  
    }

}