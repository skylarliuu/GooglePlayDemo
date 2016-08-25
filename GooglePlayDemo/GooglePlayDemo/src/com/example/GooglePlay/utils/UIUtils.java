package com.example.GooglePlay.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.example.GooglePlay.global.MyApplication;

public class UIUtils {

	public static Context getContext(){
		return MyApplication.getContext();
	}
	
	public static Handler getHandler(){
		return MyApplication.getHandler();
	}
	
	public static int getMainThread(){
		return MyApplication.getMainThread();
	}
	
	/////////////////获取资源////////////////////
	
	//获取字符串
	public static String getString(int id){
		return getContext().getResources().getString(id);
	}
	
	public static String[] getStringArray(int id){
		return getContext().getResources().getStringArray(id);
	}
	
	public static Drawable getDrawable(int id){
		return getContext().getResources().getDrawable(id);
	}
	
	public static int getColor(int id){
		return getContext().getResources().getColor(id);
	}
	
	//获取颜色状态选择器
	public static ColorStateList getColorStateList(int id){
		return getContext().getResources().getColorStateList(id);
	}
	
	//返回的是像素值
	public static int getDimen(int id){
		return getContext().getResources().getDimensionPixelSize(id);
	}
	
	/////////////////dip和px的转换//////////////
	
	public static int dip2px(float dip){
		float density = getContext().getResources().getDisplayMetrics().density;
		int px = (int) (dip*density+0.5f);
		return px;
	}
	
	public static float px2dip(int px){
		float density = getContext().getResources().getDisplayMetrics().density;
		return px/density;
	}
	
     /////////////////加载布局文件////////////////
	
	public static View inflate(int id){
		return View.inflate(getContext(), id, null);
	}
	
	////////////////////////////////////////////
	
	//判断是否运行在主线程
	public static boolean isRunOnUI(){
		int threadId = android.os.Process.myTid();
		if(threadId == getMainThread()){
			return true;
		}
		return false;
	}
	
	public static void runOnUIThread(Runnable r){
		if(isRunOnUI()){
			//直接运行
			r.run();
		}else{
			//借助handler让其运行在主线程
			getHandler().post(r);
		}
	}
	
}
