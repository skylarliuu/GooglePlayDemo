package com.example.GooglePlay.holder;

import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.GooglePlay.R;
import com.example.GooglePlay.bean.AppInfo;
import com.example.GooglePlay.utils.UIUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

public class DetailDesHolder extends BaseHolder<AppInfo> {

	private TextView tv_des, tv_author;
	private RelativeLayout rl_root;
	private ImageView iv_arrow;
	private LinearLayout.LayoutParams mParams;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_detail_des);

		tv_des = (TextView) view.findViewById(R.id.tv_des);
		rl_root = (RelativeLayout) view.findViewById(R.id.rl_root);
		tv_author = (TextView) view.findViewById(R.id.tv_author);
		iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);

		iv_arrow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggle();

			}
		});

		return view;
	}

	private boolean isOpen = true;

	protected void toggle() {
		int shortHeight = getShortHeight();
		int longHeight = getLongHeight();

		ValueAnimator animator = null;
		if (isOpen) {
			// 打开安全信息列表
			isOpen = false;
			if (shortHeight < longHeight) {
				animator = ValueAnimator.ofInt(shortHeight, longHeight);
			}
		} else {
			// 关闭安全信息
			isOpen = true;
			if (shortHeight < longHeight) {
				animator = ValueAnimator.ofInt(longHeight, shortHeight);
			}
		}

		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				// 将属性动画时刻改变的值赋给布局的高度，动态的将布局隐藏
				Integer height = (Integer) animator.getAnimatedValue();
				mParams.height = height;
				tv_des.setLayoutParams(mParams);
			}
		});
		if (animator != null) {
			animator.setDuration(200);
			animator.start();
			animator.addListener(new AnimatorListener() {

				@Override
				public void onAnimationStart(Animator arg0) {

				}

				@Override
				public void onAnimationRepeat(Animator arg0) {

				}

				@Override
				public void onAnimationEnd(Animator arg0) {
					// 滑动ScrollView最底端
					final ScrollView scrollView = getScrollView();
					//在子线程中可以避免view还没加进ScrollView时就滑到底端的问题
					scrollView.post(new Runnable() {

						@Override
						public void run() {
							scrollView.fullScroll(ScrollView.FOCUS_DOWN);

						}
					});

					if (isOpen) {
						// 箭头向下
						iv_arrow.setImageResource(R.drawable.arrow_down);
					} else {
						// 箭头向上
						iv_arrow.setImageResource(R.drawable.arrow_up);
					}
				}

				@Override
				public void onAnimationCancel(Animator arg0) {

				}
			});
		}

	}

	@Override
	public void onRefresh(AppInfo data) {

		if (data != null) {
			tv_author.setText(data.author);
			tv_des.setText(data.des);

			//在子线程中可以避免出现描述不到七行出现空白的问题
			tv_des.post(new Runnable() {
				public void run() {
					mParams = (LinearLayout.LayoutParams) tv_des
							.getLayoutParams();
					mParams.height = getShortHeight();
					tv_des.setLayoutParams(mParams);
				}
			});
		}

	}

	// 模拟描述文字，获取最大七行描述文字的高度
	private int getShortHeight() {
		TextView textView = new TextView(UIUtils.getContext());
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		textView.setText(getData().des);
		textView.setMaxLines(7);

		int width = tv_des.getMeasuredWidth();
		// 宽填充屏幕
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.EXACTLY);
		// 高包裹内容，最大值为2000
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(2000,
				MeasureSpec.AT_MOST);

		textView.measure(widthMeasureSpec, heightMeasureSpec);
		return textView.getMeasuredHeight();
	}

	private int getLongHeight() {
		TextView textView = new TextView(UIUtils.getContext());
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		textView.setText(getData().des);
		// textView.setMaxLines(7);

		int width = tv_des.getMeasuredWidth();
		// 宽填充屏幕
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.EXACTLY);
		// 高包裹内容，最大值为2000
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(2000,
				MeasureSpec.AT_MOST);

		textView.measure(widthMeasureSpec, heightMeasureSpec);
		return textView.getMeasuredHeight();
	}

	private ScrollView getScrollView() {
		ViewParent parent = tv_des.getParent();
		// 循环遍历找到ScrollView为止
		while (!(parent instanceof ScrollView)) {
			parent = parent.getParent();
		}

		return (ScrollView) parent;
	}

}
