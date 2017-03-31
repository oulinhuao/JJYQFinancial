package com.proj.androidlib.tool;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.proj.androidlib.model.DownFile;
import com.proj.androidlib.model.DownRecord;
import com.proj.androidlib.thread.ThreadPoolManager;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

/**
 * 单（多）文件多线程下载
 */
public class FileDownloadHelper {
	/**
	 * 2547[0x999]
	 */
	public static final int NO_SEND_WHAT = 0x999;
	/**
	 * 下载失败 //Tony添加
	 */
	public static final int DOWN_REEOR = 0x998;

	/**
	 * 取消下载
	 * 
	 * @author Tony
	 */
	public static final int DOWN_CANCLE = 0x997;

	/**
	 * 下载过程中通知一个文件下载完毕
	 */
	private static final int ONE_SEND_WHAT = 0x1;
	/**
	 * 线程数
	 */
	private final static int THREAD_COUNT = 3;
	/**
	 * 最长响应时间
	 */
	private final static int REQESTMAX_REQUEST_TIME = 6000;
	/**
	 * 每次写入字节数
	 */
	private final static int BYTE_SIZE = 1024 * 4;
	private int mDownloadSize;
	/**
	 * 下载线程完成的顺序
	 * 
	 */
	private int mDownThreadIndex = 0;
	/**
	 * 下载文件的索引
	 * 
	 */
	private int mIndex;

	/**
	 * 调用层通信传递
	 */
	private Handler mHandler;
	/**
	 * 下载文件的集合
	 * 
	 */
	private List<DownFile> mDownFileInfos;
	/**
	 * 下载的线程池
	 */
	private ThreadPoolManager mThreadPoolManager = null;
	/**
	 * 在每一个文件每次下载中回传一次消息
	 */
	private int mEachSendWhat = NO_SEND_WHAT;
	/**
	 * 每下载一个文件回传一次消息
	 */
	private int mOneEndSendWhat;
	/**
	 * 全部下载完毕后回传的消息
	 */
	private int mAllEndSendWhat;
	/**
	 * 每次回传下载进度条
	 */
	private int mProgressIndex = 0;

	/**
	 * 设定是否需要保留原文件拓展名
	 */
	private boolean mKeepExtension = true;

	/**
	 * 是否取消
	 * 
	 * @author Tony
	 */
	private boolean mIsCancle = false;
	private String mTempNameToDelete = "";

	/**
	 * 下载多个文件
	 * 
	 * @param downFileInfos
	 *            下载集合
	 * @param mHandler
	 *            传过来的hanler
	 * 
	 */
	public FileDownloadHelper(List<DownFile> downFileInfos, Handler mHandler) {
		this.mHandler = mHandler;
		mDownFileInfos = downFileInfos;
		mIndex = 0;
	}

	/**
	 * 下载单个文件
	 * 
	 * @param downFileInfo
	 *            单个文件
	 * @param mHandler
	 */
	public FileDownloadHelper(DownFile downFileInfo, Handler mHandler) {
		this.mHandler = mHandler;
		mDownFileInfos = new ArrayList<DownFile>();
		mDownFileInfos.add(downFileInfo);
		mIndex = 0;
	}

	/**
	 * 每下载一个文件回传一次消息（这里会回传文件索引按需使用）
	 * 
	 * @param oneEndSendWhat
	 */
	public void setOneEndSendWhat(int oneEndSendWhat) {
		this.mOneEndSendWhat = oneEndSendWhat;
	}

	/**
	 * 全部下载完毕后回传的消息
	 * 
	 * @param allEndSendWhat
	 */
	public void setAllEndSendWhat(int allEndSendWhat) {
		this.mAllEndSendWhat = allEndSendWhat;
	}

	/**
	 * 在每一个文件每次下载中回传一次消息
	 * 
	 * @param eachSendWhat
	 */
	public void setEachSendWhat(int eachSendWhat) {
		this.mEachSendWhat = eachSendWhat;
	}

	/**
	 * 设定下载后的文件是否需要保留原文件拓展名
	 * 
	 * @param is
	 *            true，保留；false，不保留
	 * @author Tony
	 * @date 2013-12-30 下午2:33:09
	 */
	public void setKeepExtension(boolean is) {
		this.mKeepExtension = is;
	}

