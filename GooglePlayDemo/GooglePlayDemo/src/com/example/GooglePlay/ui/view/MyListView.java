package com.example.GooglePlay.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListView extends ListView {

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		this.setCacheColorHint(Color.TRANSPARENT);//去掉滑动时偶然出现黑色背景
		this.setDivider(null);
		this.setSelector(new ColorDrawable());//item自带的点击效果为透明色，相当于去掉自带的点击背景色
	}

	public MyListView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		
	}

	public MyListView(Context context) {
		this(context,null);
		
	}

}
