package com.proj.JJYQFinancial;

import com.proj.androidlib.tool.LogHelper;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseFragmentActivityHeader implements OnCheckedChangeListener {

	// 注册一个控件
	// 功能类似于findviewbyid，这样比较简化
	// 使用了第三方的几个jar包 libs目录下butterknife-6.0.0.jar。使用方法 可以自行百度
	// 一般 从布局文件中拿控件、绑定click事件 都可以用这个框架
	
	@InjectView(R.id.main_menu) RadioGroup mRadioGroup;
	
	
	/**主页*/
	@InjectView(R.id.main_rdbtn_main) RadioButton mRDBtnMain;
	/**金融产品*/
	@InjectView(R.id.main_rdbtn_product) RadioButton mRDBtnProdect;
	/**个性服务*/
	@InjectView(R.id.main_rdbtn_service) RadioButton mRDBtnService;
	
	
	/** 用于对Fragment进行管理 */
	private FragmentManager mFragmentManager;

	/** 记录当前被选中的RadioButton 的 id*/
	private int mSelectedId = 0;
	// 记录当前显示的那个Fragment
	private MyBaseFragment mCurrentFragment = null;
	private HomeFragment mHomeFragment = null;// 主页
	private ProductFragment mProductFragment = null; // 金融产品 
	private ServiceFragment mServiceFragment = null; // 个性服务
	
	@Override
	protected void getViews() {
		setContentView(R.layout.activity_main);// 配置布局文件
		ButterKnife.inject(this);// ButterKnife 初始化，调用了这句后 前面注册控件的部分才有效
		
	}
	@Override
	protected void init() {
		mFragmentManager = this.getSupportFragmentManager();
		hideFragments(mFragmentManager);
		setTabSelection(mRDBtnMain.getId());
		
	}

	@Override
	protected void setListeners() {
		mRadioGroup.setOnCheckedChangeListener(this);
		
	}
	
	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentManager fragmentManager) {
		if (fragmentManager.getBackStackEntryCount() > 0) {
			fragmentManager.popBackStackImmediate();
		}
	}
	
	/**
	 * 界面切换
	 * @param id
	 */
	private void setTabSelection(int id) {
		if(mSelectedId == id){// 保证不重复选中
			return;
		}
		if(R.id.main_rdbtn_service == id){
			// TODO 个性服务显示前 需要判断 是否登录
			
			// 暂时隐藏
//			if(!LoginUtil.getIsLogin(mContext)){
//				if(mSelectedId > 0){
//					mRadioGroup.check(mSelectedId);
//				}
//				return;
//			}
		}
		
		mSelectedId = id;
		LogHelper.customLogging("setTabSelection");
		// 开启一个Fragment事务来管理Fragment
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		if(mCurrentFragment != null){
			if(mCurrentFragment.isVisible()){
				transaction.hide(mCurrentFragment);
			}
		}
		switch (id) {
		case R.id.main_rdbtn_main:
			if (mHomeFragment == null) {
				// 判断如果为空 就要初始化
				mHomeFragment = new HomeFragment();
				// 条件到事务中，并绑定到R.id.content这个控件上
				transaction.add(R.id.content, mHomeFragment, "0");
			} else {
				// 不为空就直接显示这个Fragment
				transaction.show(mHomeFragment);
			}
			mCurrentFragment = mHomeFragment;
			break;
		case R.id.main_rdbtn_product:
			if (mProductFragment == null) {
				mProductFragment = new ProductFragment();
				transaction.add(R.id.content, mProductFragment, "0");
			} else {
				transaction.show(mProductFragment);
			}
			mCurrentFragment = mProductFragment;
			break;
		case R.id.main_rdbtn_service:
			if (mServiceFragment == null) {
				mServiceFragment = new ServiceFragment();
				transaction.add(R.id.content, mServiceFragment, "0");
			} else {
				transaction.show(mServiceFragment);
			}
			mCurrentFragment = mServiceFragment;
			break;
		}
		transaction.commitAllowingStateLoss();// 通过事务的管理，提交后就会执行前面的配置
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		setTabSelection(checkedId);
		
	}
	
	


}
