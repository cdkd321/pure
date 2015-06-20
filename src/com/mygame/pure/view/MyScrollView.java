package com.mygame.pure.view;

import com.mygame.pure.log.MyLog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
	GestureDetector gestureDetector;
	View.OnTouchListener onTouchListener;
	public static float firstPoint;
	
	public float getTopHeight(){
		return firstPoint;
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 添加了一个手势选择器
		gestureDetector=new GestureDetector(new Yscroll());
		setFadingEdgeLength(0);
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			firstPoint = getScrollY();
			MyLog.i("--tom", "getScrollY():" + firstPoint);
			break;
		default:
			break;
		}
		
		return super.onTouchEvent(ev);
	}




	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		// return super.onInterceptTouchEvent(ev);
		boolean flags = super.onInterceptTouchEvent(ev) && gestureDetector.onTouchEvent(ev);
		if(getScrollY() == 0){
			return false;
		}
		MyLog.i("--tom", "MyScrollView:onInterceptTouchEvent" + flags);
		return flags;  
	}
	
	class Yscroll extends SimpleOnGestureListener{
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
			float distanceX, float distanceY) {
			//控制手指滑动的距离
			
			if (Math.abs(distanceY)>=Math.abs(distanceX)) {
				return true;
			}
			
			return false;
		}
	}

}