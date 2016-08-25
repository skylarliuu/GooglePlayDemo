package com.example.GooglePlay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.GooglePlay.R;
import com.example.GooglePlay.bean.AppInfo;
import com.example.GooglePlay.http.HttpHelper;
import com.example.GooglePlay.utils.BitmapHelper;
import com.example.GooglePlay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

//应用详情界面--应用信息
public class DetailAppInfoHolder extends BaseHolder<AppInfo> {

	private TextView tv_name,tv_downloadNum,tv_version,tv_size,tv_date;
	private ImageView iv_icon;
	private RatingBar rb_star;
	private BitmapUtils bitmapUtils;
	
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_detail_info);
		
		iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		rb_star = (RatingBar) view.findViewById(R.id.rb_star);
		tv_downloadNum = (TextView) view.findViewById(R.id.tv_downloadNum);
		tv_version = (TextView) view.findViewById(R.id.tv_version);
		tv_size = (TextView) view.findViewById(R.id.tv_size);
		tv_date = (TextView) view.findViewById(R.id.tv_date);

		bitmapUtils = BitmapHelper.getBitmapUtils();
		
		return view;
	}

	@Override
	public void onRefresh(AppInfo data) {
		if (data != null) {
			tv_date.setText(data.date);
			tv_downloadNum.setText("下载量：" + data.downloadNum);
			tv_name.setText(data.name);
			tv_size.setText(Formatter.formatFileSize(UIUtils.getContext(),
					data.size));
			tv_version.setText("版本：" + data.version);
			rb_star.setRating(data.stars);
			bitmapUtils.display(iv_icon, HttpHelper.URL + "image?name="
					+ data.iconUrl);
		}
	}

}
