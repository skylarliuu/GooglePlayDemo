package com.example.GooglePlay.ui.fragment;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;

import com.example.GooglePlay.adapter.MyBaseAdapter;
import com.example.GooglePlay.bean.CategoryInfo;
import com.example.GooglePlay.holder.BaseHolder;
import com.example.GooglePlay.holder.CategoryHolder;
import com.example.GooglePlay.holder.TitleHolder;
import com.example.GooglePlay.http.protocol.CategoryProtocol;
import com.example.GooglePlay.ui.view.MyListView;
import com.example.GooglePlay.ui.view.PagerFrameLayout.ResultState;
import com.example.GooglePlay.utils.UIUtils;

public class CategoryFragment extends BaseFragment {

	private ArrayList<CategoryInfo> data;
	
	@Override
	public View createSuccessPager() {
		MyListView listView = new MyListView(UIUtils.getContext());
		listView.setAdapter(new CategoryAdapter(data));
		
		return listView;
	}

	@Override
	public ResultState onLoad() {
		CategoryProtocol protocol = new CategoryProtocol();
		data = protocol.getData(0);
		Log.i("Category", "=============="+data);
		return check(data);
	}

	class CategoryAdapter extends MyBaseAdapter<CategoryInfo>{

		public CategoryAdapter(ArrayList<CategoryInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder getHolder(int position) {
			if(getItem(position).isTitle){
				return new TitleHolder();
			}else{
				return new CategoryHolder();
			}
			
		}

		@Override
		public boolean hasMore() {
			return false;
		}
		@Override
		public ArrayList<CategoryInfo> onLoad() {
			return null;
		}
		
		@Override
		public int getItemType(int position) {
			if(getItem(position).isTitle){
				return super.getItemType(position) +1;
			}else{
				return super.getItemType(position);
			}
				
		}
		
		//新增一种item 
		@Override
		public int getViewTypeCount() {
			return super.getViewTypeCount() +1;
		}
		
	}
	
}
