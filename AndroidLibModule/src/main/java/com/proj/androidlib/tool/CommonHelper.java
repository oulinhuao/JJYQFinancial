package com.proj.androidlib.tool;

import android.content.Context;
import android.widget.Toast;

/**
 * 通用工具类：弹出提示、检测网络等
 */
public class CommonHelper {

	/**
	 * 弹出Android-Toast提示
	 * 
	 * @param context
	 * @param rID
	 *            资源在程序中的编号
	 * @param durationType
	 *            0:短时间,1:更长时间
	 */
	public static void showToast(Context context, int rID, int durationType) {
		if (rID > 0) {
			Toast.makeText(context, rID, durationType).show();
		}
	}

	/**
	 * 弹出Android-Toast提示
	 * 
	 * @param context
	 * @param str
	 *            文字内容
	 * @param durationType
	 *            0:短时间,1:更长时间
	 */
	public static void showToast(Context context, String str, int durationType) {
		if (!str.equals("")) {
			Toast.makeText(context, str, durationType).show();
		}
	}

	/**
	 *
	 * 拼接网络连接地址
	 *            string[0]=192.168.0.1,string[1]=web
	 * @param strings
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
}
