package com.proj.JJYQFinancial.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageHelper {

	
	
	/**
	 * 根据资源编号获取图片（用BitmapFactory.decodeStream来解析，节省内存）
	 * @param context
	 * @param resId
	 * @return
	 * @author Tony
	 * @date 2014年9月14日 下午8:52:32
	 */
	public static BitmapDrawable getBitmapDrawable(Context context,int resId){
		return new BitmapDrawable(context.getResources(),getBitmap(context,resId,Bitmap.Config.RGB_565));
	}
	
	public static Bitmap getBitmap(Context context,int resId,Bitmap.Config c){
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = c;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		//获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		Bitmap bitmap = BitmapFactory.decodeStream(is,null, opt);
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}
	
	public static BitmapDrawable getBitmapDrawable(Context context,int resId,Bitmap.Config config){
		return new BitmapDrawable(context.getResources(),getBitmap(context,resId,config));
	}
	
	public static BitmapDrawable getBitmapDrawable(Context context,String path){
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		//获取资源图片
		Bitmap bitmap = BitmapFactory.decodeFile(path, opt);
		return new BitmapDrawable(context.getResources(),bitmap);
	}
	
	
	public static Drawable getDrawable(Context context, String path) {
		return  new BitmapDrawable(getBitmap(context, path));
	}
	
	public static Bitmap getBitmap(Context context, String path) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
//		opt.inPurgeable = true;
//		opt.inInputShareable = true;
		// 获取资源图片
		return  BitmapFactory.decodeFile(path, opt);
	}
	
    
}
