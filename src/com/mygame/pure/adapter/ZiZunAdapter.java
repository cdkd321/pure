package com.mygame.pure.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mygame.pure.R;
import com.mygame.pure.bean.ZiXunBean;
import com.squareup.picasso.Picasso;

public class ZiZunAdapter extends BaseAdapter {
	private Context mContext;
	private List<ZiXunBean> alerts;

	public ZiZunAdapter(Context mContext, List<ZiXunBean> alerts) {
		super();
		this.mContext = mContext;
		this.alerts = alerts;
	}

	public void setAlerts(List<ZiXunBean> alerts) {
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
					R.layout.zixun_item, parent, false);

		}
		ImageView img = BaseViewHolder.get(convertView, R.id.img);
		TextView title = BaseViewHolder.get(convertView, R.id.title);
		TextView lioulan = BaseViewHolder.get(convertView,
				R.id.lioulan);
		TextView dianzan = BaseViewHolder.get(convertView,
				R.id.dianzan);
		ZiXunBean zixun=alerts.get(position);
		title.setText(zixun.getTitle());
		lioulan.setText(zixun.getLioulanshu());
		dianzan.setText(zixun.getDianzanshu());
		Picasso.with(mContext)
		.load("http://miliapp.ebms.cn" + zixun.getImage())
		.into(img);

		return convertView;
	}

}
