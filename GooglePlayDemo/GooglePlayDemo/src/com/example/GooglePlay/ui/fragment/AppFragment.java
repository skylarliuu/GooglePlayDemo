package com.example.GooglePlay.ui.fragment;

import java.util.ArrayList;

import android.view.View;

import com.example.GooglePlay.adapter.MyBaseAdapter;
import com.example.GooglePlay.bean.AppInfo;
import com.example.GooglePlay.holder.AppHolder;
import com.example.GooglePlay.holder.BaseHolder;
import com.example.GooglePlay.http.protocol.AppProtocol;
import com.example.GooglePlay.ui.view.MyListView;
import com.example.GooglePlay.ui.view.PagerFrameLayout.ResultState;
import com.example.GooglePlay.utils.UIUtils;

public class AppFragment extends BaseFragment {

	private ArrayList<AppInfo> data;

	@Override
	public View createSuccessPager() {
		MyListView listView = new MyListView(UIUtils.getContext());

		listView.setAdapter(new AppAdapter(data));
		return listView;
	}

	@Override
	public ResultState onLoad() {

		AppProtocol protocol = new AppProtocol();
		data = (ArrayList<AppInfo>) protocol.getData(0);
		return check(data);
	}

	class AppAdapter extends MyBaseAdapter<AppInfo>{

		public AppAdapter(ArrayList<AppInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder getHolder(int position) {
			return new AppHolder();
		}

		@Override
		public ArrayList<AppInfo> onLoad() {
			AppProtocol protocol = new AppProtocol();
			ArrayList<AppInfo> moreData = (ArrayList<AppInfo>) protocol.getData(getListSize());
			return moreData;
		}
		
	}
}
