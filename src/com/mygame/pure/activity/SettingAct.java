package com.mygame.pure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mygame.pure.R;
import com.mygame.pure.view.UIItem;

public class SettingAct extends BaseActivity {
	private String mAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_setting);

		UIItem ui_alarm = (UIItem) findViewById(R.id.ui_alarm);
		UIItem ui_questions = (UIItem) findViewById(R.id.ui_questions);
		UIItem ui_bluetouth = (UIItem) findViewById(R.id.ui_bluetouth);
		UIItem ui_pwd = (UIItem) findViewById(R.id.ui_pwd);
		UIItem ui_info = (UIItem) findViewById(R.id.ui_info);

		setTitle(getString(R.string._settings));

		addBackImage(R.drawable.btn_back_bg, new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		addRightBtn(R.string.more, new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), MoreAct.class));
			}
		});

		ui_alarm.setValue(-1, -1, R.drawable.next, -1);
		ui_questions.setValue(-1, -1, R.drawable.next, -1);
		ui_bluetouth.setValue(-1, -1, R.drawable.next, -1);
		ui_pwd.setValue(-1, -1, R.drawable.next, -1);
		ui_info.setValue(-1, -1, -1, R.drawable.btn_round_radio_checkbox);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

}
