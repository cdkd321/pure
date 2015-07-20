package com.mygame.pure.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ab.soap.AbSoapListener;
import com.ab.soap.AbSoapParams;
import com.ab.soap.AbSoapUtil;
import com.mygame.pure.R;
import com.mygame.pure.view.PureActionBar;

public class ActWriteYz extends BaseActivity implements OnClickListener {
	protected PureActionBar mTkActionBar;
	private EditText mywrite;
	private Button btn_getNum_code;
	private AbSoapUtil mAbSoapUtil = null;
	private ProgressDialog pd = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.AppBaseTheme);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_writyz);
		initView();
		mAbSoapUtil = AbSoapUtil.getInstance(this);
		mAbSoapUtil.setTimeout(10000);
	}

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

	public void initView() {
		setTitle(getResources().getString(R.string.yzyx));
		pd = new ProgressDialog(ActWriteYz.this);
		mywrite = (EditText) findViewById(R.id.mywirte);
		btn_getNum_code = (Button) findViewById(R.id.btn_getNum_code);
		btn_getNum_code.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_getNum_code:
			YzEmail();// 验证邮箱码
			break;

		default:
			break;
		}
	}

	public void YzEmail() {
		pd.setCanceledOnTouchOutside(false);
		pd.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
			}
		});
		pd.setMessage(getResources().getString(R.string.isdoing));
		pd.show();
		String urlString = "http://miliapp.ebms.cn/webservice/emailAndyanzhen.asmx?op=SecurityEmial";
		String nameSpace = "http://tempuri.org/";
		String methodName = "SecurityEmial";
		AbSoapParams params = new AbSoapParams();
		params.put("user1", "APP");
		params.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params.put("securityCode", mywrite.getText().toString());
		params.put("email", getIntent().getStringExtra("userName"));

		mAbSoapUtil.call(urlString, nameSpace, methodName, params,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						if (arg1 != null) {
							String[] a = arg1.split("=");
							String[] b = a[1].split(";");
							if (b[0].equals("0")) {
								Toast.makeText(getApplicationContext(),
										getResources().getString(R.string.Pol),
										1).show();
								pd.dismiss();
								return;
							} else if (b[0].equals("1")) {
								Toast.makeText(
										getApplicationContext(),
										getResources().getString(
												R.string.Pleasehss), 1).show();
								Intent intent = new Intent();
								intent.putExtra("userName", getIntent()
										.getStringExtra("userName"));
								intent.setClass(ActWriteYz.this,
										ActSetPwd.class);
								startActivity(intent);
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
}
