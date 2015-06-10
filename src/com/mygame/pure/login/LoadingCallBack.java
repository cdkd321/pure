package com.mygame.pure.login;

import android.app.ProgressDialog;
import android.content.Context;

import com.mygame.pure.http.AjaxCallBack;

/**
 * @discription  回调处理过程,增加一个loading bar,用一个控制变量显示或者隐藏。
 * 在构造方法中设置
 * @author tom
 * @param <T>
 */
public class LoadingCallBack<T> extends AjaxCallBack<T> {

	Context context;
	ProgressDialog pDialog;
	private boolean isShowLoading = false;
	private String message;
	
	protected void setShowDialog() {
		isShowLoading = true;
	 
	}
	
	protected void setDialogMessage(String string) {
		  this.message = string;
	}
	
	public LoadingCallBack(Context context) {
		this.context = context;
	}

	@Override
	public void onStart() {
		if(isShowLoading){
			pDialog = new ProgressDialog(context);
			pDialog.setMessage(message);
			pDialog.setCancelable(false);
			pDialog.show();
		}
	}

	@Override
	public void onSuccess(T result) {
		if(isShowLoading && pDialog != null){
			pDialog.dismiss();
		}
	}

	@Override
	public void onFailure(Throwable t, int errorNo, String strMsg) {
		super.onFailure(t, errorNo, strMsg);
		if(isShowLoading && pDialog != null){
			pDialog.dismiss();
		}
	}
}
