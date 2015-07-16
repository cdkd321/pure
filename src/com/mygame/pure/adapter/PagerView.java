package com.mygame.pure.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

public class PagerView extends PagerAdapter {

	private ArrayList<View> viewlist;

	public PagerView(ArrayList<View> viewlist) {
		this.viewlist = viewlist;
	}

	@Override
	public int getCount() {
		// 设置成最大，使用户看不到边界
		return viewlist.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// Warning：不要在这里调用removeView
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		View view = viewlist.get(position);
		// 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
		ViewParent vp = view.getParent();
		if (vp != null) {
			ViewGroup parent = (ViewGroup) vp;
			parent.removeView(view);
		}
		container.addView(view);
		// add listeners here if necessary
		return view;
	}
}