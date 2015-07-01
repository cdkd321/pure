package com.mygame.pure.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.androidex.appformwork.wheelview.TimeWheelAdapter;
import com.mygame.pure.R;

public class ActAddAlert extends BaseActivity {
	private RelativeLayout alert_repeat_layout;
	private RelativeLayout labelLayout;
	private RelativeLayout RingLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_activity);
		alert_repeat_layout = (RelativeLayout) findViewById(R.id.alert_repeat_layout);
		labelLayout = (RelativeLayout) findViewById(R.id.labelLayout);
		RingLayout = (RelativeLayout) findViewById(R.id.RingLayout);
		initData();
		addBackImage(R.drawable.btn_back_bg, new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		alert_repeat_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActAddAlert.this,
						ActAlertRepeart.class);
				startActivity(intent);
			}
		});
		labelLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		RingLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		setTitle("添加闹钟");
	}

	private void initData() {
		com.androidex.appformwork.wheelview.WheelView ccwv = (com.androidex.appformwork.wheelview.WheelView) findViewById(R.id.leftwheel); // 只有一个
		ccwv.setCyclic(true);
		List<String> hours = new ArrayList<String>();
		hours.add("01");
		hours.add("02");
		hours.add("03");
		hours.add("04");
		hours.add("05");
		hours.add("06");
		hours.add("07");
		hours.add("08");
		hours.add("09");
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
		hours.add("24");
		ccwv.setViewAdapter(new TimeWheelAdapter(hours, ActAddAlert.this));
		com.androidex.appformwork.wheelview.WheelView ccwvRight = (com.androidex.appformwork.wheelview.WheelView) findViewById(R.id.rightwheel); // 只有一个
		ccwvRight.setCyclic(true);
		List<String> times = new ArrayList<String>();
		times.add("01");
		times.add("02");
		times.add("03");
		times.add("04");
		times.add("05");
		times.add("06");
		times.add("07");
		times.add("08");
		times.add("09");
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

}
