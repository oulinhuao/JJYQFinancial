package com.proj.JJYQFinancial.dialog;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.proj.JJYQFinancial.R;
import com.proj.androidlib.tool.LogHelper;
import com.proj.base.GlobalConfig;
import com.proj.view.ProgressWheel;

import android.app.Dialog;
import android.content.Context;

/**
 * 加载等待对话框
 * @author Tony
 */
public class LoadingDialog extends Dialog {
	private TextView mTxtT;
	private Context mContext;
	private String mContent = "";
	public boolean mIsCancle = false;
	private ProgressWheel mProgressWheel;
	
	public LoadingDialog(Context context) {
		super(context, R.style.NoTitleDialog);
		setContentView(R.layout.dialog_loading);
		this.mContext = context;
		mTxtT = (TextView) this.findViewById(R.id.txt);
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
		mIsCancle = false;
		mProgressWheel = (ProgressWheel)this.findViewById(R.id.progressWheel1);
	}

	public void setLoadingText(String text) {
		mContent = text;
	}
	
	public void setLoadingText(int textRes) {
		setLoadingText(mContext.getString(textRes));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window w = this.getWindow();
		w.setGravity(Gravity.CENTER);
		int l = GlobalConfig.getScreenWidth(mContext)/ 3;
		if(l < 200){
			l = 200;
		}
		getWindow().setLayout(l,l);
		mIsCancle = false;
		if(!TextUtils.isEmpty(mContent)){
			if(mTxtT.getVisibility() != View.VISIBLE){
				mTxtT.setVisibility(View.VISIBLE);
			}
		}else{
			mContent = mContext.getString(R.string.loading);
		}
		mTxtT.setText(mContent);
		if(mProgressWheel != null){
			mProgressWheel.spin();
		}
		LogHelper.customLogging("onCreate");
	}
	
	@Override
	public void cancel() {
		super.cancel();
		mIsCancle = true;
	}
	
	
	public void setDestroy(){
		if(mProgressWheel != null){
			mProgressWheel.stopSpinning();
		}
	}

//	@Override
//	public void dismiss() {
//		if(mProgressWheel != null){
//			mProgressWheel.stopSpinning();
//		}
//		super.dismiss();
//	}
	
	
	
	
}
