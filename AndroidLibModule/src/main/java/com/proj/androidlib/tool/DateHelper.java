package com.proj.androidlib.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.text.TextUtils;

/**
 * 日期时间的工具类
 */

public class DateHelper {
	public static Calendar CALENDAR = Calendar.getInstance();

	private DateHelper() {
	};

	/**
	 * 获得星期几
	 * 
	 * @return 星期几
	 */
	public static String getWeekDay() {
		String str = "";
		switch (CALENDAR.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			str = "星期日 ";
			break;
		case 2:
			str = "星期一 ";
			break;
		case 3:
			str = "星期二 ";
			break;
		case 4:
			str = "星期三 ";
			break;
		case 5:
			str = "星期四 ";
			break;
		case 6:
			str = "星期五 ";
			break;
		default:
			str = "星期六 ";
			break;
		}
		return str;
	}

	/**
	 * 根据给定时间获得星期几
	 * 
	 * @param dateTime
	 * @return 星期几
	 */
	public static String getWeekDay(long dateTime) {
		String str = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateTime);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case 1:
			str = "星期日 ";
			break;
		case 2:
			str = "星期一 ";
			break;
		case 3:
			str = "星期二 ";
			break;
		case 4:
			str = "星期三 ";
			break;
		case 5:
			str = "星期四 ";
			break;
		case 6:
			str = "星期五 ";
			break;
		default:
			str = "星期六 ";
			break;
		}
		return str;
	}

	/**
	 * 获取当前小时数
	 * 
	 * @return int
	 */
	public static int getHour() {
		return CALENDAR.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取分钟数
	 * 
	 * @return int
	 */
	public static int getMin() {
		return CALENDAR.get(Calendar.MINUTE);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return 02：01
	 */
	public static String getTime() {
		int hour = CALENDAR.get(Calendar.HOUR_OF_DAY);
		int min = CALENDAR.get(Calendar.MINUTE);
		StringBuilder stringBuilder = new StringBuilder();
		if (hour < 10)
			stringBuilder.append("0");
		stringBuilder.append(hour);
		stringBuilder.append(":");
		if (min < 10)
			stringBuilder.append("0");
		stringBuilder.append(min);
		return stringBuilder.toString();
	}

	/**
	 * 格式化时间 将long型时间转换成年月日格式
	 * 
	 * @param timeMillis
	 *            待转换的long型时间
	 * @param formatStr
	 *            yyyy#MM#dd HH#mm
	 * @return 时间字符串
	 */
	public static String formatTMToStr(long timeMillis, String formatStr) {
		DateFormat formatter = new SimpleDateFormat(formatStr);
		CALENDAR.setTimeInMillis(timeMillis);
		return formatter.format(CALENDAR.getTime());
	}

	/**
	 * 转换字符串为Date类型
	 * 
	 * @param strDate
	 *            日期字符串:2015#01#01 01#01
	 * @param strformat
	 *            日期格式：yyyy#MM#dd HH#mm#SSS
	 * @return 异常返回 null
	 */
	public static Date formatStrToDate(String strDate, String strformat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(strformat);
			Date date = sdf.parse(strDate);
			return date;
		} catch (ParseException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 时间增加（减少）天数
	 * 
	 * @param tm
	 *            时间戳
	 * @param day
	 *            增加天数
	 * @return
	 */
	public static long addDay(long tm, int day) {
		long endDate = 0;
		try {
			Calendar cd = Calendar.getInstance();
			cd.setTimeInMillis(tm);
			cd.add(Calendar.DATE, day);
			return cd.getTimeInMillis();
		} catch (Exception e) {
			return endDate;
		}
	}

	/**
	 * 将毫秒值转化成小时、分钟、秒数
	 * 
	 * @param timeMs
	 *            毫秒值
	 * @return 00小时:00分:00秒
	 */
	@SuppressWarnings("resource")
	public static String covertDiffMillisToTimeStr(long timeMs) {
		long totalSeconds = timeMs / 1000;// 获取文件有多少秒
		StringBuilder mFormatBuilder = new StringBuilder();

		Formatter formatter = new Formatter(mFormatBuilder, Locale.getDefault());
		int seconds = (int) totalSeconds % 60;
		int minutes = (int) (totalSeconds / 60) % 60;
		int hours = (int) totalSeconds / 3600;
		mFormatBuilder.setLength(0);
		if (hours > 0) {
			return formatter.format("%02d小时:%02d分:%02d秒", hours, minutes,
					seconds).toString();// 格式化字符串
		} else if (minutes > 0) {
			return formatter.format("%02d分:%02d秒", minutes, seconds).toString();
		} else {
			return formatter.format("%02d秒", seconds).toString();
		}
	}

	/**
	 * 获得服务端传回来的时间，
	 * 可以解析【"/Date(1376530771673)/"】和【"2013-08-15T09:39:31.673"】这两种格式
	 * 
	 * @param strDate
	 * @return long型时间，当参数为空，返回Long.MIN_VALUE
	 */
	public static long convertToLongByStr(String str) {
		try {
			if (str.contains("Date")) {//
				return Long.valueOf(str.substring(6, 19));
			} else if (str.contains("T")) {
				return getDayTimeToLongToSecondByStr(str.replace('T', ' ')
						.toString());
			} else if (!TextUtils.isEmpty(str)) {
				return getDayTimeToLongToSecondByStr(str);
			}
			return Long.MIN_VALUE;
		} catch (Exception e1) {
			e1.printStackTrace();
			return Long.MIN_VALUE;
		}
	}

	/**
	 * 将字符串dateStr(2013-01-31 08:59:49.42)转换成long型
	 * 
	 * @param dateStr
	 * @return long型，精确到毫秒
	 */
	private static long getDayTimeToLongToSecondByStr(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt2 = null;
		try {
			dt2 = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt2.getTime();
	}
	/**
	 * 格式化时间 将long型时间转换成特定格式的字符串
	 * 
	 * @param timeMillis
	 *            待转换的long型时间
	 * @param formatType
	 *            0 -> yyyy/MM/dd HH:mm:ss 1 -> yyyy/MM/dd 2 -> yyyy-MM-dd
	 *            HH:mm:ss 3 -> yyyy-MM-dd 4 -> yyyy年MM月dd日 HH:mm:ss 5 ->
	 *            yyyy年MM月dd日 6 -> yyyy 7 -> yy 8 -> HH 9 -> mm 10 -> ss 11 ->
	 *            yyyy/MM/dd HH:mm:ss.SSS 默认 -> yyyy-MM-dd
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
		case 15:
			formatter = new SimpleDateFormat("MM月dd日 HH:mm");
			break;
		case 16:
			formatter = new SimpleDateFormat("MM-dd");
			break;
		case 17:
			formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
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
	 *            时间字符串类型: 0 -> yyyy/MM/dd HH:mm:ss 1 -> yyyy/MM/dd 2 ->
	 *            yyyy-MM-dd HH:mm:ss 3 -> yyyy-MM-dd 4 -> yyyy年MM月dd日 HH:mm:ss
	 *            5 -> yyyy年MM月dd日 6 -> yyyy 7 -> yy 8 -> HH 9 -> mm 10 -> ss 11
	 *            -> yyyy/MM/dd HH:mm:ss.SSS 默认 -> yyyy-MM-dd
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
}
