package com.mygame.pure.activity;

import android.os.Bundle;

import com.mygame.pure.R;

public class ActFAQ extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_faq);
		addBackImage(R.drawable.back_pressed, null);
		setTitle("FAQ");
	}

}