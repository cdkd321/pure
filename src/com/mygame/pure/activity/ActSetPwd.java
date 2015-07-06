package com.mygame.pure.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ab.soap.AbSoapListener;
import com.ab.soap.AbSoapParams;
import com.ab.soap.AbSoapUtil;
import com.mygame.pure.R;
import com.mygame.pure.bean.Urls;
import com.mygame.pure.http.AjaxCallBack;
import com.mygame.pure.http.AjaxParams;
import com.mygame.pure.view.PureActionBar;

public class ActSetPwd extends BaseActivity implements OnClickListener {
	protected PureActionBar mTkActionBar;
	private Button btn_setpwd;
	private EditText et_pwdagin, et_pwd, et_code;
	private AbSoapUtil mAbSoapUtil = null;

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
		setContentView(R.layout.act_setpassword);
		initView();
	}

	public void initView() {
		setTitle("登录密码设置");
		et_code = (EditText) findViewById(R.id.et_code);
		btn_setpwd = (Button) findViewById(R.id.btn_setpwd);
		btn_setpwd.setOnClickListener(this);
		et_pwdagin = (EditText) findViewById(R.id.et_pwdagin);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		mAbSoapUtil = AbSoapUtil.getInstance(this);
		mAbSoapUtil.setTimeout(10000);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_setpwd:
			if (et_pwdagin.getText().toString()
					.equals(et_pwd.getText().toString())
					&& !et_pwd.getText().toString().trim().equals("")
					&& !et_code.getText().toString().trim().equals("")) {
				String urlString = "http://miliapp.ebms.cn/webservice/member.asmx?op=Register";
				String nameSpace = "http://tempuri.org/";
				String methodName = "Register";
				AbSoapParams params = new AbSoapParams();
				params.put("user1", "APP");
				params.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
				params.put("username", getIntent().getStringExtra("userName"));
				params.put("pass", et_pwd.getText().toString());
				params.put("appid", "3");

				mAbSoapUtil.call(urlString, nameSpace, methodName, params,
						new AbSoapListener() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								if (arg1 != null) {
									String[] a = arg1.split("=");
									String[] b = a[1].split(";");
									if (b[0].equals("1")) {
										Intent intent = new Intent();
										intent.setClass(
												getApplicationContext(),
												PersonalCenterActivity.class);
										startActivity(intent);
										ActSetPwd.this.finish();
										Toast.makeText(getApplicationContext(),
												"注册成功", 1).show();
									} else if (b[0].equals("0")) {
										Toast.makeText(getApplicationContext(),
												"失败", 1).show();
									}
								}
							}

							@Override
							public void onFailure(int arg0, String arg1,
									Throwable arg2) {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(),
										"请求失败" + arg1, 1).show();
							}
						});

				/**
				 * 判断邮箱验证码是否正确
				 */
				// getFinalHttp().post(Urls.checkEmail_istrue,
				// AjaxParamsbyEmail(), majaxCallback);
			} else {
				Toast.makeText(getApplicationContext(), "输入有误", 1).show();
			}
			break;

		default:
			break;
		}
	}

	private AjaxParams AjaxParamsbyEmail() {
		AjaxParams params = new AjaxParams();
		params.put("code", et_code.getText().toString());
		return params;
	}

	private AjaxParams AjaxParamsreg() {
		AjaxParams params = new AjaxParams();
		params.put("username", getIntent().getStringExtra("email"));
		params.put("pwd", et_pwd.getText().toString());
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
				// 注册操作
				getFinalHttp().post(Urls.registered, AjaxParamsreg(),
						majaxregCallback);
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
	protected AjaxCallBack<String> majaxregCallback = new AjaxCallBack<String>() {
		@Override
		public void onStart() {
		};

		@Override
		public void onSuccess(String str) {
			super.onSuccess(str);
			try {
				object = new JSONObject(str);
				object.getString("");
				// 进入个人中心完善资料

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
