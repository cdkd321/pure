package com.mygame.pure.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mygame.pure.R;
import com.mygame.pure.activity.ActMain;
import com.mygame.pure.bean.Constant;
import com.mygame.pure.bean.Response;
import com.mygame.pure.info.User;
import com.mygame.pure.utils.ToastHelper;

/**
 * @discription 登录接口回调处理过程
 * @author tom
 */
//public class LoginCallBack extends LoadingCallBack<String> {

//	Button btnLoad;
//	User user;
//	Login activity;
//	ProgressDialog pDialog;
//	Boolean isBackLogin;
//
//	public LoginCallBack(Boolean isBackLogin, Button btnLoad, User user,
//			Login context, boolean isShowLoading) {
//		super(context);
//		if(isShowLoading){
//			setShowDialog();
//		}
//		this.btnLoad = btnLoad;
//		this.user = user;
//		this.activity = context;
//		this.isBackLogin = isBackLogin;
//	}
//
//	@Override
//	public void onStart() {
//		setDialogMessage(activity.getString(R.string.loginggg));
//		super.onStart();
//	}
//
//	@Override
//	public void onSuccess(String result) {
//		super.onSuccess(result);
//		parseData(result);
//	}
//
//	public void parseData(String result) {
//		Response<User> response = new Gson().fromJson(result,
//				new TypeToken<Response<User>>() {
//				}.getType());
//
//		if (!isBackLogin) {
//			btnLoad.setEnabled(true);
//			btnLoad.setText(R.string.login);
//		}
//		
//		try {
//			long loginTime = System.currentTimeMillis() - activity.startTime;
//			if (loginTime < Constant.MIN_LOGINTIME) {
//				Thread.sleep(Constant.MIN_LOGINTIME - loginTime);
//			}
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			ToastHelper.ToastLg("网络异常，请检查网络设置", activity);
//		}
//
//		if ("C0000".equals(response.getCode())) {
////			activity.saveUser();
////			UserUtils.saveUserInfo(activity, response.getResponse());
//			user = response.getResponse();
//			activity.startActivity(new Intent(activity, ActMain.class));
//			activity.finish();
//		} else {
//			activity.onFindView(false);
//			ToastHelper.ToastLg(response.getMessage(), activity);
//		}
//	}
//
//	@Override
//	public void onFailure(Throwable t, int errorNo, String strMsg) {
//		super.onFailure(t, errorNo, strMsg);
//
//		activity.onFindView(false);
//		ToastHelper.ToastLg(strMsg, activity);
//	}
// }
