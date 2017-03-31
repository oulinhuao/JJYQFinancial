package com.proj.androidlib.receiver;

import com.proj.androidlib.tool.CommonHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 监听目前是否有活动的网络
 *
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
	/**
	 * 网络是否畅通
	 * 
	 */
	public static boolean NET_WORK_ACTIVE = true;

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo == null || !activeNetInfo.isConnected()) {
			NET_WORK_ACTIVE = false;
			CommonHelper.showToast(context, "请检查网络是否异常", 0);
		} else {
			NET_WORK_ACTIVE = true;
		}
	}
}
