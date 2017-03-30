package com.proj.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 程序信息的配置文件
 */
public class PreferencesApp {
	/** xml文件的名字 */
	private static final String FILENAME = "AppInfo";

	private static final String KEY_APPID = "app_id";
	private static final String KEY_APPTAG = "app_tag";
	private static final String KEY_API_KEY = "KEY_API_KEY";
	private static final String KEY_VIEWPAGE_CONFIG = "viewpage_config";
	private static final String KEY_WHEEL_GUIDE_MUSIC = "wheel_guide_music";
	private static final String KEY_KNEEL_GUIDE_MUSIC = "kneel_guide_music";
	private static final String KEY_WORSHIP_GUIDE = "kneel_guide_worship";
	private static final String KEY_NOTICE_UPDATE_TIP = "notice_update_tip";
	private static final String KEY_MARKET_SCORE = "market_score";
	private static final String KEY_REAL_NAME_CRULE = "real_name_crule";
	private static final String KEY_REAL_NAME_DIALOG = "real_name_dialog";

	/**
	 * 记录程序id
	 * 
	 * @param context
	 * @param is
	 * @author Tony
	 * @date 2014年7月30日 下午2:28:07
	 */
	public static void saveAppId(Context context, int id) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putInt(KEY_APPID, id);
		sEditor.commit();
	}

	/**
	 * 读取程序id
	 * 
	 * @param context
	 * @return
	 * @author Tony
	 * @date 2014年7月30日 下午2:28:25
	 */
	public static int readAppId(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getInt(KEY_APPID, 1);
	}

	/**
	 * 记录程序标签
	 * 
	 * @param context
	 * @param tag
	 * @author Tony
	 * @date 2014年7月30日 下午2:29:21
	 */
	public static void saveAppTag(Context context, String tag) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putString(KEY_APPTAG, tag);
		sEditor.commit();
	}

	/**
	 * 读取程序标签
	 * 
	 * @param context
	 * @return
	 * @author Tony
	 * @date 2014年7月30日 下午2:30:20
	 */
	public static String readAppTag(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getString(KEY_APPTAG, "ZMFY");
	}

	/**
	 * 百度api key
	 * 
	 * @param context
	 * @param apiKey
	 * @author Tony
	 * @date 2015年1月22日 下午4:56:35
	 */
	public static void saveApiKey(Context context, String apiKey) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putString(KEY_API_KEY, apiKey);
		sEditor.commit();
	}

	/**
	 * 获取apikey，默认在这里配置
	 * 
	 * @param context
	 * @return
	 * @author Tony
	 * @date 2015年1月22日 下午4:57:14
	 */
	public static String readApiKey(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getString(KEY_API_KEY,
				"wOQPYBcubw9pzFuj9SbOezWl");
	}

	/**
	 * 
	 * loading 界面Viewpage显示配置
	 * 
	 * @param context
	 * @param status
	 */
	public static void saveViewpageConfig(Context context, Boolean status) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putBoolean(KEY_VIEWPAGE_CONFIG, status);
		sEditor.commit();
	}

	/**
	 * 
	 * loading 界面Viewpage显示配置
	 * 
	 * @param context
	 * @param status
	 */
	public static boolean readViewpageConfig(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getBoolean(KEY_VIEWPAGE_CONFIG, false);
	}

	/**
	 * 
	 * 咒轮引导诵读配置
	 * 
	 * @param context
	 * @param status
	 */
	public static void saveWheelMusicBoo(Context context, Boolean status) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putBoolean(KEY_WHEEL_GUIDE_MUSIC, status);
		sEditor.commit();
	}

	/**
	 * 
	 * 咒轮引导诵读配置
	 * 
	 * @param context
	 * @param status
	 */
	public static boolean readWheelMusicBoo(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getBoolean(KEY_WHEEL_GUIDE_MUSIC, false);
	}
	/**
	 * 
	 * 跪拜引导诵读配置
	 * 
	 * @param context
	 * @param status
	 */
	public static void saveKneelMusicBoo(Context context, Boolean status) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putBoolean(KEY_KNEEL_GUIDE_MUSIC, status);
		sEditor.commit();
	}
	
	/**
	 * 
	 * 跪拜引导诵读配置
	 * 
	 * @param context
	 * @param status
	 */
	public static boolean readKneelMusicBoo(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getBoolean(KEY_KNEEL_GUIDE_MUSIC, false);
	}
	/**
	 * 
	 * 跪拜引导诵读配置
	 * 
	 * @param context
	 * @param status
	 */
	public static void saveWorshipGuid(Context context, Boolean status) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putBoolean(KEY_WORSHIP_GUIDE, status);
		sEditor.commit();
	}
	/**
	 * 
	 * 佛堂引导
	 * 
	 * @param context
	 * @param status
	 */
	public static boolean readWorshipGuid(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getBoolean(KEY_WORSHIP_GUIDE, false);
	}
	/**
	 * 
	 * 存储通知公告更新
	 * 
	 * @param context
	 * @param status
	 */
	public static void saveNoticeUpdateTip(Context context, Boolean status) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putBoolean(KEY_NOTICE_UPDATE_TIP, status);
		sEditor.commit();
	}
	/**
	 * 
	 * 读取通知公告更新
	 * 
	 * @param context
	 * @param status
	 */
	public static boolean readNoticeUpdateTip(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getBoolean(KEY_NOTICE_UPDATE_TIP, false);
	}
	/**
	 * 
	 * 存储商店评分
	 * 
	 * @param context
	 * @param status
	 */
	public static void saveMarketScore(Context context, Boolean status) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putBoolean(KEY_MARKET_SCORE, status);
		sEditor.commit();
	}
	/**
	 * 
	 * 读取商店评分
	 * 
	 * @param context
	 * @param status
	 */
	public static boolean readMarketScore(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getBoolean(KEY_MARKET_SCORE, false);
	}
	/**
	 * 
	 * 保存显示实名制规则
	 * 
	 * @param context
	 * @param status
	 */
	public static void saveRealNameCrule(Context context, Boolean status) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putBoolean(KEY_REAL_NAME_CRULE, status);
		sEditor.commit();
	}
	/**
	 * 
	 * 读取显示实名制规则
	 * 
	 * @param context
	 * @param status
	 */
	public static boolean readRealNameCrule(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getBoolean(KEY_REAL_NAME_CRULE, false);
	}
	/**
	 * 
	 * 保存显示实名制规则
	 * 
	 * @param context
	 * @param status
	 */
	public static void saveRealNameDialog(Context context, Boolean status) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				FILENAME, 0).edit();
		sEditor.putBoolean(KEY_REAL_NAME_DIALOG, status);
		sEditor.commit();
	}
	/**
	 * 
	 * 读取显示实名制规则
	 * 
	 * @param context
	 * @param status
	 */
	public static boolean readRealNameDialog(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILENAME, 0);
		return sharedPreferences.getBoolean(KEY_REAL_NAME_DIALOG, false);
	}
}
