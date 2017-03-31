package com.proj.androidlib.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.Log;

/**
 * 日志工具类，记录类命、方法名、行数
 */
public class LogHelper {
	/**
	 * Log默认标签名
	 * 
	 */
	public static String DEFAULT_TAG_NAME = "KDMSP";
	/**
	 * 是否为发布版本
	 */
	public static boolean IS_DEBUG = true;
	private static final String LOG_FILE_NAME = "LOG.TXT";

	/**
	 * LogCat类别
	 * 
	 * @author Ivan
	 * @date 2013-7-8 下午11:57:37
	 */
	public static enum LogCatType {
		/**
		 * 任何信息-黑色
		 */
		VERBOSE {
			public String toString() {
				return "v";
			}
		},

		/**
		 * 一般信息-绿色
		 */
		INFO {
			public String toString() {
				return "i";
			}
		},

		/**
		 * 调试信息-蓝色
		 */
		DEBUG {
			public String toString() {
				return "d";
			}
		},

		/**
		 * 警告信息-橙色
		 */
		WARN {
			public String toString() {
				return "w";
			}
		},

		/**
		 * 错误信息-红色
		 */
		ERROR {
			public String toString() {
				return "e";
			}
		}
	}

	/**
	 * Logat信息输出
	 * 
	 * @param tag
	 *            标记
	 * @param type
	 *            类别
	 * @param isLogCat
	 *            是否LogCat输出
	 * @param msg
	 *            消息内容
	 * @author Ivan
	 * @date 2013-7-9 上午12:16:02
	 */
	public static void logCat(String tag, LogCatType type, Boolean isLogCat,
			String msg) {
		if (isLogCat) {
			if (type.toString().equals(LogCatType.INFO.toString())) {
				Log.i(tag, msg);
				// Log.i的输出为绿色，一般提示性的消息information，它不会输出Log.v和Log.d的信息，但会显示i、w和e的信息
			} else if (type.toString().equals(LogCatType.VERBOSE.toString())) {
				Log.v(tag, msg);
				// Log.v
				// 的调试颜色为黑色的，任何消息都会输出，这里的v代表verbose啰嗦的意思，平时使用就是Log.v("","");
			} else if (type.toString().equals(LogCatType.DEBUG.toString())) {
				Log.d(tag, msg);
				// Log.d的输出颜色是蓝色的，仅输出debug调试的意思，但他会输出上层的信息，过滤起来可以通过DDMS的Logcat标签来选择.
			} else if (type.toString().equals(LogCatType.WARN.toString())) {
				Log.w(tag, msg);
				// Log.w的意思为橙色，可以看作为warning警告，一般需要我们注意优化Android代码，同时选择它后还会输出Log.e的信息。
			} else if (type.toString().equals(LogCatType.ERROR.toString())) {
				Log.e(tag, msg);
				// Log.e为红色，可以想到error错误，这里仅显示红色的错误信息，这些错误就需要我们认真的分析，查看栈的信息了。
			}
		}
	}

	/**
	 * 常规错误日志记录
	 * 
	 */
	public static void errorLogging() {
		if (IS_DEBUG) {
			StackTraceElement stackTraceElement = new Exception()
					.getStackTrace()[1];
			StringBuilder sBuilder = new StringBuilder("ClassName:");
			sBuilder.append(stackTraceElement.getClassName());
			sBuilder.append(",MethodName:");
			sBuilder.append(stackTraceElement.getMethodName());
			sBuilder.append(",LineNum:");
			sBuilder.append(stackTraceElement.getLineNumber());
			String tag = "ERROR";
			Log.e(tag, sBuilder.toString());
		}
	}

	/**
	 * 常规错误日志记录,追加内容
	 * 
	 * @param msg
	 */
	public static void errorLogging(String msg) {
		if (IS_DEBUG) {
			StackTraceElement stackTraceElement = new Exception()
					.getStackTrace()[1];
			StringBuilder sBuilder = new StringBuilder("ClassName:");
			sBuilder.append(stackTraceElement.getClassName());
			sBuilder.append(",MethodName:");
			sBuilder.append(stackTraceElement.getMethodName());
			sBuilder.append(",LineNum:");
			sBuilder.append(stackTraceElement.getLineNumber());
			String tag = "ERROR";
			if (msg != null && !msg.equals(""))
				sBuilder.append("," + msg);
			Log.e(tag, sBuilder.toString());
		}
	}

	/**
	 * 常规错误日志记录,写入SD卡
	 * 
	 * @param context
	 */
	public static void errorLogging(Context context, String folderName) {
		StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
		StringBuilder sBuilder = new StringBuilder("ClassName:");
		sBuilder.append(stackTraceElement.getClassName());
		sBuilder.append(",MethodName:");
		sBuilder.append(stackTraceElement.getMethodName());
		sBuilder.append(",LineNum:");
		sBuilder.append(stackTraceElement.getLineNumber());
		String tag = "ERROR";
		if (IS_DEBUG)
			Log.e(tag, sBuilder.toString());
		writeToSDFile(context, folderName, sBuilder.toString());
	}

