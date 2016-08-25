package com.example.GooglePlay.ui.fragment;

import java.util.ArrayList;

import android.view.View;

import com.example.GooglePlay.adapter.MyBaseAdapter;
import com.example.GooglePlay.bean.SubjectInfo;
import com.example.GooglePlay.holder.BaseHolder;
import com.example.GooglePlay.holder.SubjectHolder;
import com.example.GooglePlay.http.protocol.SubjectProtocol;
import com.example.GooglePlay.ui.view.MyListView;
import com.example.GooglePlay.ui.view.PagerFrameLayout.ResultState;
import com.example.GooglePlay.utils.UIUtils;

public class SubjectFragment extends BaseFragment {

	private ArrayList<SubjectInfo> data;
	
	@Override
	public View createSuccessPager() {
		MyListView listView  = new MyListView(UIUtils.getContext());
		
		listView.setAdapter(new SubjectAdapter(data));
		return listView;
	}

	@Override
	public ResultState onLoad() {
		SubjectProtocol protocol = new SubjectProtocol();
		data = (ArrayList<SubjectInfo>) protocol.getData(0);
		return check(data);
	}
	
	class SubjectAdapter extends MyBaseAdapter<SubjectInfo>{

		public SubjectAdapter(ArrayList<SubjectInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder getHolder(int position) {
			return new SubjectHolder();
		}

		@Override
		public ArrayList<SubjectInfo> onLoad() {
			SubjectProtocol protocol = new SubjectProtocol();
			ArrayList<SubjectInfo> moreData = (ArrayList<SubjectInfo>) protocol.getData(getListSize());
			return moreData;
		}
		
	}

}
