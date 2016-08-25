package com.example.GooglePlay.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.GooglePlay.ui.fragment.AppFragment;
import com.example.GooglePlay.ui.fragment.BaseFragment;
import com.example.GooglePlay.ui.fragment.CategoryFragment;
import com.example.GooglePlay.ui.fragment.GameFragment;
import com.example.GooglePlay.ui.fragment.HomeFragment;
import com.example.GooglePlay.ui.fragment.HotFragment;
import com.example.GooglePlay.ui.fragment.RecommendFragment;
import com.example.GooglePlay.ui.fragment.SubjectFragment;

public class FragmentFactory {
	//不能用list,当还没有数据填充时，不能取第一位数据
	//List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();

	private static HashMap<Integer,BaseFragment> fragmentMap = new HashMap<Integer, BaseFragment>();
	
	public static BaseFragment createFragment(int position) {
		BaseFragment fragment = fragmentMap.get(position);

		if (fragment == null) {
			switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new AppFragment();
				break;
			case 2:
				fragment = new GameFragment();
				break;
			case 3:
				fragment = new SubjectFragment();
				break;
			case 4:
				fragment = new RecommendFragment();
				break;
			case 5:
				fragment = new CategoryFragment();
				break;
			case 6:
				fragment = new HotFragment();
				break;

			}
			fragmentMap.put(position, fragment);
		}

		return fragment;
	}
}
