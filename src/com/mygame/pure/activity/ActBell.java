package com.mygame.pure.activity;

import java.io.IOException;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.mygame.pure.R;

public class ActBell extends BaseActivity {
	private boolean[] bell;
	private CheckBox checkBox1;
	private CheckBox checkBox2;
	private CheckBox checkBox3;
	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actbell_layout);
		bell = getIntent().getBooleanArrayExtra("bell");
		checkBox1 = (CheckBox) findViewById(R.id.checkbox1);
		checkBox2 = (CheckBox) findViewById(R.id.checkbox2);
		checkBox3 = (CheckBox) findViewById(R.id.checkbox3);
		if (bell[0]) {
			checkBox1.setChecked(true);
		}
		if (bell[1]) {
			checkBox2.setChecked(true);
		}
		if (bell[2]) {
			checkBox3.setChecked(true);
		}
		checkBox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					checkBox2.setChecked(false);
					checkBox3.setChecked(false);
					startAlarm();
				}
				bell[0] = arg1;
			}
		});
		checkBox2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					checkBox1.setChecked(false);
					checkBox3.setChecked(false);
					startAlarm();
				}
				bell[1] = arg1;
			}
		});
		checkBox3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					checkBox1.setChecked(false);
					checkBox2.setChecked(false);
					startAlarm();
				}
				bell[2] = arg1;
			}
		});
		addBackImage(R.drawable.btn_back_bg, new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("bell", bell);
				setResult(201, intent);
				finish();
			}
		});
		setTitle("铃声");
	}

	private void startAlarm() {
		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
		}
		try {
			AssetFileDescriptor fileDescriptor = getAssets().openFd(
					"longke.mp3");
			mediaPlayer
					.setDataSource(fileDescriptor.getFileDescriptor(),
							fileDescriptor.getStartOffset(),
							fileDescriptor.getLength());
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mediaPlayer.start();
	}
}
