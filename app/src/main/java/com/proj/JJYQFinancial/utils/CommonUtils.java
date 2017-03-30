package com.proj.JJYQFinancial.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.UUID;

import com.proj.JJYQFinancial.R;
import com.proj.JJYQFinancial.preferences.PreferencesCommon;
import com.proj.androidlib.tool.CommonHelper;
import com.proj.androidlib.tool.SdCardAndFileHelper;
import com.proj.util.FileHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CommonUtils {
	
	public static UUID getDeviceUuId(Context context) {
		UUID uuid = null;
		final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		try {
			if (!"9774d56d682e549c".equals(androidId)) {
				uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
			} else {
				final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
						.getDeviceId();
				uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
			}
		} catch (UnsupportedEncodingException e) {
			uuid = UUID.fromString("0000");
		}
		return uuid;
	}
	
	/**
	 * 获取版本名
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersionName(Context context) {
		String version = "";
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			version = info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			version = "1.0";
		}
		return version;
	}

	public static int getVersionCode(Context context) {
		int versionCode = 1;
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			versionCode = info.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	public static String getPackageName(Context context) {
		String s = "";
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			// 当前版本的包名
			s = info.packageName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			s = "";
		}
		return s;
	}

	public static int dpToPx(Resources res, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				res.getDisplayMetrics());
	}

	/**
	 * 打开本地已经下载的文件
	 * @param context
	 * @param filePath
	 * @return 是否成功
	 * @author Tony
	 * @date 2015年4月9日 下午3:53:36
	 */
	public static boolean openAPK(Context context, String filePath) {
		boolean back = false;
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		File file = new File(filePath);
		if (file.exists()) {
			PreferencesCommon.saveModifiedTime(context, 0L, 0L);
			String type = getAPKMIMEType(file);
			intent.setDataAndType(Uri.fromFile(file), type);
			context.startActivity(intent);
			back = true;
		} else {
			CommonHelper.showToast(context, R.string.file_not_exist_nomal, 0);
			back = false;
		}
		return back;
	}
	
	/**
	 * 打开文件
	 * 
	 * @param file
	 */
	public static boolean openFile(Context context,File file) {
		boolean back = false;
		try {
			System.gc();
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// 设置intent的Action属性
			intent.setAction(Intent.ACTION_VIEW);
			// 获取文件file的MIME类型
			String type = MIMEType.getMIMEType(file);
			// 设置intent的data和Type属性。
			intent.setDataAndType(/* uri */Uri.fromFile(file), type);
			// 跳转
			context.startActivity(intent);
			back = true;
		} catch (Exception e) {
			e.printStackTrace();
			CommonHelper.showToast(context, "无法打开\"" 
					+ FileHelper.getExtensionName(file.getName())
					+ "\"类型文件，请下载相关软件！", 1);
			back = false;
		}
		return back;
	}
	
	
	
	public static String getAPKMIMEType(File f) {
		String type = "";
		String fName = f.getName();
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase(Locale.getDefault());
		if (end.equals("apk")) {
			type = "application/vnd.android.package-archive";
		} else {
			type += "/*";
		}
		return type;
	}

	/**
	 * 设置全屏
	 * 
	 * @param is
	 *            true 全屏 false 取消全屏
	 * @param activity
	 * @author Tony
	 * @date 2014年10月23日 下午4:33:40
	 */
	public static void setFullScreen(boolean is, Activity activity) {
		if (is) {
			WindowManager.LayoutParams attrs = activity.getWindow()
					.getAttributes();
			attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			activity.getWindow().setAttributes(attrs);
			activity.getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		} else {
			WindowManager.LayoutParams attrs = activity.getWindow()
					.getAttributes();
			attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			activity.getWindow().setAttributes(attrs);
			activity.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}
	
	/**
	 * 检查输入框是否为空
	 * @param editText
	 * @return
	 * @author Tony
	 * @date 2015年3月24日 下午2:52:07
	 */
	public static boolean checkIsNull(EditText editText){
		if(editText == null){
			return false;
		}
		return TextUtils.isEmpty(editText.getText());
	}
	
	/**
	 * 检查密码长度，须在6-20
	 * @param editText 
	 * @return true 符合规范
	 * @author Tony
	 * @date 2015年4月14日 下午3:31:04
	 */
	public static boolean checkPswdLen(EditText editText){
		if(editText == null){
			return false;
		}
		int len = editText.getText().toString().trim().length();
		return len >= 6 && len <= 20;
	}

	/**
	 * 检查内存卡是否可用
	 * 
	 * @param content
	 * @return
	 * @author Tony
	 * @date 2014年9月11日 下午3:17:45
	 */
	public static boolean checkSDCardAvailable(Context content) {
		boolean back = false;
		if (SdCardAndFileHelper.sdCardExist()) {
			if (SdCardAndFileHelper.isAvaiableSpace(10)) {// 低于10M表示不可用
				back = true;
			} else {
				CommonHelper.showToast(content, String.format(
						content.getString(R.string.sdcard_not_enough),
						10), 0);
			}
		} else {
			CommonHelper.showToast(content, R.string.sdcard_not_exist, 0);
		}
		return back;
	}
	
	/**
	 * 获取IpAddress
	 * 
	 * @return
	 */
	public static String getIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取本机号码
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getLine1Number();
	}

	/**
	 * 获取本机Mac
	 * 
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifi.getConnectionInfo();
		if (wifiInfo != null) {
			return wifiInfo.getMacAddress();
		}

		return null;
	}
	
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {    
        ListAdapter listAdapter = listView.getAdapter();     
        if (listAdapter == null) {    
            // pre-condition    
            return;    
        }    
    
        int totalHeight = 0;    
        for (int i = 0; i < listAdapter.getCount(); i++) {    
            View listItem = listAdapter.getView(i, null, listView);    
            listItem.measure(0, 0);    
            totalHeight += listItem.getMeasuredHeight();    
        }    
    
        ViewGroup.LayoutParams params = listView.getLayoutParams();    
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));    
        listView.setLayoutParams(params);    
    }    
	
	/**
	 * 计算图片取样值
	 *
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	/**
	 * 压缩图片
	 * 
	 * @param compressPath
	 * @param path
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static String decodeSampledBitmapFromResource(File compressPath, String path, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;

		// 保存文件
		String filePath = compressPath.getPath();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = df.format(new Date());
		String type = path.substring(path.lastIndexOf("."), path.length());
		File myCaptureFile = new File(filePath, fileName + type);
		BufferedOutputStream bos;
		InputStream is = null;
		Bitmap bm = null;
		try {
			is = new URL("file://" + path).openStream();
			byte[] data = getBytes(is);
			is.close();
			bm = BitmapFactory.decodeByteArray(data, 0, data.length, options);

			bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
			// 100表示不进行压缩，70表示压缩率为30%
			bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (bm != null && !bm.isRecycled()) {
			bm.recycle();
		}

		return myCaptureFile.getPath();
	}
	public static byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024]; // 用数据装
		int len = -1;
		while ((len = is.read(buffer)) != -1) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();

		return outstream.toByteArray();
	}
}
