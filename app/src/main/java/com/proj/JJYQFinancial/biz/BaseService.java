package com.proj.JJYQFinancial.biz;

import java.lang.ref.WeakReference;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.proj.JJYQFinancial.utils.EXNetWorkHelper;
import com.proj.androidlib.tool.CommonHelper;
import com.proj.androidlib.tool.LogHelper;
import com.proj.base.GlobalConfig;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

/***
 * 业务逻辑基类，这里增加了一些返回提示功能
 * <p> 要注意，不能在非UI线程实例化
 * @author Tony
 */
public class BaseService {
	public static final String NAME_SPACE = "http://tempuri.org/";
	
	/**
	 * 成功
	 */
	public final int RETURN_CODE_SUCCESS = -40000;
	
	/**
	 * 令牌失效、接口访问失败
	 */
	public static final int RETURN_CODE_TOKENFAIL = -40001;
	/**
	 * 程序异常
	 */
	public final int RETURN_CODE_EXCEPTION = -40002;
	
	/**
	 * 空字符串
	 */
	public final int RETURN_CODE_STRINGEMPTY=-40003;
	
	/**
	 * Null值
	 */
	public final int RETURN_CODE_NULL=-40004;
	
	public static final String ERROR_START_FALG = "#error#";
	
	
	public Context mContext = null;
	public Activity mActivity = null;
	private MsgHandler mMsgHandler;
	private boolean mIsAlive = false;
	
	private final int ERROR_NULL = 0;
	/**设备类型 0：安卓登陆 1：苹果登陆*/
	public final int deviceType = 0;
	
	public BaseService(Activity activity){
		mContext = activity;
		mActivity = activity;
		mIsAlive = true;
		mMsgHandler = new MsgHandler(this);
	}
	
	/**
	 * 
	 * @param activity
	 * @param isInter
	 * @author Tony
	 */
	public BaseService(Activity activity,boolean isInter){
		mContext = activity;
		mActivity = activity;
		mIsAlive = true;
		if(!isInter){
			mMsgHandler = new MsgHandler(this);
		}
		
	}
	
	/**
	 * 检查返回值是否有异常
	 * @param jsonValue
	 * @param shoudTip 是否需要弹出提示
	 * @return
	 * @author Tony
	 */
	public boolean checkBackValue(String jsonValue,boolean shoudTip){
		boolean back = true;
		if(TextUtils.isEmpty(jsonValue)){
			//为空
			back = false;
			if(shoudTip && mMsgHandler != null){
				mMsgHandler.sendEmptyMessage(ERROR_NULL);
			}
		}else if(jsonValue.equals("null")){
			back = false;
			if(shoudTip && mMsgHandler != null){
				mMsgHandler.sendEmptyMessage(ERROR_NULL);
			}
		}else if(jsonValue.equals(EXNetWorkHelper.REQUEST_ERROR_STR_1)){
			back = false;
			if(shoudTip && mMsgHandler != null){
				mMsgHandler.sendEmptyMessage(EXNetWorkHelper.REQUEST_ERROR_CODE_1);
			}
		}else if(jsonValue.equals(EXNetWorkHelper.REQUEST_ERROR_STR_2)){
			back = false;
			if(shoudTip && mMsgHandler != null){
				mMsgHandler.sendEmptyMessage(EXNetWorkHelper.REQUEST_ERROR_CODE_2);
			}
		}else if(jsonValue.equals(EXNetWorkHelper.REQUEST_ERROR_STR_3)){
			back = false;
			if(shoudTip && mMsgHandler != null){
				mMsgHandler.sendEmptyMessage(EXNetWorkHelper.REQUEST_ERROR_CODE_3);
			}
		}else if(jsonValue.equals(EXNetWorkHelper.REQUEST_ERROR_STR_NOAPI)){
			back = false;
			LogHelper.errorLogging("api获取有问题");
		}
		return back;
	}
	
	/**
	 * 检查是否Token失效
	 * @param valueContent
	 * @return
	 * @author Tony
	 */
	public boolean checkIsTokenAvailable(String valueContent){
		boolean back = true;
		if(!TextUtils.isEmpty(valueContent)){
			if(valueContent.trim().equals("TokenFail")){
				back = false;
				if(mMsgHandler != null){
					// 1秒后 处理TokenFail的事情
					mMsgHandler.sendEmptyMessageDelayed(RETURN_CODE_TOKENFAIL,500L);
				}
			}
		}
		return back;
	}
	
