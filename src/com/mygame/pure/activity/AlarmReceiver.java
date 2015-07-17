package com.mygame.pure.activity;

import com.mygame.pure.bean.Alert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	private Alert alert;

	@Override
	public void onReceive(Context context, Intent arg1) {
		// 此处可以添加闹钟铃声
		// System.out.println("我是闹钟，我要叫醒你...");
		// Toast.makeText(arg0, "我是闹钟，我要叫醒你...", Toast.LENGTH_SHORT).show();
		Intent intentAlarm = new Intent(context, ActReceiveAlarm.class);
		alert = (Alert) arg1.getSerializableExtra("alert");
		intentAlarm.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		intentAlarm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intentAlarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intentAlarm.putExtra("alert", alert);

		context.startActivity(intentAlarm);
	}

}
