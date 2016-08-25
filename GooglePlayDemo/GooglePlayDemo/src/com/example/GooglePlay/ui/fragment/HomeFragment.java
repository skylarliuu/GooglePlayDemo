package com.example.GooglePlay.ui.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.GooglePlay.adapter.MyBaseAdapter;
import com.example.GooglePlay.bean.AppInfo;
import com.example.GooglePlay.holder.BaseHolder;
import com.example.GooglePlay.holder.HomeHeaderHolder;
import com.example.GooglePlay.holder.HomeHolder;
import com.example.GooglePlay.http.protocol.HomeProtocol;
import com.example.GooglePlay.ui.activity.HomeDetailActivity;
import com.example.GooglePlay.ui.activity.MainActivity;
import com.example.GooglePlay.ui.view.MyListView;
import com.example.GooglePlay.ui.view.PagerFrameLayout.ResultState;
import com.example.GooglePlay.utils.UIUtils;

public class HomeFragment extends BaseFragment {

	private ArrayList<AppInfo> data;
	private ArrayList<String> mPictureList;
	
	//加载数据成功后才会调用这个方法
	@Override
	public View createSuccessPager() {
		
		MyListView listView = new MyListView(UIUtils.getContext());	
		
		//头布局用单独的holder实现
		HomeHeaderHolder headerHolder = new HomeHeaderHolder();
		//给holder设置数据
		headerHolder.setData(mPictureList);
		listView.addHeaderView(headerHolder.mRootView);
		
		listView.setAdapter(new HomeAdapter(data));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(UIUtils.getContext(), HomeDetailActivity.class);
				//减去头布局的位置
				intent.putExtra("packageName", data.get(position-1).packageName);
				
				startActivity(intent);
			}
		});
		
		return listView;
	}

	//加载数据
	@Override
	public ResultState onLoad() {
				
		HomeProtocol homeProtocol = new HomeProtocol();
		data = (ArrayList<AppInfo>) homeProtocol.getData(0);
		
		//加载轮播条数据
		mPictureList = homeProtocol.getPictureList();
	
		return check(data);
	}
	
	
	class HomeAdapter extends MyBaseAdapter{

		public HomeAdapter(ArrayList<AppInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder<AppInfo> getHolder(int position) {
             return new HomeHolder();
		}

		//加载更多
		@Override
		public ArrayList<AppInfo> onLoad() {
		
			HomeProtocol homeProtocol = new HomeProtocol();
			//分页加载，把当前集合的大小作为访问页面的起始位置
			ArrayList<AppInfo> moreData = (ArrayList<AppInfo>) homeProtocol.getData(getListSize());
			
			return moreData;
		}
				
	}
}
