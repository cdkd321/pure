package com.mygame.pure.activity;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mygame.pure.R;
import com.mygame.pure.SelfDefineApplication;
import com.mygame.pure.view.UIItem;

public class MoreAct extends BaseActivity implements OnClickListener{
	private String mAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_more);
		UIItem ui_settings = (UIItem) findViewById(R.id.ui_settings);
		UIItem ui_hufu = (UIItem) findViewById(R.id.ui_hufu);
		UIItem ui_pwd = (UIItem) findViewById(R.id.ui_pwd);
		UIItem ui_yijian = (UIItem) findViewById(R.id.ui_yijian);
		UIItem connect_device = (UIItem) findViewById(R.id.connect_device);
		
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
		
		addBackImage(R.drawable.back, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		setTitle("更多");
 
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
	
}
