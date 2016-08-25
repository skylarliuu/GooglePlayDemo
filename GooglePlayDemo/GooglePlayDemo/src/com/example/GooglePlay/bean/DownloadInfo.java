package com.example.GooglePlay.bean;

import java.io.File;

import com.example.GooglePlay.manager.DownloadManager;

import android.os.Environment;

/**
 * 下载应用的信息
 * 
 * @author Administrator
 * 
 */
public class DownloadInfo {

	public String id;
	public String name;
	public long size;
	public String downloadUrl;

	public int currentPos;// 当前下载的位置
	public int currentState;// 当前下载的状态
	
	public String path;//文件下载的路径

	public static final String GOOGLE_MARKET = "GOOGLE_MARKET";
	public static final String download = "download";
	
	// 获取下载进度
	public float getProgress() {
		// 容错
		if (size == 0) {
			return 0;
		}

		float progress = currentPos / (float) size;

		return progress;
	}

	// 获取下载的路径,sdcard/GOOLE_MARKET/download/***.apk
	public String getPath() {
		StringBuffer sb = new StringBuffer();

		String sdcard = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		// 拼接文件的下载路径
		sb.append(sdcard);
		sb.append(File.separator);
		sb.append(GOOGLE_MARKET);
		sb.append(File.separator);
		sb.append(download);
		
		if(createDir(sb.toString())){
		   return sb.toString() + File.separator + "name" + ".apk";	
		}
		
		return null;

	}

	public boolean createDir(String dir){
		File fileDir = new File(dir);
		//如果文件夹不存在或者不是个文件夹
		if(!fileDir.exists() || !fileDir.isDirectory()){
			return fileDir.mkdirs();
		}
			
		//文件夹存在
		return true;
	}
		
	//对象复制
	public static DownloadInfo copy(AppInfo appInfo){
		DownloadInfo info = new DownloadInfo();
		info.id = appInfo.id;
		info.name = appInfo.name;
		info.downloadUrl = appInfo.downloadUrl;
		info.size = appInfo.size;
		
		//初始化
		info.currentPos = 0;
		info.currentState = DownloadManager.STATE_UNDO;
		info.path = info.getPath();
		
		return info;
	}

}
