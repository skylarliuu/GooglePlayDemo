package com.example.GooglePlay.holder;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.example.GooglePlay.R;
import com.example.GooglePlay.bean.CategoryInfo;
import com.example.GooglePlay.utils.UIUtils;

public class TitleHolder extends BaseHolder<CategoryInfo> {

	private TextView view;

	@Override
	public View initView() {
		view = new TextView(UIUtils.getContext());
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		view.setTextColor(Color.BLACK);
		view.setBackgroundColor(Color.TRANSPARENT);
		
		return view;
	}

	@Override
	public void onRefresh(CategoryInfo data) {
		if(data !=null){
			view.setText(data.title);
		}
	}

}
