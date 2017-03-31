package com.proj.androidlib.model;

/**
 * 下载文件
 */
public class DownRecord {
	/**
	 * 线程编号
	 */
	public int ThreadId;
	/**
	 * 开始位置
	 */
	public int StartPos;
	/**
	 * 结束位置
	 */
	public int EndPos;
	/**
	 * 完成大小
	 */
	public int CompeleteSize;
	/**
	 * 下载地址
	 */
	public String Url;
	/**
	 * 文件临时名称
	 */
	public String TempName;
}
