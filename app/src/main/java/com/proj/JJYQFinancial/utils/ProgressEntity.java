package com.proj.JJYQFinancial.utils;

/**
 * 下载进度实体
 */
public class ProgressEntity {

	/**资源编号*/
    private int ResId;
    /**百分比进度*/
    private int Progress;
    /**完成的大小*/
    private long CompleteSize;
    /**总大小*/
    private long AllSize;

    public ProgressEntity() {
    }

    public ProgressEntity(int ResId, int Progress, long CompleteSize, long AllSize) {
        this.ResId = ResId;
        this.Progress = Progress;
        this.CompleteSize = CompleteSize;
        this.AllSize = AllSize;
    }

    public int getResId() {
        return ResId;
    }

    public void setResId(int ResId) {
        this.ResId = ResId;
    }

    public int getProgress() {
        return Progress;
    }

    public void setProgress(int Progress) {
        this.Progress = Progress;
    }

    public long getCompleteSize() {
        return CompleteSize;
    }

    public void setCompleteSize(long CompleteSize) {
        this.CompleteSize = CompleteSize;
    }

    public long getAllSize() {
        return AllSize;
    }

    public void setAllSize(long AllSize) {
        this.AllSize = AllSize;
    }

}
