package com.mygame.pure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mygame.pure.R;
import com.mygame.pure.view.AbSlidingButton;

public class AlertAdapter extends BaseAdapter {
	private Context mContext;

	public AlertAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return 5;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.alert_list_item, parent, false);

		}
		TextView alert_time = BaseViewHolder.get(convertView, R.id.alert_time);
		TextView alert_name = BaseViewHolder.get(convertView, R.id.alert_name);
		TextView alert_time_repeat = BaseViewHolder.get(convertView,
				R.id.alert_time_repeat);
		AbSlidingButton slidingBtn = BaseViewHolder.get(convertView,
				R.id.mSliderBtn);
		slidingBtn.setImageResource(R.drawable.btn_bottom,
				R.drawable.btn_frame, R.drawable.btn_mask,
				R.drawable.btn_unpressed, R.drawable.btn_pressed);

		return convertView;
	}

}
