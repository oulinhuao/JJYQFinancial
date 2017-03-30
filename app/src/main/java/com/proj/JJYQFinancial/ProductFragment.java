package com.proj.JJYQFinancial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 金融产品
 */
public class ProductFragment extends MyBaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mRootView == null){
			mRootView = inflater.inflate(R.layout.fragment_product,
					container, false);
		}else{
            ViewGroup parent = (ViewGroup) mRootView.getParent();  
		    if (parent != null) {  
		        parent.removeView(mRootView);  
		    }  
		}
		return mRootView;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if(mContext == null){
			mContext = this.getActivity();
			getViews();
			init();
		}
	}

	protected void getViews() {
		
	}

	protected void init() {
	}
	
}