package com.example.GooglePlay.ui.view;

import com.example.GooglePlay.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**自定义framelayout，让framelayout的宽跟屏幕一样宽，宽高比例与图片比例一致
 * @author Administrator
 *
 */
public class RatioLayout extends FrameLayout {

	private float ratio;

	public RatioLayout(Context context) {
		this(context,null);
	}


	public RatioLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public RatioLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	
		//加载自定义属性的数组
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
		//获取自定义属性的值	
		ratio = typedArray.getFloat(R.styleable.RatioLayout_raion, 0);
		//回收typedArray,释放内存
		typedArray.recycle();
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);	
		
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		if(widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && ratio!= 0){
			//图片的宽度，根据图片的宽度和宽高比算出图片的高度，然后再加上padding，得到framelayout的高度
			int imageWidth = widthSize - getPaddingLeft() - getPaddingRight();
			int imageHeight = (int) (imageWidth / ratio);
			
		    heightSize = imageHeight + getPaddingBottom() + getPaddingTop();
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);

		}
		
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
