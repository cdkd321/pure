package com.mygame.pure.activity;

import android.os.Bundle;
import android.view.View;

import com.mygame.pure.R;

public class ActFAQ extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_faq);
		addBackImage(R.drawable.back_pressed, null);
		getTkActionBar().setRightView(new View(ActFAQ.this), null);
		setTitle(getString(R.string.faq));
	}

}
