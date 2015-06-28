package com.mygame.pure.activity;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.mygame.pure.R;
import com.mygame.pure.SelfDefineApplication;
import com.mygame.pure.bean.BltModel;
import com.mygame.pure.ble.BleService;
import com.mygame.pure.utils.Constants;
import com.mygame.pure.utils.DateUtil;
import com.mygame.pure.utils.DbUtils;
import com.mygame.pure.view.UIItem;

public class MoreAct extends BaseActivity implements OnClickListener{
	private String mAddress;
	private TextView connected_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_more);
		UIItem ui_settings = (UIItem) findViewById(R.id.ui_settings);
		UIItem ui_hufu = (UIItem) findViewById(R.id.ui_hufu);
		UIItem ui_pwd = (UIItem) findViewById(R.id.ui_pwd);
		UIItem ui_yijian = (UIItem) findViewById(R.id.ui_yijian);
		UIItem connect_device = (UIItem) findViewById(R.id.connect_device);
		connected_text=(TextView) findViewById(R.id.connected_text);
		registerBoradcastReceiver();
		ui_settings.setOnClickListener(this);
		ui_hufu.setOnClickListener(this);
		ui_pwd.setOnClickListener(this);
		ui_yijian.setOnClickListener(this);
		ui_settings.setOnClickListener(this);
		
		connect_device.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(MoreAct.this, DeviceListActivity.class);
				i.putExtra("uid", "");
				startActivityForResult(i, 0);
			}
		});
		
		addBackImage(R.drawable.btn_back_bg, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		setTitle("更多");
 
	}
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
			if(SelfDefineApplication.getInstance().mService!=null){
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
		case R.id.ui_settings:
			break;
		case R.id.ui_hufu:
			break;
		case R.id.ui_pwd:
			break;
		case R.id.ui_yijian:
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
		myIntentFilter.addAction(Constants.OLD_UPDATE_OK);
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
	
}
