package com.proj.JJYQFinancial.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.proj.JJYQFinancial.R;
import com.proj.androidlib.tool.StringHelper;
import com.proj.util.FileHelper;

import android.content.Context;
import android.text.TextUtils;

public class EXStringHelper {
	
	
	public static String TransformString(String str) {
		if (TextUtils.isEmpty(str) || str.equals("null")) {
			return "";
		}
		return str.trim();
	}

	public static float TransformFloat(String str) {
		if (TextUtils.isEmpty(str) || str.equals("null")) {
			return Float.MIN_VALUE;
		}
		return Float.parseFloat(str);
	}

	public static double TransformDouble(String str) {
		if (TextUtils.isEmpty(str) || str.equals("null")) {
			return Double.MIN_VALUE;
		}
		return Double.parseDouble(str);
	}

	public static int TransformInt(String str) {
		if (TextUtils.isEmpty(str) || str.equals("null")) {
			return Integer.MIN_VALUE;
		}
		return Integer.parseInt(str);
	}

	public static long TransformLong(String str) {
		if (TextUtils.isEmpty(str) || str.equals("null")) {
			return Long.MIN_VALUE;
		}
		return Long.valueOf(str);
	}
	
	public static Boolean TransformBoolean(String str) {
		if (TextUtils.isEmpty(str) || str.equals("null")) {
			return false;
		}
		return Boolean.parseBoolean(str);
	}
	

	public static long TransformWorkDate(String strDate){
		long back = Long.MIN_VALUE;
		if(!TextUtils.isEmpty(strDate) && strDate != "null"){
			try {
				back = EXDateHelper.getTimeToLongFromStr(strDate.trim(), 3);
			} catch (Exception e) {
			}
		}
		return back;
	}
	
	/**
	 * 使用正则表达式去掉多余的.与0
	 * 
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s) {
		if(!TextUtils.isEmpty(s)){
			if (s.indexOf(".") > 0) {
				s = s.replaceAll("0+?$", "");// 去掉多余的0
				s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
			}
		}else{
			return "";
		}
		return s;
	}
	
	
	/**
	 * 保留2位有效数字
	 * @param value
	 * @return
	 */
	public static String keepTwoAfterDot(double value) {
		String back = "";
		back = String.format("%.2f", value);
		return back;	
	}
	
	public static String keepOneAfterDot(double value) {
		String back = "";
		back = String.format("%.2f", value);
		return back;	
	}
	
	public static String keepOneAfterDotBylong(long value) {
		String back = "";
		back = String.format("%.1f", value);
		return back;	
	}
	
	/**
	 * 将字符串以 ，分割
	 * @param str
	 * @return
	 * @author OLH
	 * @date 2013-9-25 上午9:30:33
	 */
	public static String[] splitString(String str) {
		String[] mStr = str.split(",");
		return mStr;
	}
	/**
	 * 将字符串以XXX分割
	 * @param str
	 * @param with 分割字符串
	 * @return
	 * @author OLH
	 * @date 2013-9-26 下午1:41:53
	 */
	public static String[] splitStringWith(String str,String with) {
		String[] mStr = str.split(with);
		return mStr;
	}
	
	/**
	 * 获取WebSerive地址（不包含具体方法名）
	 * 如：http://www.kingdonsoft.com/XZQJHMAdmin/MobielWebService/NoticeMobileService.asmx
	 * @param serverAddress 服务器地址
	 * @param webVirualDir 虚拟目录
	 * @param serviceUrl 
	 * @return
	 * @author Ivan
	 * @date 2013-8-16 下午2:20:24
	 */
	public static String getWebServiceUrl(String serverAddress,String webVirualDir,String serviceUrl)
	{
		StringBuilder sb=new StringBuilder();
		sb.append("http://");
		sb.append(serverAddress);
		sb.append("/");
		sb.append(webVirualDir);
		sb.append("/");
		sb.append(serviceUrl);
		return sb.toString();
	}
	
