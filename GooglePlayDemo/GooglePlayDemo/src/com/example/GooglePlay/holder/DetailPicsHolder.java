package com.example.GooglePlay.holder;

import java.util.ArrayList;

import android.view.View;
import android.widget.ImageView;

import com.example.GooglePlay.R;
import com.example.GooglePlay.bean.AppInfo;
import com.example.GooglePlay.bean.AppInfo.SafeInfo;
import com.example.GooglePlay.http.HttpHelper;
import com.example.GooglePlay.utils.BitmapHelper;
import com.example.GooglePlay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

public class DetailPicsHolder extends BaseHolder<AppInfo> {

	private ImageView[] mPics;
	private BitmapUtils bitmapUtils;
	
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_detail_pics);
		
		mPics = new ImageView[5];
		
		mPics[0] = (ImageView) view.findViewById(R.id.pic_1);
		mPics[1] = (ImageView) view.findViewById(R.id.pic_2);
		mPics[2] = (ImageView) view.findViewById(R.id.pic_3);
		mPics[3] = (ImageView) view.findViewById(R.id.pic_4);
		mPics[4] = (ImageView) view.findViewById(R.id.pic_5);
		
		bitmapUtils = BitmapHelper.getBitmapUtils();
		
		return view;
	}
	
	@Override
	public void onRefresh(AppInfo data) {
		if(data != null){
			ArrayList<String> screen = data.screen;
			
			for(int i=0;i<5;i++){
				if(i<screen.size()){
					//显示
					bitmapUtils.display(mPics[i], HttpHelper.URL + "image?name=" + screen.get(i));
					
				}else{
					//隐藏掉剩余的
					mPics[i].setVisibility(View.GONE);
				}
				
			}
		}
		
	}

}
