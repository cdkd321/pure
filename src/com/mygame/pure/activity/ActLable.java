package com.mygame.pure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.mygame.pure.R;

public class ActLable extends BaseActivity {
	private EditText etLable;
	private String lable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actlable_layout);
		lable = getIntent().getStringExtra("lable");
		etLable = (EditText) findViewById(R.id.etLable);
		etLable.setText(lable);
		etLable.setSelection(lable.length());
		addBackImage(R.drawable.btn_back_bg, new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("lable", etLable.getText().toString());
				setResult(202, intent);
				finish();
			}
		});
		setTitle(getString(R.string.Tag));
	}

}
