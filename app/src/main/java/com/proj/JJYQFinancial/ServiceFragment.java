package com.proj.JJYQFinancial;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 农友圈
 * @author Tony
 */
public class ServiceFragment extends MyBaseFragment{
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_service,
				container, false);
		return mRootView;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if(mContext == null){
			mContext = this.getActivity();
			getViews();
			init();
			setListeners();
		}
	}
	
	protected void getViews() {
		ButterKnife.inject(this,mRootView);
	}
	
	protected void setListeners() {
	}
	
	private void init(){
	}
	
	
	private MsgHandler handlerDown = new MsgHandler(this);
	private static class MsgHandler extends Handler {
		private WeakReference<Object> mOB;
		ServiceFragment owner = null;

		public MsgHandler(ServiceFragment ob) {
			mOB = new WeakReference<Object>(ob);
			owner = (ServiceFragment) mOB.get();
		}
		@Override
		public void handleMessage(Message msg) {
			if (msg == null || owner == null) {
				return;
			}
			owner.executeByMessage(msg);
			super.handleMessage(msg);
		}
	}
	protected void executeByMessage(Message msg){
		if (mIsFinish) {
            return;
        }
        switch (msg.what) {
            
        }
	}

	
	
	
}