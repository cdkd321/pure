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

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.mygame.pure.R;
import com.mygame.pure.activity.ActMain;
import com.mygame.pure.activity.MoreAct;
import com.mygame.pure.adapter.HistoryAdapter;
import com.mygame.pure.bean.BltModel;
import com.mygame.pure.utils.DateUtil;
import com.mygame.pure.utils.DbUtils;
import com.mygame.pure.utils.ToastHelper;
import com.mygame.pure.view.CircleProgressBarBlue;

public class HandFragmentDown extends BaseFragment implements OnClickListener {

	ActMain mact;
	private ImageView clickPageLeft;
	private ImageView clickPageRight;
	private View date_record;
	private TextView tvDate;
	private RadioGroup rGroup;
	private int selectFlag = 0;
	private TextView tvAverageLevelData;
	private TextView tvThanLastDay;
	private TextView tvThanLastDayData;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mact = (ActMain) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.tab_fragment_hand_down, container, false);
		initView();
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
		tvAverageLevelData = (TextView) date_record
				.findViewById(R.id.tvAverageLevelData);
		tvThanLastDay = (TextView) date_record.findViewById(R.id.tvThanLastDay);
		tvThanLastDayData = (TextView) date_record
				.findViewById(R.id.tvThanLastDayData);
		tvDate.setText(DateUtil.getCurrentDate());
		biJiaoToday(DateUtil.getDateStr(DateUtil.getCurrentDate(), -1),
				DateUtil.getCurrentDate(), tvThanLastDayData);
		/*
		 * DbUtils db = DbUtils.create(getActivity()); List<BltModel> blts;
		 * float averageWater = 0; java.text.DecimalFormat df = new
		 * java.text.DecimalFormat("#0.0"); WhereBuilder builder =
		 * WhereBuilder.b("date", "==", DateUtil.getCurrentDate()); try { blts =
		 * db.findAll(Selector.from(BltModel.class).where(builder)); if (blts !=
		 * null) { int totalWater = 0; for (int i = 0; i < blts.size(); i++) {
		 * totalWater = totalWater + Integer.parseInt(blts.get(i).getWater());
		 * 
		 * } if (blts.size() > 0) { averageWater = totalWater / blts.size();
		 * tvAverageLevelData.setText(Float.parseFloat(df .format(averageWater /
		 * 45.0f + 20.0)) + "%"); }
		 * 
		 * } } catch (DbException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */

		viewList.add(date_record);
		rGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				switch (checkedId) {
				case R.id.rbLeft:
					selectFlag = 0;
					tvDate.setText(DateUtil.getCurrentDate());
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
				tvThanLastDay.setText("比今天");
				biJiaoLastDay(tvDate.getText().toString(),
						DateUtil.getCurrentDate(), tvThanLastDayData);

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
					e.printStackTrace();
				}
			}
			break;
		case R.id.clickPageRight:
			if (selectFlag == 0) {
				if (!tvDate.getText().toString()
						.equals(DateUtil.getCurrentDate())) {
					tvDate.setText(DateUtil.getDateStr(tvDate.getText()
							.toString(), 1));
					if (tvDate.getText().toString()
							.equals(DateUtil.getCurrentDate())) {
						tvThanLastDay.setText("比前一天");
						biJiaoToday(DateUtil.getDateStr(DateUtil.getCurrentDate(), -1),
								DateUtil.getCurrentDate(), tvThanLastDayData);
					} else {
						biJiaoLastDay(tvDate.getText().toString(),
								DateUtil.getCurrentDate(), tvThanLastDayData);
						tvThanLastDay.setText("比今天");
					}

				} else {
					tvThanLastDay.setText("比前一天");
					biJiaoToday(tvDate.getText().toString(),
							DateUtil.getCurrentDate(), tvThanLastDayData);
				}

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
	 * 今天的时间和前面的时间比较
	 * 
	 * @param lastDay
	 * @param today
	 * @param tvbiJiao
	 */
	private void biJiaoToday(String lastDay, String today, TextView tvbiJiao) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
		DbUtils db = DbUtils.create(getActivity());
		List<BltModel> blts;
		float averageWater = 0;
		WhereBuilder builder = WhereBuilder.b("date", "==", today);
		try {
			blts = db.findAll(Selector.from(BltModel.class).where(builder));
			if (blts != null) {
				int totalWater = 0;

				for (int i = 0; i < blts.size(); i++) {
					totalWater = totalWater
							+ Integer.parseInt(blts.get(i).getWater());

				}
				if (blts.size() > 0) {
					averageWater = totalWater / blts.size();
					tvAverageLevelData.setText(Float.parseFloat(df
							.format(averageWater / 45.0f + 20.0)) + "%");

				}else{
					tvAverageLevelData.setText("0.0%");
				}

			} else {

				tvAverageLevelData.setText("0.0%");
			}
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<BltModel> yesTodayBlts;
		float averageYesTodayWater = 0;
		// 昨天的数据
		WhereBuilder builder1 = WhereBuilder.b("date", "==", lastDay);
		try {
			yesTodayBlts = db.findAll(Selector.from(BltModel.class).where(
					builder1));
			if (yesTodayBlts != null) {
				int totalWater = 0;

				for (int i = 0; i < yesTodayBlts.size(); i++) {
					totalWater = totalWater
							+ Integer.parseInt(yesTodayBlts.get(i).getWater());

				}
				if (yesTodayBlts.size() > 0) {
					averageYesTodayWater = totalWater / yesTodayBlts.size();
				}

				// mAdapter.notifymDataSetChanged(lists);
			} else {
				averageYesTodayWater = 0;
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (averageWater - averageYesTodayWater > 0) {
			tvbiJiao.setText("+"
					+ Float.parseFloat(df
							.format((averageWater - averageYesTodayWater) / 45.0f + 20.0))
					+ "%");
		}else if (averageYesTodayWater - averageWater == 0) {
			tvbiJiao.setText( "+0%");
		} else {
			tvbiJiao.setText("-"
					+ Float.parseFloat(df
							.format((averageYesTodayWater - averageWater) / 45.0f + 20.0))
					+ "%");
		}
	}

	/**
	 * 前面的时间和后面的时间比较
	 * 
	 * @param lastDay
	 * @param today
	 * @param tvbiJiao
	 */
	private void biJiaoLastDay(String lastDay, String today, TextView tvbiJiao) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
		DbUtils db = DbUtils.create(getActivity());
		List<BltModel> blts;
		float averageWater = 0;
		WhereBuilder builder = WhereBuilder.b("date", "==", today);
		try {
			blts = db.findAll(Selector.from(BltModel.class).where(builder));
			if (blts != null) {
				int totalWater = 0;

				for (int i = 0; i < blts.size(); i++) {
					totalWater = totalWater
							+ Integer.parseInt(blts.get(i).getWater());

				}
				if (blts.size() > 0) {
					averageWater = totalWater / blts.size();

				}

			}
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<BltModel> yesTodayBlts;
		float averageYesTodayWater = 0;
		// 昨天的数据
		WhereBuilder builder1 = WhereBuilder.b("date", "==", lastDay);
		try {
			yesTodayBlts = db.findAll(Selector.from(BltModel.class).where(
					builder1));
			if (yesTodayBlts != null) {
				int totalWater = 0;

				for (int i = 0; i < yesTodayBlts.size(); i++) {
					totalWater = totalWater
							+ Integer.parseInt(yesTodayBlts.get(i).getWater());

				}
				if (yesTodayBlts.size() > 0) {
					averageYesTodayWater = totalWater / yesTodayBlts.size();
					tvAverageLevelData.setText(Float.parseFloat(df
							.format(averageYesTodayWater / 45.0f + 20.0)) + "%");
				} else {
					tvAverageLevelData.setText("0.0%");
				}

				// mAdapter.notifymDataSetChanged(lists);
			} else {
				averageYesTodayWater = 0;

				tvAverageLevelData.setText("0.0%");
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (averageYesTodayWater - averageWater > 0) {
			tvbiJiao.setText("+"
					+ Float.parseFloat(df
							.format((averageYesTodayWater - averageWater) / 45.0f + 20.0))
					+ "%");
		} else if (averageYesTodayWater - averageWater == 0) {
			tvbiJiao.setText( "+0%");
		} else {
			tvbiJiao.setText("-"
					+ Float.parseFloat(df
							.format((averageWater - averageYesTodayWater) / 45.0f + 20.0))
					+ "%");
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