	/**
	 * 自定义日志记录(标签：MyTag)
	 * 
	 * @param msg
	 *            记录追加内容
	 */
	public static void customLogging(String msg) {
		StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
		StringBuilder sBuilder = new StringBuilder("ClassName:");
		sBuilder.append(stackTraceElement.getClassName());
		sBuilder.append(",MethodName:");
		sBuilder.append(stackTraceElement.getMethodName());
		sBuilder.append(",LineNum:");
		sBuilder.append(stackTraceElement.getLineNumber());
		String tag = "MyTag";
		if (msg != null && !msg.equals(""))
			sBuilder.append("," + msg);
		if (IS_DEBUG)
			Log.v(tag, sBuilder.toString());
	}

	/**
	 * 自定义日志记录
	 * 
	 * @param tagName
	 *            记录名称,为空时：LogHelper.DEFAULT_TAG_NAME
	 * @param msg
	 *            记录追加内容
	 */
	public static void customLogging(String tagName, String msg) {
		StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
		StringBuilder sBuilder = new StringBuilder("ClassName:");
		sBuilder.append(stackTraceElement.getClassName());
		sBuilder.append(",MethodName:");
		sBuilder.append(stackTraceElement.getMethodName());
		sBuilder.append(",LineNum:");
		sBuilder.append(stackTraceElement.getLineNumber());
		String tag = "MyTag";
		if (tagName != null && !tagName.equals(""))
			tag = tagName;
		if (msg != null && !msg.equals(""))
			sBuilder.append("," + msg);
		if (IS_DEBUG)
			Log.v(tag, sBuilder.toString());
	}

	/**
	 * 自定义日志记录
	 * 
	 * @param tagName
	 *            记录标签,为空时：LogHelper.DEFAULT_TAG_NAME
	 * @param msg
	 *            记录追加内容
	 */
	public static void customLogging(Context context, String tagName, String msg) {
		StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
		StringBuilder sBuilder = new StringBuilder("ClassName:");
		sBuilder.append(stackTraceElement.getClassName());
		sBuilder.append(",MethodName:");
		sBuilder.append(stackTraceElement.getMethodName());
		sBuilder.append(",LineNum:");
		sBuilder.append(stackTraceElement.getLineNumber());
		String tag = "MyTag";
		if (tagName != null && !tagName.equals(""))
			tag = tagName;
		if (msg != null && !msg.equals(""))
			sBuilder.append("," + msg);
		if (IS_DEBUG)
			Log.v(tag, sBuilder.toString());
	}

	/**
	 * 自定义日志记录,写入SD卡
	 * 
	 * @param context
	 * @param tagName
	 *            标签,为空时：LogHelper.DEFAULT_TAG_NAME
	 * @param msg
	 *            记录追加内容
	 * @param folderName
	 *            文件夹名
	 */
	public static void customLogging(Context context, String tagName,
			String msg, String folderName) {
		StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
		StringBuilder sBuilder = new StringBuilder("ClassName:");
		sBuilder.append(stackTraceElement.getClassName());
		sBuilder.append(",MethodName:");
		sBuilder.append(stackTraceElement.getMethodName());
		sBuilder.append(",LineNum:");
		sBuilder.append(stackTraceElement.getLineNumber());
		String tag = "MyTag";
		if (tagName != null && !tagName.equals(""))
			tag = tagName;
		if (msg != null && !msg.equals(""))
			sBuilder.append("," + msg);
		if (IS_DEBUG)
			Log.v(tag, sBuilder.toString());
		writeToSDFile(context, folderName, sBuilder.toString());
	}

	/**
	 * 写入文字到SD卡
	 * 
	 * @param context
	 * @param folderName
	 *            文件夹名称
	 * @param writeContent
	 *            文字内容
	 */
	private static void writeToSDFile(Context context, String folderName,
			String writeContent) {
		if (SdCardAndFileHelper.sdCardExist()) {
			String logDirStr = SdCardAndFileHelper.getSDPath(folderName);
			try {
				File dirFile = new File(logDirStr);
				// 判断目录是否存在，不存在创建
				if (!dirFile.exists())
					dirFile.mkdirs();
				File txtFile = new File(dirFile + File.separator
						+ LOG_FILE_NAME);
				// 判断文件是否存在,不存在则创建
				if (!txtFile.exists()) {
					txtFile.createNewFile();
				}
				StringBuilder sBuilder = new StringBuilder("\r\n");
				sBuilder.append(writeContent);
				sBuilder.append("--");
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
				String dateString = formatter.format(curDate);
				sBuilder.append(dateString);
				FileOutputStream outputStream = new FileOutputStream(txtFile,
						true);
				outputStream.write(sBuilder.toString().getBytes());
				outputStream.close();
			} catch (IOException e) {
				errorLogging();
			}
		}
	}
}
