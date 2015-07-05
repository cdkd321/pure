package com.mygame.pure.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mygame.pure.R;
import com.mygame.pure.bean.Urls;
import com.mygame.pure.http.AjaxCallBack;
import com.mygame.pure.http.AjaxParams;
import com.mygame.pure.view.PureActionBar;

/**
 * 登录界面
 * 
 * @author Administrator
 * 
 */
public class ActLogin extends BaseActivity implements OnClickListener {
	protected PureActionBar mTkActionBar;
	private Button btnLoad;
	private TextView findpwd, regist;
	private EditText etUname;// 邮箱
	private EditText etPwd;// 密码
	private String ip;

	@Override
	public PureActionBar getTkActionBar() {
		mTkActionBar = (PureActionBar) findViewById(R.id.actionBar);
		return mTkActionBar;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		initView();
		getSetUp();
	}

	@Override
	public void setTitle(CharSequence title) {
		getTkActionBar();
		if (mTkActionBar != null) {
			mTkActionBar.setTitle(title, null);
		}
	}

	public void initView() {
		setTitle("登录");
		etUname = (EditText) findViewById(R.id.etUname);
		etPwd = (EditText) findViewById(R.id.etPwd);
		btnLoad = (Button) findViewById(R.id.btnLoad);
		findpwd = (TextView) findViewById(R.id.findpwd);
		regist = (TextView) findViewById(R.id.regist);
		findpwd.setOnClickListener(this);
		regist.setOnClickListener(this);
		btnLoad.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {

		case R.id.btnLoad:
			// 登录
			// getFinalHttp().post(Urls.Login_info, AjaxParams(),
			// majaxCallback);
			Intent intent2 = new Intent();
			intent2.setClass(ActLogin.this, ActMain.class);
			startActivity(intent2);
			finish();
			break;
		case R.id.findpwd:
			// 找回密码
			intent.putExtra("type", "1");
			intent.setClass(ActLogin.this, ActFindPwd.class);
			startActivity(intent);
			break;
		case R.id.regist:
			// 找回密码
			intent.putExtra("type", "2");
			intent.setClass(ActLogin.this, ActFindPwd.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	/**
	 * 邮箱地址
	 * 
	 * @return
	 */
	private AjaxParams AjaxParams() {
		AjaxParams params = new AjaxParams();
		params.put("user1", etUname.getText().toString());
		params.put("pass1", etPwd.getText().toString());
		params.put("appid", "3");
		params.put("ip", ip);
		params.put("memberid", "41");
		return params;
	}

	private JSONObject object;
	/**
	 * 刚进入界面时候赋值
	 */
	protected AjaxCallBack<String> majaxCallback = new AjaxCallBack<String>() {
		@Override
		public void onStart() {
		};

		@Override
		public void onSuccess(String str) {
			super.onSuccess(str);
			try {
				object = new JSONObject(str);
				object.getString("");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_SHORT)
					.show();
		};
	};

	public void getSetUp() {
		// 获取wifi服务
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		// 判断wifi是否开启
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		ip = intToIp(ipAddress);
	}

	private String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}
}
