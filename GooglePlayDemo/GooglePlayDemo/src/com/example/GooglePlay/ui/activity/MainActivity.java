package com.example.GooglePlay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.example.GooglePlay.R;
import com.example.GooglePlay.ui.fragment.BaseFragment;
import com.example.GooglePlay.ui.view.PagerTab;
import com.example.GooglePlay.utils.FragmentFactory;
import com.example.GooglePlay.utils.UIUtils;

public class MainActivity extends BaseActivity {

	private PagerTab mPagerTab;
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPagerTab = (PagerTab) findViewById(R.id.pagerTab);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);

		mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		//viewpager要先设置适配器填充数据，指示器才能绑定viewpager
		mPagerTab.setViewPager(mViewPager);
		
		mPagerTab.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				//页面选中时
				BaseFragment fragment = FragmentFactory.createFragment(position);
				//加载数据
				fragment.loadData();
				Log.i("---------positon:-----",position+"");
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
								
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
								
			}
		});

	}

	
		

	//如果填充的是fragment，则要继承FragmentPagerAdapter
	class MyPagerAdapter extends FragmentPagerAdapter {

		private String[] mTabNames;

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			mTabNames = UIUtils.getStringArray(R.array.tab_names);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mTabNames[position];
		}

		
		//初始化的首页怎么加载数据？？不是要切换页面才会调用onPageSelected加载数据么？
		@Override
		public Fragment getItem(int position) {
			BaseFragment fragment = FragmentFactory.createFragment(position);
			
			return fragment;
		}

		@Override
		public int getCount() {
			return mTabNames.length;
		}

	}
}
