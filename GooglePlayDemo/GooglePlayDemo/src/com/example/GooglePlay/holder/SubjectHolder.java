package com.example.GooglePlay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.GooglePlay.R;
import com.example.GooglePlay.bean.SubjectInfo;
import com.example.GooglePlay.http.HttpHelper;
import com.example.GooglePlay.utils.BitmapHelper;
import com.example.GooglePlay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

public class SubjectHolder extends BaseHolder<SubjectInfo> {

	private ImageView iv_pic;
	private TextView tv_des;
	private BitmapUtils bitmapUtils;
	
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.iv_item_subject);
		
		iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
		tv_des = (TextView) view.findViewById(R.id.tv_des);
		
		bitmapUtils = BitmapHelper.getBitmapUtils();
		bitmapUtils.configDefaultLoadingImage(R.drawable.subject_default);
		
		return view;
	}

	@Override
	public void onRefresh(SubjectInfo data) {

		if(data != null){
			tv_des.setText(data.des);
			bitmapUtils.display(iv_pic, HttpHelper.URL + "image?name=" + data.url);
		}
	}

}
