package com.mygame.pure.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.mygame.pure.R;

import android.annotation.SuppressLint;
import android.content.Context;


@SuppressLint("SimpleDateFormat")
public class DateUtil {

	public static String getTime(Long time) {
		StringBuilder strTime = new StringBuilder();
		long h = time / 3600;
		long min = time % 3600 / 60;
		long sec = time % 60;
		if (h < 10)
			strTime.append("0" + h + ":");
		else
			strTime.append(h + ":");
		if (min < 10)
			strTime.append("0" + min + ":");
		else
			strTime.append(min + ":");
		if (sec < 10)
			strTime.append("0" + sec);
		else
			strTime.append(sec);
		return strTime.toString();
	}

	public static String getHMTime(Long time) {
		StringBuilder strTime = new StringBuilder();
		long h = time / 3600;
		long min = time % 3600 / 60;
		if (h < 10)
			strTime.append("0" + h + ":");
		else
			strTime.append(h + ":");
		if (min < 10)
			strTime.append("0" + min);
		else
			strTime.append(min);
		return strTime.toString();
	}

	public static String getDateStr(String day, int dayAddNum) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		try {
			nowDate = df.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date newDate2 = new Date(nowDate.getTime() + (long)dayAddNum * 24 * 60 * 60
				* 1000);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateOk = simpleDateFormat.format(newDate2);
		return dateOk;
	}
    /** 
     * 获取两个日期之间的间隔天数 
     * @return 
     */  
    public static int getGapCount(Date startDate, Date endDate) {  
           Calendar fromCalendar = Calendar.getInstance();    
           fromCalendar.setTime(startDate);    
           fromCalendar.set(Calendar.HOUR_OF_DAY, 0);    
           fromCalendar.set(Calendar.MINUTE, 0);    
           fromCalendar.set(Calendar.SECOND, 0);    
           fromCalendar.set(Calendar.MILLISECOND, 0);    
       
           Calendar toCalendar = Calendar.getInstance();    
           toCalendar.setTime(endDate);    
           toCalendar.set(Calendar.HOUR_OF_DAY, 0);    
           toCalendar.set(Calendar.MINUTE, 0);    
           toCalendar.set(Calendar.SECOND, 0);    
           toCalendar.set(Calendar.MILLISECOND, 0);    
       
           return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));  
    } 
    /**
     * 比较2个日期大小
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compare_date(String DATE1, String DATE2) {
        
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


	public static String getMCurrentDate() {
		final Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
		String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
		/*
		 * if(mMonth.length() == 1) { mMonth= "0"+mMonth; } if(mDay.length() ==
		 * 1) { mDay= "0"+mDay; }
		 */
		return mMonth + "月" + mDay + "日";
	}

	public static String getCurrentDate() {
		/*
		 * final Calendar c = Calendar.getInstance();
		 * c.setTimeZone(TimeZone.getTimeZone("GMT+8:00")); String mMonth =
		 * String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份 String mDay =
		 * String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
		 * if(mMonth.length() == 1) { mMonth= "0"+mMonth; } if(mDay.length() ==
		 * 1) { mDay= "0"+mDay; }
		 */
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	/***
	 * 年月 MM月dd日
	 * 
	 * @param mmdd
	 * @return yyyy-mm-dd
	 */
	public static String toDateStrbyMMDD(String mmdd) {
		Calendar c = Calendar.getInstance();
		return mmdd.replace("月", "-").replace("日", "");
	}

	/***
	 * yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDateymd() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	/***
	 * yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDateyyyymmdd() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	/***
	 * yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDateymdhms() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}
	/***
	 * mouth-day
	 * 
	 * @return
	 */
	public static String getMouthDay(String date) {
		String []dates=date.split("-");
		String dateContent=dates[1]+"/"+dates[2];
		return dateContent;
	}
	/***
	 * year-Mouth
	 * 
	 * @return
	 */
	public static String getYearMouth(String date) {
		String []dates=date.split("-");
		String dateContent=dates[0]+"/"+dates[1];
		return dateContent;
	}

	/***
	 * dt130925153025 set date = 2013-09-25 set time = 15:30:25
	 * 
	 * @return
	 */
	public static String getDatatime() {
		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		return df.format(new Date());
	}

	public static String getHm() {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(new Date());
	}

	/***
	 * 获得月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);

	}

	/***
	 * 获得月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;

	}

	/***
	 * 获得月份
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonth(Date date, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM", locale);
		return sdf.format(date);

	}
	
	
	
	/***
	 * 获得周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeeks() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK)-1;
	}
	/**
	 * 获得当前年总共的周数
	 * @param year
	 * @return
	 */
	public int getWeeks(int year) 
    {
        int week = 0;
        int days = 365;
        int day = 0;
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) 
        {//判断是否闰年，闰年366天
            days = 366;
        }
        //得到一年所有天数然后除以7
        day = days % 7;//得到余下几天
        week = days / 7;//得到多少周
        return day;
    }
	
	/***
	 * 获得小时
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	/***
	 * 获得分
	 * 
	 * @param date
	 * @return
	 */
	public static int getMin() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}
	
	/***
	 * 获得日期
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DATE);

	}

	/***
	 * 获得日期
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DATE);

	}

	/***
	 * 获得一年中的第几周
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonthWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_MONTH);

	}

	/***
	 * 获得一年中的第几周
	 * 
	 * @param date
	 * @return
	 */
	public static int getYearWeek(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(df.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	public static String toDateYYYYMMDD(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
	public static String toDateMMDD(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd");
		return df.format(date);
	}

	/***
	 * 年月yyyymm
	 * 
	 * @param date
	 * @return
	 */
	public static String toDateYYYYMM(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		return df.format(date);
	}

	/***
	 * 加减日期
	 * 
	 * @param date
	 * @param addDay
	 * @return
	 */
	public static Date dateAddDay(Date date, int addDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, addDay);
		return c.getTime();
	}

	/***
	 * 加减月
	 * 
	 * @param date
	 * @param addDay
	 * @return
	 */
	public static Date dateAddMonth(Date date, int addMonth) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, addMonth);
		return c.getTime();
	}
	/***
	 * 获得当年分钟
	 * 
	 * @param date
	 * @param addDay
	 * @return
	 */
	public static int getCurrentMinutes() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY)*60+c.get(Calendar.MINUTE);
	}
	
	public static int getWeek(String dates) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse(dates);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(date == null)
			return 1;
		int Week = 1;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			Week = 7;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			Week = 1;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			Week = 2;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			Week = 3;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			Week = 4;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			Week = 5;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			Week = 6;
		}
		return Week;
	}
	
	public static String getTime(int number){
		int time = number*5;
		StringBuilder strTime = new StringBuilder();
		long h = time / 3600;
		long min = time % 3600 / 60;
		if (h < 10)
			strTime.append("0" + h + ":");
		else
			strTime.append(h + ":");
		if (min < 10)
			strTime.append("0" + min);
		else
			strTime.append(min);
		return strTime.toString();
	}
	
	public static String toString(int number){
		return number<10?"0"+number:""+number;
	}
}