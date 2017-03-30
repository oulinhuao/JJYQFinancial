package com.proj.base;

import com.proj.JJYQFinancial.preferences.PreferencesCommon;

import android.content.Context;

/**
 * 全局配置
 * @author Tony
 */
public class GlobalConfig {
	
	/** 登陆用户的id，<=0 表示没登陆 */
	public static int USER_ID = 0;
	
	/** 程序是否在运行 */
	public static boolean IS_RUNNING = false;
	
	/** 下载数据列表时提示条数 */
	public final static int MSG_WHAT_TIP_DOWN_COUNT = 5370;
	/** 通知界面，有数据需要下载 */
	public final static int MSG_WHAT_TIP_NEED_DOWN = 5371;
	
	/** 下载数据时提示文字的键 */
	public static String getKeyMsgText(){
		return "Text1";
	}
	
	/**
	 * 设定是否登录
	 * @param is
	 * @author Tony
	 */
	public static void setIsLogin(boolean is){
		if(!is){
			setUserId(0);
		}
	}
	
	/**
	 * 获得是否登录
	 * @return
	 * @author Tony
	 */
	public static boolean getIsLogin(){
		return USER_ID > 0;
	}
	
	/**
	 * 设定用户id
	 * @param id
	 * @author Tony
	 */
	public static void setUserId(int id){
		USER_ID = id;
	}
	
	/**
	 * 获取登陆用户的id
	 * @return
	 * @author Tony
	 */
	public static int getUserId(){
		return USER_ID;
	}
	private static String Token = "";
	public static String getToken(){
		return Token;
	}
	public static void setToken(String t,String code){
		Token = t;
	}
	
	
	
	public static void setExit(){
		IS_RUNNING = false;
		setIsLogin(false);
	}
	
	public static int getAppId(){
		return 1;
	}

	
	private static int mScreenWidth = 0;
	private static int mScreenHeight = 0;

	public static int getScreenWidth(Context context){
		if(mScreenWidth == 0){
			int[] info = PreferencesCommon.readScreenInfo(context);
			mScreenWidth = info[0];
			mScreenHeight = info[1];
		}
		return mScreenWidth;
	}
	
	
	public static int getScreenHeight(Context context){
		if(mScreenWidth == 0){
			int[] info = PreferencesCommon.readScreenInfo(context);
			mScreenWidth = info[0];
			mScreenHeight = info[1];
		}
		return mScreenHeight;
	}
	public static void setScreenInfo(int w,int h){
		mScreenWidth = w;
		mScreenHeight = h;
	}
	
	
	/** 加密字段 */
	private static final String COM_NAME = "KINGDON";
	/** 登陆后赋值TokenId */
	public static String TOKEN_ID = "";
	/**
	 * 设定用户TokenId
	 * 
	 */
	public static void setTokenId(String tokenId) {
		TOKEN_ID = tokenId;
	}

	/**
	 * 获取登陆用户的TokenId
	 * 
	 */
	public static String getTokenId() {
		return TOKEN_ID;
	}

	/**
	 * 获取登陆用户的TokenId
	 * 
	 */
	public static String getTokenIdPsd() {
		return TOKEN_ID + COM_NAME;
	}
	
}
