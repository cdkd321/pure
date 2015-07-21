package com.mygame.pure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.mygame.pure.R;

public class ActAlertRepeart extends BaseActivity {
	private CheckBox checkBox1;
	private CheckBox checkBox2;
	private CheckBox checkBox3;
	private CheckBox checkBox4;
	private CheckBox checkBox5;
	private CheckBox checkBox6;
	private CheckBox checkBox7;
	private boolean[] reReart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_alert_repeart);
		checkBox1 = (CheckBox) findViewById(R.id.check1);
		checkBox2 = (CheckBox) findViewById(R.id.check2);
		checkBox3 = (CheckBox) findViewById(R.id.check3);
		checkBox4 = (CheckBox) findViewById(R.id.check4);
		checkBox5 = (CheckBox) findViewById(R.id.check5);
		checkBox6 = (CheckBox) findViewById(R.id.check6);
		checkBox7 = (CheckBox) findViewById(R.id.check7);
		reReart = getIntent().getBooleanArrayExtra("reReart");
		if (reReart[0]) {
			checkBox1.setChecked(true);
		}
		if (reReart[1]) {
			checkBox2.setChecked(true);
		}
		if (reReart[2]) {
			checkBox3.setChecked(true);
		}
		if (reReart[3]) {
			checkBox4.setChecked(true);
		}
		if (reReart[4]) {
			checkBox5.setChecked(true);
		}
		if (reReart[5]) {
			checkBox6.setChecked(true);
		}
		if (reReart[6]) {
			checkBox7.setChecked(true);
		}
		checkBox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				reReart[0] = arg1;
			}
		});
		checkBox2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				reReart[1] = arg1;
			}
		});
		checkBox3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				reReart[2] = arg1;
			}
		});
		checkBox4.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				reReart[3] = arg1;
			}
		});
		checkBox5.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				reReart[4] = arg1;
			}
		});
		checkBox6.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				reReart[5] = arg1;
			}
		});
		checkBox7.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				reReart[6] = arg1;
			}
		});
		addBackImage(R.drawable.btn_back_bg, new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("reReart", reReart);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		addRightBtn("", null);
		setTitle(getString(R.string.Repeat));
	}

}
