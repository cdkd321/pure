package com.mygame.pure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mygame.pure.R;
import com.mygame.pure.adapter.AlertAdapter;

public class ActAlert extends BaseActivity {
	private ListView alert_list;
	private AlertAdapter mAlertAdapter;
	private TextView addAlertText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_alert);
		alert_list = (ListView) findViewById(R.id.alert_list);
		addAlertText = (TextView) findViewById(R.id.addAlertText);
		mAlertAdapter = new AlertAdapter(ActAlert.this);
		alert_list.setAdapter(mAlertAdapter);
		addAlertText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActAlert.this, ActAddAlert.class);
				startActivity(intent);
			}
		});
	}

}
