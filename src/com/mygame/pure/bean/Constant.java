package com.mygame.pure.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

public class Constant {

	public static final long MIN_LOGINTIME = 0;
	public static class Preference {

		public static final String LOGIN_USER = null;
		public static final String UNAME = null;
		public static final String PWD = null;

		public static SharedPreferences getSharedPreferences(Context context) {
			return context.getSharedPreferences("MyFellowShipSharePref", Context.MODE_PRIVATE);
		}
	}

}
