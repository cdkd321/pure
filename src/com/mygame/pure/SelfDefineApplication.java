package com.mygame.pure;

import android.app.Application;

import com.mygame.pure.ble.BleService;
import com.mygame.pure.http.FinalHttp;
import com.mygame.pure.info.User;
import com.mygame.pure.utils.DeviceConfiger;

public class SelfDefineApplication extends Application {
	public BleService mService;
	public static  boolean  isStop;
	public static boolean finishLogin;
	private static SelfDefineApplication application;
	private static FinalHttp finalHttp;
	public static int selectPostion;

	public static SelfDefineApplication getInstance() {
		return application;
	}

	public User getUser() {
		return null;
	}

	public FinalHttp getFinalHttp() {
		if (finalHttp == null) {
			finalHttp = new FinalHttp();
		}
		return finalHttp;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		finalHttp = getFinalHttp();
		application = this;
		DeviceConfiger.init();
	}

}
