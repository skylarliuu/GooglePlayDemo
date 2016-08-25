package com.example.GooglePlay.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.net.Uri;

import com.example.GooglePlay.bean.AppInfo;
import com.example.GooglePlay.bean.DownloadInfo;
import com.example.GooglePlay.http.HttpHelper;
import com.example.GooglePlay.http.HttpHelper.HttpResult;
import com.example.GooglePlay.utils.IOUtils;
import com.example.GooglePlay.utils.UIUtils;

/**
 * 观察者模式
 * 
 * DownloadManager:被观察者
 * 
 * @author Administrator
 * 
 */
public class DownloadManager {

	// 下载的状态
	public static final int STATE_UNDO = 1;// 未下载
	public static final int STATE_WAIT = 2;// 等待下载
	public static final int STATE_DOWNLOAD = 3;// 正在下载
	public static final int STATE_ERROR = 4;// 下载失败
	public static final int STATE_SUCCESS = 5;// 下载成功
	public static final int STATE_PAUSE = 6;// 暂停下载

	private static DownloadManager mDM;

	private ArrayList<DownloadObserver> mObserverList = new ArrayList<DownloadObserver>();

	// 根据id存放DownloadInfo
	private HashMap<String, DownloadInfo> mInfoMap = new HashMap<String, DownloadInfo>();
	// 根据id存放DownloadTask
	private HashMap<String, DownloadTask> mTaskMap = new HashMap<String, DownloadTask>();

	private DownloadManager() {};

	
	public static DownloadManager getDownloadManager() {
		if (mDM == null) {
			mDM = new DownloadManager();
		}

		return mDM;
	}

	// 注册观察者
	public synchronized void registerObserver(DownloadObserver observer) {
		if (observer != null && !mObserverList.contains(observer)) {
			mObserverList.add(observer);
		}
	}

	// 注销观察者
	public synchronized void unregisterObserver(DownloadObserver observer) {
		if (observer != null && mObserverList.contains(observer)) {
			mObserverList.remove(observer);
		}
	}

	// 被观察者通过此方法通知各观察者更新状态
	public synchronized void notifyDownloadStateChange(DownloadInfo downloadInfo) {
		for (DownloadObserver observer : mObserverList) {
			observer.notifyDownloadStateChange(downloadInfo);
		}

	}

	// 被观察者通过此方法通知各观察者更新进度
	public synchronized void notifyDownloadProgressChange(
			DownloadInfo downloadInfo) {
		for (DownloadObserver observer : mObserverList) {
			observer.notifyDownloadProgressChange(downloadInfo);
		}
	}

	// 观察者接口
	public interface DownloadObserver {
		// 状态改变通知
		public void notifyDownloadStateChange(DownloadInfo downloadInfo);

		// 进度改变通知
		public void notifyDownloadProgressChange(DownloadInfo downloadInfo);
	}

	// 下载
	public synchronized void download(AppInfo appInfo) {
		// 先看是否已有过该下载（断点续传）
		DownloadInfo downloadInfo = mInfoMap.get(appInfo.id);

		if (downloadInfo == null) {
			downloadInfo = DownloadInfo.copy(appInfo);
		}

		// 等待下载
		downloadInfo.currentState = STATE_WAIT;
		notifyDownloadStateChange(downloadInfo);

		mInfoMap.put(downloadInfo.id, downloadInfo);
		// 执行下载任务
		DownloadTask task = new DownloadTask(downloadInfo);
		ThreadManager.getThreadPool().execute(task);

		mTaskMap.put(downloadInfo.id, task);
	}

	// 下载的子线程
	class DownloadTask implements Runnable {

		private DownloadInfo downloadInfo;

		public DownloadTask(DownloadInfo downloadInfo) {
			this.downloadInfo = downloadInfo;
		}

