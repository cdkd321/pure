package com.mygame.pure.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.mygame.pure.R;
import com.mygame.pure.activity.MainActivity;
import com.mygame.pure.activity.MoreAct;
import com.mygame.pure.adapter.HistoryAdapter;
import com.mygame.pure.utils.DateUtil;
import com.mygame.pure.utils.ToastHelper;
import com.mygame.pure.view.CircleProgressBarBlue;

public class HandFragmentDown extends BaseFragment implements OnClickListener {

	MainActivity mact;
	private ImageView clickPageLeft;
	private ImageView clickPageRight;
	private View date_record;
	private TextView tvDate;
	private RadioGroup rGroup;
	private int selectFlag = 0;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mact = (MainActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.tab_fragment_hand_down, container,
				false);
		initView();
		mact.addBackImage(R.drawable.btn_more_bg, new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), MoreAct.class));
			}
		});
		return rootView;
	}

	private void initView() {
		ViewPager vpager = (ViewPager) rootView.findViewById(R.id.vPager);
		clickPageLeft = (ImageView) rootView.findViewById(R.id.clickPageLeft);
		clickPageRight = (ImageView) rootView.findViewById(R.id.clickPageRight);
		rGroup = (RadioGroup) rootView.findViewById(R.id.rGroup);
		clickPageLeft.setOnClickListener(this);
		clickPageRight.setOnClickListener(this);
		List<View> viewList = new ArrayList<View>();
		date_record = View.inflate(activity, R.layout.date_record, null);
		tvDate = (TextView) date_record.findViewById(R.id.tvDate);
		tvDate.setText(DateUtil.getCurrentDate());
		viewList.add(date_record);
		rGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				switch (checkedId) {
				case R.id.rbLeft:
					selectFlag = 0;
					break;
				case R.id.rbRight:
					selectFlag = 2;

					tvDate.setText(df.format(getFirstDayOfMonth(new Date()))
							+ "~" + df.format(getLastDayOfMonth(new Date())));
					break;
				case R.id.rbMid:
					selectFlag = 1;
					tvDate.setText(df.format(getFirstDayOfWeek(new Date()))
							+ "~" + df.format(getLastDayOfWeek(new Date())));
					break;

				default:
					break;
				}
			}
		});
		/*
		 * for (int i = 0; i < 1; i++) { View view = View.inflate(activity,
		 * R.layout.date_record, null); viewList.add(view); }
		 */
		HistoryAdapter adapter = new HistoryAdapter(viewList);
		CircleProgressBarBlue pb = (CircleProgressBarBlue) rootView
				.findViewById(R.id.cpb);
		pb.setPoint(50);
		vpager.setAdapter(adapter);
	}

	// 点击每天显示水平
	public void clickPageLeft(View v) {

	}

	public void clickPageRight(View v) {

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.abarLeft: // 左边更多按钮
			ToastHelper.ToastSht("you click left", getActivity());
			break;
		case R.id.abarRight: // 右边检测按钮
			ToastHelper.ToastSht("you click right", getActivity());
			break;
		case R.id.clickPageLeft:
			if (selectFlag == 0) {
				tvDate.setText(DateUtil.getDateStr(tvDate.getText().toString(),
						-1));
			} else if (selectFlag == 1) {
				String[] dates = tvDate.getText().toString().split("~");
				tvDate.setText(DateUtil.getDateStr(dates[0], -7) + "~"
						+ DateUtil.getDateStr(dates[1], -7));

			} else if (selectFlag == 2) {
				String[] dates = tvDate.getText().toString().split("~");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					tvDate.setText(df.format(getReduceMonth(df.parse(dates[0])))
							+ "~"
							+ df.format(getReduceMonth(df.parse(dates[1]))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case R.id.clickPageRight:
			if (selectFlag == 0) {
				tvDate.setText(DateUtil.getDateStr(tvDate.getText().toString(),
						1));
			} else if (selectFlag == 1) {
				String[] dates = tvDate.getText().toString().split("~");
				tvDate.setText(DateUtil.getDateStr(dates[0], -7) + "~"
						+ DateUtil.getDateStr(dates[1], -7));
			} else if (selectFlag == 2) {
				String[] dates = tvDate.getText().toString().split("~");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					tvDate.setText(df.format(getAddMonth(df.parse(dates[0])))
							+ "~" + df.format(getAddMonth(df.parse(dates[1]))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 取得当前日期所在周的第一天
	 */
	public Date getFirstDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // 星期一
		return c.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 */
	public Date getLastDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // 星期天
		return c.getTime();
	}

	/**
	 * 取得当前日期所在月的最后一天
	 */
	public Date getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();// 获取当前日期
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		cal.add(Calendar.MONTH, 1);// 月增加1天
		cal.add(Calendar.DAY_OF_MONTH, -1);// 日期倒数一日,既得到本月最后一天
		return cal.getTime();
	}

	/**
	 * 月增加一天
	 */
	public Date getAddMonth(Date date) {
		Calendar cal = Calendar.getInstance();// 获取当前日期
		cal.setTime(date);
		if (cal.get(Calendar.MONTH) == 11) {
			cal.set(Calendar.MONTH, 0);
			cal.add(Calendar.YEAR, 1);
		} else {
			cal.add(Calendar.MONTH, 1);// 月增加1天
		}
		if (cal.get(Calendar.DAY_OF_MONTH) != 1) {
			int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
			cal.set(Calendar.DAY_OF_MONTH, dateOfMonth);
		}
		return cal.getTime();
	}

	/**
	 * 月减少一天
	 */
	public Date getReduceMonth(Date date) {
		Calendar cal = Calendar.getInstance();// 获取当前日期
		cal.setTime(date);
		if (cal.get(Calendar.MONTH) == 0) {
			cal.set(Calendar.MONTH, 11);
			cal.add(Calendar.YEAR, -1);
		} else {
			cal.add(Calendar.MONTH, -1);// 月增加1天
		}
		if (cal.get(Calendar.DAY_OF_MONTH) != 1) {
			int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
			cal.set(Calendar.DAY_OF_MONTH, dateOfMonth);
		}

		return cal.getTime();
	}

	/**
	 * 取得当前日期所在月的第一天
	 */
	public Date getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();// 获取当前日期
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天

		return cal.getTime();
	}

}