	/**
	 * 获取WebSerive地址（不包含具体方法名）
	 * @param context
	 * @param serviceUrl
	 * @return
	 * @author Tony
	 */
	public static String getWebServiceUrl(Context context,String serviceUrl) {
		StringBuilder sb = new StringBuilder();
		sb.append("http://");
		sb.append(context.getString(R.string.server_address));
		sb.append("/");
		sb.append(context.getString(R.string.server_interface_virtualdir));
		sb.append("/");
		sb.append(serviceUrl);
		return sb.toString();
	}

	
	/**
	 * 整理下载文件的路径
	 * @param serverAddress ip
	 * @param webVirualDir 虚拟目录
	 * @param upload 服务端存放目录
	 * @param serviceUrl 文件信息
	 * @return
	 * @author Tony
	 * @date 2013-12-16 上午11:19:43
	 */
	public static String getDownFileUrl(String serverAddress,String webVirualDir,String upload,String serviceUrl)
	{
		StringBuilder sb=new StringBuilder();
		sb.append("http://");
		sb.append(serverAddress);
		sb.append("/");
		sb.append(webVirualDir);
		sb.append("/");
		sb.append(upload);
		sb.append(serviceUrl);
		return sb.toString();
	}
	/**
	 * 整理下载文件的路径
	 * @param serverAddress
	 * @param webVirualDir
	 * @param serviceUrl
	 * @return
	 * @author Tony
	 * @date 2013-12-25 上午9:39:24
	 */
	public static String getDownFileUrlNoUpload(String serverAddress,String webVirualDir,String serviceUrl)
	{
		StringBuilder sb=new StringBuilder();
		sb.append("http://");
		sb.append(serverAddress);
		sb.append("/");
		sb.append(webVirualDir);
		sb.append(serviceUrl);
		return sb.toString();
	}
	
