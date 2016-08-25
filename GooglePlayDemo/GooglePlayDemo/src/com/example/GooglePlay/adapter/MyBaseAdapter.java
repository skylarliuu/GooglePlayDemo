package com.example.GooglePlay.adapter;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.GooglePlay.holder.BaseHolder;
import com.example.GooglePlay.holder.MoreHolder;
import com.example.GooglePlay.manager.ThreadManager;
import com.example.GooglePlay.utils.UIUtils;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	// 一定要从0开始
	private static final int TYPE_NOMAL = 1;
	private static final int TYPE_MORE = 0;
	protected static final String tag = "MyBaseAdapter";

	private ArrayList<T> data;

	public MyBaseAdapter(ArrayList<T> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size() + 1;
	}

	@Override
	public T getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 2;// 两种item布局
	}

	@Override
	public int getItemViewType(int position) {
		if (position == getCount() - 1) {
			return TYPE_MORE;
		} else {
			return getItemType(position);
		}
	}

	// 默认是正常类型，如果不是子类可以重写这个方法
	public int getItemType(int position) {
		return TYPE_NOMAL;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseHolder holder;
		if (convertView == null) {
			// 1.加载布局
			// 2.findviewbyid
			// 3.打标记
			if (getItemViewType(position) == TYPE_MORE) {

				holder = new MoreHolder(hasMore());
			} else {

				holder = getHolder(position); // 由子类去继承BaseHolder,实现抽象方法
			}
		} else {
			holder = (BaseHolder) convertView.getTag();
		}

		if (holder != null) {
			if (getItemViewType(position) != TYPE_MORE) {
				// 根据数据刷新界面
				holder.setData(getItem(position));
			} else {
				// 此时是加载更多，不需要设置数据，只需要加载数据
				MoreHolder moreHolder = (MoreHolder) holder;

				if (moreHolder.getData() == MoreHolder.STATE_MORE_MORE) {
					// 有更多数据才去加载
					loadMore(moreHolder);
				}
			}
		}
		return holder.mRootView;
	}

	// 是否还有更多数据，默认为有更多数据
	public boolean hasMore() {
		return true;
	}

	// 让子类去重新改方法，决定返回哪种类型的holder
	public abstract BaseHolder getHolder(int position);

	private boolean isLoadMore = false;// 加载更多的标志

	private void loadMore(final MoreHolder holder) {
		if (!isLoadMore) {
			isLoadMore = true;
//			new Thread() {
//				public void run() {
//					final ArrayList<T> moreData = onLoad();
//					Log.i(tag, "moreData:--------" + moreData);
//					// setData更新UI了
//					UIUtils.runOnUIThread(new Runnable() {
//						@Override
//						public void run() {
//							if (moreData != null) {
//								// 加载成功
//								if (moreData.size() < 20) {
//									// 如果加载的数据少于20条，则认为没有更多数据了
//									holder.setData(MoreHolder.STATE_MORE_NONE);
//								} else {
//									// 还有更多数据可以加载
//									holder.setData(MoreHolder.STATE_MORE_MORE);
//								}
//
//								// 把加载的数据加入集合
//								data.addAll(moreData);
//								// 刷新界面
//								MyBaseAdapter.this.notifyDataSetChanged();
//
//							} else {
//								// 加载失败
//								holder.setData(MoreHolder.STATE_MORE_ERROR);
//							}
//
//							isLoadMore = false;
//						}
//					});
//
//				};
//			}.start();

			ThreadManager.getThreadPool().execute(new Runnable() {

				@Override
				public void run() {

					final ArrayList<T> moreData = onLoad();
					Log.i(tag, "moreData:--------" + moreData);
					// setData更新UI了
					UIUtils.runOnUIThread(new Runnable() {
						@Override
						public void run() {
							if (moreData != null) {
								// 加载成功
								if (moreData.size() < 20) {
									// 如果加载的数据少于20条，则认为没有更多数据了
									holder.setData(MoreHolder.STATE_MORE_NONE);
								} else {
									// 还有更多数据可以加载
									holder.setData(MoreHolder.STATE_MORE_MORE);
								}

								// 把加载的数据加入集合
								data.addAll(moreData);
								// 刷新界面
								MyBaseAdapter.this.notifyDataSetChanged();

							} else {
								// 加载失败
								holder.setData(MoreHolder.STATE_MORE_ERROR);
							}

							isLoadMore = false;
						}
					});

				}
			});
		}
	}

	// 让子类去实现具体的加载更多的方法，在子线程中运行
	public abstract ArrayList<T> onLoad();

	// 获取当前数据集合的大小
	public int getListSize() {
		return data.size();
	}

}
