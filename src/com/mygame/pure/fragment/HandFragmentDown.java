package com.mygame.pure.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.PointD;
import org.xclcharts.chart.SplineData;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
import com.mygame.pure.adapter.HistoryAdapter;
import com.mygame.pure.bean.Average;
import com.mygame.pure.bean.BltModel;
import com.mygame.pure.ble.BleService;
import com.mygame.pure.utils.Constants;
import com.mygame.pure.utils.DateUtil;
import com.mygame.pure.utils.DbUtils;
import com.mygame.pure.utils.ToastHelper;
import com.mygame.pure.view.CircleProgressBarBlue;
import com.mygame.pure.view.MyrogressBar;
import com.mygame.pure.view.SplineChart03View;

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
	private TextView detectionTimes;
	private MyrogressBar progressBar;
	private SplineChart03View chartView;
	private int checkType;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mact = (ActMain) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.tab_fragment_hand_down, container,
				false);
		initView();
		return rootView;
	}

	private void initView() {
		ViewPager vpager = (ViewPager) rootView.findViewById(R.id.vPager);
		clickPageLeft = (ImageView) rootView.findViewById(R.id.clickPageLeft);
		clickPageRight = (ImageView) rootView.findViewById(R.id.clickPageRight);
		rGroup = (RadioGroup) rootView.findViewById(R.id.rGroup);
		detectionTimes = (TextView) rootView.findViewById(R.id.detection_times);
		chartView = (SplineChart03View) rootView
				.findViewById(R.id.spline_chart);

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
		progressBar = (MyrogressBar) rootView.findViewById(R.id.myProgress);

		biJiaoToday(DateUtil.getDateStr(DateUtil.getCurrentDate(), -1),
				DateUtil.getCurrentDate(), tvThanLastDayData);
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
		Float d = Float.parseFloat(tvAverageLevelData.getText().toString()
				.replace("%", "")) / 60f;
		progressBar.setProgress(Float.parseFloat(df.format(d)));
		refreshChartView(tvDate.getText().toString());
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
					refreshChartView(DateUtil.getCurrentDate());
					tvDate.setText(DateUtil.getCurrentDate());
					biJiaoToday(
							DateUtil.getDateStr(DateUtil.getCurrentDate(), -1),
							DateUtil.getCurrentDate(), tvThanLastDayData);
					break;
				case R.id.rbRight:
					selectFlag = 2;
					Average averagem = refreshMonthChartView(
							df.format(getFirstDayOfMonth(new Date())),
							df.format(getLastDayOfMonth(new Date())));
					tvDate.setText(df.format(getFirstDayOfMonth(new Date()))
							+ "~" + df.format(getLastDayOfMonth(new Date())));
					tvAverageLevelData.setText(averagem.getAverage() + "%");
					detectionTimes.setText("检测次数 " + averagem.getCount() + "次");
					break;
				case R.id.rbMid:
					selectFlag = 1;
					Average average = refreshWeekChartView(DateUtil.getDateStr(
							df.format(getFirstDayOfWeek(new Date())), -1));
					tvDate.setText(DateUtil.getDateStr(
							df.format(getFirstDayOfWeek(new Date())), -1)
							+ "~" + df.format(getLastDayOfWeek(new Date())));
					tvAverageLevelData.setText(average.getAverage() + "%");
					detectionTimes.setText("检测次数 " + average.getCount() + "次");
					break;

				default:
					break;
				}
				java.text.DecimalFormat dfd = new java.text.DecimalFormat(
						"#0.00");
				Float d = Float.parseFloat(tvAverageLevelData.getText()
						.toString().replace("%", "")) / 60f;
				progressBar.setProgress(Float.parseFloat(dfd.format(d)));
				reFreshDegreeView();
			}
		});
		/*
		 * for (int i = 0; i < 1; i++) { View view = View.inflate(activity,
		 * R.layout.date_record, null); viewList.add(view); }
		 */
		HistoryAdapter adapter = new HistoryAdapter(viewList);
		reFreshDegreeView();
		vpager.setAdapter(adapter);
		registerBoradcastReceiver();
	}

	public static HandFragmentDown newInstance(int checkType) {
		HandFragmentDown f = new HandFragmentDown();
		Bundle args = new Bundle();
		args.putInt("checkType", checkType);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		checkType = getArguments().getInt("checkType");
	}

	private void reFreshDegreeView() {
		CircleProgressBarBlue pb = (CircleProgressBarBlue) rootView
				.findViewById(R.id.cpb);
		TextView tvDegreeText = (TextView) rootView
				.findViewById(R.id.tvDegreeText);
		pb.setProgress(Float.parseFloat(tvAverageLevelData.getText().toString()
				.replace("%", "")) / 100f);
		tvDegreeText.setText(tvAverageLevelData.getText().toString());
	}

	/**
	 * 刷新图表
	 */
	private void refreshChartView(String date) {
		// 线1的数据集
		List<PointD> linePoint1 = new ArrayList<PointD>();
		if (hourAvera(DateUtil.getCurrentDate(), "01") != 0) {
			linePoint1.add(new PointD(4d, hourAvera(DateUtil.getCurrentDate(),
					"01")));
		}
		if (hourAvera(date, "02") != 0) {

			linePoint1.add(new PointD(8d, hourAvera(DateUtil.getCurrentDate(),
					"02")));
		}
		if (hourAvera(date, "03") != 0) {
			linePoint1.add(new PointD(12d, hourAvera(DateUtil.getCurrentDate(),
					"03")));

		}
		if (hourAvera(date, "04") != 0) {
			linePoint1.add(new PointD(16d, hourAvera(DateUtil.getCurrentDate(),
					"04")));

		}
		if (hourAvera(date, "05") != 0) {

			linePoint1.add(new PointD(20d, hourAvera(DateUtil.getCurrentDate(),
					"05")));
		}
		if (hourAvera(date, "06") != 0) {

			linePoint1.add(new PointD(24d, hourAvera(DateUtil.getCurrentDate(),
					"06")));
		}
		if (hourAvera(date, "07") != 0) {
			linePoint1.add(new PointD(28d, hourAvera(DateUtil.getCurrentDate(),
					"07")));

		}
		if (hourAvera(date, "08") != 0) {

			linePoint1.add(new PointD(32d, hourAvera(DateUtil.getCurrentDate(),
					"08")));
		}
		if (hourAvera(date, "09") != 0) {

			linePoint1.add(new PointD(36d, hourAvera(DateUtil.getCurrentDate(),
					"09")));
		}
		if (hourAvera(date, "10") != 0) {

			linePoint1.add(new PointD(40d, hourAvera(DateUtil.getCurrentDate(),
					"10")));
		}
		if (hourAvera(date, "11") != 0) {
			linePoint1.add(new PointD(44d, hourAvera(DateUtil.getCurrentDate(),
					"11")));

		}
		if (hourAvera(date, "12") != 0) {

			linePoint1.add(new PointD(48d, hourAvera(DateUtil.getCurrentDate(),
					"12")));
		}
		if (hourAvera(date, "13") != 0) {

			linePoint1.add(new PointD(52d, hourAvera(DateUtil.getCurrentDate(),
					"13")));
		}
		if (hourAvera(date, "14") != 0) {

			linePoint1.add(new PointD(56d, hourAvera(DateUtil.getCurrentDate(),
					"14")));
		}
		if (hourAvera(date, "15") != 0) {

			linePoint1.add(new PointD(60d, hourAvera(DateUtil.getCurrentDate(),
					"15")));
		}
		if (hourAvera(date, "16") != 0) {

			linePoint1.add(new PointD(64d, hourAvera(DateUtil.getCurrentDate(),
					"16")));
		}
		if (hourAvera(date, "17") != 0) {
			linePoint1.add(new PointD(68d, hourAvera(DateUtil.getCurrentDate(),
					"17")));

		}
		if (hourAvera(date, "18") != 0) {

			linePoint1.add(new PointD(72d, hourAvera(DateUtil.getCurrentDate(),
					"18")));
		}
		if (hourAvera(date, "19") != 0) {
			linePoint1.add(new PointD(76d, hourAvera(DateUtil.getCurrentDate(),
					"19")));

		}
		if (hourAvera(date, "20") != 0) {

			linePoint1.add(new PointD(80d, hourAvera(DateUtil.getCurrentDate(),
					"20")));
		}
		if (hourAvera(date, "21") != 0) {

			linePoint1.add(new PointD(84d, hourAvera(DateUtil.getCurrentDate(),
					"21")));
		}
		if (hourAvera(date, "22") != 0) {

			linePoint1.add(new PointD(88d, hourAvera(DateUtil.getCurrentDate(),
					"22")));
		}
		if (hourAvera(date, "23") != 0) {

			linePoint1.add(new PointD(92d, hourAvera(DateUtil.getCurrentDate(),
					"23")));
		}
		if (hourAvera(date, "24") != 0) {

			linePoint1.add(new PointD(96d, hourAvera(DateUtil.getCurrentDate(),
					"24")));
		}
		SplineData dataSeries1 = new SplineData("线一", linePoint1, Color.rgb(
				179, 147, 197));
		// 把线弄细点
		dataSeries1.getLinePaint().setStrokeWidth(2);
		// dataSeries1.setLabelVisible(true);
		LinkedList<SplineData> chartData = new LinkedList<SplineData>();

		// 设定数据源
		chartData.add(dataSeries1);
		chartView.setChartData(chartData);
		chartView.initView();
	}

	/**
	 * 刷新月图表
	 */

	private Average refreshMonthChartView(String startDate, String endDate) {
		Average averageEntity = new Average();
		float average = 0;
		int count = 0;
		List<PointD> linePoint1 = new ArrayList<PointD>();
		String ends[] = endDate.split("-");
		int totalDay = Integer.parseInt(ends[2]);
		double pointd = (Double) 100d / totalDay;
		for (int i = 0; i < totalDay; i++) {
			average = average
					+ dayAvera(DateUtil.getDateStr(startDate, i)).getAverage();
			count = count
					+ dayAvera(DateUtil.getDateStr(startDate, i)).getCount();
			if(dayAvera(
					DateUtil.getDateStr(startDate, i)).getAverage()!=0){
				linePoint1.add(new PointD(i * pointd, dayAvera(
						DateUtil.getDateStr(startDate, i)).getAverage()));
			}
			
		}
		SplineData dataSeries1 = new SplineData("线一", linePoint1, Color.rgb(
				179, 147, 197));
		// 把线弄细点
		dataSeries1.getLinePaint().setStrokeWidth(2);
		// dataSeries1.setLabelVisible(true);
		LinkedList<SplineData> chartData = new LinkedList<SplineData>();
		LinkedList<String> chartLabels = new LinkedList<String>();
		chartLabels.add("1");
		chartLabels.add("15");
		chartLabels.add("30");
		// 设定数据源
		chartData.add(dataSeries1);
		chartView.setChartData(chartData);
		chartView.setChartLabels(chartLabels);
		averageEntity.setAverage(average);
		averageEntity.setCount(count);
		chartView.initView();
		return averageEntity;
	}

	/**
	 * 刷新周图表
	 */
	private Average refreshWeekChartView(String date) {
		// 线1的数据集
		Average averageEntity = new Average();
		float average = 0;
		int count = 0;
		List<PointD> linePoint1 = new ArrayList<PointD>();
		if (dayAvera(date).getAverage() != 0) {
			average = average + dayAvera(date).getAverage();
			count = count + dayAvera(date).getCount();
			linePoint1.add(new PointD(14d, dayAvera(date).getAverage()));
		}
		if (dayAvera(DateUtil.getDateStr(date, 1)).getAverage() != 0) {
			average = average
					+ dayAvera(DateUtil.getDateStr(date, 1)).getAverage();
			count = count + dayAvera(DateUtil.getDateStr(date, 1)).getCount();
			linePoint1.add(new PointD(28d, dayAvera(
					DateUtil.getDateStr(date, 1)).getAverage()));
		}
		if (dayAvera(DateUtil.getDateStr(date, 2)).getAverage() != 0) {
			average = average
					+ dayAvera(DateUtil.getDateStr(date, 2)).getAverage();
			count = count + dayAvera(DateUtil.getDateStr(date, 2)).getCount();
			linePoint1.add(new PointD(42d, dayAvera(
					DateUtil.getDateStr(date, 2)).getAverage()));

		}
		if (dayAvera(DateUtil.getDateStr(date, 3)).getAverage() != 0) {
			average = average
					+ dayAvera(DateUtil.getDateStr(date, 3)).getAverage();
			count = count + dayAvera(DateUtil.getDateStr(date, 3)).getCount();
			linePoint1.add(new PointD(56d, dayAvera(
					DateUtil.getDateStr(date, 3)).getAverage()));

		}
		if (dayAvera(DateUtil.getDateStr(date, 4)).getAverage() != 0) {
			average = average
					+ dayAvera(DateUtil.getDateStr(date, 4)).getAverage();
			count = count + dayAvera(DateUtil.getDateStr(date, 4)).getCount();
			linePoint1.add(new PointD(70d, dayAvera(
					DateUtil.getDateStr(date, 4)).getAverage()));

		}
		if (dayAvera(DateUtil.getDateStr(date, 5)).getAverage() != 0) {
			average = average
					+ dayAvera(DateUtil.getDateStr(date, 5)).getAverage();
			count = count + dayAvera(DateUtil.getDateStr(date, 5)).getCount();
			linePoint1.add(new PointD(84d, dayAvera(
					DateUtil.getDateStr(date, 5)).getAverage()));

		}
		if (dayAvera(DateUtil.getDateStr(date, 6)).getAverage() != 0) {
			average = average
					+ dayAvera(DateUtil.getDateStr(date, 6)).getAverage();
			count = count + dayAvera(DateUtil.getDateStr(date, 6)).getCount();
			linePoint1.add(new PointD(98d, dayAvera(
					DateUtil.getDateStr(date, 6)).getAverage()));

		}
		SplineData dataSeries1 = new SplineData("线一", linePoint1, Color.rgb(
				179, 147, 197));
		// 把线弄细点
		dataSeries1.getLinePaint().setStrokeWidth(2);
		// dataSeries1.setLabelVisible(true);
		LinkedList<SplineData> chartData = new LinkedList<SplineData>();
		LinkedList<String> chartLabels = new LinkedList<String>();
		chartLabels.add("日");
		chartLabels.add("一");
		chartLabels.add("二");
		chartLabels.add("三");
		chartLabels.add("四");
		chartLabels.add("五");
		chartLabels.add("六");
		// 设定数据源
		chartData.add(dataSeries1);
		chartView.setChartData(chartData);
		chartView.setChartLabels(chartLabels);
		chartView.initView();
		averageEntity.setAverage(average);
		averageEntity.setCount(count);
		return averageEntity;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(mReceiver);
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
		java.text.DecimalFormat dfDec = new java.text.DecimalFormat("#0.00");
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
				refreshChartView(tvDate.getText().toString());

			} else if (selectFlag == 1) {
				String[] dates = tvDate.getText().toString().split("~");
				tvDate.setText(DateUtil.getDateStr(dates[0], -7) + "~"
						+ DateUtil.getDateStr(dates[1], -7));
				Average average = refreshWeekChartView(DateUtil.getDateStr(
						dates[0], -7));

				tvAverageLevelData.setText(average.getAverage() + "%");
				detectionTimes.setText("检测次数 " + average.getCount() + "次");

			} else if (selectFlag == 2) {
				String[] dates = tvDate.getText().toString().split("~");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					tvDate.setText(df.format(getReduceMonth(df.parse(dates[0])))
							+ "~"
							+ df.format(getReduceMonth(df.parse(dates[1]))));

					Average averagem = refreshMonthChartView(df
							.format(getReduceMonth(df.parse(dates[0]))),
							df.format(getReduceMonth(df.parse(dates[1]))));
					tvAverageLevelData.setText(averagem.getAverage() + "%");
					detectionTimes.setText("检测次数 " + averagem.getCount() + "次");
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			Float dl = Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", "")) / 60f;
			reFreshDegreeView();
			progressBar.setProgress(Float.parseFloat(dfDec.format(dl)));
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
						biJiaoToday(DateUtil.getDateStr(
								DateUtil.getCurrentDate(), -1),
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
				refreshChartView(tvDate.getText().toString());

			} else if (selectFlag == 1) {
				String[] dates = tvDate.getText().toString().split("~");
				tvDate.setText(DateUtil.getDateStr(dates[0], 7) + "~"
						+ DateUtil.getDateStr(dates[1], 7));
				Average average = refreshWeekChartView(DateUtil.getDateStr(
						dates[0], 7));

				tvAverageLevelData.setText(average.getAverage() + "%");
				detectionTimes.setText("检测次数 " + average.getCount() + "次");
			} else if (selectFlag == 2) {
				String[] dates = tvDate.getText().toString().split("~");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					tvDate.setText(df.format(getAddMonth(df.parse(dates[0])))
							+ "~" + df.format(getAddMonth(df.parse(dates[1]))));
					Average averagem = refreshMonthChartView(df
							.format(getAddMonth(df.parse(dates[0]))),
							df.format(getAddMonth(df.parse(dates[1]))));
					tvAverageLevelData.setText(averagem.getAverage() + "%");
					detectionTimes.setText("检测次数 " + averagem.getCount() + "次");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			reFreshDegreeView();
			Float dr = Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", "")) / 60f;
			progressBar.setProgress(Float.parseFloat(dfDec.format(dr)));
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
		WhereBuilder builder = WhereBuilder.b("date", "==", today).and(
				"modelstate", "==", checkType);
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
					detectionTimes.setText("检测次数 " + blts.size() + "次");

				} else {
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
		WhereBuilder builder1 = WhereBuilder.b("date", "==", lastDay).and(
				"modelstate", "==", checkType);
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
		} else if (averageYesTodayWater - averageWater == 0) {
			tvbiJiao.setText("+0%");
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
		WhereBuilder builder = WhereBuilder.b("date", "==", today).and(
				"modelstate", "==", checkType);
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
		WhereBuilder builder1 = WhereBuilder.b("date", "==", lastDay).and(
				"modelstate", "==", checkType);
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
					tvAverageLevelData
							.setText(Float.parseFloat(df
									.format(averageYesTodayWater / 45.0f + 20.0))
									+ "%");
					detectionTimes.setText("检测次数 " + yesTodayBlts.size() + "次");
				} else {
					tvAverageLevelData.setText("0.0%");
					detectionTimes.setText("检测次数  0" + "次");
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
			tvbiJiao.setText("+0%");
		} else {
			tvbiJiao.setText("-"
					+ Float.parseFloat(df
							.format((averageWater - averageYesTodayWater) / 45.0f + 20.0))
					+ "%");
		}
	}

	/**
	 * 从数据库中查询某个小时检查的平均值
	 * 
	 * @param lastDay
	 * @param today
	 * @param tvbiJiao
	 */
	private float hourAvera(String today, String hour) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
		DbUtils db = DbUtils.create(getActivity());
		List<BltModel> blts;
		float averageWater = 0;
		WhereBuilder builder = WhereBuilder.b("date", "==", today)
				.and("hour", "==", hour).and("modelstate", "==", checkType);
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
					averageWater = Float.parseFloat(df
							.format(averageWater / 45.0f + 20.0));

				}

			}
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 0;
		}
		return averageWater;

	}

	/**
	 * 从数据库中查询某天检查的平均值
	 * 
	 * @param lastDay
	 * @param today
	 * @param tvbiJiao
	 */
	private Average dayAvera(String day) {
		Average average = new Average();
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
		DbUtils db = DbUtils.create(getActivity());
		List<BltModel> blts;
		float averageWater = 0;
		WhereBuilder builder = WhereBuilder.b("date", "==", day).and(
				"modelstate", "==", checkType);
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
					average.setCount(blts.size());
					averageWater = Float.parseFloat(df
							.format(averageWater / 45.0f + 20.0));
					average.setAverage(averageWater);

				}

			}
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return average;
		}
		return average;

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
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 5); // 星期天
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

	private void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(Constants.UPDATE_OK);
		myIntentFilter.addAction(Constants.CANCEL_REFRESH);
		myIntentFilter.addAction(BleService.ACTION_GATT_CONNECTED);
		myIntentFilter.addAction(BleService.ACTION_GATT_DISCONNECTED);
		myIntentFilter.addAction(BleService.ACTION_STATUS_WRONG);
		myIntentFilter.addAction(BleService.ACTION_TIME_TOOSHORT);
		myIntentFilter.addAction(BleService.ACTION_START);
		myIntentFilter.addAction(Constants.SYNCHRONOUS_FAILURE);
		myIntentFilter.addAction(Constants.OLD_UPDATE_OK);
		myIntentFilter.addAction(Constants.CLEAR_AlL);
		// 注册广播
		getActivity().registerReceiver(mReceiver, myIntentFilter);
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
			String action = intent.getAction();
			if (Constants.UPDATE_OK.equals(action)) {
				int waters = intent.getIntExtra("waters", 0);

				DbUtils db = DbUtils.create(getActivity());
				List<BltModel> blts;
				float averageWater = 0;
				WhereBuilder builder = WhereBuilder.b("date", "==",
						DateUtil.getCurrentDate()).and("modelstate", "==",
						checkType);
				try {
					blts = db.findAll(Selector.from(BltModel.class).where(
							builder));
					if (blts != null) {
						int totalWater = 0;

						for (int i = 0; i < blts.size(); i++) {
							totalWater = totalWater
									+ Integer.parseInt(blts.get(i).getWater());

						}
						if (blts.size() > 0) {
							averageWater = totalWater / blts.size();
							// averageWater=Float.parseFloat(df.format(averageWater/45.0f+20.0))/100f;
							tvAverageLevelData
									.setText(Float.parseFloat(df
											.format(averageWater / 45.0f + 20.0))
											+ "%");
							detectionTimes.setText("检测次数 " + blts.size() + "次");
						}

					}
				} catch (DbException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				List<BltModel> yesTodayBlts;
				float averageYesTodayWater = 0;
				// 昨天的数据
				WhereBuilder builder1 = WhereBuilder.b("date", "==",
						DateUtil.dateAddDay(new Date(), -1)).and("modelstate",
						"==", checkType);
				try {
					yesTodayBlts = db.findAll(Selector.from(BltModel.class)
							.where(builder1));
					if (yesTodayBlts != null) {
						int totalWater = 0;

						for (int i = 0; i < yesTodayBlts.size(); i++) {
							totalWater = totalWater
									+ Integer.parseInt(yesTodayBlts.get(i)
											.getWater());

						}
						if (yesTodayBlts.size() > 0) {
							averageYesTodayWater = totalWater
									/ yesTodayBlts.size();
						}

						// mAdapter.notifymDataSetChanged(lists);
					} else {
						averageYesTodayWater = 0;
					}
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (averageWater - averageYesTodayWater >= 0) {
					tvThanLastDayData
							.setText("+"
									+ Float.parseFloat(df
											.format((averageWater - averageYesTodayWater) / 45.0f + 20.0))
									+ "%");
				} else {
					tvThanLastDayData
							.setText("-"
									+ Float.parseFloat(df
											.format((averageYesTodayWater - averageWater) / 45.0f + 20.0))
									+ "%");
				}
				refreshChartView(tvDate.getText().toString());
				reFreshDegreeView();
				java.text.DecimalFormat dfc = new java.text.DecimalFormat("#0.00");
				Float d = Float.parseFloat(tvAverageLevelData.getText().toString()
						.replace("%", "")) / 60f;
				progressBar.setProgress(Float.parseFloat(dfc.format(d)));

			}

		}
	};

}
