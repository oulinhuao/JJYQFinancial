package com.proj.androidlib.tool;

import java.io.File;
import java.util.Locale;

import android.os.Environment;
import android.os.StatFs;

/**
 * SD卡和文件操作工具类
 *
 */
public class SdCardAndFileHelper {
	private SdCardAndFileHelper() {
	};

	/**
	 * 判断SD卡是否存在
	 * 
	 * @return boolean
	 */
	public static boolean sdCardExist() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 返回SD卡路径，以"/"结尾
	 * 
	 * @return boolean
	 */
	public static String getSDPath() {
		return Environment.getExternalStorageDirectory() + "/";
	}

	/**
	 * 返回SD卡和文件夹路径，以"/"结尾
	 * 
	 * @param path
	 *            SD卡下文件夹路径
	 * @return "/sdcard/DCIM/"
	 */
	public static String getSDPath(String path) {
		StringBuilder builder = new StringBuilder();
		builder.append(Environment.getExternalStorageDirectory()).append("/")
				.append(path).append("/");
		return builder.toString();
	}

	/**
	 * 检查文件是否存在
	 * 
	 * @param filePath
	 *            文件路径
	 * @return boolean
	 */
	public static boolean isFileExist(String filePath) {
		File file = new File(filePath);
		if (file.exists())
			return true;
		else
			return false;
	}

	/**
	 * 文件存在则删除
	 * 
	 * @param filePath
	 *            文件路径
	 * @return boolean
	 */
	public static boolean delFileExist(String filePath) {
		File file = new File(filePath);
		if (file.exists())
			return file.delete();
		else
			return true;
	}

	/**
	 * 获取文件大小
	 * 
	 * @param filePath
	 * @return long
	 */
	public static long getFileSize(String filePath) {
		long size = 0;
		File file = new File(filePath);
		if (file.exists())
			size = file.length();
		return size;
	}

	/**
	 * 判断SD卡剩余空间
	 * 
	 * @param sizeMb
	 *            如果小于sizeMb返回false
	 * @return
	 * @author OLH
	 * @date 2013-6-18 下午1:36:31
	 */
	public static boolean isAvaiableSpace(int sizeMb) {
		boolean ishasSpace = false;
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			String sdcard = Environment.getExternalStorageDirectory().getPath();
			StatFs statFs = new StatFs(sdcard);
			long blockSize = statFs.getBlockSize();
			long blocks = statFs.getAvailableBlocks();
			long availableSpare = (blocks * blockSize) / (1024 * 1024);
			if (availableSpare > sizeMb) {
				ishasSpace = true;
			}
		}
		return ishasSpace;
	}

	/**
	 * 从URL中获取文件名(全小写带后缀)
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileNameFromUrl(String url) {
		String back = "";
		if ((url != null) && (url.length() > 0)) {
			if (!url.contains("/")) {
				back = url;
			} else {
				int p = url.lastIndexOf('/');
				if ((p > -1) && (p < (url.length()))) {
					back = url.substring(url.lastIndexOf("/") + 1);
				}
			}
		}
		return back.toLowerCase(Locale.getDefault());
	}

}
