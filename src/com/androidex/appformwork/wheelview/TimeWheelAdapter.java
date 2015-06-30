package com.androidex.appformwork.wheelview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mygame.pure.R;

/**
 * /** HeadAdapter
 */
public class TimeWheelAdapter extends AbstractWheelAdapter {
	// Image size
	final int IMAGE_WIDTH = 50;
	final int IMAGE_HEIGHT = 50;
	public List<String> timeDateList = null;
	private LayoutInflater inflater;
	private Context context;

	/**
	 * Constructor
	 */
	public TimeWheelAdapter(List<String> timeDateList, Context context) {
		this.context = context;
		this.timeDateList = timeDateList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getItemsCount() {
		return timeDateList.size();
	}

	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		if (cachedView == null) {
			cachedView = inflater.inflate(R.layout.head_select_layout, null);
		}
		TextView textView1 = (TextView) cachedView.findViewById(R.id.textView1);
		textView1.setText(timeDateList.get(index));

		return cachedView;
	}
}
