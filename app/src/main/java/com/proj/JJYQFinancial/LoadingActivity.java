package com.proj.JJYQFinancial;

import java.lang.ref.WeakReference;

import com.proj.JJYQFinancial.R;
import com.proj.androidlib.BaseActivity;

import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * 引导页
 * @author Tony
 */
public class LoadingActivity extends BaseActivity {
	
	private final int MSG_GO_MAIN = 10;
	
	@Override
	public void init() {
		setContentView(R.layout.activity_loading);
		
		mMsgHandler.sendEmptyMessageDelayed(MSG_GO_MAIN, 1000);
	}

	@Override
	public void onClick(View v) {
		
	}

	@Override
	protected void getViews() {
		
	}

	@Override
	protected void setListeners() {
		
	}
	
	
	
	private MsgHandler mMsgHandler = new MsgHandler(this);
	private static class MsgHandler extends Handler{
		private WeakReference<Object> mOB;
		LoadingActivity owner = null;
		public MsgHandler(LoadingActivity ob){
			mOB = new WeakReference<Object>(ob);
			owner = (LoadingActivity)mOB.get();
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
	
	protected void executeByMessage(Message msg){
		switch (msg.what) {
		case MSG_GO_MAIN:// 跳转到主界面
			openActivity(MainActivity.class);
			finish();
			break;
		}
	}


}
