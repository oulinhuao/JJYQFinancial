package com.proj.JJYQFinancial.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.proj.JJYQFinancial.R;
import com.proj.JJYQFinancial.biz.BaseService;
import com.proj.JJYQFinancial.utils.EXNetWorkHelper;
import com.proj.JJYQFinancial.utils.EXStringHelper;
import com.proj.androidlib.tool.LogHelper;
import com.proj.androidlib.tool.NetWorkHelper;

import android.app.Activity;
import android.text.TextUtils;

/**
 * 信息
 */

// 业务层。activity的只要业务逻辑放到这里来写
public class InformationService extends BaseService{
	
//	private InformationDaoHelper mWorkNoticeHelper = null;
//	public InformationDaoHelper getDaoHelper(){
//		return mWorkNoticeHelper;
//	}
//	
	public InformationService(Activity activity) {
		super(activity);
//		this.mWorkNoticeHelper = new InformationDaoHelper(mContext);
	}
	
	/**
	 * 获取总条数
	 * @return
	 */
	public int getEntityCount(int type){
		int  result=RETURN_CODE_EXCEPTION;
		try {
			// 从网络获取数据 第一步，拿到url，url是由一个服务端的接口地址（下面的url）加服务端的函数名称（下面的method）组成
			// 这里用的接口 是服务端（.net）用C#写的WebService接口，具体可以百度
			
			String url = mContext.getString(R.string.server_interface_info);// http://www.kingdonsoft.com/zsnd/MobielWebService/InformationMobileService.asmx
			String method = mContext.getString(R.string.server_method_InformationCount);// GetInformationCount
			// 接口需要的参数1
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("DataType", getPaType(type));// 接口需要的参数1
			map.put("condition", getCondition());// 接口需要的参数2
			
			String jsonValue = "";
			jsonValue = EXNetWorkHelper.getWSDataByKsoap(
					EXStringHelper.getWebServiceUrl(mContext,
							url),
							NAME_SPACE,
							method,
							map);
		
			if(checkBackValue(jsonValue, true)){
				if(EXStringHelper.isNumeric(jsonValue)){
					result = Integer.valueOf(jsonValue);
				}else{
					return 0;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result = RETURN_CODE_EXCEPTION;
		}
		return result;
	}
	public int getEntityCountById(int id){
		int  result=RETURN_CODE_EXCEPTION;
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("DataType", "");
			map.put("condition", getCondition(id));
			String jsonValue = "";
			jsonValue = EXNetWorkHelper.getWSDataByKsoap(mContext.getString(R.string.server_interface_info),
					NAME_SPACE,
					mContext.getString(R.string.server_method_InformationCount),
					map);
			
			if(checkBackValue(jsonValue, true)){
				JSONObject jsonObject = JSON.parseObject(jsonValue);
				String value = jsonObject.getString("d");
				if(TextUtils.isEmpty(value) || value.equals("null")){
					return 0;
				}
				result = Integer.valueOf(value);
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result = RETURN_CODE_EXCEPTION;
		}
		return result;
	}
	/**
	 * 翻页获取列表
	 * @param pageSize
	 * @param pageIndex
	 * @param cid
	 * @return
	 */
	public List<Information> getEntityListPaged(int pageSize, int pageIndex,
			int type) {
		List<Information> list = new ArrayList<Information>();
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			map.put("DataType", getPaType(type));
			map.put("pageSize", pageSize);
			map.put("pageIndex", pageIndex);
			map.put("condition", getCondition());
			map.put("DType", deviceType);
			
			String jsonValue = "";
			jsonValue = EXNetWorkHelper.getWSDataByKsoap(
					EXStringHelper.getWebServiceUrl(mContext, mContext.getString(R.string.server_interface_info)),
					NAME_SPACE,
					mContext.getString(R.string.server_method_InformationList),
					map);
		
			if(checkBackValue(jsonValue, true)){
				LogHelper.customLogging("成功");
				if(jsonValue.startsWith("[")){
					list = JSON.parseArray(jsonValue, Information.class);
					// 获取到了列表
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	public List<Information> getEntityListById(int pageSize, int pageIndex,
			int id) {
		List<Information> list = new ArrayList<Information>();
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			map.put("DataType", "");
			map.put("pageSize", pageSize);
			map.put("pageIndex", pageIndex);
			map.put("condition", getCondition(id));
			map.put("DType", deviceType);
			
			String jsonValue = "";
			jsonValue = EXNetWorkHelper.getWSDataByKsoap(
					EXStringHelper.getWebServiceUrl(mContext, mContext.getString(R.string.server_interface_info)),
					NAME_SPACE,
					mContext.getString(R.string.server_method_InformationList),
					map);
			
			if(checkBackValue(jsonValue, true)){
				LogHelper.customLogging("成功");
				JSONObject jsonObject = JSON.parseObject(jsonValue);
				String value = jsonObject.getString("d");
				if(TextUtils.isEmpty(value) || value.equals("null")){
					return list;
				}
				list = JSON.parseArray(value, Information.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	
	
	private String getCondition(){
		StringBuilder sb = new StringBuilder();
		sb.append(" and IsDeleted = 0 and IsDisplay = 1 ");
		return sb.toString();
	}
	private String getCondition(int id){
		StringBuilder sb = new StringBuilder();
		sb.append(" and IsDeleted = 0 and IsDisplay = 1  and CategoryId = ");
		sb.append(id);
		return sb.toString();
	}
	private String getPaType(int type){
		String s = "";
		switch(type){
		case 0:
			s = "zxzx";
			break;
		case 1:
			s = "nmkj";
			break;
		case 2:
			s = "tfjs";
			break;
		case 3:
			s = "hmzc";
			break;
		}
		return s;
	}
	
	public Information downContent(int id){
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("infoId", id);
			map.put("DType", deviceType);
			
			String webserviceUrl = EXStringHelper.getWebServiceUrl(mContext,
					mContext.getString(R.string.server_interface_info));//接口地址
			String method = mContext.getString(R.string.server_method_InformationContent);
			
			String jsonValue = NetWorkHelper.getWSDataByKsoap(
					webserviceUrl,
					method,
					NAME_SPACE,
					map);
		
			if(checkBackValue(jsonValue, true)){
				LogHelper.customLogging("成功");
				String value = "";
				JSONObject jsonObject = JSON.parseObject(jsonValue);
				value = jsonObject.getString("d");
				if(TextUtils.isEmpty(value) || value.equals("null")){
					return null;
				}
				return JSON.parseObject(value,Information.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
	
}
