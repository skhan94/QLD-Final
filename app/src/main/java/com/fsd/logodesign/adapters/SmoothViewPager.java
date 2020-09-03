package com.fsd.logodesign.adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.Scroller;
import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import java.lang.reflect.Field;


public class SmoothViewPager extends ViewPager {


	private boolean disable =true;

	public SmoothViewPager(Context context) {
		super(context);
		postInitViewPager();
	}

	public SmoothViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		postInitViewPager();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// Never allow swiping to switch between pages
		if(disable){
			return super.onInterceptTouchEvent(event);
		}else{
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Never allow swiping to switch between pages
		if(disable){
			return super.onTouchEvent(event);
		}else{
			return false;
		}
	}
	public void setSwipeAble(boolean value){
		this.disable =value;
	}  

	public boolean getSwipwAble(){
	return this.disable;
	}
	private ScrollerCustomDuration mScroller = null;

	/**
	 * Override the Scroller instance with our own class so we can change the
	 * duration
	 */
	private void postInitViewPager() {
		try {
			Field scroller = ViewPager.class.getDeclaredField("mScroller");
			scroller.setAccessible(true);
			Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
			interpolator.setAccessible(true);

			mScroller = new ScrollerCustomDuration(getContext(),
					(Interpolator) interpolator.get(null));
			scroller.set(this, mScroller);
		} catch (Exception e) {
		}
	}




	/**
	 * Set the factor by which the duration will change
	 */
	public void setScrollDurationFactor(double scrollFactor) {
		mScroller.setScrollDurationFactor(scrollFactor);
	}


	public class ScrollerCustomDuration extends Scroller {

		private double mScrollFactor = 2;

		public ScrollerCustomDuration(Context context) {
			super(context);
		}

		public ScrollerCustomDuration(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		@SuppressLint("NewApi")
		public ScrollerCustomDuration(Context context, Interpolator interpolator, boolean flywheel) {
			super(context, interpolator, flywheel);
		}

		/**
		 * Set the factor by which the duration will change
		 */
		public void setScrollDurationFactor(double scrollFactor) {
			mScrollFactor = scrollFactor;
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {
			super.startScroll(startX, startY, dx, dy, (int) (duration * mScrollFactor));
		}

	}


}
