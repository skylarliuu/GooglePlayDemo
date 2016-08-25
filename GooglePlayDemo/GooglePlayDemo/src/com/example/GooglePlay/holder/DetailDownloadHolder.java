package com.example.GooglePlay.holder;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.GooglePlay.R;
import com.example.GooglePlay.bean.AppInfo;
import com.example.GooglePlay.bean.DownloadInfo;
import com.example.GooglePlay.manager.DownloadManager;
import com.example.GooglePlay.manager.DownloadManager.DownloadObserver;
import com.example.GooglePlay.ui.view.ProgressHorizontal;
import com.example.GooglePlay.utils.UIUtils;

//下载观察者
public class DetailDownloadHolder extends BaseHolder<AppInfo> implements
		DownloadObserver, OnClickListener {

	private ProgressHorizontal phProgress;
	private FrameLayout fl_download;
	private Button btn_download;
	private DownloadManager mDM;
	private int mCurrentState;
	private float mProgress;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_detail_download);

		fl_download = (FrameLayout) view.findViewById(R.id.fl_download);
		btn_download = (Button) view.findViewById(R.id.btn_download);
		fl_download.setOnClickListener(this);
		btn_download.setOnClickListener(this);

		phProgress = new ProgressHorizontal(UIUtils.getContext());
		phProgress.setProgressTextColor(Color.WHITE);// 文字颜色为白色
		phProgress.setProgressTextSize(UIUtils.dip2px(18));// 文字大小
		phProgress.setProgressResource(R.drawable.progress_normal);// 进度条颜色
		phProgress.setProgressBackgroundResource(R.drawable.progress_bg);// 进度条背景色

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);

		fl_download.addView(phProgress,params);

		// 注册观察者
		mDM = DownloadManager.getDownloadManager();
		mDM.registerObserver(this);

		return view;
	}

	@Override
	public void onRefresh(AppInfo data) {

		DownloadInfo downloadInfo = mDM.getDownloadInfo(data);

		if (downloadInfo != null) {
			// 已经下载过
			mCurrentState = downloadInfo.currentState;
			mProgress = downloadInfo.getProgress();
		} else {
			// 第一次下载
			mCurrentState = DownloadManager.STATE_UNDO;
			mProgress = 0;
		}

		refreshUI(mCurrentState, mProgress);
	}

	@Override
	public void notifyDownloadStateChange(DownloadInfo downloadInfo) {
//		// 只有改变的是本应用才去更新
//		if (getData().id.equals(downloadInfo.id)) {
//			// 在主线程中更新UI
//			refreshOnMainUI(downloadInfo);
//		}
		refreshOnMainUI(downloadInfo);

	}

	@Override
	public void notifyDownloadProgressChange(DownloadInfo downloadInfo) {
//		if (getData().id.equals(downloadInfo.id)) {
//			// 在主线程中更新UI
//			refreshOnMainUI(downloadInfo);
//		}
		refreshOnMainUI(downloadInfo);

	}

	private void refreshOnMainUI(final DownloadInfo downloadInfo) {
			// 只有改变的是本应用才去更新
			AppInfo appInfo = getData();
			if (downloadInfo.id != null && appInfo.id != null) {
			if (appInfo.id.equals(downloadInfo.id)) {
				UIUtils.runOnUIThread(new Runnable() {

					@Override
					public void run() {
						refreshUI(downloadInfo.currentState,
								downloadInfo.getProgress());

					}
				});
			}
		}

	}

	public void refreshUI(int state, float progress) {
		mCurrentState = state;
		mProgress = progress;

		switch (mCurrentState) {
		case DownloadManager.STATE_UNDO:
			btn_download.setVisibility(View.VISIBLE);
			fl_download.setVisibility(View.GONE);
			btn_download.setText("下载");
			break;

		case DownloadManager.STATE_WAIT:
			btn_download.setVisibility(View.VISIBLE);
			fl_download.setVisibility(View.GONE);
			btn_download.setText("等待下载");
			break;

		case DownloadManager.STATE_DOWNLOAD:
			btn_download.setVisibility(View.GONE);
			fl_download.setVisibility(View.VISIBLE);
			phProgress.setCenterText("");
			phProgress.setProgress(progress);
			break;

		case DownloadManager.STATE_PAUSE:
			btn_download.setVisibility(View.GONE);
			fl_download.setVisibility(View.VISIBLE);
			phProgress.setCenterText("暂停");
			break;

		case DownloadManager.STATE_ERROR:
			btn_download.setVisibility(View.VISIBLE);
			fl_download.setVisibility(View.GONE);
			btn_download.setText("下载失败");
			break;

		case DownloadManager.STATE_SUCCESS:
			btn_download.setVisibility(View.VISIBLE);
			fl_download.setVisibility(View.GONE);
			btn_download.setText("安装");
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_download:
		case R.id.fl_download:
			if (mCurrentState == DownloadManager.STATE_UNDO
					|| mCurrentState == DownloadManager.STATE_PAUSE
					|| mCurrentState == DownloadManager.STATE_ERROR) {
				// 点击下载
				mDM.download(getData());

			} else if (mCurrentState == DownloadManager.STATE_WAIT
					|| mCurrentState == DownloadManager.STATE_DOWNLOAD) {
				// 点击暂停
				mDM.pause(getData());
			} else if (mCurrentState == DownloadManager.STATE_SUCCESS) {
				// 点击安装
				mDM.install(getData());
			} 

			break;

		default:
			break;
		}

	}

}
