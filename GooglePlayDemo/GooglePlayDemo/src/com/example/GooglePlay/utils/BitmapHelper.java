package com.example.GooglePlay.utils;



import com.example.GooglePlay.R;
import com.lidroid.xutils.BitmapUtils;

/**单例模式生成bitmapUtils
 * @author Administrator
 *
 */
public class BitmapHelper {
	
	private static BitmapUtils mBitmapUtils = null;

	public static BitmapUtils getBitmapUtils() {
		if (mBitmapUtils == null) {
			mBitmapUtils = new BitmapUtils(UIUtils.getContext());
		}

		//默认加载图片是一张空图
		mBitmapUtils.configDefaultLoadingImage(R.drawable.nothing);
		return mBitmapUtils;
	}
	
}
