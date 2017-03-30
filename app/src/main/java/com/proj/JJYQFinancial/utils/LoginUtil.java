package com.proj.JJYQFinancial.utils;

import com.proj.JJYQFinancial.LoginActivity;
import com.proj.base.GlobalConfig;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

public class LoginUtil {
	public static String KEY = "OPEN_NAME";

	private static final int requestCodeDefault = 12345;
	
	/**
	 * 检查是否登录，没有登录直接打开登陆界面 登录完了会自动关闭登录界面
	 * @param context
	 * @return
	 * @author Tony
	 */
	public static boolean getIsLogin(Context context){
		boolean back = false;
		if(!getIsLogin(context,requestCodeDefault,"")){
		}else{
			back = true;
		}
		return back;
	}
	
	/**
	 * 检查是否登录，没登陆打开登录界面
	 * @param context
	 * @param requestCode
	 * @param activityName
	 * @return
	 * @author Tony
	 */
	public static boolean getIsLogin(Context context,int requestCode,
			String activityName){
		boolean back = false;
		if(EXNetWorkHelper.isNetworkAvailable(context, true)){
			if(!GlobalConfig.getIsLogin()){
				Intent intent = null;
				intent = new Intent(context,LoginActivity.class);
				Bundle bundle = new Bundle();
				if(!TextUtils.isEmpty(activityName)){
					bundle.putString(KEY, activityName);
				}
				bundle.putBoolean("mIsAliveOpen", true);
				intent.putExtras(bundle);
				((Activity) context).startActivityForResult(intent,requestCode);
			}else{
				back = true;
			}
		}else{
			back = GlobalConfig.getIsLogin();
		}
		return back;
	}
	
	
}
