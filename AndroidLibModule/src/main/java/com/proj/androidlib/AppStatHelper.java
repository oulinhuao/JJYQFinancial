package com.proj.androidlib;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.proj.androidlib.tool.LogHelper;
import com.proj.androidlib.tool.NetWorkHelper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

/**
 * 程序运行统计
 *
 */
public class AppStatHelper {
	/**
	 * 提交统计的命名空间
	 * 
	 */
	public static String NAMESPACE = "http://tempuri.org/";
	/**
	 * 提交统计的WebService地址
	 */
	// public static String WEB_SERVICE_URL =
	// "http://www.kingdonsoft.com/Mobileutil/WebService/SoftService.asmx";
	public static String WEB_SERVICE_URL = "http://www.kingdonsoft.com/KDMSP/webservice/logservice.asmx";
	/**
	 * 提交异常的方法
	 * 
	 */
	public static String METHOD_NAME = "InsertAppStat";
	protected static final String PREFS_FILE = "device_id";
	protected static final String PREFS_DEVICE_ID = "device_id";
	private static UUID mUuid;
	private Context mContext;
	private String mAppId, mAppSecret;

	public AppStatHelper(Context context, String appId, String appSecret) {
		mContext = context;
		mAppId = appId;
		mAppSecret = appSecret;
		if (mUuid == null) {
			final String androidId = Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID);
			try {
				if (!"9774d56d682e549c".equals(androidId)) {
					mUuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
				} else {
					final String deviceId = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE))
							.getDeviceId();
					mUuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
				}
			} catch (UnsupportedEncodingException e) {
				mUuid = UUID.fromString("0000");

			}
		}
	}

	public UUID getDeviceUuid() {
		return mUuid;
	}

	/**
	 * 发送错误报告到服务端
	 */
	public void submitToServer() {
		if (NetWorkHelper.isNetworkAvailable(mContext, false))
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						PackageManager pm = mContext.getPackageManager();
						PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
						SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
						rpc.addProperty("appId", mAppId);
						rpc.addProperty("appSecret", mAppSecret);
						rpc.addProperty("phoneType", 0);//phoneType 0-android 1-ios
						rpc.addProperty("appVersion", info.versionCode);
						rpc.addProperty("phoneFactory", Build.MANUFACTURER);
						rpc.addProperty("phoneModel", Build.MODEL);
						rpc.addProperty("oSVersion", Build.VERSION.RELEASE);
						rpc.addProperty("phoneId", mUuid.toString());
						TelephonyManager mTm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
						rpc.addProperty("phoneNum", mTm.getLine1Number());
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.bodyOut = rpc;
						envelope.dotNet = true;
						envelope.setOutputSoapObject(rpc);
						HttpTransportSE ht = new HttpTransportSE(WEB_SERVICE_URL);
						ht.call(NAMESPACE + METHOD_NAME, envelope);
						envelope.getResponse();
					} catch (Exception e) {
						LogHelper.errorLogging();
					}
				}
			}).start();

	}
}
