package com.mygame.pure.log;

import android.util.Log;

public class MyLog {

	static boolean isDebug = true;
	
	public static void i(String tag, String msg) {
		if(isDebug){
			Log.i(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if(isDebug){
			Log.e(tag, msg);
		}
	}

}
