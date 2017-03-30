package com.proj.JJYQFinancial.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 通用配置文件
 */
public class PreferencesCommon {
	/**xml文件的名字 */
	private static final String COMMON_STATE = "Common";
	/** 程序第一次运行 */
	private static final String KEY_IS_FRIST_RUN = "frist_run";
	/** 屏幕尺寸信息 */
	private static final String KEY_SCREEN_W = "KEY_SCREEN_W";
	private static final String KEY_SCREEN_H = "KEY_SCREEN_H";

	/** 安装包最后修改时间 */
	private static final String KEY_APK_LAST_MODIFIED_TIME = "modified_time";
	private static final String KEY_APK_VERSION = "apk_version";
	
	/**
     * 点赞提示时间
     */
    private static final String KEY_PRAISE_TIP_TIME = "KEY_PRAISE_TIP_TIME";
    private static final String KEY_PRAISE_TIP_TIME_ID = "KEY_PRAISE_TIP_TIME_ID";
	/**
	 * 保存是否是首次运行
	 * @param context
	 * @param is
	 * @author Tony
	 * @date 2014-3-17 上午11:07:32
	 */
	public static void saveFirstStatus(Context context, boolean is) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				COMMON_STATE, 0).edit();
		sEditor.putBoolean(KEY_IS_FRIST_RUN, is);
		sEditor.commit();
	}
	
	/**
	 * 读取是否是首次运行
	 * @param context
	 * @return true 是首次运行
	 * @author Tony
	 * @date 2014-3-17 上午11:07:16
	 */
	public static boolean readFirstStatus(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				COMMON_STATE, 0);
		return sharedPreferences.getBoolean(KEY_IS_FRIST_RUN, true);
	}
	
	/**
	 * 保存屏幕信息
	 * @param context
	 * @param w
	 * @param h
	 * @author Tony
	 * @date 2014年12月29日 上午10:02:26
	 */
	public static void saveScreenInfo(Context context, int w,int h) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				COMMON_STATE, 0).edit();
		sEditor.putInt(KEY_SCREEN_W, w);
		sEditor.putInt(KEY_SCREEN_H, h);
		sEditor.commit();
	}
	/**
	 * 读取屏幕信息
	 * @param context
	 * @return int[] 0：宽 ；1：高
	 * @author Tony
	 * @date 2014年12月29日 上午10:02:36
	 */
	public static int[] readScreenInfo(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				COMMON_STATE, 0);
		int[] s = new int[2];
		s[0] = sharedPreferences.getInt(KEY_SCREEN_W, 0);
		s[1] = sharedPreferences.getInt(KEY_SCREEN_H, 0);
		return s;
	}
	
	/**
	 * 保存apk下载时间
	 * @param context
	 * @param time
	 * @param version
	 * @author Tony
	 */
	public static void saveModifiedTime(Context context, long time, long version) {
		SharedPreferences.Editor sEditor = context.getSharedPreferences(
				COMMON_STATE, 0).edit();
		sEditor.putLong(KEY_APK_LAST_MODIFIED_TIME, time);
		sEditor.putLong(KEY_APK_VERSION, version);
		sEditor.commit();
	}

	/**
	 * 获取最后修改时间
	 * @param context
	 * @return
	 * @author Tony
	 * @date 2015年2月2日 上午11:03:09
	 */
	public static long[] readModifiedTime(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				COMMON_STATE, 0);
		long[] s = new long[2];
		s[0] = sharedPreferences.getLong(KEY_APK_LAST_MODIFIED_TIME, 0L);
		s[1] = sharedPreferences.getLong(KEY_APK_VERSION, 0L);
		return s;
	}
	
	
	/**
     * 保存点赞提示时间
     *
     * @param context
     * @param time
     * @author Tony
     * @date 2014年12月22日 下午4:42:03
     */
    public static void savePraiseTime(Context context, String time, int uid) {
        SharedPreferences.Editor sEditor = context.getSharedPreferences(
        		COMMON_STATE, 0).edit();
        sEditor.putString(KEY_PRAISE_TIP_TIME, time);
        sEditor.putInt(KEY_PRAISE_TIP_TIME_ID, uid);
        sEditor.commit();
    }

    /**
     * 获取点赞提示时间
     *
     * @param context
     * @return
     * @author Tony
     * @date 2014年12月22日 下午4:42:13
     */
    public static String readPraiseTime(Context context, int uid) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
        		COMMON_STATE, 0);
        if (uid != sharedPreferences.getInt(KEY_PRAISE_TIP_TIME_ID, 0)) {
            return "";
        }
        return sharedPreferences.getString(KEY_PRAISE_TIP_TIME, "");
    }
}
