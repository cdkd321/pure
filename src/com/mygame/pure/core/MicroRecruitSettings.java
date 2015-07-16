package com.mygame.pure.core;

import android.content.Context;
import android.content.SharedPreferences;

public class MicroRecruitSettings extends AppSettings {

	private static final String SHARED_PREFERENCES_NAME = "pmway.settings";

	private final SharedPreferences mGlobalPreferences;

	public MicroRecruitSettings(Context context) {
		mGlobalPreferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
	}

	@Override
	public SharedPreferences getGlobalPreferences() {
		return mGlobalPreferences;
	}

	// 判断用户名是否存在
	public StringPreference USER_NAME = new StringPreference("username", "");// 用户名
	public StringPreference USER_head = new StringPreference("userhead", "");// 头像地址
	public StringPreference USER_nick = new StringPreference("usernick", "");// 昵称
	public StringPreference USER_AGE = new StringPreference("age", "");// 昵称

}
