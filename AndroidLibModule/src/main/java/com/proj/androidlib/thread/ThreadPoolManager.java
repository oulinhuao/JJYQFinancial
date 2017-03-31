package com.proj.androidlib.thread;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
	/** 线程池维护线程的最大数量 默认:5 */
	private int mMaximumPoolSize = 5;
	/** 任务队列大小  默认:5*/
	private int mQueueSize = 5;
	/** 任务队列 */
	private BlockingQueue<Runnable> mWorkQueue = null;
	/** 线程池 */
	private ExecutorService mThreadPool = null;
	/** 执行任务服务 */
	private ScheduledExecutorService mScheduledExecutorService = null;

	private static ThreadPoolManager mThreadPoolManager = null;

	private ThreadPoolManager() {
	}

	public static ThreadPoolManager getInstance() {
		if (mThreadPoolManager == null)
			mThreadPoolManager = new ThreadPoolManager();
		return mThreadPoolManager;
	}

	public void init() {
		mWorkQueue = null;
		mThreadPool = null;
		mScheduledExecutorService = null;

		if (mQueueSize <= 0) {
			return;
		}

		// 初始化任务队列
		mWorkQueue = new ArrayBlockingQueue<Runnable>(mQueueSize);

		// 初始化执行任务线程池
		mThreadPool = Executors.newFixedThreadPool(mMaximumPoolSize);

		// 在这弄一个子线程处理 execute 不能接受某个任务。
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if (ThreadPoolManager.this.mWorkQueue == null) {
					return;
				}

				if (ThreadPoolManager.this.mWorkQueue.isEmpty()) {
					return;
				}

				Runnable poll = ThreadPoolManager.this.mWorkQueue.poll();
				ThreadPoolManager.this.mThreadPool.execute(poll);
			}
		};

		// 开启定时执行任务服务
		mScheduledExecutorService = Executors.newScheduledThreadPool(1);
		// 创建并执行一个在给定初始延迟后首次启用的定期操作，
		// 随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。
		// 这里是什么时候开始调度runnable执行 threadPool 不能执行的任务
		mScheduledExecutorService.scheduleWithFixedDelay(runnable, 0L, 10L,
				TimeUnit.MILLISECONDS);
	}

	/**
	 * 设置线程池最大线程数
	 * 
	 * @param maximumPoolSize
	 */
	public void setMaximumPoolSize(int maximumPoolSize) {
		this.mMaximumPoolSize = maximumPoolSize;
	}

	/**
	 * 获取线程池最大线程数
	 * 
	 * @return
	 */
	public int getMaximumPoolSize() {
		return this.mMaximumPoolSize;
	}

	/**
	 * 设置任务队列大小
	 * 
	 * @param queueSize
	 */
	public void setQueueSize(int queueSize) {
		this.mQueueSize = queueSize;
	}

	/**
	 * 获取线程池最大线程数
	 * 
	 * @return
	 */
	public int getQueueSize() {
		return this.mQueueSize;
	}

	/**
	 * 添加任务
	 * 
	 * @param runnable
	 */
	public void addTasks(Runnable runnable) {
		if (mWorkQueue != null)
			try {
				mWorkQueue.put(runnable);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * 移除任务
	 * 
	 * @param runnable
	 */
	public void removeTasks(Runnable runnable) {
		if (mWorkQueue != null && mWorkQueue.contains(runnable))
			mWorkQueue.remove(runnable);
	}

	/**
	 * 关闭任务
	 */
	public void closeTasks() {
		if (mThreadPool != null && !mThreadPool.isShutdown()) {
			List<Runnable> list = mThreadPool.shutdownNow();
			if (list != null) {
				for (Runnable runnable : list) {
					runnable = null;
				}
			}
			mThreadPool = null;
		}
		if (mScheduledExecutorService != null
				&& !mScheduledExecutorService.isShutdown()) {
			List<Runnable> list = mScheduledExecutorService.shutdownNow();
			if (list != null) {
				for (Runnable runnable : list) {
					runnable = null;
				}
			}
			mScheduledExecutorService = null;
		}
		if (mWorkQueue != null) {
			mWorkQueue.clear();
			mWorkQueue = null;
		}
	}
}