	public static String getDownFileUrl(Context context,
			String fileServerPath)
	{
		if(TextUtils.isEmpty(fileServerPath)){
			return "";
		}
		StringBuilder sb=new StringBuilder();
		sb.append("http://");
		sb.append(context.getString(R.string.server_address));
		sb.append("/");
		sb.append(context.getString(R.string.server_interface_virtualdir));
		if(!fileServerPath.startsWith("/")){
			sb.append("/");
		}
		if(!fileServerPath.startsWith("/Upload")){
			sb.append("/Upload");
		}
		
		String name = FileHelper.getFileNameFromUrl(fileServerPath);
		sb.append(fileServerPath.replace(name, ""));
		try {
			sb.append(URLEncoder.encode(name,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	public static int convertToInt(String str) throws NumberFormatException {
		int s, e;
		for (s = 0; s < str.length(); s++)
			if (Character.isDigit(str.charAt(s)))
				break;
		for (e = str.length(); e > 0; e--)
			if (Character.isDigit(str.charAt(e - 1)))
				break;
		if (e > s) {
			try {
				return Integer.parseInt(str.substring(s, e));
			} catch (NumberFormatException ex) {
				throw new NumberFormatException();
			}
		} else {
			throw new NumberFormatException();
		}
	}
	
	public static List<String> matchImgPath(String source, String element, String attr) {  
        List<String> result = new ArrayList<String>();  
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";  
        Matcher m = Pattern.compile(reg).matcher(source);  
        while (m.find()) {  
            String r = m.group(1);  
            result.add(r);  
        }  
        return result;  
    }
	
	public static boolean isQQ(String QQ) {
		 String regex = "^[1-9]\\d{4,11}$";
		 return QQ.matches(regex);
	}
	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("[0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}
	
	/**
	 * 校验组织机构代码
	 * @param OrgCode
	 * @return
	 * @author Tony
	 * @date 2015年4月7日 上午11:05:26
	 */
	public static boolean isOrgCode(String OrgCode) {
		String regex = "[a-zA-Z0-9]{8}-[a-zA-Z0-9]";
		return OrgCode.matches(regex);
	}
	
//	/**
//	 * 获取imageloader生成的文件名
//	 * @param urlWhole
//	 * @return
//	 * @author Tony
//	 * @date 2014年11月12日 下午10:39:25
//	 */
//	public static String getHashCodeName(String urlWhole){
//		if(TextUtils.isEmpty(urlWhole)){
//			// 下载路径有问题
//			return "";
//		}
//		String back = "";
//		try {
//			HashCodeFileNameGenerator nameg = new HashCodeFileNameGenerator();
//			back = nameg.generate(urlWhole);
//		} catch (Exception e) {
//		}
//		return back;
//				
//	}
	
	public static int[] getBackPoint(String value){
		if(!TextUtils.isEmpty(value) && !value.equals("null")){
			if(value.contains(",") && value.length() >= 3){
				String[] strs = value.split(",");
				if(strs != null && strs.length == 2){
					int[] back = new int[2];
					back[0] = Integer.valueOf(strs[0]);
					back[1] = Integer.valueOf(strs[1]);
					return back;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取时间字符串
	 * @param cTime
	 * @param dstTime
	 * @return
	 * @author Tony
	 * @date 2014年12月17日 下午11:05:39
	 */
	public static String getDateBeforStr(long cTime,long dstTime){
		String back = "";
		long[] getDistanceTime = getDistanceTime(cTime,dstTime);
		if(getDistanceTime[0] > 0){
			back = (int)getDistanceTime[0] + "天前";
		}else if(getDistanceTime[1] > 0){
			back = (int)getDistanceTime[1] + "小时前";
		}else if(getDistanceTime[2] > 0){
			if(getDistanceTime[2] >= 2){
				back = (int)getDistanceTime[2] + "分前";
			}else{
				back = "刚刚";
			}
		}
		return back;
	}
	
	/**
	 * 两个时间相差距离多少天多少小时多少分
	 * @param cTime 对比时间
	 * @param dstTime 目标时间
	 * @return @return long[] 返回值为：{天, 时, 分}
	 * @author Tony
	 * @date 2014年12月17日 下午10:56:46
	 */
	public static long[] getDistanceTime(long cTime,long dstTime) {  
        long day = 0;  
        long hour = 0;  
        long min = 0;  
//        long sec = 0;  
        
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
//        sec = (diff/1000-day*24*60*60-hour*60*60-min*60);  
//        return new long[]{day, hour, min, sec};  
        return new long[]{day, hour, min};  
    } 
	
	public static String getDurationStr(long duration){
		StringBuilder sb = new StringBuilder();
		
		return sb.toString();
	}
	
	/**
	 * 将ms转换为分秒时间显示函数
	 * 
	 * @param time
	 * @return
	 */
	public static String showTime(long time) {
		if(time < 60000){
			return time/1000 + "''";
		}
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
	/**
	 * 获取时间字符串
	 * 
	 * @param cTime
	 * @param dstTime
	 * @return
	 * @author Tony
	 * @date 2014年12月17日 下午11:05:39
	 */
	public static String getDateBeforStr(long dstTime) {
		String back = "";
		Calendar calendar = Calendar.getInstance();
		int year1 = calendar.get(Calendar.YEAR);
		calendar.setTimeInMillis(dstTime);
		int year2 = calendar.get(Calendar.YEAR);
		if ((year1 - year2) > 0) {
			return formatDataToyyyMMdd(dstTime, 1);
		}
		back = EXDateHelper.getTimeToStrFromLong(dstTime, 15);
		return back;
	}
	/**
	 * 格式化时间 将long型时间转换成年月日格式
	 * 
	 * @param timeMillis
	 *            待转换的long型时间
	 * @param formatType
	 *            0代表yyyy/MM/dd HH:mm，1代表yyyy年MM月dd日，2代表yyyy/MM/dd
	 * @return 字符串类型
	 */
	public static String formatDataToyyyMMdd(long timeMillis, int formatType) {
		DateFormat formatter = null;
		switch (formatType) {
		case 0:
			formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			break;
		case 1:
			formatter = new SimpleDateFormat("yyyy年MM月dd日");
			break;
		case 2:
			formatter = new SimpleDateFormat("yyyy/MM/dd");
			break;
		case 3:
			formatter = new SimpleDateFormat("yyyy/MM/dd HH");
			break;
		case 4:
			formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时");
			break;
		case 5:
			formatter = new SimpleDateFormat("HH");
			break;
		case 6:
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case 7:
			formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			break;
		case 8:
			formatter = new SimpleDateFormat("yyyy-MM-dd HH");
			break;
		case 9:
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			break;
		case 10:
			formatter = new SimpleDateFormat("MM/dd HH:mm");
			break;
		case 11:
			formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			break;
		case 12:
			formatter = new SimpleDateFormat("yyyy");
			break;
		case 13:
			formatter = new SimpleDateFormat("MM");
			break;
		case 14:
			formatter = new SimpleDateFormat("dd");
			break;
		case 15:
			formatter = new SimpleDateFormat("yyyy/MM");
			break;
		case 16:
			formatter = new SimpleDateFormat("yyyy年MM月");
			break;
		case 17:
			formatter = new SimpleDateFormat("HH:mm");
			break;
		default:
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			break;
		}
		CALENDAR.setTimeInMillis(timeMillis);
		return formatter.format(CALENDAR.getTime());
	}

	public static Calendar CALENDAR = Calendar.getInstance();
	
	
	/**
	 * 获取加密后的手机号和用户名（3-8位）
	 * 
	 * @param s
	 * @return
	 * @author Tony
	 * @date 2014年8月19日 下午9:04:53
	 */
	public static String getStrEncrypt(String s) {
		String str = s;
		if (TextUtils.isEmpty(s)) {
			str = "";
		} else if (StringHelper.isMobilePhoneNum(str)) {
			str = str.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
		} else if (str.length() == 8) {
			str = str.substring(0, 3) + "***" + str.substring(6, 8);
		} else if (str.length() == 7) {
			str = str.substring(0, 2) + "***" + str.substring(5, 7);
		} else if (str.length() == 6) {
			str = str.substring(0, 1) + "***" + str.substring(4, 6);
		} else if (str.length() == 5) {// 正常的用户名
			str = str.substring(0, 1) + "***" + str.substring(4, 5);
		} else if (str.length() == 4) {
			str = "***" + str.substring(3, 4);
		} else if (str.length() == 3) {
			str = "**" + str.substring(2, 3);
		}
		return str;
	}
	
	
	
	// 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
 
    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
}
