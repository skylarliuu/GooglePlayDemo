package com.example.GooglePlay.holder;

import com.example.GooglePlay.R;
import com.example.GooglePlay.bean.CategoryInfo;
import com.example.GooglePlay.http.HttpHelper;
import com.example.GooglePlay.utils.BitmapHelper;
import com.example.GooglePlay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryHolder extends BaseHolder<CategoryInfo> implements
		OnClickListener {

	private ImageView iv1, iv2, iv3;
	private TextView tv1, tv2, tv3;
	private LinearLayout ll1, ll2, ll3;
	private BitmapUtils bitmapUtils;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.lv_item_category);

		iv1 = (ImageView) view.findViewById(R.id.iv1);
		iv2 = (ImageView) view.findViewById(R.id.iv2);
		iv3 = (ImageView) view.findViewById(R.id.iv3);
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);
		tv3 = (TextView) view.findViewById(R.id.tv3);
		ll1 = (LinearLayout) view.findViewById(R.id.ll1);
		ll2 = (LinearLayout) view.findViewById(R.id.ll2);
		ll3 = (LinearLayout) view.findViewById(R.id.ll3);

		bitmapUtils = BitmapHelper.getBitmapUtils();
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);

		ll1.setOnClickListener(this);
		ll2.setOnClickListener(this);
		ll3.setOnClickListener(this);

		return view;
	}

	@Override
	public void onRefresh(CategoryInfo data) {
		if (data != null) {
			bitmapUtils
					.display(iv1, HttpHelper.URL + "image?name=" + data.url1);
			bitmapUtils
					.display(iv2, HttpHelper.URL + "image?name=" + data.url2);
			bitmapUtils
					.display(iv3, HttpHelper.URL + "image?name=" + data.url3);

			tv1.setText(data.name1);
			tv2.setText(data.name2);
			tv3.setText(data.name3);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll1:
            Toast.makeText(UIUtils.getContext(), data.name1, Toast.LENGTH_SHORT).show();
			break;
		case R.id.ll2:
			Toast.makeText(UIUtils.getContext(), data.name2, Toast.LENGTH_SHORT).show();
			break;
		case R.id.ll3:
			Toast.makeText(UIUtils.getContext(), data.name3, Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

}
