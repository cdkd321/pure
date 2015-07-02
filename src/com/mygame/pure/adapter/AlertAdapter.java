package com.mygame.pure.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mygame.pure.R;
import com.mygame.pure.bean.Alert;
import com.mygame.pure.view.AbSlidingButton;

public class AlertAdapter extends BaseAdapter {
	private Context mContext;
	private List<Alert> alerts;

	public AlertAdapter(Context mContext, List<Alert> alerts) {
		super();
		this.mContext = mContext;
		this.alerts = alerts;
	}

	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return alerts.size();
	}

	@Override
	public Object getItem(int position) {
		return alerts.get(position);
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
		Alert alert = alerts.get(position);
		alert_name.setText(alert.getAlertName());
		alert_time.setText(alert.getAlertTime());
		String[] repeats = alert.getAlertRepeat().split(",");
		String repeat = "";
		if (repeats[0].equals("1")) {
			repeat = repeat + "周日  ";
		}
		if (repeats[1].equals("1")) {
			repeat = repeat + "周一  ";
		}
		if (repeats[2].equals("1")) {
			repeat = repeat + "周二  ";
		}
		if (repeats[3].equals("1")) {
			repeat = repeat + "周三  ";
		}
		if (repeats[4].equals("1")) {
			repeat = repeat + "周四  ";
		}
		if (repeats[5].equals("1")) {
			repeat = repeat + "周五  ";
		}
		if (repeats[6].equals("1")) {
			repeat = repeat + "周六  ";
		}

		alert_time_repeat.setText(repeat);

		return convertView;
	}

}
