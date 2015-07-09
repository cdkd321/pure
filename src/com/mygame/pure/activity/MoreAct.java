package com.mygame.pure.activity;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.soap.AbSoapListener;
import com.ab.soap.AbSoapParams;
import com.ab.soap.AbSoapUtil;
import com.mygame.pure.R;
import com.mygame.pure.SelfDefineApplication;
import com.mygame.pure.ble.BleService;
import com.mygame.pure.core.MicroRecruitSettings;
import com.mygame.pure.utils.Constants;
import com.mygame.pure.view.CircleImageView;
import com.mygame.pure.view.UIItem;
import com.squareup.picasso.Picasso;

public class MoreAct extends BaseActivity implements OnClickListener {
	private String mAddress;
	private TextView connected_text;
	private CircleImageView cimg;
	private MicroRecruitSettings settings;
	private Context context;
	private AbSoapUtil mAbSoapUtil = null;
	private TextView loadagin;
	private TextView w_w;
	private ImageButton back_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_more);
		context = this;
		settings = new MicroRecruitSettings(context);
		mAbSoapUtil = AbSoapUtil.getInstance(this);
		mAbSoapUtil.setTimeout(10000);
		w_w = (TextView) findViewById(R.id.nick);
		back_btn = (ImageButton) findViewById(R.id.back_btn);
		back_btn.setOnClickListener(this);
		UIItem alert_settings = (UIItem) findViewById(R.id.alert_settings);
		UIItem ui_hufu = (UIItem) findViewById(R.id.ui_hufu);
		UIItem ui_FAQ = (UIItem) findViewById(R.id.FAQ);
		UIItem ui_yijian = (UIItem) findViewById(R.id.ui_yijian);
		UIItem connect_device = (UIItem) findViewById(R.id.connect_device);
		connected_text = (TextView) findViewById(R.id.connected_text);
		cimg = (CircleImageView) findViewById(R.id.cimg);
		loadagin = (TextView) findViewById(R.id.loadagin);
		loadagin.setOnClickListener(this);
		registerBoradcastReceiver();
		alert_settings.setOnClickListener(this);
		ui_hufu.setOnClickListener(this);
		ui_FAQ.setOnClickListener(this);
		ui_yijian.setOnClickListener(this);
		connect_device.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(MoreAct.this, DeviceListActivity.class);
				i.putExtra("uid", "");
				startActivityForResult(i, 0);
			}
		});
		cimg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (settings.USER_NAME.getValue().equals("")) {
					Intent i = new Intent(MoreAct.this, ActLogin.class);
					startActivity(i);
				} else {
					Intent i = new Intent(MoreAct.this,
							PersonalCenterActivity.class);
					i.putExtra("isgone", "0");
					startActivity(i);
				}
			}
		});

		addBackImage(R.drawable.btn_back_bg, new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		setTitle("更多");
		// isLogin();
		if (settings.USER_NAME.getValue().equals("")) {
			Toast.makeText(getApplicationContext(), "用户未登录", 1).show();
		} else {
			getHeadusername();
		}

	}

	// public void isLogin() {
	// if (settings.USER_NAME.getValue().equals("")) {
	// // 用户未登录
	// } else {
	// // 获取头像和昵称
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// getHeadusername();
	// }
	// }).start();
	// }
	// }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && data != null) {
			BluetoothDevice device = data.getExtras().getParcelable(
					BluetoothDevice.EXTRA_DEVICE);
			mAddress = device.getAddress();
			if (SelfDefineApplication.getInstance().mService != null) {
				SelfDefineApplication.getInstance().mService.connect(mAddress);
			}
		} else if (requestCode == 1) { //
			if (resultCode == Activity.RESULT_OK) {
				Toast.makeText(this, "bluetooth open success!",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "bluetooth open failed",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alert_settings:
			Intent intent = new Intent(MoreAct.this, ActAlert.class);
			startActivity(intent);
			break;
		case R.id.ui_hufu:
			startActivity(new Intent(v.getContext(), ActKnowSkin.class));
			break;
		case R.id.FAQ:
			startActivity(new Intent(v.getContext(), ActFAQ.class));
			break;
		case R.id.ui_yijian:
			String toaddress;
			// Intent intent2 = new Intent();
			// intent2.setClass(MoreAct.this, SendEmailAct.class);
			// startActivity(intent2);
			if (getResources().getConfiguration().locale.getCountry().equals(
					"CN")
					|| getResources().getConfiguration().locale.getCountry()
							.equals("TW")) {
				toaddress = "customerservice@hali-Power.com";
			} else {
				toaddress = "cs@hali-Power.com";
			}
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("text/plain");

			i.putExtra(Intent.EXTRA_EMAIL, new String[] { toaddress });
			i.putExtra(Intent.EXTRA_SUBJECT, "产品意见反馈");
			i.putExtra(Intent.EXTRA_TEXT, "请输入邮件反馈内容");
			try {
				startActivity(Intent.createChooser(i, "邮件发送中..."));
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(MoreAct.this, "您没有安装邮件客户端", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.loadagin:
			getHeadusername();
			break;
		case R.id.back_btn:
			finish();
			break;
		default:
			break;
		}
	}

	private void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(Constants.UPDATE_OK);
		myIntentFilter.addAction(Constants.CANCEL_REFRESH);
		myIntentFilter.addAction(BleService.ACTION_GATT_CONNECTED);
		myIntentFilter.addAction(BleService.ACTION_GATT_DISCONNECTED);
		myIntentFilter.addAction(BleService.ACTION_STATUS_WRONG);
		myIntentFilter.addAction(BleService.ACTION_TIME_TOOSHORT);
		myIntentFilter.addAction(BleService.ACTION_START);
		myIntentFilter.addAction(Constants.SYNCHRONOUS_FAILURE);
		myIntentFilter.addAction(Constants.CLEAR_AlL);
		// 注册广播
		getActivity().registerReceiver(mReceiver, myIntentFilter);
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
			String action = intent.getAction();
			if (BleService.ACTION_GATT_CONNECTED.equals(action)) {
				connected_text.setText("已连接");
				connected_text.setVisibility(View.VISIBLE);
			}
			if (BleService.ACTION_GATT_DISCONNECTED.equals(action)) {
				connected_text.setText("断开连接");
				connected_text.setVisibility(View.VISIBLE);
			}
			if (BleService.ACTION_STATUS_WRONG.equals(action)) {
				connected_text.setText("断开连接");
				connected_text.setVisibility(View.VISIBLE);
			}

		}
	};

	public void getHeadusername() {

		// 获取用户的相关信息
		// String urlString =
		// "http://miliapp.ebms.cn/webservice/member.asmx?op=GetListByUserName";
		// String nameSpace = "http://tempuri.org/";
		// String methodName = "GetListByUserName";
		// AbSoapParams params = new AbSoapParams();
		// params.put("user1", "APP");
		// params.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		// params.put("username", "longke1988@163.com");

		String urlString3 = "http://miliapp.ebms.cn/webservice/member.asmx?op=GetListByUserName";
		String nameSpace3 = "http://tempuri.org/";
		String methodName3 = "GetListByUserName";
		AbSoapParams params3 = new AbSoapParams();
		params3.put("user1", "APP");
		params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params3.put("username", settings.USER_NAME.getValue());

		mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						if (arg1.indexOf("Nicheng=") != -1) {
							String[] a = arg1.split("Nicheng=");
							String[] b = a[1].split(";");
							w_w.setText(b[0]);
						}
						if (arg1.indexOf("Touxiang=") != -1) {
							String[] a1 = arg1.split("Touxiang=");
							String[] b1 = a1[1].split(";");
							Picasso.with(context)
									.load("http://miliapp.ebms.cn/" + b1[0])
									.placeholder(R.drawable.hcy_icon)
									.error(R.drawable.hcy_icon).into(cimg);
						}

						// AbDialogUtil.showAlertDialog(MoreAct.this, "���ؽ��",
						// arg1, new AbDialogOnClickListener() {
						//
						// @Override
						// public void onNegativeClick() {
						// // TODO Auto-generated method
						// // stub
						//
						// }
						//
						// @Override
						// public void onPositiveClick() {
						// // TODO Auto-generated method
						// // stub
						//
						// }
						//
						// });
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						// TODO Auto-generated method stub
						// Toast.makeText(getApplicationContext(), "请求失败" +
						// arg1,
						// 1).show();
					}
				});
	}
}
