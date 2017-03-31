package com.proj.androidlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View.OnClickListener;


/**
 * FragmentActivity基类
 *
 */
public abstract class BaseFragmentActivity extends FragmentActivity  implements
	OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
		getViews();
		init();
		setListeners();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}

	/**
	 * 初始化事件
	 */
	protected abstract void init();
	/**
	 * 绑定View
	 */
	protected abstract void getViews();

	/**
	 * 注册事件
	 */
	protected abstract void setListeners();

	/**
	 * Open a new Activity by class name
	 * 
	 * @param pClass
	 */
	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	/**
	 * Open a new Activity and bring some parameters by bundle
	 * 
	 * @param pClass
	 * @param bundle
	 */
	protected void openActivity(Class<?> pClass, Bundle bundle) {
		Intent intent = new Intent(this, pClass);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/**
	 * Open a new ForResult Activity by class name requestCode
	 * 
	 * @param pClass
	 * @param requestCode
	 */
	protected void openActivityForResult(Class<?> pClass, int requestCode) {
		openActivityForResult(pClass, null, requestCode);
	}

	/**
	 * Open a new Activity and bring some parameters by bundle
	 * 
	 * @param pClass
	 * @param bundle
	 * @param requestCode
	 */
	protected void openActivityForResult(Class<?> pClass, Bundle bundle,
			int requestCode) {
		Intent intent = new Intent(this, pClass);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
	}

	/**
	 * Open a new ForResult Activity by class name requestCode
	 * 
	 * @param pClass
	 * @param resultCode
	 */
	protected void openActivitySetResult(Class<?> pClass, int resultCode) {
		openActivitySetResult(pClass, null, resultCode);
	}

	/**
	 * Open a new Activity and bring some parameters by bundle
	 * 
	 * @param pClass
	 * @param bundle
	 * @param resultCode
	 */
	protected void openActivitySetResult(Class<?> pClass, Bundle bundle,
			int resultCode) {
		Intent intent = new Intent(this, pClass);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		setResult(resultCode, intent);
	}

	/**
	 * Open a new Activity by action string
	 * 
	 * @param action
	 */
	protected void openActivity(String action) {
		openActivity(action, null);
	}

	/**
	 * Open a new Activity by action string and bring some parameters
	 * 
	 * @param action
	 * @param bundle
	 */
	protected void openActivity(String action, Bundle bundle) {
		Intent intent = new Intent(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

}
