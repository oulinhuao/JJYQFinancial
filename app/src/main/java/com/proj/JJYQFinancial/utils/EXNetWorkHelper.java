package com.proj.JJYQFinancial.utils;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.util.EntityUtils;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.proj.androidlib.receiver.ConnectionChangeReceiver;
import com.proj.androidlib.tool.CommonHelper;
import com.proj.androidlib.tool.LogHelper;
import com.proj.androidlib.tool.StringHelper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

/**
 * 网络操作类(拓展)，后期可以更新到工具类中
 * @author Tony
 */
public class EXNetWorkHelper {

	/**
	 * WIFI网络
	 */
	public static final int NETTYPE_NULL = 0x00;
	/**
	 * WIFI网络
	 */
	public static final int NETTYPE_WIFI = 0x01;
	/**
	 * WAP网络
	 */
	public static final int NETTYPE_CMWAP = 0x02;
	/**
	 * NET网络
	 */
	public static final int NETTYPE_CMNET = 0x03;

	/**
	 * 调用WebService方法返回类型：1表示XML
	 */
	public static final int RERURN_TYPE_XML = 1;

	/**
	 * 调用WebService方法返回类型：2表示JSON
	 */
	public static final int RERURN_TYPE_JSON = 2;
	/**
	 * 请求超时时间
	 * 
	 */
	public static int TIME_OUT = 20000;

	/**
	 * 比对是否异常的标准值
	 */
	public static final int REQUEST_NO_ERROR_CODE = -0x1000;
	/**
	 * 比对是否异常的标准值
	 */
	public static final int REQUEST_TIME_OUT = -0x1001;
	/**
	 * 服务端接口异常
	 */
	public static final int REQUEST_ERROR_CODE_1 = -0x10;
	/**
	 * 服务端访问异常
	 */
	public static final int REQUEST_ERROR_CODE_2 = -0x11;
	/**
	 * 网络异常
	 */
	public static final int REQUEST_ERROR_CODE_3 = -0x12;
	/**
	 * 服务端接口异常
	 */
	public static final String REQUEST_ERROR_STR_1 = "ER1";
	/**
	 * 服务端访问异常
	 */
	public static final String REQUEST_ERROR_STR_2 = "ER2";
	/**
	 * 网络异常
	 */
	public static final String REQUEST_ERROR_STR_3 = "ER3";
	/**
	 * API获取有问题
	 */
	public static final String REQUEST_ERROR_STR_NOAPI = "noapi";

	/** 上次显示的时间 */
	private static long mLastToastTime = 0L;
	
