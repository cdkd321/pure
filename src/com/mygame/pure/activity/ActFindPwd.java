package com.mygame.pure.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ab.soap.AbSoapListener;
import com.ab.soap.AbSoapParams;
import com.ab.soap.AbSoapUtil;
import com.mygame.pure.R;
import com.mygame.pure.http.AjaxCallBack;
import com.mygame.pure.http.AjaxParams;
import com.mygame.pure.view.PureActionBar;

public class ActFindPwd extends BaseActivity implements OnClickListener {
	protected PureActionBar mTkActionBar;
	private Button btn_getNum;
	private EditText et_email_2;
	private AbSoapUtil mAbSoapUtil = null;
	public ProgressDialog pd = null;

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
		setTheme(R.style.AppBaseTheme);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.findpwd);
		pd = new ProgressDialog(ActFindPwd.this);
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
		et_email_2 = (EditText) findViewById(R.id.et_email_2);
		mAbSoapUtil = AbSoapUtil.getInstance(this);
		mAbSoapUtil.setTimeout(10000);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_getNum:
			if (!et_email_2.getText().toString().equals("")) {
				if (getIntent().getStringExtra("type").equals("1")) {
					// 找回密码
					findpwd();
				} else if (getIntent().getStringExtra("type").equals("2")) {

					pd.setCanceledOnTouchOutside(false);
					pd.setOnCancelListener(new OnCancelListener() {

						@Override
						public void onCancel(DialogInterface dialog) {
						}
					});
					pd.setMessage("验证中...");
					pd.show();
					// 判断邮箱是否已经被注册
					Isregist();
				}

			} else {
				Toast.makeText(getApplicationContext(), "请输入邮箱地址", 1).show();
			}
			break;

		default:
			break;
		}
	}

	public void findpwd() {
		final ProgressDialog pd = new ProgressDialog(ActFindPwd.this);
		pd.setCanceledOnTouchOutside(false);
		pd.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
			}
		});
		pd.setMessage("验证码发送中...");
		pd.show();
		// 发送验证码
		String urlString = "http://miliapp.ebms.cn/webservice/member.asmx?op=FindPassword";
		String nameSpace = "http://tempuri.org/";
		String methodName = "FindPassword";
		AbSoapParams params = new AbSoapParams();
		params.put("user1", "APP");
		params.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params.put("username", et_email_2.getText().toString());
		params.put("id", "10");

		mAbSoapUtil.call(urlString, nameSpace, methodName, params,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						pd.dismiss();
						// TODO Auto-generated method stub
						if (arg1 != null) {
							String[] a = arg1.split("=");
							String[] b = a[1].split(";");
							if (b[0].equals("0")) {
								Toast.makeText(getApplicationContext(), "失败", 1)
										.show();
							} else if (b[0].equals("-1")) {
								Toast.makeText(getApplicationContext(),
										"接口验证错误", 1).show();
							} else if (b[0].equals("-3")) {
								Toast.makeText(getApplicationContext(),
										"用户不存在", 1).show();
							} else if (b[0].equals("1")) {
								Toast.makeText(getApplicationContext(),
										"邮件发送成功", 1).show();
								finish();
							} else if (b[0].equals("-2")) {
								Toast.makeText(getApplicationContext(),
										"邮件发送失败", 1).show();
							}
						}
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "请求失败" + arg1,
								1).show();
						pd.dismiss();
					}
				});
	}

	public void Isregist() {
		String urlString = "http://miliapp.ebms.cn/webservice/member.asmx?op=IfRegister";
		String nameSpace = "http://tempuri.org/";
		String methodName = "IfRegister";
		AbSoapParams params = new AbSoapParams();
		params.put("user1", "APP");
		params.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params.put("username", getIntent().getStringExtra("userName"));

		mAbSoapUtil.call(urlString, nameSpace, methodName, params,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						if (arg1 != null) {
							String[] a = arg1.split("=");
							String[] b = a[1].split(";");
							if (b[0].equals("1")) {
								setYzM();
							} else if (b[0].equals("0")) {
								Toast.makeText(getApplicationContext(),
										"该邮箱已经被注册", 1).show();
								pd.dismiss();
							}
						}
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "请求失败" + arg1,
								1).show();
						pd.dismiss();
					}
				});
	}

	public void setYzM() {
		// 发送验证码
		String urlString = "http://miliapp.ebms.cn/webservice/emailAndyanzhen.asmx?op=AddEmailcode";
		String nameSpace = "http://tempuri.org/";
		String methodName = "AddEmailcode";
		AbSoapParams params = new AbSoapParams();
		params.put("user1", "APP");
		params.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params.put("email", et_email_2.getText().toString());
		params.put("id", "8");

		mAbSoapUtil.call(urlString, nameSpace, methodName, params,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						pd.dismiss();
						// TODO Auto-generated method stub
						if (arg1 != null) {
							String[] a = arg1.split("=");
							String[] b = a[1].split(";");
							if (b[0].equals("0")) {
								Toast.makeText(getApplicationContext(), "失败", 1)
										.show();
							} else if (b[0].equals("1")) {
								Intent intent = new Intent();
								intent.putExtra("updatepwd", "0");
								intent.putExtra("userName", et_email_2
										.getText().toString());
								intent.setClass(ActFindPwd.this,
										ActWriteYz.class);
								startActivity(intent);
							} else if (b[0].equals("-1")) {
								Toast.makeText(getApplicationContext(),
										"该邮箱已经发送过邮件", 1).show();
							} else if (b[0].equals("-2")) {
								Toast.makeText(getApplicationContext(),
										"邮件发送失败", 1).show();
							}
						}
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "请求失败" + arg1,
								1).show();
						pd.dismiss();
					}
				});
	}
}
