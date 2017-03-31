package com.proj.androidlib;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.alibaba.fastjson.JSON;
import com.proj.androidlib.model.UpdateApk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 应用程序类
 */
public class AppcationHelper {
	private Context mContext;
	/**
	 * WebService命名空间
	 */
	private static String sNameSpace = "http://tempuri.org/";
	/**
	 * 更新软件WebService地址
	 */
	private static String sStrWebService = "http://www.kingdonsoft.com/KDMSP/WebService/SoftService.asmx";
	/**
	 * 更新软件方法
	 * 
	 */
	private static String sMethodName = "GetNewApk";

	public AppcationHelper(Context context) {
		mContext = context;
	}

	/**
	 * @param context
	 * @param nameSpace
	 *            WebService命名空间
	 * @param strWebService
	 *            更新软件WebService地址
	 * @param methodName
	 *            更新软件方法名
	 */
	public AppcationHelper(Context context, String nameSpace,
			String strWebService, String methodName) {
		mContext = context;
		sNameSpace = nameSpace;
		sStrWebService = strWebService;
		sMethodName = methodName;
	}

	/**
	 * 获取应用程序当前版本号
	 * @return 版本号，默认：1
	 */
	public int getAppVersionCode() {
		int versionCode = 1;
		try {
			// ---get the package info---
			PackageManager pm = mContext.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
			versionCode = pi.versionCode;
		} catch (Exception e) {

		}
		return versionCode;
	}

	/**
	 * 获取应用程序当前版本名称
	 * @return 版本名称，默认：1.0
	 */
	public String getAppVersionName() {
		String versionName = "1.0";
		try {
			// ---get the package info---
			PackageManager pm = mContext.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
			versionName = pi.versionName;
		} catch (Exception e) {

		}
		return versionName;
	}

	/**
	 * 获取应用程序名称
	 * @return 应用程序名称
	 */
	public String getAppName() {
		String appName = "";
		try {
			// ---get the package info---
			PackageManager pm = mContext.getPackageManager();
			ApplicationInfo applicationInfo = pm.getApplicationInfo(
					mContext.getPackageName(), 0);
			appName = pm.getApplicationLabel(applicationInfo).toString();
		} catch (Exception e) {

		}
		return appName;
	}

	/**
	 * 检测是否有更新
	 * @param versionCode
	 * @param appId
	 * @param appSecret
     * @return
     */
	public UpdateApk getUpdate(int versionCode, String appId, String appSecret) {
		UpdateApk updateApk = null;
		try {
			SoapObject rpc = new SoapObject(sNameSpace, sMethodName);
			rpc.addProperty("versionCode", versionCode);
			rpc.addProperty("appId", appId);
			rpc.addProperty("appSecret", appSecret);
			rpc.addProperty("deviceType", 0);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER12);
			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);
			HttpTransportSE ht = new HttpTransportSE(sStrWebService);
			ht.call(sNameSpace + sMethodName, envelope);
			Object detail = envelope.getResponse();
			updateApk = JSON.parseObject(detail.toString(), UpdateApk.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updateApk;
	}
}
