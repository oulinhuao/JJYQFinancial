package com.proj.JJYQFinancial.preferences;

import com.proj.JJYQFinancial.utils.DesUtils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 登录相关配置
 */
public class PreferencesLogin {
	/**xml文件的名字 */
	private static final String FILENAME = "login_data";
	
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PSWD = "password";
	private static final String KEY_PSWD_COUNT = "password_count";
	private static final String KEY_TOKEN = "token_id";
	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_BOX = "box";
	
	
	/**
	 * 存储账户信息
	 * @param context
	 * @param name 帐户名
	 * @param pswd
	 * @author Tony
	 * @date 2014年6月17日 下午4:20:37
	 */
	public static void saveAccountInfo(Context context, String name,String pswd) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		try {
			DesUtils des = new DesUtils();
			sEditor.putString(KEY_USERNAME, des.encrypt(name));
			sEditor.putString(KEY_PSWD, des.encrypt(pswd));
			sEditor.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取账户信息
	 * @param context
	 * @return 字符串数组，0 用户名，1 密码
	 * @author Tony
	 * @date 2014年6月17日 下午4:20:50
	 */
	public static String[] readAccountInfo(Context context) {
		String[] str = new String[2];
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		str[0] = sharedPreferences.getString(KEY_USERNAME, "");
		str[1] = sharedPreferences.getString(KEY_PSWD, "");
		try {
			DesUtils des = new DesUtils();
			str[0] = des.decrypt(str[0]);
			str[1] = des.decrypt(str[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	public static void saveUserId(Context context, int id) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putInt(KEY_USER_ID, id);
		sEditor.commit();
	}
	public static int readUserId(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getInt(KEY_USER_ID, 0);
	}
	
	public static void saveTokenId(Context context, String token) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putString(KEY_TOKEN, token);
		sEditor.commit();
	}
	public static String readTokenId(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getString(KEY_TOKEN, "");
	}
	
	public static void savePswdCount(Context context, int count) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putInt(KEY_PSWD_COUNT, count);
		sEditor.commit();
	}
	public static int readPswdCount(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getInt(KEY_PSWD_COUNT, 0);
	}
	
	
	public static void saveBox(Context context, String dateStr) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putString(KEY_BOX, dateStr);
		sEditor.commit();
	}
	public static String readBox(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getString(KEY_BOX, "");
	}
}
