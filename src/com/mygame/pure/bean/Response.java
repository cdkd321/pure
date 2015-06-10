package com.mygame.pure.bean;

import org.json.JSONException;
import org.json.JSONObject;
 

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author 
 * 
 * @param <T>
 */
public class Response<T> {

	public Response(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	String code;
	String msg;
	String valicode;
	T data;
	String token;

	/**
	 * 获取接口返回状态（自动跳转）和弹出错误提示
	 * @param context
	 * @return
	 */
	public boolean getResultAndTip(Context context) {
		if ("C0000".equals(code) || "C0010".equals(code)) {
			return true;
		}
		if (null != context) {
			// 无数据不提示
			if (!"C0010".equals(code) && !"C0005".equals(code) && !"C0008".equals(code)) {
				Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			}
		}
		return false;
	}

	/**
	 * 只获取接口返回状态
	 * 
	 * @param context
	 * @return
	 */
	public boolean getResult(Context context) {
		if ("C0000".equals(code)) {
			return true;
		}
		return false;
	}

	public String getMessage() {
		return msg;
	}

	public T getResponse() {
		return data;
	}

	/**
	 * 构建异常消息josn
	 * 
	 * @param value
	 * @return
	 */
	public static String setFailStr(String value) {
		JSONObject object = new JSONObject();
		try {
			object.put("msg", value);
			object.put("code", "0000");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object.toString();
	}

	public String getValiCode() {
		return this.valicode;
	}

	public String getCode() {
		return this.code;
	}

	public String getToken() {
		return this.token;
	}
}
