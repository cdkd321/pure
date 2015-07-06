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

import com.ab.soap.AbSoapListener;
import com.ab.soap.AbSoapParams;
import com.ab.soap.AbSoapUtil;
import com.mygame.pure.R;
import com.mygame.pure.bean.Urls;
import com.mygame.pure.http.AjaxCallBack;
import com.mygame.pure.http.AjaxParams;
import com.mygame.pure.view.PureActionBar;

public class ActFindPwd extends BaseActivity implements OnClickListener {
	protected PureActionBar mTkActionBar;
	private Button btn_getNum;
	private EditText et_email;
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
		setContentView(R.layout.findpwd);
		initView();
	}

	public void initView() {
		if (getIntent().getStringExtra("type").equals("1")) {
			setTitle("找回密码");
		} else if (getIntent().getStringExtra("type").equals("2")) {
			setTitle("注册");
		}

		btn_getNum = (Button) findViewById(R.id.btn_getNum);
		btn_getNum.setOnClickListener(this);
		et_email = (EditText) findViewById(R.id.et_email_2);
		mAbSoapUtil = AbSoapUtil.getInstance(this);
		mAbSoapUtil.setTimeout(10000);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_getNum:
			if (!et_email.getText().equals("")) {
				if (getIntent().getStringExtra("type").equals("1")) {
					/**
					 * 判断该邮箱是否可用即可
					 */
					// getFinalHttp().post(Urls.checkEmail_info,
					// AjaxParamsbyEmail(),
					// majaxEmailCallback);
				} else if (getIntent().getStringExtra("type").equals("2")) {
					// 发送验证码
					String urlString = "http://miliapp.ebms.cn/webservice/emailAndyanzhen.asmx?op=AddEmailcode";
					String nameSpace = "http://tempuri.org/";
					String methodName = "AddEmailcode";
					AbSoapParams params = new AbSoapParams();
					params.put("user1", "APP");
					params.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
					params.put("email", et_email.getText().toString());
					params.put("id", "654321");

					mAbSoapUtil.call(urlString, nameSpace, methodName, params,
							new AbSoapListener() {

								@Override
								public void onSuccess(int arg0, String arg1) {
									// TODO Auto-generated method stub
									if (arg1 != null) {
										String[] a = arg1.split("=");
										String[] b = a[1].split(";");
										if (b[0].equals("0")) {
											Toast.makeText(
													getApplicationContext(),
													"失败", 1).show();
										} else if (b[0].equals("1")) {
											Toast.makeText(
													getApplicationContext(),
													"插入数据库成功", 1).show();
										} else if (b[0].equals("-1")) {
											Toast.makeText(
													getApplicationContext(),
													"该邮箱已经发送过邮件", 1).show();
										} else if (b[0].equals("-2")) {
											Toast.makeText(
													getApplicationContext(),
													"邮件发送失败", 1).show();
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

					// 注册新用户
					/**
					 * 判断邮箱是否存在
					 */
					// getFinalHttp().post(Urls.checkEmail_info,
					// AjaxParamsbyEmail(),
					// majaxEmailCallback);

					String urlString3 = "http://miliapp.ebms.cn/webservice/emailAndyanzhen.asmx?op=SecurityEmial";
					String nameSpace3 = "http://tempuri.org/";
					String methodName3 = "SecurityEmial";
					AbSoapParams params3 = new AbSoapParams();
					params3.put("user1", "APP");
					params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
					params3.put("username", et_email.getText().toString());
					params3.put("securityCode", "654321");

					mAbSoapUtil.call(urlString3, nameSpace3, methodName3,
							params3, new AbSoapListener() {

								@Override
								public void onSuccess(int arg0, String arg1) {
									// TODO Auto-generated method stub
									if (arg1 != null) {
										String[] a = arg1.split("=");
										String[] b = a[1].split(";");
										if (b[0].equals("0")) {
											Toast.makeText(
													getApplicationContext(),
													"邮箱不存在", 1).show();
										} else if (b[0].equals("1")) {
											Toast.makeText(
													getApplicationContext(),
													"邮箱验证通过", 1).show();
											Intent intent2 = new Intent();
											intent2.putExtra("userName",
													et_email.getText()
															.toString());
											intent2.setClass(
													getApplicationContext(),
													ActSetPwd.class);
											startActivity(intent2);
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
