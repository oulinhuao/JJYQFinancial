package com.proj.util;

import java.io.File;
import java.net.URLEncoder;
import java.util.Locale;

import com.proj.JJYQFinancial.R;
import com.proj.JJYQFinancial.utils.MIMEType;
import com.proj.androidlib.tool.LogHelper;
import com.proj.androidlib.tool.SdCardAndFileHelper;

import android.content.Context;
import android.text.TextUtils;

public class FileHelper {
	
	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		String back = "";
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				back = filename.substring(dot + 1);
			}
		}
		return back;
	}

	/**
	 * 获取不带扩展名的文件名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) {
		String back = "";
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				back = filename.substring(0, dot);
			}
		}
		return back;
	}
	
	/**
	 * 从URL中获取文件名(全小写带后缀)
	 * @param url
	 * @return
	 */
	public static String getFileNameFromUrl(String url) {
		String back = "";
		if ((url != null) && (url.length() > 0)) {
			if(!url.contains("/")){
				back = url;
			}else{
				int p = url.lastIndexOf('/');
				if ((p > -1) && (p < (url.length()))) {
					back = url.substring(url.lastIndexOf("/") + 1);
				}
			}
		}
		return back.toLowerCase(Locale.getDefault());
	}
	
	/**
	 * 将资源下载url中文件含有中文符号的做次转换
	 * @param url
	 * @return
	 * @author Tony
	 * @date 2014年8月24日 下午3:25:40
	 */
	public static String getFormatResUrl(String url) {
		String back = url;
		try {
			String rName = getFileNameFromUrl(url);
			String eName = getExtensionName(rName);
			rName = getFileNameNoEx(rName);
			if(StringHelper.isChinese(rName)){
				rName = URLEncoder.encode(getFileNameNoEx(rName),"UTF-8");
			}
			int index = url.lastIndexOf("/");
			if(index > 0){
				url = url.substring(0,index+1);
				back = url + rName + "." + eName; 
			}
		} catch (Exception e) {
		}
		return back.toLowerCase(Locale.getDefault());
	}
	
	
	/**
	 * 获取程序内部的文件路径
	 * @param context
	 * @return
	 * @author Tony
	 * @date 2013-12-26 上午10:26:05
	 */
	public static String getAppFilePath(Context context) {
		StringBuilder back = new StringBuilder();
		back.append(context.getFilesDir().getAbsolutePath());
		return back.toString();
	}
	
	/**
	 * 获取文件存放路径（程序内部）
	 * @param context
	 * @param fileType
	 * @param fileName
	 * @return
	 * @author Tony
	 * @date 2013-12-26 上午10:27:39
	 */
	public static String getFilePathSaveInApp(Context context,String fileName) {
		StringBuilder back = new StringBuilder();
		back.append(getAppFilePath(context));
		back.append("/");
		back.append(getFileNameFromUrl(fileName));
		return back.toString();
	}
	
	/**
	 * 删除文件夹 
	 * @param folderPath 文件夹完整绝对路径
	 * @author Tony
	 * @date 2014-1-14 下午10:11:59
	 */
    public static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //删除完里面所有内容
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //删除空文件夹
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}

    /**
     * 删除指定文件夹下所有文件
     * @param path 文件夹完整绝对路径
     * @return
     * @author Tony
     * @date 2014-1-14 下午10:12:18
     */
	public static boolean delAllFile(String path) {
	 	boolean flag = false;
	 	File file = new File(path);
	    if (!file.exists()) {
	    	return flag;
	    }
	    if (!file.isDirectory()) {
	    	return flag;
	    }
	    String[] tempList = file.list();
	    File temp = null;
	    int size = tempList.length;
	    LogHelper.customLogging(null, "文件个数：" + size); 
	    for (int i = 0; i < size; i++) {
	    	if (path.endsWith(File.separator)) {
	    		temp = new File(path + tempList[i]);
	    	} else {
	    		temp = new File(path + File.separator + tempList[i]);
	    	}
	    	if (temp.isFile()) {
	    		temp.delete();
	    	}
	    	if (temp.isDirectory()) {
	    		delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	    		delFolder(path + "/" + tempList[i]);//再删除空文件夹
	    		flag = true;
	    	}
	    }
	    return flag;
	 }
	
	public static String fixLastSlash(String str) {
		String res = str == null ? "/" : str.trim() + "/";
		if (res.length() > 2 && res.charAt(res.length() - 2) == '/')
			res = res.substring(0, res.length() - 1);
		return res;
	}
	
	 public static long getDirSize(String path) {     
	        //判断文件是否存在     
		 File file = new File(path);
	        if (file.exists()) {     
	            //如果是目录则递归计算其内容的总大小    
	            if (file.isDirectory()) {     
	                File[] children = file.listFiles();     
	                long size = 0;     
	                for (File f : children)     
	                    size += getDirSize(f.getAbsolutePath());     
	                return size;     
	            } else {
	            	long size = file.length();        
	                return size;     
	            }     
	        } else {   
	        	LogHelper.customLogging("文件或者文件夹不存在，请检查路径是否正确！");
	            return 0;     
	        }     
	    }

	public static String getPathCacheSaveIn(Context context) {
		String p = context.getString(R.string.cache_path);
		return SdCardAndFileHelper.getSDPath(p);
	}
	
	/**
	 * 根据文件后缀判断是否是文件
	 * @param extensionName
	 * @return
	 * @author Tony
	 * @date 2015年4月15日 上午10:33:22
	 */
	public static boolean isFileAvailable(String extensionName){
		boolean back = false;
		if(!TextUtils.isEmpty(extensionName)){
			if(!MIMEType.getMIMEType(extensionName).equals("*/*")){
				back = true;
			}
		}
		return back;
	}
	
	public static String getDownUrl(Context context,String filePath) {
		//http://kingdonsoft.com/BodhiAdmin/Upload//BChant/20140418032106.png
		StringBuilder sb = new StringBuilder();
		sb.append("http://");
		sb.append(context.getString(R.string.server_address));
		sb.append("/");
		sb.append(context.getString(R.string.server_interface_virtualdir));
		sb.append("/");
		sb.append(filePath);
		return sb.toString();
	}
	/**
	 * 获取图片文件存放路径
	 * 
	 * @param context
	 * @return
	 * @author Tony
	 */
	public static String getPathImgSaveIn(Context context) {
		String p = context.getString(R.string.img_path);
		return SdCardAndFileHelper.getSDPath(p);
	}
	/**
	 * 删除临时文件
	 * @param filter
	 * @param path
	 */
	public static void removeFile(String filter, String path){
		if(!TextUtils.isEmpty(filter)&& !TextUtils.isEmpty(path) && path.contains(filter)){
			File file = new File(path);
			if(file.exists()){
				file.delete();
			}
		}
	}
	
}
