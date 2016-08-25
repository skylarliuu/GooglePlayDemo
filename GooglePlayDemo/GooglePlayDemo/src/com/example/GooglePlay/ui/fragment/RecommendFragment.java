package com.example.GooglePlay.ui.fragment;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.example.GooglePlay.http.protocol.RecommendProtocol;
import com.example.GooglePlay.ui.view.PagerFrameLayout.ResultState;
import com.example.GooglePlay.ui.view.fly.ShakeListener;
import com.example.GooglePlay.ui.view.fly.ShakeListener.OnShakeListener;
import com.example.GooglePlay.ui.view.fly.StellarMap;
import com.example.GooglePlay.utils.UIUtils;

public class RecommendFragment extends BaseFragment {

	private ArrayList<String> data;
	
	@Override
	public View createSuccessPager() {
		final StellarMap stellarMap = new StellarMap(UIUtils.getContext());
		stellarMap.setAdapter(new RecommendAdapter());
		
		int padding = UIUtils.dip2px(10);
		stellarMap.setInnerPadding(padding, padding, padding, padding);
		//9行6列
		stellarMap.setRegularity(6, 9);
		//设置默认组
		stellarMap.setGroup(0,true);
		
		//摇晃事件监听
		ShakeListener shakeListener = new ShakeListener(UIUtils.getContext());
		shakeListener.setOnShakeListener(new OnShakeListener() {
			
			@Override
			public void onShake() {
				// 摇晃显示下一页
				stellarMap.zoomIn();
			}
		});
		
		return stellarMap;
	}

	@Override
	public ResultState onLoad() {
		RecommendProtocol protocol = new RecommendProtocol();
		data = (ArrayList<String>) protocol.getData(0);
		return check(data);
	}
	
	class RecommendAdapter implements StellarMap.Adapter{

		//数据的页数
		@Override
		public int getGroupCount() {
			
			return 2;
		}

		//每页数据的个数
		@Override
		public int getCount(int group) {
			int count  = data.size() / getGroupCount();
			
			//多余的加入最后一页
			if(group == getGroupCount()-1){
			   return count + data.size()%getGroupCount();
			}
			
			return count;
		}

		@Override
		public View getView(int group, int position, View convertView) {
		   //加上之前几页的数目总数
			position += getCount(group-1) * group; 
			
			TextView textView = new TextView(UIUtils.getContext());
			textView.setText(data.get(position));
			
			Random random = new Random();
			//随机字体 16sp - 25sp
			int size = 16 + random.nextInt(10);
			//随机颜色
			int r = 30 +random.nextInt(210);
			int g = 30 +random.nextInt(210);
			int b = 30 +random.nextInt(210);
			
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
			textView.setTextColor(Color.rgb(r, g, b));
			
			return textView;
		}

		//返回下一页的id
		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn) {
			if(!isZoomIn){
				//下一组
				//向上滑，如果是最后一页，返回到第一页
				if(group >= getGroupCount()-1){
					return 0;
				}else{
					return group +1;
				}
				
			}else{
				//上一组
				//向下滑，如果是第一页，返回到最后一页
				if(group == 0){
					return getGroupCount() -1;
				}else{
					return group -1;
				}
				
			}

		}
		
	}

}
