package com.mygame.pure.activity;

import android.os.Bundle;

import com.mygame.pure.R;
import com.mygame.pure.view.UIItem;

public class MoreAct extends BaseActivity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_more);
		
		UIItem ui_settings = (UIItem) findViewById(R.id.ui_settings);
		UIItem ui_hufu = (UIItem) findViewById(R.id.ui_hufu);
		UIItem ui_pwd = (UIItem) findViewById(R.id.ui_pwd);
		UIItem ui_yijian = (UIItem) findViewById(R.id.ui_yijian);
		
		
//		ui_settings.setValue(R.drawable.sitting_pressed, R.string._settings, " ");
//		ui_hufu.setValue(R.drawable.drop_pressed, "护肤设置, " ");
//		ui_pwd.setValue(R.drawable.sitting_pressed, R.string._settings, " ");
//		ui_yijian.setValue(R.drawable.sitting_pressed, R.string._settings, " ");
		
		
	}
	
}
