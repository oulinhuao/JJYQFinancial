package com.proj.JJYQFinancial;

import butterknife.ButterKnife;

import com.proj.androidlib.tool.LogHelper;
import com.proj.view.SizeAdjustingTextView;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MyBaseFragment extends Fragment {

	protected Context mContext;
	protected SizeAdjustingTextView mTxtTile;
	protected View mRootView;

	/**记录界面是否finish，如果是，一切网络操作将不影响界面*/
	protected boolean mIsFinish = false;
	
	/**
	 * 设定标题名称
	 * @param titleStrRes 文字id
	 * @author Tony
	 */
	protected void setTitle(int titleStrRes){
		setTitle(mContext.getString(titleStrRes));
	}
	
	
	/**
	 * 设定标题名称
	 * @param titleStr 文字
	 * @author Tony
	 * 
	 * 
	 */
	protected void setTitle(String titleStr){
		if(mRootView == null){
			LogHelper.errorLogging("Fragment布局不存在");
			return;
		}
		
		if(mTxtTile == null){
			mTxtTile = (SizeAdjustingTextView)mRootView.findViewById(R.id.txt_title);
		}
		if(mTxtTile != null){
			mTxtTile.setText(titleStr);
		}
	}
	
	/**
	 * 隐藏返回按钮
	 * 
	 * @author Tony
	 * @date 2015年4月28日 下午2:49:45
	 */
	protected void setNOReturnBtn(){
		if(mRootView == null){
			LogHelper.errorLogging("Fragment布局不存在");
			return;
		}
		Button returnBtn = null;
		if(returnBtn == null){
			returnBtn = (Button)mRootView.findViewById(R.id.btn_return);
		}
		if(returnBtn != null){
			returnBtn.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mIsFinish = false;
	}
	
	@Override
	public void onResume() {
		mIsFinish = false;
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		mIsFinish = true;
//		ButterKnife.Unbinder(this);
		super.onDestroy();
	}
	
	@Override
	public void onStop() {
		mIsFinish = true;
		super.onStop();
	}
	
	
}
