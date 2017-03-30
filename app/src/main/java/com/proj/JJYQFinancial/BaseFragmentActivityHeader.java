package com.proj.JJYQFinancial;

import com.proj.JJYQFinancial.utils.RecycleBitmap;
import com.proj.androidlib.BaseFragmentActivity;
import com.proj.view.SizeAdjustingTextView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class BaseFragmentActivityHeader extends BaseFragmentActivity{
	/**上下文实例*/
	protected Context mContext;
	/**返回按钮*/
	protected Button mBtnReturnBack;
	/**右侧按钮*/
	protected ImageView mBtnRight;
	/**标题*/
	protected SizeAdjustingTextView mTxtTitle;
	/**背景*/
	protected View mLayoutRoot;
	protected View mLayoutTop;
	
	/**记录界面是否finish，如果是，一切网络操作将不影响界面*/
	protected boolean mIsFinish = false;
	private String mTitleCache = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mContext = this;
		mIsFinish = false;
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
	}
	@Override
	protected void getViews() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub
	}
	
	/***
	 * 右侧按钮的点击事件，如果需要，在子activity里面重写即可
	 * @author Tony
	 */
	protected void pueryClicked(){
		
	}
	
	public void setTitleRight(String title){
		if(mBtnRight == null){
			mBtnRight = (ImageView) this.findViewById(R.id.btn_right);
		}
		setTitle(title);
		setBtnRight();
	}
	
	/**
	 * 设置界面顶部事件,带查询按钮
	 * ：返回按钮点击后会调用onBackPressed();
	 * @param titleRes 标题文字资源id
	 */
	public void setTitleRight(int titleRes){
		if(mBtnRight == null){
			mBtnRight = (ImageView) this.findViewById(R.id.btn_right);
		}
		setTitle(titleRes);
		setBtnRight();
	}
	
	private void setBtnRight(){
		if(mBtnRight != null && mBtnRight.getVisibility() != View.VISIBLE){
			mBtnRight.setVisibility(View.VISIBLE);;
		}
		if(mBtnRight != null){
			mBtnRight.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pueryClicked();
				}
			});
		}
	}
	
	/**
	 * 设置界面顶部事件
	 * ：返回按钮点击后会调用onBackPressed();
	 * @param titleRes 标题文字资源id
	 */
	public void setTitle(int titleRes){
		setTitle(getString(titleRes));
	}
	
	/**
	 * 设置界面顶部事件
	 * ：返回按钮点击后会调用onBackPressed();
	 * @param titleRes 标题文字资源id
	 */
	public void setTitle(String title){
		if(mLayoutTop == null){
			mLayoutTop = this.findViewById(R.id.layout_top);
		}
		
		if(mBtnReturnBack == null){
			mBtnReturnBack = (Button) this.findViewById(R.id.btn_return);
		}
		if(mBtnReturnBack != null){
			mBtnReturnBack.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
		}
		
		if(mTxtTitle == null){
			mTxtTitle = (SizeAdjustingTextView) this.findViewById(R.id.txt_title);
			mTxtTitle.setText(title);
//			TextMover.moveNewTextIntoCell(mTxtTitle, mTitleCache);
		}
		mTitleCache = title;
		
	}
	
//	/**
//	 * 设置背景色
//	 */
//	public void setBG(){
//		if(mLayoutRoot == null){
//			mLayoutRoot = this.findViewById(R.id.layout_root);
//		}
//		if(mLayoutRoot != null){
//			setBackgroudImg(mLayoutRoot,Config.ARGB_8888);
//		}
//	}
//	
//	
//	/**
//	 * 动态添加界面背景，减少内存占用
//	 * @param rootView
//	 * @author Tony
//	 */
//	@SuppressWarnings("deprecation")
//	protected void setBackgroudImg(View rootView){
//		try {
//			if(rootView != null){
//				rootView.setBackgroundDrawable(ImageHelper.getBitmapDrawable(this, R.drawable.bg));
//			}
//		} catch (Exception e) {
//		}
//	}
//	
//	@SuppressWarnings("deprecation")
//	protected void setBackgroudImg(View rootView,Config c){
//		try {
//			if(rootView != null){
//				rootView.setBackgroundDrawable(ImageHelper.getBitmapDrawable(this, R.drawable.bg,c));
//			}
//		} catch (Exception e) {
//		}
//	}
	
	@Override
	protected void onDestroy() {
		mIsFinish = true;
		if(mLayoutRoot != null){
			RecycleBitmap.recycleBackgroundBitMap(mLayoutRoot);
		}
		super.onDestroy();
	}

	
	

	
	
	
	
}
