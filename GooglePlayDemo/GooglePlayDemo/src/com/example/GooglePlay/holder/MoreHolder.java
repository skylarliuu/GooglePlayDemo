package com.example.GooglePlay.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.GooglePlay.R;
import com.example.GooglePlay.utils.UIUtils;

public class MoreHolder extends BaseHolder<Integer> {

	private LinearLayout ll_load_more;
	private TextView tv_load_error;

	// 加载更多的三种状态：可以加载更多、加载错误、没有更多数据可以加载
	public static final int STATE_MORE_MORE = 0;
	public static final int STATE_MORE_ERROR = 1;
	public static final int STATE_MORE_NONE = 2;

	public MoreHolder(boolean hasMore) {
		//根据是否还有更多数据状态去设置数据刷新状态
		setData(hasMore ? STATE_MORE_MORE : STATE_MORE_NONE);
	}

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.lv_item_load_more);

		ll_load_more = (LinearLayout) view.findViewById(R.id.ll_load_more);
		tv_load_error = (TextView) view.findViewById(R.id.tv_load_error);

		return view;
	}

	@Override
	public void onRefresh(Integer data) {
		switch (data) {
		case STATE_MORE_MORE:
			ll_load_more.setVisibility(View.VISIBLE);
			tv_load_error.setVisibility(View.GONE);
			break;
		case STATE_MORE_NONE:
			ll_load_more.setVisibility(View.GONE);
			tv_load_error.setVisibility(View.GONE);
			break;
		case STATE_MORE_ERROR:
			ll_load_more.setVisibility(View.GONE);
			tv_load_error.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}

	}

}
