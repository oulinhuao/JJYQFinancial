/**
 * http://www.kingdonsoft.com/zsnd/MobielWebService/InformationMobileService.asmx
 * 接口在这里，可以浏览器调用下看看效果
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */









package com.proj.JJYQFinancial.test;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.proj.JJYQFinancial.BaseActivityHeader;
import com.proj.JJYQFinancial.R;
import com.proj.JJYQFinancial.R2;
import com.proj.JJYQFinancial.dialog.LoadingDialog;
import com.proj.JJYQFinancial.utils.EXDateHelper;
import com.proj.JJYQFinancial.utils.EXNetWorkHelper;
import com.proj.androidlib.tool.CommonHelper;
import com.proj.base.GlobalConfig;
import com.proj.base.ListViewDataAdapter;
import com.proj.base.ViewHolderBase;
import com.proj.base.ViewHolderCreate;
import com.proj.view.MyListView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 信息列表界面
 * @author Tony
 */
public class InfoListActivity extends BaseActivityHeader implements 
	OnScrollListener{ //implements OnScrollListener{
	
	@BindView(R2.id.list_lv) MyListView mListView;
	private LoadingDialog mLoadingDialog;
	
	private ListViewDataAdapter<Information, ViewHolder> adapter;
	private String mTitle = "";
	
	private int mType = 0;
	
	
	private List<Information> mList;
//	private List<Information> mList;
	private InformationService mInformationService;

	private final int DO_SUCCESS = 1;
	private final int DO_FAILED = 2;
	private final int NEXT_SUCCESS = 3;
	private final int NEXT_FAILED = 4;
	private final int SHOW_FRIST = 5;
	
	/** 每页数据大小*/
	private final int PAGE_SIZE = 20;
	/** 页数*/
	private int mPageIndex = 0;
	/** 总条数*/
	private int mAllCount = 0;

	@Override
	protected void getViews() {
		setContentView(R.layout.activity_list);
		ButterKnife.bind(this);
	}
	
	
	@Override
	protected void init() {
		setTitle("测试从网络获取列表");
		
		mInformationService = new InformationService(this);
		if(mList == null){
			mList = new ArrayList<Information>();
		}
		initListView();
		downData();
	}
	private void initListView(){
		mListView.init(mContext);
		mListView.setTipTextColor(mContext.getResources().getColor(R.color.black));
		mListView.setOnScrollListener(this);
		
//		// 下拉刷新
//		mListView.setonRefreshListener(new OnRefreshListener() {
//			@Override
//			public void onRefresh() {
//				update();
//			}
//		});
	}

	@Override
	protected void setListeners() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// item的点击事件
				if(position < 0 || position > getListCount()){
					return;
				}
				Information wn = mList.get(position);
				if(wn != null){
					Bundle bundle = new Bundle();
					bundle.putSerializable("entity", wn);
					openActivityForResult(InfoContentTxtActivity.class, bundle, 10);
				}
			}
		});
	}
	
	private void showLoading(){
		if(mLoadingDialog == null){
			mLoadingDialog = new LoadingDialog(mContext);
		}
		mLoadingDialog.show();
	}
	private void hideLoading(){
		if(mLoadingDialog != null && mLoadingDialog.isShowing()){
			mLoadingDialog.dismiss();
		}
	}
	
	/**
	 * 获取列表当前的条数
	 * @return
	 * @author Tony
	 */
	private int getListCount(){
		if(mList == null){
			return 0;
		}else{
			return mList.size();
		}
	}
	/**
	 * 重置数据
	 */
	private void resetData(){
		mPageIndex=0;
		mAllCount=0;
		if(mList != null){
			mList.clear();// 清空列表
		}
	}
	
	/**
	 * 开始下载数据，下载第一页
	 */
	private void downData() {
		if(EXNetWorkHelper.isNetworkAvailable(mContext, false)){
			showLoading();
			resetData();// 因为是第一页，先把之前的数据清空
			// 开启一个线程:android中网络操作必须在子线程中调用,然后
			new Thread(){
				public void run() {
					// 首先获取总条数
					mAllCount = mInformationService.getEntityCount(mType);
					// 开始下载第一页
					mList = mInformationService.getEntityListPaged(
							PAGE_SIZE,
							1,
							mType);
					mPageIndex = 1;
					
					// 通过Message发送消息通知绑定数据
					Message msg = new Message();
					msg.what = DO_SUCCESS;
					mMsgHandler.sendMessage(msg);
				};
			}.start();
		}else{
			if(mAllCount == 0){
				CommonHelper.showToast(mContext, R.string.nonet, 0);
			}
		}
	}
	private void getNextPage(){
		if(EXNetWorkHelper.isNetworkAvailable(mContext, false)){
			// 开启一个线程，android中网络操作必须在子线程中调用,然后
			new Thread(){
				public void run() {
					// 首先获取总条数
					// 开始下载第一页
					mList = mInformationService.getEntityListPaged(
							PAGE_SIZE,
							mPageIndex + 1,
							mType);
					mPageIndex ++;
					// 通过Message发送消息通知绑定数据
					Message msg = new Message();
					msg.what = NEXT_SUCCESS;
					mMsgHandler.sendMessage(msg);
				};
			}.start();
		}else{
			if(mAllCount == 0){
				CommonHelper.showToast(mContext, R.string.nonet, 0);
			}
		}
	}
	
	private void setDateToList(){
		if(adapter == null){
			adapter = new ListViewDataAdapter<Information,ViewHolder>(new ViewHolderCreate<Information,ViewHolder>() {
				@Override
				public ViewHolder createHolder() {
					return new ViewHolder();
				}
			});
			adapter.addList(mList);
			mListView.setAdapter(adapter);
		}else{
			adapter.clear();
			adapter.addList(mList);
		}
		if(getListCount() >= PAGE_SIZE){
			mListView.addFooterViewMine();
		}
	}
	
	private MsgHandler mMsgHandler = new MsgHandler(this);
	private static class MsgHandler extends Handler{
		private WeakReference<Object> mOB;
		InfoListActivity owner = null;
		public MsgHandler(InfoListActivity ob){
			mOB = new WeakReference<Object>(ob);
			owner = (InfoListActivity)mOB.get();
		}
		@Override
		public void handleMessage(Message msg) {
			if(msg == null || owner == null){
				return;
			}
			owner.executeByMessage(msg);
			super.handleMessage(msg);
		}
	}
	
	protected void executeByMessage(Message msg){
		if(mIsFinish){
			return;
		}
		switch (msg.what) {
		case DO_SUCCESS:
			setDateToList();
			hideLoading();
			break;
		case DO_FAILED:
			break;
		case NEXT_SUCCESS:
			adapter.addList(mList);
			adapter.notifyDataSetChanged();
			break;
		case NEXT_FAILED:
			break;
		case SHOW_FRIST:
			mListView.setSelection(0);
			break;
		case GlobalConfig.MSG_WHAT_TIP_DOWN_COUNT:// 提示条数
			break;
		case GlobalConfig.MSG_WHAT_TIP_NEED_DOWN:// 有数据需要下载
			break;
		}
	}
	
	
	
	
	
	

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_IDLE:
			if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
				if (mListView.state == mListView.DONE) {
					// 滑动到底部，且没在更新下载，需要加载更多
					if(getListCount() < mAllCount){
						getNextPage();
					}else{
						mListView.removeFooterViewMine();
					}
				}
			}
			break;
		}
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		mListView.setFirstVisiableItem(firstVisibleItem);
	}
	
	public class ViewHolder implements ViewHolderBase<Information> {
	
		@BindView(R2.id.info_title) TextView txtTitle;
		@BindView(R2.id.info_time) TextView txtTime;
		
		@Override
		public View createView(LayoutInflater inflater, ViewGroup parent) {
			View convertView = inflater.inflate(R.layout.list_item_info_txt, parent,false);
			ButterKnife.bind(this, convertView);
			return convertView;
		}

		@Override
		public void showData(int position, Information itemData) {
			if(itemData != null){
				if(TextUtils.isEmpty(itemData.getTitle())){
					txtTitle.setText(" ");
				}else{
					txtTitle.setText(itemData.getTitle());
				}
				
//				txtTime.setText(itemData.getPublishDate());
				
				if(itemData.getPublishDate() <= 0){
					txtTime.setText(" ");
				}else{
					txtTime.setText(EXDateHelper.getCommontDateStringFromLong(
							itemData.getPublishDate()));
				}
			}
		}
	} 
}