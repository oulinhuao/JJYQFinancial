package com.proj.JJYQFinancial.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 
 * 工具类，用来回收图片
 * 
 * @author jqqiu
 * 
 */
public class RecycleBitmap {
	private RecycleBitmap() {
	};

	public static void recyclePic(ViewGroup layout) {
		int size = layout.getChildCount();
		for (int i = 0; i < size; i++) {
			View view = layout.getChildAt(i);
			recycleBackgroundBitMap(view);
			if (view instanceof ViewGroup) {
				recyclePic((ViewGroup) view);// 递归条用
			}
			if (view instanceof ImageView) {
				recycleImageViewBitMap((ImageView) view);
			}
		}
	}

	public static void recycleBackgroundBitMap(View view) {
		if (view != null) {
			Drawable bd = view.getBackground();
			view.setBackgroundDrawable(null);
			if (bd != null){
				bd.setCallback(null);
				if(bd instanceof BitmapDrawable){
					rceycleBitmapDrawable((BitmapDrawable) bd);
				}
			}
		}
	}

	public static void recycleImageViewBitMap(ImageView imageView) {
		if (imageView != null) {
			Drawable d = imageView.getDrawable();
			if(d != null){
				BitmapDrawable bd = (BitmapDrawable) d;
				imageView.setImageBitmap(null);
				if (bd != null)
					rceycleBitmapDrawable(bd);
			}
		}
	}

	public static void rceycleBitmapDrawable(BitmapDrawable bitmapDrawable) {
		Bitmap bitmap = bitmapDrawable.getBitmap();
		bitmapDrawable.setCallback(null);
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
	}
}
