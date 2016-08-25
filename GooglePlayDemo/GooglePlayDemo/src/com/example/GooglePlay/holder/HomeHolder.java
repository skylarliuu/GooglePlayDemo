package com.example.GooglePlay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.GooglePlay.R;
import com.example.GooglePlay.bean.AppInfo;
import com.example.GooglePlay.http.HttpHelper;
import com.example.GooglePlay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

public class HomeHolder extends BaseHolder<AppInfo> {

	private TextView tv_name;
	private TextView tv_size;
	private TextView tv_des;
	private RatingBar rb_star;
	private ImageView iv_icon;
	private BitmapUtils bitmapUtils;
	private FrameLayout fl_progress;

	@Override
	public View initView() {
		//加载布局，findviewbyid
		View view = UIUtils.inflate(R.layout.lv_item_home);
		
		iv_icon = (ImageView)view.findViewById(R.id.iv_icon);
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		tv_size = (TextView) view.findViewById(R.id.tv_size);
		tv_des = (TextView) view.findViewById(R.id.tv_des);
		rb_star = (RatingBar)view.findViewById(R.id.rb_star);
		fl_progress = (FrameLayout) view.findViewById(R.id.fl_progress);
		
		//bitmapUtils = BitmapHelper.getBitmapUtils();
		bitmapUtils = new BitmapUtils(UIUtils.getContext());
		
		return view;
	}

	@Override
	public void onRefresh(AppInfo data) {
			tv_name.setText(data.name);
			tv_des.setText(data.des);
			tv_size.setText(Formatter.formatFileSize(UIUtils.getContext(),
					data.size));
			rb_star.setRating(data.stars);
			
			bitmapUtils.display(iv_icon, HttpHelper.URL + "image?name="
					+ data.iconUrl);
		
	}

}
