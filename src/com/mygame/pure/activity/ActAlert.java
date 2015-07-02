package com.mygame.pure.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.mygame.pure.R;
import com.mygame.pure.adapter.AlertAdapter;
import com.mygame.pure.bean.Alert;
import com.mygame.pure.utils.Constants;
import com.mygame.pure.utils.DbUtils;

public class ActAlert extends BaseActivity {
	private ListView alert_list;
	private AlertAdapter mAlertAdapter;
	private TextView addAlertText;
	private ImageButton back_btn;
	private List<Alert> alerts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_alert);
		alert_list = (ListView) findViewById(R.id.alert_list);
		addAlertText = (TextView) findViewById(R.id.addAlertText);
		back_btn = (ImageButton) findViewById(R.id.back_btn);
		alerts = new ArrayList<Alert>();
		mAlertAdapter = new AlertAdapter(ActAlert.this, alerts);
		alert_list.setAdapter(mAlertAdapter);
		registerBoradcastReceiver();
		refreshData();
		addAlertText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActAlert.this, ActAddAlert.class);
				startActivity(intent);
			}
		});
		back_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		alert_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Alert alert = alerts.get(arg2);
				Intent intent = new Intent(ActAlert.this, ActAddAlert.class);
				intent.putExtra("isEdit", true);
				intent.putExtra("alert", alert);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	private void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();

		myIntentFilter.addAction(Constants.ADD_ALERT_OK);
		// 注册广播
		getActivity().registerReceiver(mReceiver, myIntentFilter);
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Constants.ADD_ALERT_OK.equals(action)) {
				refreshData();

			}

		}

	};

	private void refreshData() {
		DbUtils db = DbUtils.create(getActivity());
		try {
			alerts = db.findAll(Selector.from(Alert.class));
			if (alerts != null) {
				mAlertAdapter.setAlerts(alerts);
			}
		} catch (DbException e1) {
			e1.printStackTrace();
		}
	};

}
