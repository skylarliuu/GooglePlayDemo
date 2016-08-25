package com.example.GooglePlay.holder;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.GooglePlay.R;
import com.example.GooglePlay.bean.AppInfo;
import com.example.GooglePlay.bean.AppInfo.SafeInfo;
import com.example.GooglePlay.http.HttpHelper;
import com.example.GooglePlay.utils.BitmapHelper;
import com.example.GooglePlay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

public class DetailSafeHolder extends BaseHolder<AppInfo> {

	private ImageView[] mSafeUrl;
	private ImageView[] mSafeDesUrl;
	private TextView[] mSafeDes;
	private LinearLayout[] mlayouts;

	private BitmapUtils bitmapUtils;
	private RelativeLayout rl_root;
	private LinearLayout ll_root;
	private int ll_height;
	private ImageView arrow;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_detail_safe);

		mSafeUrl = new ImageView[4];
		mSafeUrl[0] = (ImageView) view.findViewById(R.id.safeUrl_1);
		mSafeUrl[1] = (ImageView) view.findViewById(R.id.safeUrl_2);
		mSafeUrl[2] = (ImageView) view.findViewById(R.id.safeUrl_3);
		mSafeUrl[3] = (ImageView) view.findViewById(R.id.safeUrl_4);

		mSafeDesUrl = new ImageView[4];
		mSafeDesUrl[0] = (ImageView) view.findViewById(R.id.safeDesUrl_1);
		mSafeDesUrl[1] = (ImageView) view.findViewById(R.id.safeDesUrl_2);
		mSafeDesUrl[2] = (ImageView) view.findViewById(R.id.safeDesUrl_3);
		mSafeDesUrl[3] = (ImageView) view.findViewById(R.id.safeDesUrl_4);

		mSafeDes = new TextView[4];
		mSafeDes[0] = (TextView) view.findViewById(R.id.safeDes_1);
		mSafeDes[1] = (TextView) view.findViewById(R.id.safeDes_2);
		mSafeDes[2] = (TextView) view.findViewById(R.id.safeDes_3);
		mSafeDes[3] = (TextView) view.findViewById(R.id.safeDes_4);

		mlayouts = new LinearLayout[4];
		mlayouts[0] = (LinearLayout) view.findViewById(R.id.ll_des1);
		mlayouts[1] = (LinearLayout) view.findViewById(R.id.ll_des2);
		mlayouts[2] = (LinearLayout) view.findViewById(R.id.ll_des3);
		mlayouts[3] = (LinearLayout) view.findViewById(R.id.ll_des4);

		bitmapUtils = BitmapHelper.getBitmapUtils();

		rl_root = (RelativeLayout) view.findViewById(R.id.rl_root);
		ll_root = (LinearLayout) view.findViewById(R.id.ll_root);

		arrow = (ImageView) view.findViewById(R.id.arrow);
		
		rl_root.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggle();

			}
		});

		return view;
	}

	private boolean isOpen = true;// 是否打开安全信息的标记
	private LinearLayout.LayoutParams params;

	// 点击打开或隐藏安全信息详情
	public void toggle() {
		ValueAnimator animator = null;
		if (isOpen) {
			// 打开安全信息列表
			isOpen = false;
			animator = ValueAnimator.ofInt(0,ll_height);

		} else {
			// 关闭安全信息
			isOpen = true;
			animator = ValueAnimator.ofInt(ll_height,0);
		}

		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				// 将属性动画时刻改变的值赋给布局的高度，动态的将布局隐藏
				Integer height = (Integer) animator.getAnimatedValue();
				params.height = height;
                ll_root.setLayoutParams(params);
			}
		});
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
				if(isOpen){
					//箭头向下
					arrow.setImageResource(R.drawable.arrow_down);
				}else{
					//箭头向上
					arrow.setImageResource(R.drawable.arrow_up);
				}
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				
			}
		});
	}

	@Override
	public void onRefresh(AppInfo data) {
		if (data != null) {
			ArrayList<SafeInfo> safeInfo = data.SafeInfo;
			int size = safeInfo.size();

			for (int i = 0; i < 4; i++) {

				if (i < size) {
					SafeInfo info = safeInfo.get(i);
					if (info != null) {
						// 该位置有数据
						bitmapUtils.display(mSafeUrl[i], HttpHelper.URL
								+ "image?name=" + info.safeUrl);
						bitmapUtils.display(mSafeDesUrl[i], HttpHelper.URL
								+ "image?name=" + info.safeDesUrl);
						mSafeDes[i].setText(info.safeDes);

					}
				} else {
					// 该位置没有数据，隐藏布局
					mSafeUrl[i].setVisibility(View.GONE);
					mlayouts[i].setVisibility(View.GONE);
				}

			}

		}
		
		ll_root.measure(0, 0);
		ll_height = ll_root.getMeasuredHeight();
		
		params = (LinearLayout.LayoutParams) ll_root.getLayoutParams();		
		// 隐藏安全信息
		params.height = 0;
		ll_root.setLayoutParams(params);

	}

}
