package com.example.GooglePlay.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableUtils {

	public static GradientDrawable getGradientDrawable(int radius,int color ){
		
		//xml文件中shape对应的类
		GradientDrawable drawable = new GradientDrawable();
		//设置成圆角矩形
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setCornerRadius(radius);
		drawable.setColor(color);
		
		return drawable;
	}
	
	public static StateListDrawable getSelector(Drawable normal,Drawable press){
		//选择器
		StateListDrawable selector = new StateListDrawable();
		selector.addState(new int[]{android.R.attr.state_pressed},press);
		selector.addState(new int[]{}, normal);
		
		return selector;
	}
	
	public static StateListDrawable getSelector(int normal,int press,int radius){
		GradientDrawable bg_normal = getGradientDrawable(radius,normal);
		GradientDrawable bg_press = getGradientDrawable(radius,press);	
		
		StateListDrawable selector = getSelector(bg_normal, bg_press);
		
		return selector;
	}
}
