package com.example.GooglePlay.holder;

import java.util.ArrayList;

import android.content.IntentSender.SendIntentException;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.GooglePlay.R;
import com.example.GooglePlay.http.HttpHelper;
import com.example.GooglePlay.utils.BitmapHelper;
import com.example.GooglePlay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

public class HomeHeaderHolder extends BaseHolder<ArrayList<String>> {

	private ArrayList<String> mPictureList;
	private ViewPager viewPager;
	private LinearLayout ll_indicator;
	private ImageView pointView;
	private ArrayList<ImageView> mPointList;
	private int mPreviousPos;

	@Override
	public View initView() {
		RelativeLayout rl_root = new RelativeLayout(UIUtils.getContext());
		// 找相对布局的父布局listview设置布局参数
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(
				LayoutParams.MATCH_PARENT, UIUtils.dip2px(150));
		rl_root.setLayoutParams(params);

		// 向相对布局中添加viewpager
		viewPager = new ViewPager(UIUtils.getContext());
		RelativeLayout.LayoutParams vpParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		rl_root.addView(viewPager, vpParams);

		// 添加容纳指示器的布局
		ll_indicator = new LinearLayout(UIUtils.getContext());
		RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		int padding = UIUtils.dip2px(5);
		ll_indicator.setPadding(padding, padding, padding, padding);
		// 设置线性布局的位置
		llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rl_root.addView(ll_indicator, llParams);

		return rl_root;
	}

	@Override
	public void onRefresh(ArrayList<String> data) {
		this.mPictureList = data;

		// 设置数据适配器
		viewPager.setAdapter(new HomeHeaderAdapter());
		
		viewPager.setCurrentItem(1000 * mPictureList.size());

		//存放小圆点的集合
		mPointList = new ArrayList<ImageView>();
		// 动态添加指示器小圆点
		for (int i = 0; i < mPictureList.size(); i++) {
			pointView = new ImageView(UIUtils.getContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				 LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			
			if (i == 0) {
				pointView.setImageResource(R.drawable.indicator_selected);
			} else {
				params.leftMargin = UIUtils.dip2px(4);
				pointView.setImageResource(R.drawable.indicator_normal);
			}

			mPointList.add(pointView);
			ll_indicator.addView(pointView, params);
		}
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				position = position % mPictureList.size();
				
				mPointList.get(position).setImageResource(R.drawable.indicator_selected);
				mPointList.get(mPreviousPos).setImageResource(R.drawable.indicator_normal);
			
				mPreviousPos = position;
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		
		//自动轮播
		HomeHeaderRunnable runnable  = new HomeHeaderRunnable();
		runnable.start();
	}
	
	class HomeHeaderRunnable implements Runnable{

		private Handler handler;

		public void start(){
			handler = UIUtils.getHandler();
			handler.removeCallbacksAndMessages(null);//移除已有的消息
			handler.postDelayed(this, 3000);//调用run方法
		}
		
		@Override
		public void run() {
			int currentItem = viewPager.getCurrentItem();
			++currentItem;
			viewPager.setCurrentItem(currentItem);
			
			handler.postDelayed(this, 3000);//调用run方法
		}
		
	}

	class HomeHeaderAdapter extends PagerAdapter {

		private BitmapUtils bitmapUtils;

		public HomeHeaderAdapter() {
			bitmapUtils = BitmapHelper.getBitmapUtils();
		}

		@Override
		public int getCount() {
			//return mPictureList.size();
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			position = position % mPictureList.size();
			
			ImageView imageView = new ImageView(UIUtils.getContext());
			imageView.setScaleType(ScaleType.FIT_XY);
			String url = mPictureList.get(position);
			bitmapUtils
					.display(imageView, HttpHelper.URL + "image?name=" + url);

			container.addView(imageView);

			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

}
