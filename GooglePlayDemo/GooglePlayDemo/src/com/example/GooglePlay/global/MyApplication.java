package com.example.GooglePlay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

public class MyApplication extends Application {

	private static Context context;
	private static Handler handler;
	private static int mainThread;
	
	@Override
	public void onCreate() {
		context = getApplicationContext() ;
	    handler = new Handler();
	    mainThread = android.os.Process.myTid();
	}

	public static Context getContext() {
		return context;
	}

	public static Handler getHandler() {
		return handler;
	}

	public static int getMainThread() {
		return mainThread;
	}

	
}