package com.proj.androidlib.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.util.EntityUtils;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.proj.androidlib.receiver.ConnectionChangeReceiver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络操作类
 */
public class NetWorkHelper {
	/**
	 * 网络是否畅通
	 * 
	 */
	public static boolean NET_WORK_ACTIVE = true;
	/**
	 * 无网络
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
	public static int TIME_OUT = 10000;

	/**
	 * 比对是否异常的标准值
	 */
	public static final int REQUEST_NO_ERROR_CODE = -0x1000;
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
	 * 检查网络是否连接
	 * 
	 * @param context
	 * @param t
	 *            当t为true时进行网络不可用提示
	 * @return True为可用，False为不可用
	 */
	public static boolean isNetworkAvailable(Context context, boolean t) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				return true;
			}
		}
		if (t)
			CommonHelper.showToast(context, "当前网络不可用，请检查设置", 0);
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
	 * @param webServiceUrl
	 *            WebService地址
	 * @param webMethod
	 *            调用方法
	 * @param returnType
	 *            返回类型：RERURN_TYPE_XML,RERURN_TYPE_JSON
	 * @return 正常：数据;异常：服务端接口异常-REQUEST_ERROR_CODE_1
	 *         服务端访问异常-REQUEST_ERROR_CODE_2
	 */
	public static String getWSData(String webServiceUrl, String webMethod,
			int returnType) {
		String result = "";
		return "";
//		if (!ConnectionChangeReceiver.NET_WORK_ACTIVE)
//			return REQUEST_ERROR_STR_3;
//		try {
//			HttpPost request = new HttpPost(webServiceUrl + "/" + webMethod);
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
//
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
//
//		} catch (Exception e) {
//			LogHelper.errorLogging(e.getMessage());
//			return REQUEST_ERROR_STR_2;
//		}
	}

	/**
	 * 调用WebService方法,返回类型(XML/JSON)----PS:
	 * JSON需要在WebService中添加：[System.Web.Script.Services.ScriptService],
	 * XML需要在WebCofig的system.web中添加HttpSoap&HttpPost注册
	 * 
	 * @param webServiceUrl
	 *            WebService地址
	 * @param webMethod
	 *            调用方法
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
//		StringBuilder sBuilder = new StringBuilder("{");
//		int i = 0;
//		// 使用迭代器映射map实例
//		Iterator iterator = map.entrySet().iterator();
//		while (iterator.hasNext()) {
//			if (i > 0)
//				sBuilder.append(",");
//			Entry entry = (Entry) iterator.next();
//			Object key = entry.getKey();
//			Object val = entry.getValue();
//			sBuilder.append(key.toString());
//			sBuilder.append(":'");
//			sBuilder.append(val.toString());
//			sBuilder.append("'");
//			i++;
//		}
//		sBuilder.append("}");
//		if (!ConnectionChangeReceiver.NET_WORK_ACTIVE)
//			return REQUEST_ERROR_STR_3;
//		try {
//			HttpPost request = new HttpPost(webServiceUrl + "/" + webMethod);
//			request.setEntity(new StringEntity(sBuilder.toString()));
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
	 * 使用ksoap协议调用WebService
	 * 
	 * @param serviceUrl
	 *            WSDL文档的URL
	 * @param namespace
	 *            命名空间
	 * @param method
	 *            调用方法名
	 * @param params
	 *            请求参数
	 * @param isNet
	 *            是否.Net服务端
	 * @return 请求返回结果
	 */
	public static String getWSDataByKsoap(String serviceUrl, String namespace,
			String method, HashMap<String, Object> params, boolean isNet) {
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
		envelope.dotNet = isNet;
		HttpTransportSE transport = new HttpTransportSE(serviceUrl);
		try {
			// 调用WebService
			transport.call(soapAction, envelope);
			SoapObject object = (SoapObject) envelope.bodyIn;
			result = object.getPropertyAsString(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	 /**
     * 使用ksoap协议调用C#编写的WebService
     *
     * @param serviceUrl WSDL文档的URL
     * @param namespace  命名空间
     * @param method     调用方法名
     * @param params     请求参数
     * @return 请求返回结果
     */
    public static String getWSDataByKsoap(String serviceUrl, String namespace,
                                          String method, HashMap<String, Object> params) {
        String result = null;
        String soapAction = namespace + method;
        SoapObject soapObject = new SoapObject(namespace, method);

        if (params != null) {
            for (String key : params.keySet()) {
                // soapObject.addProperty(key, params.get(key));
                PropertyInfo pi = new PropertyInfo();
                pi.setName(key);
                pi.setValue(params.get(key));

                if (params.get(key) != null)
                    pi.setType(params.get(key).getClass());

                soapObject.addProperty(pi);
            }
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);

        HttpTransportSE transport = new HttpTransportSE(serviceUrl);
        try {
            // 解决异常java.io.EOFException
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection",
                    "close"));
            // 调用WebService
            transport.call(soapAction, envelope, headerPropertyArrayList);
            if (envelope.bodyIn instanceof SoapFault) {
                String str = ((SoapFault) envelope.bodyIn).faultstring;
                LogHelper.errorLogging(str);
            } else {
                SoapObject object = (SoapObject) envelope.bodyIn;
                if (object.getPropertyCount() > 0)
                    result = object.getPropertyAsString(0);
            }
        } catch (Exception e) {
            LogHelper.errorLogging(e.toString());
        } finally {
            if (transport != null) {
                transport.reset();
                try {
                    transport.getServiceConnection().disconnect();
                } catch (Exception e) {
                    LogHelper.errorLogging(e.toString());
                }
            }
        }
        return result;
    }
}
