package com.proj.JJYQFinancial.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 */
@SuppressLint("SimpleDateFormat")
public class EXDateHelper {
	public static Calendar CALENDAR = Calendar.getInstance();
	/**
	 * 获得日期
	 * 
	 * @param calendar
	 * @return
	 */
	public static String getDate(Calendar calendar) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		return df.format(calendar.getTime());
	}
	
	/**
	 * 获取当前时间字符串
	 * @return
	 * @author Tony
	 * @date 2014年7月16日 下午3:17:20
	 */
	public static String getCurrentDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 获取当年年份
	 * @return
	 * @author Tony
	 * @date 2015年1月9日 下午5:11:01
	 */
	public static int getCurrentYear(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		return Integer.valueOf(df.format(Calendar.getInstance().getTime()));
	}
	
	/**
	 * 将Long值的时间转换成标准日期格式
	 * 
	 * @param millis
	 * @return "yyyy-MM-dd HH:mm:ss"
	 */
	public static String GetDateStringFromLong(long millis) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (millis != Long.MIN_VALUE) {
			Date dt = new Date(millis);
			return sdf.format(dt);
		} else {
			return "1900-01-01 00:00:00";
		}
	}
	
	public static String getCommontDateStringFromLong(long millis) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (millis != Long.MIN_VALUE) {
			Date dt = new Date(millis);
			return sdf.format(dt);
		} else {
			return " ";
		}
	}
	
	public static String GetDateStringFromLongMillisecond(long millis) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			if(millis < 0){
				millis = 0;
			}
			if (millis != Long.MIN_VALUE) {
				Date dt = new Date(millis);
				return sdf.format(dt);
			} else {
				return "1800-01-01 00:00:00.000";
			}
		} catch (Exception e) {
			return "1800-01-01 00:00:00.000";
		}
	}

	/**
	 * 格式化时间 将long型时间转换成特定格式的字符串
	 * 
	 * @param timeMillis
	 *            待转换的long型时间
	 * @param formatType
	 *            	0 -> yyyy/MM/dd HH:mm:ss
	 *				1 -> yyyy/MM/dd
	 *				2 -> yyyy-MM-dd HH:mm:ss
	 * 				3 -> yyyy-MM-dd
	 *				4 -> yyyy年MM月dd日 HH:mm:ss
	 *				5 -> yyyy年MM月dd日
	 *				6 -> yyyy
	 *				7 -> yy
	 *				8 -> HH
	 *				9 -> mm
	 *				10 -> ss
	 *				11 -> yyyy/MM/dd HH:mm:ss.SSS
	 *				默认 -> yyyy-MM-dd
	 * @return 字符串类型
	 */
	public static String getTimeToStrFromLong(long timeMillis, int formatType) {
		DateFormat formatter = null;
		switch (formatType) {
		case 0:
			formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			break;
		case 1:
			formatter = new SimpleDateFormat("yyyy/MM/dd");
			break;
		case 2:
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		case 3:
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case 4:
			formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			break;
		case 5:
			formatter = new SimpleDateFormat("yyyy年MM月dd日");
			break;
		case 6:
			formatter = new SimpleDateFormat("yyyy");
			break;
		case 7:
			formatter = new SimpleDateFormat("yy");
			break;
		case 8:
			formatter = new SimpleDateFormat("HH");
			break;
		case 9:
			formatter = new SimpleDateFormat("mm");
			break;
		case 10:
			formatter = new SimpleDateFormat("ss");
			break;
		case 11:
			formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			break;
		case 12:
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			break;
		case 13:
			formatter = new SimpleDateFormat("dd");
			break;
		case 14:
			formatter = new SimpleDateFormat("yyyy-MM");
			break;
		default:
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			break;
		}
		CALENDAR.setTimeInMillis(timeMillis);
		return formatter.format(CALENDAR.getTime());
	}
	
	
	/**
	 * 将字符串转换成long型
	 * 
	 * @param date
	 * @param formatType
	 *          时间字符串类型:
	 *          0 -> yyyy/MM/dd HH:mm:ss
	 *			1 -> yyyy/MM/dd
	 *			2 -> yyyy-MM-dd HH:mm:ss
	 * 			3 -> yyyy-MM-dd
	 *			4 -> yyyy年MM月dd日 HH:mm:ss
	 *			5 -> yyyy年MM月dd日
	 *			6 -> yyyy
	 *			7 -> yy
	 *			8 -> HH
	 *			9 -> mm
	 *			10 -> ss
	 *			11 -> yyyy/MM/dd HH:mm:ss.SSS
	 *			默认 -> yyyy-MM-dd
	 * @return long型，精确到毫秒
	 */
	@SuppressLint("SimpleDateFormat")
	public static long getTimeToLongFromStr(String date, int formatType) {
		String type = "yyyy-MM-dd";
		switch (formatType) {
		case 0:
			type = "yyyy/MM/dd HH:mm:ss";
			break;
		case 1:
			type = "yyyy/MM/dd";
			break;
		case 2:
			type = "yyyy-MM-dd HH:mm:ss";
			break;
		case 3:
			type = "yyyy-MM-dd";
			break;
		case 4:
			type = "yyyy年MM月dd日 HH:mm:ss";
			break;
		case 5:
			type = "yyyy年MM月dd日";
			break;
		case 6:
			type = "yyyy";
			break;
		case 7:
			type = "yy";
			break;
		case 8:
			type = "HH";
			break;
		case 9:
			type = "mm";
			break;
		case 10:
			type = "ss";
			break;
		case 11:
			type = "yyyy/MM/dd HH:mm:ss.SSS";
			break;
		case 12:
			type = "yyyy-MM-dd HH:mm";
			break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		Date dt2 = null;
		try {
			dt2 = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt2.getTime();
	}

	/**
	 * 当前日期增加几月
	 * @param dateLong
	 * @param n
     * @return
     */
	public static long addMonth(long dateLong, int n) {
		try {
			Date dt = new Date(dateLong);
			Calendar cd = Calendar.getInstance();
			cd.setTime(dt);
			cd.add(Calendar.MONTH, n);// 增加一月
			return cd.getTimeInMillis();
		} catch (Exception e) {
			return 0;
		}
	}
	/**
	 * 当前日期增加几天(时间字符串格式为：yyyy年MM月dd日)
	 * 
	 * @param date
	 *            当前日期(yyyy年MM月dd日)
	 * @param n
	 *            天数
	 * @return
	 */
	public static String addDay(String date, int n) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(date));
			cd.add(Calendar.DATE, n);// 增加一天
			return sdf.format(cd.getTime());
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 增加一天
	 * @param dateTime
	 * @param n
	 * @return
	 * @author OLH
	 * @date 2013-9-12 下午5:05:52
	 */
	public static long addDayFromLong(long dateTime, int n) {
		try {
			Calendar cd = Calendar.getInstance();
			cd.setTimeInMillis(dateTime);
			cd.add(Calendar.DATE, n);// 增加一天
			return cd.getTimeInMillis();
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 获得给定时间所在的周，一周的时间数组(每天0点的long型时间)
	 * ，从周日开始
	 * @param date
	 * @return
	 * @author OLH
	 * @date 2013-9-12 下午3:21:32
	 */
	public static long[] getWeekDays(long date)
	{
		long[] days = new long[7];
		Calendar cal = Calendar.getInstance();
		date = EXDateHelper.getTimeToLongFromStr(
				EXDateHelper.getTimeToStrFromLong(date, 3) + " 00:00:00",2);
		cal.setTimeInMillis(date);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		for(int i = 0; i < 7;i++){
			days[i] =cal.getTimeInMillis();
		    cal.add(Calendar.DAY_OF_MONTH, 1);//加一天
		}
		return days;
	}
	
	/**
	 * 判断给定时间是否在7天之前
	 * @param targetTime
	 * @return
	 * @author Tony
	 * @date 2014-3-20 下午10:54:49
	 */
	public static boolean isBeforSevenDay(long targetTime){
		Calendar calendarTarget = Calendar.getInstance();
		calendarTarget.setTimeInMillis(targetTime);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -8);//7天前
		return calendarTarget.before(calendar);
	}
	
	/**
	 * 判断给定时间是否是X天前
	 * @param targetTime
	 * @param diff
	 * @return
	 * @author Tony
	 * @date 2014年11月15日 下午8:12:59
	 */
	public static boolean isBeforXDay(long targetTime,int diff){
		Calendar calendarTarget = Calendar.getInstance();
		calendarTarget.setTimeInMillis(targetTime);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -diff);//X天前
		return calendarTarget.before(calendar);
	}
	
	
	/**
	 * 将Long型时间转换成Date格式
	 * @param time
	 * @return
	 * @author Tony
	 * @date 2013-12-11 下午3:45:47
	 */
	public static Date getDateFromLong(long time){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return cal.getTime();
	}
	
	/**
	 * 将ms转换为分秒时间显示函数
	 * 
	 * @param time
	 * @return
	 */
	public static String showTime(long time) {
		// 将ms转换为s
		time /= 1000;
		// 求分
		int minute = (int) (time / 60);
		// 求秒
		int second = (int) (time % 60);
		if(minute >= 60){
			int min = minute % 60;
			int hour = minute / 60;
			if(hour > 0){
				return String.format(Locale.getDefault(),"%02d:%02d:%02d", hour, min, second);
			}
		}
		minute %= 60;
		return String.format(Locale.getDefault(),"%02d:%02d", minute, second);
	}
	
	public static String getDailyDate(long millis) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (millis != Long.MIN_VALUE) {
			Date dt = new Date(millis);
			return sdf.format(dt);
		} else {
			return "";
		}
	}
	
	/**
	 * 检查所给时间是否为合法生日
	 * @param d
	 * @return
	 * @author Tony
	 * @date 2014年8月7日 下午10:29:00
	 */
	public static boolean isBrithdayAvable(Long d){
		if(d == null){
			return false;
		}
		boolean back = false;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(d.longValue());
		Calendar current = Calendar.getInstance();
		if(c.get(Calendar.YEAR) > 1801 
				&& c.before(current) && c.get(Calendar.YEAR) <= current.get(Calendar.YEAR)  ){
			return true;
		}
		return back;
	}
	
	/**
	 * 获取相差时间字符串
	 * @param cTime
	 * @param dstTime
	 * @return
	 * @author Tony
	 * @date 2015年1月9日 下午1:55:15
	 */
	public static String getDateDiffStr(long cTime,long dstTime){
		String back = "";
		long[] getDistanceTime = getDistanceTime(cTime,dstTime);
		if(getDistanceTime[0] > 0){
			back = (int)getDistanceTime[0] + "天前";
		}else if(getDistanceTime[1] > 0){
			back = (int)getDistanceTime[1] + "小时前";
		}else if(getDistanceTime[2] > 0){
			back = (int)getDistanceTime[2] + "分前";
		}else{
			back = (int)getDistanceTime[2] + "秒前";
		}
		return back;
	}
	
	/**
	 * 两个时间相差距离多少天多少小时多少分
	 * @param cTime 对比时间
	 * @param dstTime 目标时间
	 * @return @return long[] 返回值为：{天, 时, 分，秒}
	 * @author Tony
	 * @date 2014年12月17日 下午10:56:46
	 */
	public static long[] getDistanceTime(long cTime,long dstTime) {  
        long day = 0;  
        long hour = 0;  
        long min = 0;  
        long sec = 0;  
        
        long time1 = cTime;  
        long time2 = dstTime;  
        long diff ;  
        if(time1<time2) {  
            diff = time2 - time1;  
        } else {  
            diff = time1 - time2;  
        }  
        day = diff / (24 * 60 * 60 * 1000);  
        hour = (diff / (60 * 60 * 1000) - day * 24);  
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
        sec = (diff/1000-day*24*60*60-hour*60*60-min*60);  
        return new long[]{day, hour, min, sec};  
    }
	
	/**
	 * 获取当前时间
	 * @return yyyy年MM月dd日 EEEE
	 * @author Tony
	 * @date 2015年5月4日 下午3:11:06
	 */
	public static String getDateWeek() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 EEEE");
		return df.format(System.currentTimeMillis());
	}
	
	
	/**
	 * 
	 * @param jumpMonth
	 * @param jumpYear
	 * @param year_c
	 * @param month_c
	 * @param day_c
	 * 
	 * @return String[3] 分别代表 年 月 日
	 * @author Tony
	 */
	public static String[] getJumpData(int jumpMonth, int jumpYear, int year_c, int month_c, int day_c){
		String[] back = new String[3];
		
		int stepYear = year_c + jumpYear;
		int stepMonth = month_c + jumpMonth;
		if (stepMonth > 0) {
			// 往下一个月滑动
			if (stepMonth % 12 == 0) {
				stepYear = year_c + stepMonth / 12 - 1;
				stepMonth = 12;
			} else {
				stepYear = year_c + stepMonth / 12;
				stepMonth = stepMonth % 12;
			}
		} else {
			// 往上一个月滑动
			stepYear = year_c - 1 + stepMonth / 12;
			stepMonth = stepMonth % 12 + 12;
			if (stepMonth % 12 == 0) {

			}
		}

		back[0] = String.valueOf(stepYear);; // 得到当前的年份
		back[1] = String.valueOf(stepMonth); // 得到本月
													// （jumpMonth为滑动的次数，每滑动一次就增加一月或减一月）
		back[2] = String.valueOf(day_c); // 得到当前日期是哪天
		
		return back;
	}
	
	/**
	 * 获取当月组成201509型
	 * @return
	 * @author Tony
	 */
	public static String getYearMonth(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		return df.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 获取今天日期组成20150902型
	 * @return
	 * @author Tony
	 */
	public static String getToday(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(Calendar.getInstance().getTime());
	}
	
}
