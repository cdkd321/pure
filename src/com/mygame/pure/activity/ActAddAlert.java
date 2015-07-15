package com.mygame.pure.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidex.appformwork.wheelview.TimeWheelAdapter;
import com.androidex.appformwork.wheelview.WheelView;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.mygame.pure.R;
import com.mygame.pure.bean.Alert;
import com.mygame.pure.utils.AbDateUtil;
import com.mygame.pure.utils.Constants;
import com.mygame.pure.utils.DateUtil;
import com.mygame.pure.utils.DbUtils;

public class ActAddAlert extends BaseActivity {
	private RelativeLayout alert_repeat_layout;
	private RelativeLayout labelLayout;
	private RelativeLayout RingLayout;
	private TextView repeat_time;
	private TextView bellText;
	private TextView lable_text;
	private TextView tvDelete;
	boolean[] reReart = new boolean[7];
	boolean[] bell = new boolean[3];
	private String lable;
	private WheelView leftWheel;
	private WheelView rightWheel;
	List<String> hours;
	List<String> times;
	private Alert alertEdit;
	protected AlarmManager alarmManager;
	protected PendingIntent operation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_activity);
		bell = new boolean[] { true, false, false };

		alert_repeat_layout = (RelativeLayout) findViewById(R.id.alert_repeat_layout);
		labelLayout = (RelativeLayout) findViewById(R.id.labelLayout);
		RingLayout = (RelativeLayout) findViewById(R.id.RingLayout);
		repeat_time = (TextView) findViewById(R.id.repeat_time);
		bellText = (TextView) findViewById(R.id.bellText);
		tvDelete = (TextView) findViewById(R.id.tvDelete);
		lable_text = (TextView) findViewById(R.id.lable_text);
		leftWheel = (WheelView) findViewById(R.id.leftwheel);
		rightWheel = (WheelView) findViewById(R.id.rightwheel);
		initData();
		alertEdit = (Alert) getIntent().getSerializableExtra("alert");
		if (alertEdit != null) {
			tvDelete.setVisibility(View.VISIBLE);
			tvDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
					clearAlarm();
					DbUtils db = DbUtils.create(ActAddAlert.this);
					db.configAllowTransaction(true);
					db.configDebug(true);

					// PedometerModel pedometer =
					// db.findFirst(Selector.from(PedometerModel.class).where("datestring","=",pedometer.getDatestring()));
					WhereBuilder builder = WhereBuilder.b("currenttime", "==",
							alertEdit.getCurrenttime());
					try {
						db.delete(Alert.class, builder);
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent intent1 = new Intent(Constants.ADD_ALERT_OK);
					sendBroadcast(intent1);
					finish();
				}

			});
			String[] rePeats = alertEdit.getAlertRepeat().split(",");
			String repeat = "";
			for (int i = 0; i < rePeats.length; i++) {

				if ("1".equals(rePeats[i])) {
					switch (i) {
					case 0:
						repeat = repeat + "周日";
						break;
					case 1:
						repeat = repeat + "周一";
						break;
					case 2:
						repeat = repeat + "周二";
						break;
					case 3:
						repeat = repeat + "周三";
						break;
					case 4:
						repeat = repeat + "周四";
						break;
					case 5:
						repeat = repeat + "周五";
						break;
					case 6:
						repeat = repeat + "周六";
						break;

					}
					reReart[i] = true;
				} else {
					reReart[i] = false;
				}
			}
			repeat_time.setText(repeat);
			lable_text.setText(alertEdit.getAlertName());
			if ("1".equals(alertEdit.getAlertmusic())) {
				bellText.setText("布谷鸟");
				bell[0] = true;
			}
			if ("1".equals(alertEdit.getAlertmusic())) {
				bellText.setText("叮当");
				bell[1] = true;
				bell[0] = false;
			}
			if ("1".equals(alertEdit.getAlertmusic())) {
				bellText.setText("梦幻");
				bell[2] = true;
				bell[0] = false;
			}
			String[] times = alertEdit.getAlertTime().split(":");
			int hourIndex = 0;
			if (times[0].startsWith("0")&&!times[0].equals("0")) {
				hourIndex = Integer.parseInt(times[0].replace("0", ""));
			} else {
				hourIndex = Integer.parseInt(times[0]);
			}
			leftWheel.setCurrentItem(hourIndex - 1);
			int miniteIndex = 0;
			if (times[1].startsWith("0")) {
				miniteIndex = Integer.parseInt(times[1].replace("0", ""));
			} else {
				miniteIndex = Integer.parseInt(times[1]);
			}
			rightWheel.setCurrentItem(miniteIndex - 1);
		}
		addBackImage(R.drawable.btn_back_bg, new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		addRightBtn(R.string.save, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 获取AlarmManager对象
				alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
				if (alertEdit != null) {
					clearAlarm();
				}
				DbUtils db = DbUtils.create(ActAddAlert.this);
				db.configAllowTransaction(true);
				db.configDebug(true);
				Alert alert = new Alert();
				alert.setAlertName(lable_text.getText().toString());
				alert.setAlertTime(hours.get(leftWheel.getCurrentItem()) + ":"
						+ times.get(rightWheel.getCurrentItem()));
				String clock = "9" + DateUtil.getCurrentDate().split("-")[1]
						+ DateUtil.getCurrentDate().split("-")[2]
						+ hours.get(leftWheel.getCurrentItem())
						+ times.get(rightWheel.getCurrentItem());
				alert.setClockid(Integer.parseInt(clock));
				String alertRepeat = "";

				for (int i = 0; i < reReart.length; i++) {
					if (reReart[i]) {
						Calendar cal = Calendar.getInstance();

						System.out.println("今天的分钟: "
								+ Integer.parseInt(hours.get(leftWheel
										.getCurrentItem())));
						System.out.println("今天的秒钟: "
								+ Integer.parseInt(times.get(rightWheel
										.getCurrentItem())));
						cal.set(Calendar.HOUR, Integer.parseInt(hours
								.get(leftWheel.getCurrentItem())));
						cal.set(Calendar.MINUTE, Integer.parseInt(times
								.get(rightWheel.getCurrentItem())));
						cal.set(Calendar.SECOND, 0);
						int week=AbDateUtil.getWeekNumber(DateUtil.getCurrentDate(),"yyyy-MM-dd");
						System.out.println("今天的日期: " + cal.getTime());
						long alertTime = cal.getTimeInMillis();
						System.out.println("alertTime>>>>>>>"+alertTime);
						System.out.println("System>>>>>>>"+System.currentTimeMillis()+50000);
						switch (i) {
						case 0:
							// 创建Intent对象，action为android.intent.action.ALARM_RECEIVER
							Intent intentAlarm = new Intent(
									"android.intent.action.ALARM_RECEIVER");
							intentAlarm.putExtra("alert", alert);
							operation = PendingIntent.getBroadcast(
									ActAddAlert.this, alert.getClockid(),
									intentAlarm, 0);
							switch (week) {
							case 0:
								if(alertTime>System.currentTimeMillis()){
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime, 86400000 * 7, operation);
								}else{
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime+86400000 * 7, 86400000 * 7, operation);
								}
								break;
							case 1:
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime+86400000 * 6, 86400000 * 7, operation);
							case 2:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 5, 86400000 * 7, operation);
								break;
							case 3:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 4, 86400000 * 7, operation);
								break;
							case 4:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 3, 86400000 * 7, operation);
								break;
							case 5:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 2, 86400000 * 7, operation);
								break;
							case 6:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 1, 86400000 * 7, operation);
								break;

							
							}
							
							break;
						case 1:
							Intent intentAlarm1 = new Intent(
									"android.intent.action.ALARM_RECEIVER");
							intentAlarm1.putExtra("alert", alert);
							operation = PendingIntent.getBroadcast(
									ActAddAlert.this, alert.getClockid() + 1,
									intentAlarm1, 0);
							switch (week) {
							case 0:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 , 86400000 * 7, operation);
								break;
							case 1:
								if(alertTime>System.currentTimeMillis()){
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime, 86400000 * 7, operation);
								}else{
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime+86400000 * 7, 86400000 * 7, operation);
								}
							case 2:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000*6 , 86400000 * 7, operation);
								break;
							case 3:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 5, 86400000 * 7, operation);
								break;
							case 4:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 4, 86400000 * 7, operation);
								break;
							case 5:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 3, 86400000 * 7, operation);
								break;
							case 6:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 2, 86400000 * 7, operation);
								break;

							
							}
							
							break;
						case 2:
							Intent intentAlarm2 = new Intent(
									"android.intent.action.ALARM_RECEIVER");
							intentAlarm2.putExtra("alert", alert);
							operation = PendingIntent.getBroadcast(
									ActAddAlert.this, alert.getClockid() + 2,
									intentAlarm2, 0);
							switch (week) {
							case 0:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000* 2 , 86400000* 7 , operation);
								break;
							case 1:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 , 86400000* 7 , operation);
							case 2:
								if(alertTime>System.currentTimeMillis()){
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime, 86400000 * 7, operation);
								}else{
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime+86400000 * 7, 86400000 * 7, operation);
								}
								
								break;
							case 3:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 6, 86400000 * 7, operation);
								break;
							case 4:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 5, 86400000 * 7, operation);
								break;
							case 5:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 4, 86400000 * 7, operation);
								break;
							case 6:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 3, 86400000 * 7, operation);
								break;

							
							}
							

							break;
						case 3:
							Intent intentAlarm3 = new Intent(
									"android.intent.action.ALARM_RECEIVER");
							intentAlarm3.putExtra("alert", alert);
							operation = PendingIntent.getBroadcast(
									ActAddAlert.this, alert.getClockid() + 3,
									intentAlarm3, 0);
							switch (week) {
							case 0:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000* 3 , 86400000*7 , operation);
								break;
							case 1:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000*2 , 86400000*7 , operation);
							case 2:
								
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 , 86400000 * 7, operation);
								break;
							case 3:
								if(alertTime>System.currentTimeMillis()){
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime, 86400000 * 7, operation);
								}else{
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime+86400000 * 7, 86400000 * 7, operation);
								}
								
								break;
							case 4:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 6, 86400000 * 7, operation);
								break;
							case 5:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 5, 86400000 * 7, operation);
								break;
							case 6:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 4, 86400000 * 7, operation);
								break;

							
							}
							
							break;
						case 4:
							Intent intentAlarm4 = new Intent(
									"android.intent.action.ALARM_RECEIVER");
							intentAlarm4.putExtra("alert", alert);
							operation = PendingIntent.getBroadcast(
									ActAddAlert.this, alert.getClockid() + 4,
									intentAlarm4, 0);
							switch (week) {
							case 0:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000* 4, 86400000*7 , operation);
								break;
							case 1:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000*3 , 86400000*7 , operation);
							case 2:
								
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000*2 , 86400000 * 7, operation);
								break;
							case 3:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 , 86400000 * 7, operation);
								
								
								break;
							case 4:
								if(alertTime>System.currentTimeMillis()){
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime, 86400000 * 7, operation);
								}else{
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime+86400000 * 7, 86400000 * 7, operation);
								}
								break;
							case 5:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 6, 86400000 * 7, operation);
								break;
							case 6:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 5, 86400000 * 7, operation);
								break;

							
							}
							
							break;
						case 5:
							Intent intentAlarm5 = new Intent(
									"android.intent.action.ALARM_RECEIVER");
							intentAlarm5.putExtra("alert", alert);
							operation = PendingIntent.getBroadcast(
									ActAddAlert.this, alert.getClockid() + 5,
									intentAlarm5, 0);
							switch (week) {
							case 0:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000* 5, 86400000*7 , operation);
								break;
							case 1:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000*4 , 86400000*7 , operation);
							case 2:
								
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000*3 , 86400000 * 7, operation);
								break;
							case 3:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000*2 , 86400000 * 7, operation);
								
								
								break;
							case 4:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 , 86400000 * 7, operation);
								
								break;
							case 5:
								if(alertTime>System.currentTimeMillis()){
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime, 86400000 * 7, operation);
								}else{
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime+86400000 * 7, 86400000 * 7, operation);
								}
								break;
							case 6:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 * 6, 86400000 * 7, operation);
								break;

							
							}
							
							break;
						case 6:
							Intent intentAlarm6 = new Intent(
									"android.intent.action.ALARM_RECEIVER");
							intentAlarm6.putExtra("alert", alert);
							operation = PendingIntent.getBroadcast(
									ActAddAlert.this, alert.getClockid() + 6,
									intentAlarm6, 0);
							switch (week) {
							case 0:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000* 6, 86400000*7 , operation);
								break;
							case 1:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000*5 , 86400000*7 , operation);
							case 2:
								
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000*4 , 86400000 * 7, operation);
								break;
							case 3:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000*3 , 86400000 * 7, operation);
								
								
								break;
							case 4:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000*2 , 86400000 * 7, operation);
								
								break;
							case 5:
								alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
										alertTime+86400000 , 86400000 * 7, operation);
								
								break;
							case 6:
								if(alertTime>System.currentTimeMillis()){
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime, 86400000 * 7, operation);
								}else{
									alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
											alertTime+86400000 * 7, 86400000 * 7, operation);
								}
								break;

							
							}
							break;

						}

						if (i != 6) {
							alertRepeat = alertRepeat + "1,";
						} else {
							alertRepeat = alertRepeat + "1";
						}

					} else {
						if (i != 6) {
							alertRepeat = alertRepeat + "0,";
						} else {
							alertRepeat = alertRepeat + "0";
						}
					}
				}
				String alertMusic = "";
				for (int i = 0; i < bell.length; i++) {
					if (bell[i]) {
						alertMusic = i + "";
					}
				}
				alert.setCurrenttime(System.currentTimeMillis());
				alert.setAlertmusic(alertMusic);
				alert.setAlertRepeat(alertRepeat);
				try {

					if (alertEdit != null) {
						WhereBuilder builder = WhereBuilder.b("currenttime",
								"==", alertEdit.getCurrenttime());
						db.update(alert, builder, "alertname", "alerttime",
								"alertrepeat", "alertmusic");
					} else {
						db.saveBindingId(alert);
					}

				} catch (DbException e) {
					e.printStackTrace();
				}

				Intent intent = new Intent(Constants.ADD_ALERT_OK);
				sendBroadcast(intent);
				finish();
			}
		});

		alert_repeat_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActAddAlert.this,
						ActAlertRepeart.class);
				intent.putExtra("reReart", reReart);
				startActivityForResult(intent, 0);
			}
		});
		labelLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActAddAlert.this, ActLable.class);
				intent.putExtra("lable", lable_text.getText().toString());
				startActivityForResult(intent, 2);
			}
		});
		RingLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActAddAlert.this, ActBell.class);
				intent.putExtra("bell", bell);
				startActivityForResult(intent, 1);
			}
		});
		setTitle("添加闹钟");
	}

	/**
	 * 取消闹钟
	 */
	private void clearAlarm() {
		Intent intent = new Intent("android.intent.action.ALARM_RECEIVER");
		for (int i = 0; i < reReart.length; i++) {
			if (reReart[i]) {

				switch (i) {
				case 0:
					operation = PendingIntent.getBroadcast(ActAddAlert.this,
							alertEdit.getClockid(), intent, 0);
					alarmManager.cancel(operation);
					break;
				case 1:
					operation = PendingIntent.getBroadcast(ActAddAlert.this,
							alertEdit.getClockid() + 1, intent, 0);
					alarmManager.cancel(operation);
					break;
				case 2:
					operation = PendingIntent.getBroadcast(ActAddAlert.this,
							alertEdit.getClockid() + 2, intent, 0);
					alarmManager.cancel(operation);

					break;
				case 3:
					operation = PendingIntent.getBroadcast(ActAddAlert.this,
							alertEdit.getClockid() + 3, intent, 0);
					alarmManager.cancel(operation);

					break;
				case 4:
					operation = PendingIntent.getBroadcast(ActAddAlert.this,
							alertEdit.getClockid() + 4, intent, 0);
					alarmManager.cancel(operation);
					break;
				case 5:
					operation = PendingIntent.getBroadcast(ActAddAlert.this,
							alertEdit.getClockid() + 5, intent, 0);
					alarmManager.cancel(operation);

					break;
				case 6:
					operation = PendingIntent.getBroadcast(ActAddAlert.this,
							alertEdit.getClockid() + 6, intent, 0);
					alarmManager.cancel(operation);
					break;

				}

			}

		}
	}

	private void initData() {
		leftWheel = (com.androidex.appformwork.wheelview.WheelView) findViewById(R.id.leftwheel); // 只有一个
		leftWheel.setCyclic(true);
		hours = new ArrayList<String>();
		hours.add("0");
		hours.add("1");
		hours.add("2");
		hours.add("3");
		hours.add("4");
		hours.add("5");
		hours.add("6");
		hours.add("7");
		hours.add("8");
		hours.add("9");
		hours.add("10");
		hours.add("11");
		hours.add("12");
		hours.add("13");
		hours.add("14");
		hours.add("15");
		hours.add("16");
		hours.add("17");
		hours.add("18");
		hours.add("19");
		hours.add("20");
		hours.add("21");
		hours.add("22");
		hours.add("23");
		leftWheel.setViewAdapter(new TimeWheelAdapter(hours, ActAddAlert.this));

		com.androidex.appformwork.wheelview.WheelView ccwvRight = (com.androidex.appformwork.wheelview.WheelView) findViewById(R.id.rightwheel); // 只有一个
		ccwvRight.setCyclic(true);
		times = new ArrayList<String>();
		times.add("0");
		times.add("1");
		times.add("2");
		times.add("3");
		times.add("4");
		times.add("5");
		times.add("6");
		times.add("7");
		times.add("8");
		times.add("9");
		times.add("10");
		times.add("11");
		times.add("12");
		times.add("13");
		times.add("14");
		times.add("15");
		times.add("16");
		times.add("17");
		times.add("18");
		times.add("19");
		times.add("20");
		times.add("21");
		times.add("22");
		times.add("23");
		times.add("24");
		times.add("25");
		times.add("26");
		times.add("27");
		times.add("28");
		times.add("29");
		times.add("30");
		times.add("31");
		times.add("32");
		times.add("33");
		times.add("34");
		times.add("35");
		times.add("36");
		times.add("37");
		times.add("38");
		times.add("39");
		times.add("40");
		times.add("41");
		times.add("42");
		times.add("43");
		times.add("44");
		times.add("45");
		times.add("46");
		times.add("47");
		times.add("48");
		times.add("49");
		times.add("50");
		times.add("51");
		times.add("52");
		times.add("53");
		times.add("54");
		times.add("55");
		times.add("56");
		times.add("57");
		times.add("58");
		times.add("59");
		ccwvRight.setViewAdapter(new TimeWheelAdapter(times, ActAddAlert.this));
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1 == RESULT_OK) {
			reReart = arg2.getBooleanArrayExtra("reReart");
			String repeat = "";
			for (int i = 0; i < reReart.length; i++) {
				if (reReart[i]) {
					switch (i) {
					case 0:
						repeat = repeat + "周日  ";
						break;
					case 1:
						repeat = repeat + "周一  ";
						break;
					case 2:
						repeat = repeat + "周二  ";
						break;
					case 3:
						repeat = repeat + "周三  ";
						break;
					case 4:
						repeat = repeat + "周四  ";
						break;
					case 5:
						repeat = repeat + "周五  ";
						break;
					case 6:
						repeat = repeat + "周六  ";
						break;

					}
				}
			}
			repeat_time.setText(repeat);
		} else if (arg1 == 201) {
			bell = arg2.getBooleanArrayExtra("bell");
			String bells = "";
			for (int i = 0; i < bell.length; i++) {
				if (bell[i]) {
					switch (i) {
					case 0:
						bells = bells + "布谷鸟  ";
						break;
					case 1:
						bells = bells + "叮当  ";
						break;
					case 2:
						bells = bells + "梦幻  ";
						break;

					}
				}
			}
			bellText.setText(bells);
		} else if (arg1 == 202) {
			lable = arg2.getStringExtra("lable");
			lable_text.setText(lable);

		}

	}
}
