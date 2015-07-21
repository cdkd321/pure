package com.mygame.pure.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.mygame.pure.R;
import com.mygame.pure.bean.Alert;
import com.mygame.pure.utils.DateUtil;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class ActReceiveAlarm extends Activity {

	private MediaPlayer mMediaPlayer;
	private Vibrator vibrator;
	private long[] pattern = { 0, 2000, 1000 };// start from, vibrate till ,
												// pause duration
	private Animation shake;
	private SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a",
			Locale.getDefault());
	private Alert alert;
	private String music="bugu.mp3";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receive_alarm);
		initData();
		initUI();

	}

	private void initUI() {
		alert = (Alert) getIntent().getSerializableExtra("alert");
		playAlarm();

		findViewById(R.id.ibCancelAlarm).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						mMediaPlayer.stop();
						mMediaPlayer.release();
						vibrator.cancel();
						finish();
					}
				});
		
		long time = getIntent().getLongExtra("TIME", 0);
		((TextView) findViewById(R.id.tvTimeRecAct)).setText(DateUtil.getHm());
		// ((TextView)findViewById(R.id.tvTimeRecAct)).setTypeface(Typeface.createFromAsset(getAssets(),
		// "fonts/Raleway-Regular.ttf"));
		shake = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.vibrate_anim);
		findViewById(R.id.ibCancelAlarm).setAnimation(shake);
	}

	private void playAlarm() {

		/*
		 * timerVibrate=new Timer(); timerVibrate.sc
		 */
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(pattern, 0);

		/*
		 * Uri alert = RingtoneManager
		 * .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		 */
		if (mMediaPlayer == null) {
			mMediaPlayer = new MediaPlayer();
		} else {
			mMediaPlayer.stop();
			mMediaPlayer.reset();
		}
		// mMediaPlayer = new MediaPlayer();
		// mMediaPlayer.setDataSource(getApplicationContext(), alert);
		if(alert==null){
			 music = "bugu.mp3";
		}else{
			if ("0".equals(alert.getAlertmusic())) {
				music = "bugu.mp3";
			} else if ("1".equals(alert.getAlertmusic())) {
				music = "lingdang.mp3";
			} else if ("2".equals(alert.getAlertmusic())) {
				music = "menghuan.mp3";
			}
		}
		

		try {
			AssetFileDescriptor fileDescriptor = getAssets().openFd(music);
			mMediaPlayer
					.setDataSource(fileDescriptor.getFileDescriptor(),
							fileDescriptor.getStartOffset(),
							fileDescriptor.getLength());
			getSystemService(AUDIO_SERVICE);

			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
			mMediaPlayer.setLooping(true);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// }

	}

	private void initData() {
		Window wind;
		wind = this.getWindow();
		wind.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		wind.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		wind.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
	}

}
