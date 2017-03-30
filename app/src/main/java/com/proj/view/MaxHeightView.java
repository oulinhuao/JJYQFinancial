package com.proj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class MaxHeightView extends RelativeLayout {

	public MaxHeightView(Context context) {
        super(context, null);
    }

    public MaxHeightView(Context context, AttributeSet attrs) {
    	super(context, attrs, 0);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	if(mMaxHeight>-1 && getMeasuredHeight()>mMaxHeight){  
            setMeasuredDimension(getMeasuredWidth(), mMaxHeight);  
        }
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    public void setMaxHeight(int height){  
        mMaxHeight = height;  
    }
    private int mMaxHeight = -1;

}