	/**
	 * 累计已下载大小
	 * 
	 * @param size
	 */
	private void append(int size, int fileSize) {
		if (mIsCancle) {
			return;
		}
		mDownloadSize += size;
		// 用消息将下载信息传给进度条，对进度条进行更新
		if (mHandler != null && mEachSendWhat != NO_SEND_WHAT) {
			// 进度条百分比
			float d = (float) mDownloadSize / (float) fileSize;
			int downProgress = (int) (d * 100);
			if (downProgress > mProgressIndex) {
				Message message = Message.obtain();
				message.what = mEachSendWhat;
				message.arg1 = downProgress;// 百分比进度
				message.arg2 = mDownloadSize; // 已下载
				message.obj = fileSize; // 总大小
				mHandler.sendMessage(message);
				mProgressIndex++;
			}
		}
	}

	/**
	 * 开始下载文件集合
	 */
	public void downloadFile() {
		mIsCancle = false;
		int size = mDownFileInfos.size();
		if (mIndex < size) {
			DownFile downFileInfo = mDownFileInfos.get(mIndex);
			mDownloadSize = 0;
			InitTask initTask = new InitTask(downFileInfo);
			initTask.execute();
		} else {
			// 全部下载完毕
			mHandler.sendEmptyMessage(mAllEndSendWhat);
			downCompelete();
		}
	}

	/**
	 * 获取文件大小，创建本地文件
	 * 
	 * @author Wayne
	 * 
	 */
	class InitTask extends AsyncTask<Void, Void, Void> {
		private DownFile mDownFileInfo;
		/**
		 * 未下载完的临时名字
		 * 
		 */
		private String tempName;
		/**
		 * 文件大小
		 */
		private int fileLen;

		public InitTask(DownFile downFileInfo) {
			mDownFileInfo = downFileInfo;
			tempName = String.valueOf(System.currentTimeMillis());
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL(mDownFileInfo.Url);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setConnectTimeout(REQESTMAX_REQUEST_TIME);
				connection.setRequestMethod("GET");
				// 在Android2.2以上的版本默认采用gzip压缩 加上这一句的目的就是为了不让文件被压缩
				connection.setRequestProperty("Accept-Encoding", "identity");
				connection
						.setRequestProperty(
								"User-Agent",
								"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
				fileLen = connection.getContentLength();
				File file = new File(mDownFileInfo.LocalPath);
				if (!file.exists()) {
					file.mkdirs();
				}
				file = new File(mDownFileInfo.LocalPath + tempName);
				mTempNameToDelete = mDownFileInfo.LocalPath + tempName;
				if (!file.exists()) {
					file.createNewFile();
				}
				// 本地访问文件
				RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
				accessFile.setLength(fileLen);
				accessFile.close();
				connection.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			int range = fileLen / THREAD_COUNT;
			mThreadPoolManager = ThreadPoolManager.getInstance();
			mThreadPoolManager.setQueueSize(THREAD_COUNT);
			mThreadPoolManager.init();
			for (int i = 0; i < THREAD_COUNT; i++) {
				DownRecord downRecord = new DownRecord();
				downRecord.ThreadId = i;
				downRecord.StartPos = i * range;
				downRecord.CompeleteSize = 0;
				downRecord.Url = mDownFileInfo.Url;
				downRecord.TempName = tempName;
				if (i > THREAD_COUNT - 2)
					downRecord.EndPos = fileLen - 1;
				else
					downRecord.EndPos = (i + 1) * range - 1;
				DownRun downRun = new DownRun(downRecord,
						mDownFileInfo.LocalPath, mDownFileInfo.FileName,
						fileLen);
				mThreadPoolManager.addTasks(downRun);
			}
			super.onPostExecute(result);
		}
	}

