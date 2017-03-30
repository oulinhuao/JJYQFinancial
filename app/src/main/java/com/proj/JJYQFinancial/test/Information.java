package com.proj.JJYQFinancial.test;

/**
 * 实体类
 * @author Tony
 *
 */
public class Information implements java.io.Serializable{
	/**
	 * 这个不用管
	 */
	private static final long serialVersionUID = -907928198072785316L;

	/** 本地Id */
    private Long LocalId;
    
	/** 服务端Id */
    private int Id;
    
	/** 标题 */
    private String Title;
    
	/** 信息类型 */
    private int InfoType;
    
	/** 内容 */
    private String Content;
    
	/** 目录编号 */
    private int CategoryID;
    
	/** 目录名 */
    private String CategoryName;
    
	/** 查看次数 */
    private int ViewCount;
    
	/** 是否显示 */
    private boolean IsDisplay;
    
	/** 是否删除 */
    private boolean IsDeleted;
    
	/** 创建时间 */
    private long CreateTime;
    
	/** 发布时间 */
    private long PublishDate;
    
	/** 编辑时间 */
    private long EditTime;
    

    public Information() {
    }

    public Information(Long LocalId) {
        this.LocalId = LocalId;
    }

    

	/**
	 * 获取本地Id
	 */
    public Long getLocalId() {
        return LocalId;
    }

	/**
	 * 设置本地Id
	 */
    public void setLocalId(Long LocalId) {
        this.LocalId = LocalId;
    }

	/**
	 * 获取服务端Id
	 */
    public int getId() {
        return Id;
    }

	/**
	 * 设置服务端Id
	 */
    public void setId(int Id) {
        this.Id = Id;
    }

	/**
	 * 获取标题
	 */
    public String getTitle() {
        return Title;
    }

	/**
	 * 设置标题
	 */
    public void setTitle(String Title) {
        this.Title = Title;
    }

	/**
	 * 获取信息类型
	 */
    public int getInfoType() {
        return InfoType;
    }

	/**
	 * 设置信息类型
	 */
    public void setInfoType(int InfoType) {
        this.InfoType = InfoType;
    }

	/**
	 * 获取内容
	 */
    public String getContent() {
        return Content;
    }

	/**
	 * 设置内容
	 */
    public void setContent(String Content) {
        this.Content = Content;
    }

	/**
	 * 获取目录编号
	 */
    public int getCategoryID() {
        return CategoryID;
    }

	/**
	 * 设置目录编号
	 */
    public void setCategoryID(int CategoryID) {
        this.CategoryID = CategoryID;
    }

	/**
	 * 获取目录名
	 */
    public String getCategoryName() {
        return CategoryName;
    }

	/**
	 * 设置目录名
	 */
    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

	/**
	 * 获取查看次数
	 */
    public int getViewCount() {
        return ViewCount;
    }

	/**
	 * 设置查看次数
	 */
    public void setViewCount(int ViewCount) {
        this.ViewCount = ViewCount;
    }

	/**
	 * 获取是否显示
	 */
    public boolean getIsDisplay() {
        return IsDisplay;
    }

	/**
	 * 设置是否显示
	 */
    public void setIsDisplay(boolean IsDisplay) {
        this.IsDisplay = IsDisplay;
    }

	/**
	 * 获取是否删除
	 */
    public boolean getIsDeleted() {
        return IsDeleted;
    }

	/**
	 * 设置是否删除
	 */
    public void setIsDeleted(boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

	/**
	 * 获取创建时间
	 */
    public long getCreateTime() {
        return CreateTime;
    }

	/**
	 * 设置创建时间
	 */
    public void setCreateTime(long CreateTime) {
        this.CreateTime = CreateTime;
    }

	/**
	 * 获取发布时间
	 */
    public long getPublishDate() {
        return PublishDate;
    }

	/**
	 * 设置发布时间
	 */
    public void setPublishDate(long PublishDate) {
        this.PublishDate = PublishDate;
    }

	/**
	 * 获取编辑时间
	 */
    public long getEditTime() {
        return EditTime;
    }

	/**
	 * 设置编辑时间
	 */
    public void setEditTime(long EditTime) {
        this.EditTime = EditTime;
    }
}
