package com.example.GooglePlay.holder;

import android.view.View;

public abstract class BaseHolder<T> {

	public View mRootView;
	public T data;
	
	public BaseHolder(){
		mRootView = initView();
		//打标记
		mRootView.setTag(this);
	}
	
	//设置数据，刷新界面
	public void setData(T data){
		this.data = data;
		onRefresh(data);
	}
	
	//获取数据
	public T getData(){
		return data;
	}
	

	//加载布局，findviewbyid，让子类去实现
	public abstract View initView();
	//刷新界面
	public abstract void onRefresh(T data);
	
}