	/**
	 * 检查当前是否有可有网络,并确定是否通知
	 * @param context
	 * @param tip ? 当tip为true时进行网络不可用提示,且为true时 不能放在子线程中使用
	 * @return True为可用，False为不可用
	 */
	public static boolean isNetworkAvailable(Context context, boolean tip) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		long current = System.currentTimeMillis();
		if (tip && current - mLastToastTime >= 2000L){
			// 两秒内不重复提醒
			mLastToastTime = current;
			CommonHelper.showToast(context, "当前网络不可用，请检查设置", 0);
		}
		return false;
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @return NETTYPE_NULL：没有网络 ，NETTYPE_WIFI：WIFI网络 ， NETTYPE_CMWAP：WAP网络
	 *         ，NETTYPE_CMNET：NET网络
	 */
	public static int getNetworkType(Context context) {
		int netType = NETTYPE_NULL;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (!StringHelper.isEmpty(extraInfo)) {
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}
	
	/**
	 * 调用WebService方法,返回类型(XML/JSON)----PS:
	 * JSON需要在WebService中添加：[System.Web.Script.Services.ScriptService],
	 * XML需要在WebCofig的system.web中添加HttpSoap&HttpPost注册
	 * 
	 * @param webServiceUrlWithMethod
	 *            WebService地址(带方法)
	 * @param returnType
	 *            返回类型：RERURN_TYPE_XML,RERURN_TYPE_JSON
	 * @return 正常：数据;异常：服务端接口异常-REQUEST_ERROR_CODE_1
	 *         服务端访问异常-REQUEST_ERROR_CODE_2
	 */
	public static String getWSData(String webServiceUrlWithMethod,int returnType) {
		return getWSData(webServiceUrlWithMethod,null,returnType,null);
	}
	
	/**
	 * 调用WebService方法,返回类型(XML/JSON)----PS:
	 * JSON需要在WebService中添加：[System.Web.Script.Services.ScriptService],
	 * XML需要在WebCofig的system.web中添加HttpSoap&HttpPost注册
	 * 
	 * @param webServiceUrlWithMethod
	 *            WebService地址(带方法)
	 * @param returnType
	 *            返回类型：RERURN_TYPE_XML,RERURN_TYPE_JSON
	 * @return 正常：数据;异常：服务端接口异常-REQUEST_ERROR_CODE_1
	 *         服务端访问异常-REQUEST_ERROR_CODE_2
	 */
	public static String getWSData(String webServiceUrlWithMethod,
			int returnType,HashMap<String, Object> map) {
		return getWSData(webServiceUrlWithMethod,null,returnType,map);
	}

	/**
	 * 调用WebService方法,返回类型(XML/JSON)----PS:
	 * JSON需要在WebService中添加：[System.Web.Script.Services.ScriptService],
	 * XML需要在WebCofig的system.web中添加HttpSoap&HttpPost注册
	 * 
	 * @param webServiceUrl
	 *            WebService地址
	 * @param webMethod
	 *            调用方法(如果WebService地址中带有方法，webMethod传null或""即可)
	 * @param returnType
	 *            返回类型：RERURN_TYPE_XML,RERURN_TYPE_JSON
	 * @return 正常：数据;异常：服务端接口异常-REQUEST_ERROR_CODE_1
	 *         服务端访问异常-REQUEST_ERROR_CODE_2
	 */
	public static String getWSData(String webServiceUrl, String webMethod,
			int returnType) {
		return getWSData(webServiceUrl,webMethod,returnType,null);
	}

	/**
	 * 调用WebService方法,返回类型(XML/JSON)----PS:
	 * JSON需要在WebService中添加：[System.Web.Script.Services.ScriptService],
	 * XML需要在WebCofig的system.web中添加HttpSoap&HttpPost注册
	 * 
	 * @param webServiceUrl
	 *            WebService地址
	 * @param webMethod
	 *            调用方法(如果WebService地址中带有方法，webMethod传null或""即可)
	 * @param returnType
	 *            返回类型：RERURN_TYPE_XML,RERURN_TYPE_JSON
	 * @param map
	 *            参数和值
	 * @return 正常：数据;异常：服务端接口异常-REQUEST_ERROR_CODE_1
	 *         服务端访问异常-REQUEST_ERROR_CODE_2
	 */
	public static String getWSData(String webServiceUrl, String webMethod,
			int returnType, HashMap<String, Object> map) {
		return "";
//		String result = "";
//		StringBuilder sBuilder = null;
//		if(map != null){
//			sBuilder = new StringBuilder("{");
//			int i = 0;
//			// 使用迭代器映射map实例
//			Iterator iterator = map.entrySet().iterator();
//			while (iterator.hasNext()) {
//				if (i > 0)
//					sBuilder.append(",");
//				Entry entry = (Entry) iterator.next();
//				Object key = entry.getKey();
//				Object val = entry.getValue();
//				sBuilder.append(key.toString());
//				sBuilder.append(":'");
//				sBuilder.append(val.toString());
//				sBuilder.append("'");
//				i++;
//			}
//			sBuilder.append("}");
//		}
//		if (!ConnectionChangeReceiver.NET_WORK_ACTIVE)
//			return REQUEST_ERROR_STR_3;
//		try {
//			String url = "";
//			if(TextUtils.isEmpty(webMethod)){
//				url = webServiceUrl;
//			}else{
//				url = webServiceUrl + "/" + webMethod;
//			}
//			HttpPost request = new HttpPost(url);
//			if(sBuilder != null){
//				request.setEntity(new StringEntity(sBuilder.toString()));
//			}
//			switch (returnType) {
//			case RERURN_TYPE_XML:
//				request.setHeader("Content-Type", "application/soap+xml");
//				break;
//			case RERURN_TYPE_JSON:
//				request.setHeader("Content-Type", "application/json");
//				break;
//			default:
//				request.setHeader("Content-Type",
//						"application/x-www-form-urlencoded");
//				break;
//			}
//			HttpParams params = new BasicHttpParams();
//			/* 连接超时 */
//			HttpConnectionParams.setConnectionTimeout(params, TIME_OUT);
//			/* 请求超时 */
//			HttpConnectionParams.setSoTimeout(params, TIME_OUT);
//			HttpResponse httpResponse = new DefaultHttpClient(params)
//					.execute(request);
//			int statusCode = httpResponse.getStatusLine().getStatusCode();
//			if (statusCode == 200) {
//				result = EntityUtils.toString(httpResponse.getEntity());
//				return result;
//			} else {
//				LogHelper.customLogging(null, "statusCode:" + statusCode);
//				return REQUEST_ERROR_STR_1;
//			}
//		} catch (Exception e) {
//			LogHelper.errorLogging(e.getMessage());
//			return REQUEST_ERROR_STR_2;
//		}
	}
	
	/**
	 * 根据错误类型返回错误代码，无错误返回 REQUEST_NO_ERROR_CODE
	 * 
	 * @return 服务端接口异常-REQUEST_ERROR_CODE_1，服务端访问异常-REQUEST_ERROR_CODE_2，网络异常-
	 *         REQUEST_ERROR_CODE_3
	 */
	public static int returnErrCodeByStr(String errStr) {
		if (errStr.equals(REQUEST_ERROR_STR_1))
			return REQUEST_ERROR_CODE_1;
		else if (errStr.equals(REQUEST_ERROR_STR_2))
			return REQUEST_ERROR_CODE_2;
		else if (errStr.equals(REQUEST_ERROR_STR_3))
			return REQUEST_ERROR_CODE_3;
		return REQUEST_NO_ERROR_CODE;
	}

	/**
	 * 
	 * 使用ksoap协议调用C#编写的WebService
	 * @param serviceUrl WSDL文档的URL
	 * @param namespace 命名空间
	 * @param method 调用方法名
	 * @param params 请求参数
	 * @return TIME_OUT 访问超时
	 * @author Tony 
	 * @date 2015年4月17日 下午3:01:05
	 */
	public static String getWSDataByKsoap(String serviceUrl,
			String namespace, String method, HashMap<String, Object> params) {
		String result = null;
		String soapAction = namespace + method;
		SoapObject soapObject = new SoapObject(namespace, method);

		if (params != null) {
			for (String key : params.keySet()) {
				soapObject.addProperty(key, params.get(key));
			}
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;

		HttpTransportSE transport = new HttpTransportSE(serviceUrl,TIME_OUT);
		try {
			// 调用WebService
			transport.call(soapAction, envelope);
			SoapObject object = (SoapObject) envelope.bodyIn;
			result = object.getPropertyAsString(0);
		} catch (SocketTimeoutException e) {
			result = "TIME_OUT";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 暂用这个方法
	 * @param serviceUrl
	 * @param namespace
	 * @param method
	 * @param params
	 * @param timeOut
	 * @return
	 */
	public static String getWSDataByKsoap(String serviceUrl,
			String namespace, String method, HashMap<String, Object> params,int timeOut) {
		String result = null;
		String soapAction = namespace + method;
		SoapObject soapObject = new SoapObject(namespace, method);
		
		if (params != null) {
			for (String key : params.keySet()) {
				soapObject.addProperty(key, params.get(key));
			}
		}
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		
		HttpTransportSE transport = new HttpTransportSE(serviceUrl,timeOut);
		try {
			// 调用WebService
			transport.call(soapAction, envelope);
			SoapObject object = (SoapObject) envelope.bodyIn;
			result = object.getPropertyAsString(0);
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			result = "TIME_OUT";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
