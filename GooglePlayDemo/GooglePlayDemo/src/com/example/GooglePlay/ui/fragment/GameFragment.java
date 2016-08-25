package com.example.GooglePlay.ui.fragment;

import android.view.View;

import com.example.GooglePlay.ui.view.PagerFrameLayout.ResultState;

public class GameFragment extends BaseFragment {

	@Override
	public View createSuccessPager() {
		
		return null;
	}

	@Override
	public ResultState onLoad() {
		return ResultState.EMPTY;
	}

}
