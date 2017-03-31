package com.proj.androidlib.service;

import java.io.File;

import com.proj.androidlib.R;
import com.proj.androidlib.model.DownFile;
import com.proj.androidlib.tool.FileDownloadHelper;
import com.proj.androidlib.tool.SdCardAndFileHelper;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

/**
 * 更新软件服务,需Intent传入APP_NAME:软件名称，URL：下载地址
 * 
 */
public class SoftUpdateService extends Service {
	/**
	 * 程序名称
	 */
	private String mAppName;
	/**
	 * 下载地址
	 * 
	 */
	private String mUrl;
	/**
	 * 更新进度条回传
	 */
	private static final int EACH_SEND_WHAT = 0x10;
	/**
	 * 下载完毕后回传
	 */
	private static final int ALL_END_SEND_WHAT = 0x11;

	/** 服务是否在运行 */
	public static boolean mIsRunning = false;

	/**
	 * 通知栏
	 */
	private NotificationManager mUpdateNotificationManager;
	private Notification mUpdateNotification;
	/**
	 * 通知栏跳转Intent
	 */
	private Intent mUpdateIntent;
	private PendingIntent mPendingIntent;
	/**
	 * 文件路径
	 */
	private String mStrFile;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null && intent.hasExtra("APP_NAME")
				&& intent.hasExtra("URL")) {
			mAppName = intent.getStringExtra("APP_NAME");
			mUrl = intent.getStringExtra("URL");
			mUpdateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			String strFormat = getString(R.string.kdmsp_down_soft_notice_titile);
			String appName = String.format(strFormat, mAppName);
			// 设置通知栏显示内容
			mUpdateNotification = new Notification(
					android.R.drawable.stat_sys_download, appName,
					System.currentTimeMillis());
			mUpdateNotification.contentView = new RemoteViews(getPackageName(),
					R.layout.notification_content_view);
			mUpdateNotification.contentView.setProgressBar(
					R.id.notification_content_view_progress, 100, 0, false);
			mUpdateNotification.contentView.setTextViewText(
					R.id.notification_content_view_text_title, appName);
			mUpdateNotification.contentView.setTextViewText(
					R.id.notification_content_view_text_progress, "0%");

			// 3.指定通知的标题、内容和intent
			mUpdateIntent = new Intent("");
			mPendingIntent = PendingIntent.getActivity(this, 0, mUpdateIntent,
					PendingIntent.FLAG_CANCEL_CURRENT);
			mUpdateNotification.contentIntent = mPendingIntent;
			// 发出通知
			mUpdateNotificationManager.notify(0, mUpdateNotification);
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		if (!TextUtils.isEmpty(mAppName) && !TextUtils.isEmpty(mUrl)) {
			DownFile downFile = new DownFile();
			downFile.FileName = mAppName + ".apk";
			downFile.LocalPath = SdCardAndFileHelper
					.getSDPath(getString(R.string.kdmsp_down_soft_notice_path));
			mStrFile = downFile.LocalPath + downFile.FileName;
			SdCardAndFileHelper.delFileExist(mStrFile);
			downFile.Url = mUrl;
			FileDownloadHelper downloadHelper = new FileDownloadHelper(
					downFile, handler);
			downloadHelper.setEachSendWhat(EACH_SEND_WHAT);
			downloadHelper.setAllEndSendWhat(ALL_END_SEND_WHAT);
			downloadHelper.downloadFile();
			mIsRunning = true;
		}
		super.onStart(intent, startId);
	}


	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 下载更新状态
			case EACH_SEND_WHAT:
				mUpdateNotification.contentView.setProgressBar(
						R.id.notification_content_view_progress, 100, msg.arg1,
						false);
				mUpdateNotification.contentView.setTextViewText(
						R.id.notification_content_view_text_progress, msg.arg1
								+ "%");
				mUpdateNotificationManager.notify(0, mUpdateNotification);
				break;
			// 下载完毕后
			case ALL_END_SEND_WHAT:
				File file = new File(mStrFile);
				Uri uri = Uri.fromFile(file);
				Intent installIntent = new Intent(Intent.ACTION_VIEW);
				installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				installIntent.setDataAndType(uri,
						"application/vnd.android.package-archive");

				mPendingIntent = PendingIntent.getActivity(
						SoftUpdateService.this, 0, installIntent, 0);
				NotificationCompat.Builder builder = new NotificationCompat.Builder(SoftUpdateService.this);
				builder.setContentText(getString(R.string.kdmsp_down_soft_complete));
				builder.setContentTitle(mAppName);
				builder.setSmallIcon(android.R.drawable.stat_sys_download_done);
				builder.setAutoCancel(true);
				builder.setWhen(System.currentTimeMillis());
				builder.setContentIntent(mPendingIntent);
				Notification notification = builder.build();
				mUpdateNotificationManager.notify(10, notification);
				stopSelf();
				mIsRunning = false;
				break;
			}
		};
	};
}