		@Override
		public void run() {
			if (downloadInfo != null) {

				// 改变状态，通知刷新
				downloadInfo.currentState = STATE_DOWNLOAD;
				notifyDownloadStateChange(downloadInfo);

				File file = new File(downloadInfo.path);

				HttpResult httpResult;

				// 文件不存在，当前下载位置为0，或者已下载的文件长度不对时需要重新下载
				if (!file.exists() || downloadInfo.currentPos == 0
						|| file.length() != downloadInfo.currentPos) {
					// 删除文件，重新下载
					file.delete();
					downloadInfo.currentPos = 0;
					// 正式下载
					httpResult = HttpHelper.download(HttpHelper.URL
							+ "download?name=" + downloadInfo.downloadUrl);

				} else {
					// 断点续传
					httpResult = HttpHelper.download(HttpHelper.URL
							+ "download?name=" + downloadInfo.downloadUrl
							+ "&range=" + file.length());
				}

				if (httpResult != null && httpResult.getInputStream() != null) {
					// 下载成功，将下载结果写入文件
					InputStream is = httpResult.getInputStream();
					FileOutputStream fos = null;
					try {
						
						fos = new FileOutputStream(file, true);// 可以断点续传

						byte[] buffer = new byte[1024];
						int len = 0;

						while ((len = is.read(buffer)) != -1
								&& downloadInfo.currentState == STATE_DOWNLOAD) {
							fos.write(buffer, 0, len);
							fos.flush();// 把缓冲区全部写入本地

							// 更新进度
							downloadInfo.currentPos += len;
							notifyDownloadProgressChange(downloadInfo);
						}


					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						IOUtils.close(is);
						IOUtils.close(fos);
					}
					
					// 文件写入完成
					if (file.length() == downloadInfo.size) {
						// 写入成功
						downloadInfo.currentState = STATE_SUCCESS;
						notifyDownloadStateChange(downloadInfo);
					} else if (downloadInfo.currentState == STATE_PAUSE) {
						// 中途暂停
						notifyDownloadStateChange(downloadInfo);
					} else {
						// 写入失败
						file.delete();// 删除无效文件
						downloadInfo.currentPos = 0;
						downloadInfo.currentState = STATE_ERROR;
						notifyDownloadStateChange(downloadInfo);
					}

				} else {
					// 网络异常
					file.delete();// 删除无效文件
					downloadInfo.currentPos = 0;
					downloadInfo.currentState = STATE_ERROR;
					notifyDownloadStateChange(downloadInfo);

				}

				// 不管下载成功,失败还是暂停, 下载任务已经结束,都需要从当前任务集合中移除
				mTaskMap.remove(downloadInfo.id);

			}

		}

	}

	// 暂停
	public synchronized void pause(AppInfo appInfo) {

		if (appInfo != null) {
			DownloadInfo downloadInfo = DownloadInfo.copy(appInfo);
			if (downloadInfo != null) {
				// 只有正在下载和等待下载才能暂停
				if (downloadInfo.currentState == STATE_DOWNLOAD
						|| downloadInfo.currentState == STATE_WAIT) {

					DownloadTask task = mTaskMap.get(downloadInfo.id);

					// 从等待队列中移除。暂停分两种情况：一种是还在队列中等待下载的被暂停，此时从队列中移除；另一种是正在下载，
					// 此时通过download方法中run的写入条件while循环控制，检测到状态为PAUSE则不会继续写入、
					if (task != null) {
						ThreadManager.getThreadPool().remove(task);
					}

					downloadInfo.currentState = STATE_PAUSE;
					notifyDownloadStateChange(downloadInfo);
				}
			}
		}
	}

	// 安装
	public synchronized void install(AppInfo appInfo) {

		DownloadInfo downloadInfo = mInfoMap.get(appInfo.id);

		if (downloadInfo != null) {
			// 跳到系统的安装页面进行安装
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.parse("file://" + downloadInfo.path),
					"application/vnd.android.package-archive");
			UIUtils.getContext().startActivity(intent);
		}

	}
	
	public DownloadInfo getDownloadInfo(AppInfo info){
		
		return mInfoMap.get(info.id);
	}
}
