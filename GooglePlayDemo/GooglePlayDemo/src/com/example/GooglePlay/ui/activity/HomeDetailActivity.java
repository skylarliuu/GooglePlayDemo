package com.example.GooglePlay.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.example.GooglePlay.R;
import com.example.GooglePlay.bean.AppInfo;
import com.example.GooglePlay.bean.DownloadInfo;
import com.example.GooglePlay.holder.DetailAppInfoHolder;
import com.example.GooglePlay.holder.DetailDesHolder;
import com.example.GooglePlay.holder.DetailDownloadHolder;
import com.example.GooglePlay.holder.DetailPicsHolder;
import com.example.GooglePlay.holder.DetailSafeHolder;
import com.example.GooglePlay.http.protocol.HomeDetailProtocol;
import com.example.GooglePlay.ui.view.PagerFrameLayout;
import com.example.GooglePlay.ui.view.PagerFrameLayout.ResultState;
import com.example.GooglePlay.utils.UIUtils;

public class HomeDetailActivity extends BaseActivity {

	private String packageName;
	private AppInfo data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		packageName = getIntent().getStringExtra("packageName");

		PagerFrameLayout frameLayout = new PagerFrameLayout(this) {
			
			@Override
			public ResultState onLoad() {
				return HomeDetailActivity.this.onLoad();
			}
			
			@Override
			public View createSuccessPager() {
				return HomeDetailActivity.this.createSuccessPager();
			}
		};
		
		setContentView(frameLayout);
		
		//加载数据,调用onLoad()
		frameLayout.loadData();
	}
	
	//加载网络数据，该方法运行在子线程中
	public ResultState onLoad(){
		HomeDetailProtocol protocol = new HomeDetailProtocol(packageName);
		data = (AppInfo) protocol.getData(0);
		
		if(data != null){
			return ResultState.SUCCESS;
		}else{
			return ResultState.ERROR;
		}
	}
	
	//加载成功的页面
	public View createSuccessPager() {
		
		View view = UIUtils.inflate(R.layout.activity_home_detail);	
		
		//初始化应用信息模块
		FrameLayout fl_info = (FrameLayout) view.findViewById(R.id.fl_info);	
		DetailAppInfoHolder appInfoHolder = new DetailAppInfoHolder();
		appInfoHolder.setData(data);		
		fl_info.addView(appInfoHolder.mRootView);
		
		//初始化安全信息模块
		FrameLayout fl_safe = (FrameLayout) view.findViewById(R.id.fl_safe);		
		DetailSafeHolder safeHolder = new DetailSafeHolder();
		safeHolder.setData(data);
		fl_safe.addView(safeHolder.mRootView);
		
		//初始化应用截图模块
		HorizontalScrollView hsv_pics = (HorizontalScrollView) view.findViewById(R.id.hsv_pics);
		DetailPicsHolder picsHolder = new DetailPicsHolder();
		picsHolder.setData(data);
		hsv_pics.addView(picsHolder.mRootView);
		
		//初始化应用描述模块
		FrameLayout fl_des = (FrameLayout) view.findViewById(R.id.fl_des);		
		DetailDesHolder desHolder = new DetailDesHolder();
		desHolder.setData(data);
		fl_des.addView(desHolder.mRootView);
		
		//初始化底部下载模块
		FrameLayout fl_download = (FrameLayout) view.findViewById(R.id.fl_download);		
		DetailDownloadHolder downloadHolder = new DetailDownloadHolder();
		downloadHolder.setData(data);
		fl_download.addView(downloadHolder.mRootView);
		
		return view;
	}
}
