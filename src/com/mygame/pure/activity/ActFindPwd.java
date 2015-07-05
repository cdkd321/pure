package com.mygame.pure.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mygame.pure.R;
import com.mygame.pure.bean.Urls;
import com.mygame.pure.http.AjaxCallBack;
import com.mygame.pure.http.AjaxParams;
import com.mygame.pure.view.PureActionBar;

public class ActFindPwd extends BaseActivity implements OnClickListener {
	protected PureActionBar mTkActionBar;
	private Button btn_getNum;
	private EditText et_email;

	@Override
	public PureActionBar getTkActionBar() {
		mTkActionBar = (PureActionBar) findViewById(R.id.actionBar);
		return mTkActionBar;
	}

	@Override
	public void setTitle(CharSequence title) {
		getTkActionBar();
		if (mTkActionBar != null) {
			mTkActionBar.setTitle(title, null);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpwd);
		initView();
	}

	public void initView() {
		setTitle("注册");
		btn_getNum = (Button) findViewById(R.id.btn_getNum);
		btn_getNum.setOnClickListener(this);
		et_email = (EditText) findViewById(R.id.et_email);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_getNum:
			if (!et_email.getText().equals("")) {
				Intent intent = new Intent();
				intent.putExtra("email", et_email.getText().toString());
				intent.setClass(getApplicationContext(), ActSetPwd.class);
				startActivity(intent);
				if (getIntent().getStringArrayExtra("type").equals("1")) {
					/**
					 * 判断该邮箱是否可用即可
					 */
					// getFinalHttp().post(Urls.checkEmail_info,
					// AjaxParamsbyEmail(),
					// majaxEmailCallback);
				} else if (getIntent().getStringArrayExtra("type").equals("2")) {
					// 注册新用户
					/**
					 * 判断该用户是否已经被注册
					 */
					// getFinalHttp().post(Urls.checkEmail_info,
					// AjaxParamsbyEmail(),
					// majaxEmailCallback);
				}

			} else {
				Toast.makeText(getApplicationContext(), "请输入邮箱地址", 1).show();
			}
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
		params.put("email", et_email.getText().toString());
		params.put("user1", et_email.getText().toString());
		params.put("pass1", getIntent().getStringExtra("pwd"));
		return params;
	}

	private AjaxParams AjaxParamsbyEmail() {
		AjaxParams params = new AjaxParams();
		params.put("username", et_email.getText().toString());
		params.put("user1", et_email.getText().toString());
		params.put("pass1", getIntent().getStringExtra("pwd"));
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
	protected AjaxCallBack<String> majaxEmailCallback = new AjaxCallBack<String>() {
		@Override
		public void onStart() {
		};

		@Override
		public void onSuccess(String str) {
			super.onSuccess(str);
			try {
				object = new JSONObject(str);
				object.getString("");
				/**
				 * 获取验证码
				 */
				// getFinalHttp().post(Urls.emailAndyanzhen, AjaxParams(),
				// majaxCallback);
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
}
