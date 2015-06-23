package com.mygame.pure.activity;

import com.mygame.pure.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 关于界面
 * @author tom
 */
public class ActSpecify extends BaseActivity implements OnClickListener {
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_specify);
		addBackImage(R.drawable.back_pressed, null);
		setTitle("详情");
	}

	@Override
	public void onClick(View v) {
		 
	}
 
}
