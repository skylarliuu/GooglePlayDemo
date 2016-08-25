package com.example.GooglePlay.ui.fragment;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.GooglePlay.http.protocol.HotProtocol;
import com.example.GooglePlay.ui.view.FlowLayout;
import com.example.GooglePlay.ui.view.PagerFrameLayout.ResultState;
import com.example.GooglePlay.utils.DrawableUtils;
import com.example.GooglePlay.utils.UIUtils;

public class HotFragment extends BaseFragment {

	private ArrayList<String> data;
	
	@Override
	public View createSuccessPager() {
		ScrollView scrollView = new ScrollView(UIUtils.getContext());
		int padding = UIUtils.dip2px(10);
		scrollView.setPadding(padding, padding, padding, padding);
		
		FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
		
		scrollView.addView(flowLayout);	
		
		for(final String str:data){
			TextView textView = new TextView(UIUtils.getContext());
			textView.setText(str);
			textView.setGravity(Gravity.CENTER);
			textView.setPadding(padding, padding, padding, padding);
			
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		
			Random random = new Random();
			//随机颜色
			int r = 30 +random.nextInt(210);
			int g = 30 +random.nextInt(210);
			int b = 30 +random.nextInt(210);
			
			int color = 0xffcecece;// 按下后偏白的背景色
			StateListDrawable selector = DrawableUtils.getSelector(Color.rgb(r, g, b), color, UIUtils.dip2px(6));
		
			textView.setBackgroundDrawable(selector);
			
			textView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(UIUtils.getContext(), str, Toast.LENGTH_SHORT).show();
					
				}
			});
		
			flowLayout.addView(textView);
		}
		
		return scrollView;
	}

	@Override
	public ResultState onLoad() {
		HotProtocol protocol = new HotProtocol();
		data = (ArrayList<String>) protocol.getData(0);		
		return check(data);
	}
	
}