	/**
	 * 下载过程中消息通知
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ONE_SEND_WHAT:
				mIndex++;
				if (mIndex <= mDownFileInfos.size()) {
					Message messageOne = Message.obtain();
					messageOne.what = mOneEndSendWhat;
					messageOne.arg1 = mIndex;// 处理完成的文件
					messageOne.arg2 = mDownFileInfos.size();// 文件个数
					messageOne.obj = msg.obj;// 文件存储路径
					mHandler.sendMessage(messageOne);
				}
				downloadFile();
				break;
			case DOWN_CANCLE:
				deleteTempFile();
				break;
			case DOWN_REEOR:
				mHandler.sendEmptyMessage(DOWN_REEOR);
				break;
			}
		};
	};

	private void DownRun() {
		// TODO 自动生成的方法存根

	}

	class DownRun implements Runnable {
		private DownRecord mDownLoadInfo;
		private String mLocalPath, mFileName;
		private int mFileLen;

		public DownRun(DownRecord downLoadInfo, String localPath,
				String fileName, int fileLen) {
			mDownLoadInfo = downLoadInfo;
			mLocalPath = localPath;
			mFileLen = fileLen;
			mFileName = fileName;
		}

		@Override
		public void run() {
			HttpURLConnection connection = null;
			RandomAccessFile randomAccessFile = null;
			InputStream is = null;
			try {
				URL url = new URL(mDownLoadInfo.Url);
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(REQESTMAX_REQUEST_TIME);
				connection.setRequestMethod("GET");
				// 设置范围，格式为Range：bytes x-y;
				connection
						.setRequestProperty(
								"Range",
								"bytes="
										+ (mDownLoadInfo.StartPos + mDownLoadInfo.CompeleteSize)
										+ "-" + mDownLoadInfo.EndPos);
				connection
						.setRequestProperty(
								"User-Agent",
								"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
				LogHelper.customLogging(null, mDownLoadInfo.StartPos + "："
						+ mDownLoadInfo.CompeleteSize + ":"
						+ mDownLoadInfo.EndPos);
				// 临时文件
				String tempFilePath = mLocalPath + mDownLoadInfo.TempName;
				randomAccessFile = new RandomAccessFile(tempFilePath, "rwd");
				randomAccessFile.seek(mDownLoadInfo.StartPos
						+ mDownLoadInfo.CompeleteSize);
				// 将要下载的文件写到保存在保存路径下的文件中
				is = connection.getInputStream();
				byte[] buffer = new byte[BYTE_SIZE];
				int length = 0;
				while ((length = is.read(buffer)) != -1 && !mIsCancle) {
					randomAccessFile.write(buffer, 0, length);
					mDownLoadInfo.CompeleteSize += length;
					append(length, mFileLen);
				}
				if (mIsCancle) {// Tony添加
					handler.sendEmptyMessage(DOWN_CANCLE);// 取消下载
				} else {
					mDownThreadIndex++;
					if (mDownloadSize < mFileLen) {
						// 线程下载完毕后，文件没有下载完毕的情况
						if (mDownThreadIndex > THREAD_COUNT - 1) {
							LogHelper.errorLogging("下载有问题");
							mDownLoadInfo.EndPos = mFileLen;
							mDownLoadInfo.StartPos = mDownloadSize;
							mDownLoadInfo.CompeleteSize = 0;
							DownRun downRun = new DownRun(mDownLoadInfo,
									mLocalPath, mFileName, mFileLen);
							mThreadPoolManager.addTasks(downRun);
						}
					} else {
						if (mDownThreadIndex >= THREAD_COUNT) {
							mDownThreadIndex = 0;
							File file = new File(tempFilePath);
							String newName = mFileName;
							if (!mKeepExtension) {
								newName = removeExtensionName(newName);
							}
							file.renameTo(new File(mLocalPath, newName));
							Message messageOne = Message.obtain();
							messageOne.what = ONE_SEND_WHAT;
							messageOne.obj = mLocalPath + newName;
							handler.handleMessage(messageOne);
						}
					}
				}
			} catch (Exception e) {
				handler.sendEmptyMessage(DOWN_REEOR);// 下载失败//Tony添加
				deleteTempFile();// 删除临时文件
				e.printStackTrace();
			} finally {
				try {
					is.close();
					randomAccessFile.close();
					connection.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	/***
	 * 取消下载
	 * 
	 * @author //Tony添加
	 * @date 2013-11-29 下午5:04:04
	 */
	public void downCancle() {
		mIsCancle = true;
		// 关闭线程池
		if (mThreadPoolManager != null) {
			mThreadPoolManager.closeTasks();
		}
		deleteTempFile();
	}

	/**
	 * 删除临时文件
	 * 
	 * @author OLH
	 * @date 2013-11-29 下午5:50:18
	 */
	private void deleteTempFile() {
		if (!TextUtils.isEmpty(mTempNameToDelete)) {
			File file = new File(mTempNameToDelete);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * 下载完成
	 * 
	 * @author Tony
	 * @date 2013-12-26 下午2:03:11
	 */
	public void downCompelete() {
		try {
			// 关闭线程池
			if (mThreadPoolManager != null) {
				mThreadPoolManager.closeTasks();
			}
		} catch (Exception e) {
			LogHelper.customLogging(null, "销毁线程异常");
		}
	}

	/**
	 * 获取除去扩展名的文件名
	 * 
	 * @param filename
	 *            文件名
	 * @return
	 */
	private String removeExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

}
