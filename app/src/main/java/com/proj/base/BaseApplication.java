package com.proj.base;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.impl.FileCountLimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.proj.androidlib.AppcationHelper;
import com.proj.androidlib.tool.LogHelper;
import com.proj.util.FileHelper;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class BaseApplication extends Application {
	
	public boolean mIsFrist = true;
	
	public static BaseApplication mInstance;
	/**
	 * 是否发布
	 * 
	 * 发布新版要
	 * 1、IS_RELEASE 置为 true
	 * 2、检查版本号版本名称
	 * 
	 */
	public static boolean IS_RELEASE = true;

	@Override
	public void onCreate() {
		super.onCreate();
		LogHelper.customLogging("BaseApplication---->onCreate");
		if(mIsFrist){
			// 避免同一次启动的多次调用
			mIsFrist = false;
			if (mInstance == null){
				mInstance = this;
			}
			LogHelper.IS_DEBUG = !IS_RELEASE;
			if (IS_RELEASE) {
				AppcationHelper appcationHelper = new AppcationHelper(
						getApplicationContext());
			}
			initImageLoader(this);
		}
	}
	
	/**
     * Called when the overall system is running low on memory
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.w("BaseApplication", "System is running low on memory");
    }

    /**
     * @return the main context of the Application
     */
    public static Context getAppContext()
    {
        return mInstance;
    }

    /**
     * @return the main resources from the Application
     */
    public static Resources getAppResources()
    {
        if(mInstance == null) return null;
        return mInstance.getResources();
    }
    
    
    private void initImageLoader(Context context) {
		String cachePath = FileHelper.getPathCacheSaveIn(context);
		File f = new File(cachePath);
		if(f.isDirectory() && !f.exists()){
			f.mkdirs();
		}
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.discCache(new DiscCache(f, 1000))
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	
	private class DiscCache extends FileCountLimitedDiscCache{
		public DiscCache(File cacheDir, int maxFileCount) {
			super(cacheDir, maxFileCount);
		}
	}
	
}
