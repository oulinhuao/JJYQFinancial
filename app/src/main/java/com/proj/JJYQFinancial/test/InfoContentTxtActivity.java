package com.proj.JJYQFinancial.test;


import com.proj.JJYQFinancial.BaseActivityHeader;
import com.proj.JJYQFinancial.R;
import com.proj.JJYQFinancial.utils.EXDateHelper;

import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 *  展示信息详情
 */
public class InfoContentTxtActivity extends BaseActivityHeader{
	
	@InjectView(R.id.textView1) TextView txtTitle;
	@InjectView(R.id.textView2) TextView txtTime;
	@InjectView(R.id.textView3) TextView txtContent;

	private Information mInfo;
	
	

	@Override
	protected void getViews() {
		setContentView(R.layout.activity_info_content_txt);
		ButterKnife.inject(this);
		
	}

	@Override
	protected void init() {
		setTitle("展示信息详情");
		
		Object obj = getIntent().getSerializableExtra("entity");
		if(obj != null){
			mInfo = (Information)obj;
		}
		setValue();
	}
	private void setValue(){
		if(mInfo == null){
			return;
		}
		
		txtTitle.setText(mInfo.getTitle());
		txtTime.setText(EXDateHelper.getCommontDateStringFromLong(mInfo.getPublishDate()));
		txtContent.setText(mInfo.getContent());
	}
	
	
	
	
}
