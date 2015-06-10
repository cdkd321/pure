package com.mygame.pure.utils;

import com.google.gson.Gson;
import com.mygame.pure.bean.Constant;
import com.mygame.pure.info.User;
import com.mygame.pure.login.Login;

public class UserUtils {

	public static void saveUserInfo(Login activity, User response) {
		 String json = new Gson().toJson(response);
		 Constant.Preference.getSharedPreferences(activity).edit().putString(Constant.Preference.LOGIN_USER, json).commit();
	}

}
