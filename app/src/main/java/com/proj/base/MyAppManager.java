package com.proj.base;

import java.util.Stack;

import android.app.Activity;
import android.content.Context;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * 
 * @author Ivan
 * @date 2013-5-6 下午10:04:29
 */
public class MyAppManager {
	private static Stack<Activity> mActivitiesStack;
	private static MyAppManager mAppManager;

	private MyAppManager() {
	}

	/**
	 * 单一实例
	 */
	public static MyAppManager getAppManager() {
		if (mAppManager == null) {
			mAppManager = new MyAppManager();
		}
		return mAppManager;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (mActivitiesStack == null) {
			mActivitiesStack = new Stack<Activity>();
		}
		mActivitiesStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		Activity activity = mActivitiesStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		Activity activity = mActivitiesStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			mActivitiesStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : mActivitiesStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = mActivitiesStack.size(); i < size; i++) {
			if (null != mActivitiesStack.get(i)) {
				mActivitiesStack.get(i).finish();
			}
		}
		mActivitiesStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	public void appExit(Context context) {
		try {
			finishAllActivity();
			//System.exit(0);
		} catch (Exception e) {
		}
	}
}
