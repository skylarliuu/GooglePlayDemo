package com.example.GooglePlay.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.GooglePlay.R;
import com.example.GooglePlay.manager.ThreadManager;
import com.example.GooglePlay.utils.UIUtils;

/**
 * 根据加载数据的结果决定当前状态 根据当前状态决定帧布局中显示哪个页面
 * 
 * @author Administrator 未加载-加载中-加载失败-加载为空-加载成功
 */
public abstract class PagerFrameLayout extends FrameLayout {

	private static final int STATE_UNLOAD = 0;
	private static final int STATE_LOADING = 1;
	private static final int STATE_ERROR = 2;
	private static final int STATE_EMPTY = 3;
	private static final int STATE_SUCCESS = 4;

	private int mCurrentSate = STATE_UNLOAD;

	private View mLoadingPage;
	private View mErrorPage;
	private View mEmptyPage;
	private View mSuccessPage;

	public PagerFrameLayout(Context context) {
		this(context, null);
	}

	public PagerFrameLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PagerFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initView();
	}

	private void initView() {

		if (mLoadingPage == null) {
			mLoadingPage = UIUtils.inflate(R.layout.pager_loading);
			addView(mLoadingPage);
		}
		if (mErrorPage == null) {
			mErrorPage = UIUtils.inflate(R.layout.pager_error);
			addView(mErrorPage);

			Button btn_loading_again = (Button) mErrorPage
					.findViewById(R.id.btn_loading_again);
			btn_loading_again.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 重新加载数据
					loadData();
				}
			});
		}
		if (mEmptyPage == null) {
			mEmptyPage = UIUtils.inflate(R.layout.pager_empty);
			addView(mEmptyPage);
		}

		showCurrentPage();
	}

	private void showCurrentPage() {

		if (mCurrentSate == STATE_UNLOAD || mCurrentSate == STATE_LOADING) {
			mLoadingPage.setVisibility(View.VISIBLE);
		} else {
			mLoadingPage.setVisibility(View.GONE);
		}

		mEmptyPage.setVisibility(mCurrentSate == STATE_EMPTY ? View.VISIBLE
				: View.GONE);
		mErrorPage.setVisibility(mCurrentSate == STATE_ERROR ? View.VISIBLE
				: View.GONE);

		// 加载成功页面为空，且当前状态是加载成功状态，则初始化加载成功页面
		if (mSuccessPage == null && mCurrentSate == STATE_SUCCESS) {
			mSuccessPage = createSuccessPager();
			if (mSuccessPage != null) {
				addView(mSuccessPage);
			}
		}

		// 初始化后也可能为空
		if (mSuccessPage != null) {
			mSuccessPage
					.setVisibility(mCurrentSate == STATE_SUCCESS ? View.VISIBLE
							: View.GONE);
		}

	}

	public void loadData() {
		if (mCurrentSate != STATE_LOADING) {
			mCurrentSate = STATE_LOADING;
			// 所以其实正在加载的过程并没有真正用正在加载中的页面显示出来，而是在加载完成前都是显示的是未加载的页面，
			// 而未加载和正在加载中又是同一个页面，所以不需要在正在加载的时候更新页面了
//			new Thread() {
//				public void run() {
//					// 加载网络数据，返回结果状态，根据状态决定显示哪个页面
//
//					ResultState resultState = onLoad();
//					if (resultState != null) {
//
//						mCurrentSate = resultState.getState();
//						// 刷新页面,要在主线程中更新
//						UIUtils.runOnUIThread(new Runnable() {
//
//							@Override
//							public void run() {
//								showCurrentPage();
//							}
//						});
//
//					}
//				};
//			}.start();

			ThreadManager.getThreadPool().execute(new Runnable() {

				@Override
				public void run() {

					// 加载网络数据，返回结果状态，根据状态决定显示哪个页面

					ResultState resultState = onLoad();
					if (resultState != null) {

						mCurrentSate = resultState.getState();
						// 刷新页面,要在主线程中更新
						UIUtils.runOnUIThread(new Runnable() {

							@Override
							public void run() {
								showCurrentPage();
							}
						});

					}

				}
			});
		}
	}

	// 加载成功的布局各不相同，所以由装载framelayout的basefragment去实现
	public abstract View createSuccessPager();

	// 加载网络数据由其调用者实现
	public abstract ResultState onLoad();

	/**
	 * 加载网络后返回的状态 枚举值-相当于把对象（ERROR）与传入的参数（STATE_ERROR）绑定
	 * 
	 * @author Administrator
	 * 
	 */
	public enum ResultState {
		ERROR(STATE_ERROR), EMPTY(STATE_EMPTY), SUCCESS(STATE_SUCCESS);

		private int state;

		private ResultState(int state) {
			this.state = state;
		}

		// 获取对象的state值
		public int getState() {
			return state;
		}
	}

}
