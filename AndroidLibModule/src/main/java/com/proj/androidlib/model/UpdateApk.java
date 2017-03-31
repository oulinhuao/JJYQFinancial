package com.proj.androidlib.model;

public class UpdateApk {

	public String getReturnMsg() {
		return ReturnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		ReturnMsg = returnMsg;
	}

	/**
	 * 更新地址
	 *
	 * @return
	 */
	public String getUpdateUrl() {
		return UpdateUrl;
	}

	public void setUpdateUrl(String updateUrl) {
		UpdateUrl = updateUrl;
	}

	/**
	 * 版本名称
	 *
	 * @return
	 */
	public String getVersionName() {
		return VersionName;
	}

	/**
	 * 版本名称
	 *
	 * @param versionName
	 */
	public void setVersionName(String versionName) {
		VersionName = versionName;
	}

	/**
	 * 是否强制更新
	 *
	 * @return
	 */
	public boolean getIsEnforce() {
		return IsEnforce;
	}

	public void setIsEnforce(boolean isEnforce) {
		IsEnforce = isEnforce;
	}

	/**
	 * 更新日志
	 *
	 * @return
	 */
	public String getUpdateDes() {
		return UpdateDes;
	}

	/**
	 * 更新日志
	 *
	 * @param updateDes
	 */
	public void setUpdateDes(String updateDes) {
		UpdateDes = updateDes;
	}

	/**
	 * 返回消息
	 */
	private String ReturnMsg;

	/**
	 * 更新地址
	 */
	private String UpdateUrl;

	/**
	 * 版本名称
	 */
	private String VersionName;

	/**
	 * 是否强制更新
	 */
	private boolean IsEnforce;

	/**
	 * 更新日志
	 */
	private String UpdateDes;
}
