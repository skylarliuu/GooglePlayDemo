package com.example.GooglePlay.ui.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.GooglePlay.ui.view.PagerFrameLayout;
import com.example.GooglePlay.ui.view.PagerFrameLayout.ResultState;
import com.example.GooglePlay.utils.UIUtils;

public abstract class BaseFragment extends Fragment {

	private PagerFrameLayout frameLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
   
		//PagerFrameLayout是自定义view,可以直接作为view返回		
		frameLayout = new PagerFrameLayout(UIUtils.getContext()) {		
		@Override
		public View createSuccessPager() {
			
			return BaseFragment.this.createSuccessPager();
		}

		@Override
		public ResultState onLoad() {
			
			return BaseFragment.this.onLoad();
		}

	};
			
		return frameLayout;
	}
	
	//由各个fragment去实现加载成功的view
	public abstract View createSuccessPager();
	//由各个fragment去实现加载各自的数据
	public abstract ResultState onLoad();
	
	public void loadData(){
		
		frameLayout.loadData();
	}
	
	
	/**检测下载数据的返回结果
	 * @param data
	 * @return
	 */
	public ResultState check(Object data){
		if(data != null){
			if(data instanceof ArrayList){
				//是个集合
				ArrayList list = (ArrayList) data;
				if(list.size()<0){
					//集合为空
					return ResultState.EMPTY;
				}else{
	
					return ResultState.SUCCESS;
				}
			}
		}
		
		return ResultState.ERROR;
	}
	
}
