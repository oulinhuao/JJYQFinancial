package com.proj.view;

import com.proj.JJYQFinancial.R;
import com.proj.JJYQFinancial.utils.EXDateHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MyListView extends ListView {

	public final int RELEASE_To_REFRESH = 0;
	public final int PULL_To_REFRESH = 1;
	public final int REFRESHING = 2;
	public final int DONE = 3;
	public final int LOADING = 4;
	
	private Context mContext;

	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;

	private LayoutInflater inflater;

	private LinearLayout headView;
	private LinearLayout footView;

	private TextView tipsTextview;
	public TextView lastUpdatedTextView;
	
	private ImageView arrowImageView;
//	private ProgressBar progressBar;
	private ImageView progressBar;
	private Animation animRotate;
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;

	// 用于保证startY的值在一个完整的touch事件中只被记录一次
	private boolean isRecored;

	private int headContentHeight;

	private int startY;
	private int firstItemIndex;

	/** 控件的状态 */
	public int state;

	private boolean isBack;

	private OnRefreshListener refreshListener;

	private boolean isRefreshable;
	/** mini style ?*/
	private boolean mIsSmall = false;
	public static final int msgWhat = 1234;//向头部写入信息的消息种类代码  
	public static String msgContent = "";//向头部写入信息的消息内容  
	//记录底部加载布局是否显示
	private boolean mIsWaitLayoutShow = false;
	
	public void setIsSmallStyle(boolean is){
		mIsSmall = is;
	}

	public MyListView(Context context) {
		super(context);
		this.mContext = context;
	}
	

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}
	
	/**
	 * 初始化
	 * @param context
	 * @param headLayout 头部布局id
	 * @author Tony
	 * @date 2014年12月11日 下午3:33:43
	 */
	public void init(Context context,int headLayout){
		this.mContext = context;
		setCacheColorHint(context.getResources().getColor(R.color.transparent));
		inflater = LayoutInflater.from(context);
		headView = (LinearLayout)inflater.inflate(headLayout, null);
		footView = (LinearLayout)inflater.inflate(R.layout.layout_waiting, null);
		lastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);
		if(mIsSmall){
			lastUpdatedTextView.setVisibility(View.INVISIBLE);
			lastUpdatedTextView = null;
		}
		headView.setClickable(false);
		headView.setFocusable(false);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		arrowImageView = (ImageView) headView
				.findViewById(R.id.head_arrowImageView);
		if(!mIsSmall){
			arrowImageView.setMinimumWidth(70);
			arrowImageView.setMinimumHeight(50);
		}
		progressBar = (ImageView) headView
				.findViewById(R.id.head_progressBar);
		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();

		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		addHeaderView(headView, null, true);
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);

		state = DONE;
		isRefreshable = false;
		
		animRotate = AnimationUtils.loadAnimation(context, R.anim.rotate);
	}

	/**
	 * 初始化(默认布局)
	 * @param context
	 * @author Tony
	 * @date 2014年12月11日 下午3:34:05
	 */
	public void init(Context context) {
		this.mContext = context;
		init(context,R.layout.refresh_head);
	}
	
	public void setTipTextColor(int color){
		tipsTextview.setTextColor(color);
		lastUpdatedTextView.setTextColor(color);
	}
	

	/**
	 * 添加一个等待布局
	 * @author Tony
	 * @date 2014-1-2 下午12:00:21
	 */
	public void addFooterViewMine(){
		if(!mIsWaitLayoutShow){
			mIsWaitLayoutShow = true;
			this.addFooterView(footView);
		}
	}
	/**
	 * 自定义的移除脚布局
	 * @author Tony
	 * @date 2014-1-2 下午12:02:37
	 */
	public void removeFooterViewMine(){
		if(mIsWaitLayoutShow && footView != null){
			mIsWaitLayoutShow = false;
			this.removeFooterView(footView);
		}
	}
	
	public void setFirstVisiableItem(int num) {
		firstItemIndex = num;
	}

	public boolean onTouchEvent(MotionEvent event) {

		if (isRefreshable) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (firstItemIndex == 0 && !isRecored) {
					isRecored = true;
					startY = (int) event.getY();
				}
				break;

			case MotionEvent.ACTION_UP:

				if (state != REFRESHING && state != LOADING) {
					if (state == DONE) {
						// 什么都不做
					}
					if (state == PULL_To_REFRESH) {
						state = DONE;
						changeHeaderViewByState();
					}
					if (state == RELEASE_To_REFRESH) {
						state = REFRESHING;
						changeHeaderViewByState();
						onRefresh();
					}
				}

				isRecored = false;
				isBack = false;

				break;

			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();

				if (!isRecored && firstItemIndex == 0) {
					isRecored = true;
					startY = tempY;
				}

				if (state != REFRESHING && isRecored && state != LOADING) {

					// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

					// 可以松手去刷新了
					if (state == RELEASE_To_REFRESH) {
						setSelection(0);

						// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
						if (((tempY - startY) / RATIO < headContentHeight)
								&& (tempY - startY) > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
						// 一下子推到顶了
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();
						}
						// 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
						else {
							// 不用进行特别的操作，只用更新paddingTop的值就行了
						}
					}
					// 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
					if (state == PULL_To_REFRESH) {
						setSelection(0);

						// 下拉到可以进入RELEASE_TO_REFRESH的状态
						if ((tempY - startY) / RATIO >= headContentHeight) {
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();
						}
						// 上推到顶了
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();
						}
					}

					// done状态下
					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}

					// 更新headView的size
					if (state == PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight
								+ (tempY - startY) / RATIO, 0, 0);

					}

					// 更新headView的paddingTop
					if (state == RELEASE_To_REFRESH) {
						headView.setPadding(0, (tempY - startY) / RATIO
								- headContentHeight, 0, 0);
					}

				}

				break;
			}
		}

		return super.onTouchEvent(event);
	}

	/**
	 * 设定条数
	 * @param str
	 * @author Tony
	 * @date 2014-3-24 下午3:58:44
	 */
	public void setTitleTxt(String str){
		tipsTextview.setText(str);
	}
	
	// 当状态改变时候，调用该方法，以更新界面
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.clearAnimation();
			progressBar.setVisibility(View.INVISIBLE);
			tipsTextview.setVisibility(View.VISIBLE);
			if(lastUpdatedTextView != null){
				lastUpdatedTextView.setVisibility(View.VISIBLE);
			}
			tipsTextview.setText("松开即可刷新");
			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.INVISIBLE);
			progressBar.clearAnimation();
			tipsTextview.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			// 是由RELEASE_To_REFRESH状态转变来的
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);

			}
			if(lastUpdatedTextView != null){
				lastUpdatedTextView.setVisibility(View.VISIBLE);
			}
			tipsTextview.setText("下拉刷新");
			break;

		case REFRESHING:
			if(headView != null && headView.getVisibility() != View.VISIBLE){
				headView.setVisibility(View.VISIBLE);
			}
			headView.setPadding(0, 0, 0, 0);
			progressBar.startAnimation(animRotate);
			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.INVISIBLE);
			if(lastUpdatedTextView != null){
				lastUpdatedTextView.setVisibility(View.VISIBLE);
			}
			tipsTextview.setText("正在刷新...");
			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			progressBar.clearAnimation();
			progressBar.setVisibility(View.INVISIBLE);
			arrowImageView.clearAnimation();
			if(mIsSmall){
				arrowImageView.setImageResource(R.drawable.arrow_down_small);
			}else{
				arrowImageView.setImageResource(R.drawable.arrow);
			}
			if(lastUpdatedTextView != null){
				lastUpdatedTextView.setVisibility(View.VISIBLE);
			}
			tipsTextview.setText("下拉刷新");
			break;
		}
	}

	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

	public void onRefreshComplete(long lastTime) {
		state = DONE;
		if(lastUpdatedTextView != null){
			lastUpdatedTextView.setText(mContext.getString(R.string.recently_updated)
					+ EXDateHelper.getTimeToStrFromLong(lastTime, 4));
		}
		changeHeaderViewByState();
	}
	
	/**
	 * 通知刷新完毕，无需时间提示
	 * @author OLH
	 * @date 2013-11-10 下午9:54:16
	 */
	public void onRefreshCompleteNoTime() {
		state = DONE;
		changeHeaderViewByState();
	}

	public void onBeginRefresh(long lastTime) {
		if(state != REFRESHING){
			state = REFRESHING;
			if(lastUpdatedTextView != null){
				String str = mContext.getString(R.string.no_recently_updated);
				if(lastTime > 0){
					str = mContext.getString(R.string.recently_updated)
							+ EXDateHelper.getTimeToStrFromLong(lastTime, 4);
				}else{
					str = mContext.getString(R.string.recently_updated)
							+ "--";
				}
				lastUpdatedTextView.setText(str);
			}
			changeHeaderViewByState();
		}
	}
	
	/**
	 * 无需更新时间提示
	 * 
	 * @author OLH
	 * @date 2013-11-10 下午9:52:48
	 */
	public void onBeginRefreshNoTime() {
		if(state != REFRESHING){
			state = REFRESHING;
			changeHeaderViewByState();
		}
	}

	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	// 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

}
