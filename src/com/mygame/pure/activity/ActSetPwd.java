package com.mygame.pure.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
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
import com.mygame.pure.core.MicroRecruitSettings;
import com.mygame.pure.view.PureActionBar;

public class ActSetPwd extends BaseActivity implements OnClickListener {
	protected PureActionBar mTkActionBar;
	private Button btn_setpwd;
	private EditText et_pwdagin, et_pwd;
	private AbSoapUtil mAbSoapUtil = null;
	private ProgressDialog pd = null;
	private MicroRecruitSettings settings;

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
		setContentView(R.layout.act_setpassword);
		settings = new MicroRecruitSettings(getApplicationContext());
		initView();
	}

	public void initView() {
		setTitle(R.string.SetLoginPassword);
		pd = new ProgressDialog(ActSetPwd.this);
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
					&& !et_pwd.getText().toString().trim().equals("")) {

				pd.setCanceledOnTouchOutside(false);
				pd.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
					}
				});
				pd.setMessage(getResources().getString(R.string.isdoing));
				pd.show();
				// if (getIntent().getStringExtra("updatepwd").equals("0")) {

				// 判断邮箱中的验证码是否正确
				// 进行注册操作
				// Isregist();
				regist();
				// } else if
				// (getIntent().getStringExtra("updatepwd").equals("1")) {
				// // 验证邮箱
				// // 更改密码
				// // YzEmailfindpwd();
				// }

				/**
				 * 判断邮箱验证码是否正确
				 */
				// getFinalHttp().post(Urls.checkEmail_istrue,
				// AjaxParamsbyEmail(), majaxCallback);
			} else {
				Toast.makeText(getApplicationContext(), R.string.editError, 1)
						.show();
			}
			break;

		default:
			break;
		}
	}

	public void regist() {
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
						Toast.makeText(getApplicationContext(),
								R.string.RegistrationSuccessful, 1).show();
						// TODO Auto-generated method stub
						if (arg1 != null) {
							String[] a = arg1.split("=");
							String[] b = a[1].split(";");
							if (b[0].equals("1")) {
								pd.dismiss();
								Intent intent = new Intent();
								settings.USER_NAME.setValue(getIntent()
										.getStringExtra("userName"));
								intent.putExtra("isgone", "1");
								intent.setClass(getApplicationContext(),
										PersonalCenterActivity.class);
								startActivity(intent);
								ActSetPwd.this.finish();

							} else if (b[0].equals("0")) {
								Toast.makeText(getApplicationContext(),
										R.string.LoadingFailed, 1).show();
								pd.dismiss();
							}
						}
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), arg1, 1).show();
						pd.dismiss();
					}
				});
	}

	// public void Isregist() {
	// String urlString =
	// "http://miliapp.ebms.cn/webservice/member.asmx?op=IfRegister";
	// String nameSpace = "http://tempuri.org/";
	// String methodName = "IfRegister";
	// AbSoapParams params = new AbSoapParams();
	// params.put("user1", "APP");
	// params.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
	// params.put("username", getIntent().getStringExtra("userName"));
	//
	// mAbSoapUtil.call(urlString, nameSpace, methodName, params,
	// new AbSoapListener() {
	// @Override
	// public void onSuccess(int arg0, String arg1) {
	// // TODO Auto-generated method stub
	// if (arg1 != null) {
	// String[] a = arg1.split("=");
	// String[] b = a[1].split(";");
	// if (b[0].equals("1")) {
	// Toast.makeText(getApplicationContext(),
	// "表示可以注册", 1).show();
	// YzEmail();
	// } else if (b[0].equals("0")) {
	// Toast.makeText(getApplicationContext(),
	// "该用户已经被注册", 1).show();
	// pd.dismiss();
	// }
	// }
	// }
	//
	// @Override
	// public void onFailure(int arg0, String arg1, Throwable arg2) {
	// // TODO Auto-generated method stub
	// Toast.makeText(getApplicationContext(), "请求失败" + arg1,
	// 1).show();
	// pd.dismiss();
	// }
	// });
	// }

	// public void YzEmailfindpwd() {
	// String urlString3 =
	// "http://miliapp.ebms.cn/webservice/emailAndyanzhen.asmx?op=SecurityEmial";
	// String nameSpace3 = "http://tempuri.org/";
	// String methodName3 = "SecurityEmial";
	// AbSoapParams params3 = new AbSoapParams();
	// params3.put("user1", "APP");
	// params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
	// params3.put("email", getIntent().getStringExtra("userName"));
	// params3.put("securityCode", et_code.getText().toString());
	// mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
	// new AbSoapListener() {
	// @Override
	// public void onSuccess(int arg0, String arg1) {
	// // TODO Auto-generated method stub
	// if (arg1 != null) {
	// String[] a = arg1.split("=");
	// String[] b = a[1].split(";");
	// if (b[0].equals("0")) {
	// Toast.makeText(getApplicationContext(),
	// "邮箱不存在,或验证有误,请重试", 1).show();
	// pd.dismiss();
	// return;
	// } else if (b[0].equals("1")) {
	// Toast.makeText(getApplicationContext(),
	// "邮箱发送成功,请打开邮件查看密码", 1).show();
	// // updatePwd();
	// }
	// }
	// }
	//
	// @Override
	// public void onFailure(int arg0, String arg1, Throwable arg2) {
	// // TODO Auto-generated method stub
	// Toast.makeText(getApplicationContext(), "请求失败" + arg1,
	// 1).show();
	// pd.dismiss();
	// }
	// });
	// }

	// public void updatePwd() {
	// String urlString3 =
	// "http://miliapp.ebms.cn/webservice/member.asmx?op=UpdatePassWord";
	// String nameSpace3 = "http://tempuri.org/";
	// String methodName3 = "UpdatePassWord";
	// AbSoapParams params3 = new AbSoapParams();
	// params3.put("user1", "APP");
	// params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
	// params3.put("username", getIntent().getStringExtra("userName"));
	// params3.put("pass", et_pwd.getText().toString());
	// mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
	// new AbSoapListener() {
	// @Override
	// public void onSuccess(int arg0, String arg1) {
	// // TODO Auto-generated method stub
	// if (arg1 != null) {
	// String[] a = arg1.split("=");
	// String[] b = a[1].split(";");
	// if (b[0].equals("0")) {
	// Toast.makeText(getApplicationContext(), "重置失败",
	// 1).show();
	// pd.dismiss();
	// return;
	// } else if (b[0].equals("1")) {
	// Toast.makeText(getApplicationContext(), "成功", 1)
	// .show();
	// pd.dismiss();
	// }
	// }
	// }
	//
	// @Override
	// public void onFailure(int arg0, String arg1, Throwable arg2) {
	// // TODO Auto-generated method stub
	// Toast.makeText(getApplicationContext(), "请求失败" + arg1,
	// 1).show();
	// pd.dismiss();
	// }
	// });
	// }
}