	public boolean checkBackValueNotNull(String jsonValue,boolean shoudTip){
		boolean back = true;
		if(jsonValue.equals("null")){
			back = false;
			if(shoudTip && mMsgHandler != null){
				mMsgHandler.sendEmptyMessage(ERROR_NULL);
			}
		}else if(jsonValue.equals(EXNetWorkHelper.REQUEST_ERROR_STR_1)){
			back = false;
			if(shoudTip && mMsgHandler != null){
				mMsgHandler.sendEmptyMessage(EXNetWorkHelper.REQUEST_ERROR_CODE_1);
			}
		}else if(jsonValue.equals(EXNetWorkHelper.REQUEST_ERROR_STR_2)){
			back = false;
			if(shoudTip && mMsgHandler != null){
				mMsgHandler.sendEmptyMessage(EXNetWorkHelper.REQUEST_ERROR_CODE_2);
			}
		}else if(jsonValue.equals(EXNetWorkHelper.REQUEST_ERROR_STR_3)){
			back = false;
			if(shoudTip && mMsgHandler != null){
				mMsgHandler.sendEmptyMessage(EXNetWorkHelper.REQUEST_ERROR_CODE_3);
			}
		}
		return back;
	}
	
	/**
	 * 检查返回数据是否正常（ErrorCode ！= 0）
	 * @param value
	 * @return 如果数据正常，返回DataObject，否则返回ErrorMsg（以ERROR_START_FALG开头）
	 * 			返回“”代表返回值有问题了
	 * @author Tony
	 * @date 2015年4月6日 下午2:07:21
	 */
	public String checkErrorCode(String value){
		if(TextUtils.isEmpty(value)){
			return "";
		}
		String back = "";
		JSONObject jBack = JSON.parseObject(value);
		if(jBack.containsKey("ErrorCode")){
			int code = jBack.getIntValue("ErrorCode");
			if(code == 0 && jBack.containsKey("DataObject")){
				// 数据正常
				back = jBack.getString("DataObject");
			}else{
				if(jBack.containsKey("ErrorMsg")){
					String errorMsg = jBack.getString("ErrorMsg");
					if(!TextUtils.isEmpty(errorMsg)){
						back = ERROR_START_FALG+errorMsg;
					}
				}else{
					LogHelper.errorLogging("!containsKey ErrorMsg");
				}
			}
		}else{
			LogHelper.errorLogging("!containsKey ErrorCode");
		}
		return back;
	}
	
	/**
	 * 根据消息执行任务
	 * @param msg
	 * @author Tony
	 */
	private void executeByMessage(Message msg){
		switch (msg.what) {
		case EXNetWorkHelper.REQUEST_ERROR_CODE_1:
			CommonHelper.showToast(mContext, "服务端接口异常", 0);
			break;
		case EXNetWorkHelper.REQUEST_ERROR_CODE_2:
			CommonHelper.showToast(mContext, "服务端访问异常", 0);
			break;
		case EXNetWorkHelper.REQUEST_ERROR_CODE_3:
			CommonHelper.showToast(mContext, "网络异常", 0);
			break;
		case ERROR_NULL:
			// 没有内容
			break;
		}
	}
	
	private static class MsgHandler extends Handler{
		private WeakReference<Object> mOB;
		BaseService owner = null;
		public MsgHandler(BaseService ob){
			super();
			mOB = new WeakReference<Object>(ob);
			owner = (BaseService)mOB.get();
		}
		@Override
		public void handleMessage(Message msg) {
			if(msg == null || owner == null){
				return;
			}
			owner.executeByMessage(msg);
			super.handleMessage(msg);
		}
	}
	/**
	 * 通知UI显示加载框
	 * @param handler
	 * @author Tony
	 */
	protected void showTip(Handler handler){
		if(mIsAlive == false){
			return ;
		}
		Message msg = new Message();
		msg.what = GlobalConfig.MSG_WHAT_TIP_NEED_DOWN;
		// 有数据需要下载，项目显示等待布局
		handler.sendMessage(msg);
	}
	
	/**
	 * 通知UI显示下载进度
	 * @param handler
	 * @author Tony
	 */
	protected void showTipMsg(Handler handler,String str){
		if(mIsAlive == false){
			return ;
		}
		Message msg = new Message();// 重新获取一次，避免报错（This message is already in use）
		msg.what = GlobalConfig.MSG_WHAT_TIP_DOWN_COUNT; 
		Bundle bundle = new Bundle();    
		bundle.putString(GlobalConfig.getKeyMsgText(),str);  //往Bundle中存放数据  
		msg.setData(bundle);//mes利用Bundle传递数据  
		handler.sendMessage(msg);//用activity中的handler发送消
	}
	

}
