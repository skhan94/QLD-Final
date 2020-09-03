package com.fsd.logodesign.methods;

import android.content.Context;
import android.support.v4.view.PagerTitleStrip;
import android.util.AttributeSet;

public class CustomFontPagerTitleStrip extends PagerTitleStrip {
	//Moved typeface instantiation here, only required once.



	public CustomFontPagerTitleStrip(Context context) {
		super(context);

	}
	public CustomFontPagerTitleStrip(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
//	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		super.onLayout(changed, l, t, r, b);
//		if(!isInEditMode()){
//			Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/playball.ttf");
//			for (int i=0; i<this.getChildCount(); i++) {
//				if (this.getChildAt(i) instanceof TextView) {
//					((TextView)this.getChildAt(i)).setTypeface(tf);
//				}
//			}
//		}
//	}
}