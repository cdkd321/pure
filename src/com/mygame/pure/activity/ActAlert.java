package com.mygame.pure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mygame.pure.R;
import com.mygame.pure.adapter.AlertAdapter;

public class ActAlert extends BaseActivity {
	private ListView alert_list;
	private AlertAdapter mAlertAdapter;
	private TextView addAlertText;
	private ImageButton back_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_alert);
		alert_list = (ListView) findViewById(R.id.alert_list);
		addAlertText = (TextView) findViewById(R.id.addAlertText);
		back_btn=(ImageButton) findViewById(R.id.back_btn);
		mAlertAdapter = new AlertAdapter(ActAlert.this);
		alert_list.setAdapter(mAlertAdapter);
		addAlertText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActAlert.this, ActAddAlert.class);
				startActivityForResult(intent, 0);
			}
		});
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	

}
