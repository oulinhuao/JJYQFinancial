package com.proj.androidlib.tool;

import java.security.MessageDigest;
import java.util.Locale;
import java.util.regex.Pattern;

//import org.apache.http.conn.util.InetAddressUtils;

import android.text.TextUtils;

/**
 * 字符串操作类
 */
public class StringHelper {
	private static final int[] mWi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
			5, 8, 4, 2, 1 };
	private static final int[] mVi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
	private static int[] mAi = new int[18];

	private final static Pattern emailer = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

	/**
	 * 拼接网络连接地址
	 * 
	 * @param context
	 * @param strings
	 *            string[0]=192.168.0.1,string[1]=web
	 * @return http://192.168.0.1/web
	 */
	public static String splStrToWebUrl(String... strings) {
		StringBuilder sBuilder = new StringBuilder("http://");
		int i = 0;
		for (String string : strings) {
			sBuilder.append(string);
			if (i < strings.length - 1)
				sBuilder.append("/");
			i++;
		}
		return sBuilder.toString();
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 *            文件名
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot);
			}
		}
		return filename;
	}

	/**
	 * 获取文件名(去除扩展名)
	 * 
	 * @param filename
	 *            文件名
	 * @return
	 */
	public static String getFileName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否是全数字
	 * 
	 * @param str
	 * @return
	 * @author Tony
	 * @date 2014-3-6 下午1:16:40
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回最小Long
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return Long.MIN_VALUE;
	}

	/**
	 * 字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 是否为座机号码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPhoneNum(String str) {
		if (str.matches("^0\\d{2,3}(\\-)?\\d{7,8}$")) {
			return true;
		}
		return false;
	}

	/**
	 * 是否为手机号码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobilePhoneNum(String str) {
		if (str.matches("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[0|6|7|8])|(18[0-9]))\\d{8}$")) {
			return true;
		}
		return false;
	}

	/**
	 * 匹配正浮点数
	 * 
	 * @param str
	 * @return
	 * @author OLH
	 * @date 2013-9-18 下午2:45:20
	 */
	public static boolean isPositiveFloat(String str) {
		if (str.matches("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$")) {
			return true;
		}
		return false;
	}

	/**
	 * 是否为邮编验证
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isZip(String str) {
		if (str.matches("[0-9]\\d{5}(?!\\d)")) {
			return true;
		}
		return false;
	}

	/**
	 * MD5加密
	 * 
	 * @param text
	 * @return
	 */
	public static String md5(String text) {
		if (text == null)
			return "";
		StringBuffer hexString = new StringBuffer();
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());

			byte[] digest = md.digest();

			for (int i = 0; i < digest.length; i++) {
				text = Integer.toHexString(0xFF & digest[i]);
				if (text.length() < 2) {
					text = "0" + text;
				}
				hexString.append(text);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hexString.toString().toUpperCase();
	}

	public static String TransformString(String str) {
		if (TextUtils.isEmpty(str) || str.equals("null")) {
			return "";
		}
		return str;
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

	/**
	 * 验证身份证
	 */
	public static boolean isCerNo(String idCard) {
		if (idCard
				.matches("^\\d{6}((19)|(20))\\d{2}((0[1-9])|(1[012]))(0[1-9]|[12][0-9]|3[01])\\d{3}([0-9]|X|x)$")) {
			// 验证校验码
			String idCardLast = idCard.substring(17, 18).toUpperCase(
					Locale.getDefault());
			if (idCardLast.equals(getVerify(idCard))) {
				return true;
			}
		}

		return false;
	}

	private static String getVerify(String eightcardid) {
		int remaining = 0;
		if (eightcardid.length() == 18) {
			eightcardid = eightcardid.substring(0, 17);
		}
		if (eightcardid.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eightcardid.substring(i, i + 1);
				mAi[i] = Integer.parseInt(k);
			}
			for (int i = 0; i < 17; i++) {
				sum = sum + mWi[i] * mAi[i];
			}
			remaining = sum % 11;
		}
		return remaining == 2 ? "X" : String.valueOf(mVi[remaining]);

	}
}
