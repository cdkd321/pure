package com.mygame.pure.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mygame.pure.R;
import com.mygame.pure.SelfDefineApplication;
import com.mygame.pure.ble.BleService;

public class DeviceListActivity extends Activity {
	private List<BluetoothDevice> deviceList;
	private ListView lv;
	private Button bu_cancle;
	private DeviceAdapter adapter;
	private MyReceiver receiver = new MyReceiver();
	private IntentFilter filter = new IntentFilter(
			BleService.ACTION_DEVICE_FOUND);
	
	private UUID uuid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_devicelist);
		String uid = getIntent().getExtras().getString("uid");
//		if (uid != null){
//			uuid = UUID.fromString(uid);
//		}
		init();
	}

	private void init() {
		deviceList = new ArrayList<BluetoothDevice>();
		lv = (ListView) findViewById(R.id.devicelist_lv);
		bu_cancle = (Button) findViewById(R.id.devicelist_btn_cancel);
		bu_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		adapter = new DeviceAdapter();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(onItemClickListener);
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(receiver);
		SelfDefineApplication.getInstance().mService.scan(false, null);
		deviceList.clear();
	}

	@Override
	protected void onStart() {
		super.onStart();
		registerReceiver(receiver, filter);
		SelfDefineApplication.getInstance().mService.scan(true,new UUID[]{uuid});
	}

	// ����豸
	private void addDevice(BluetoothDevice device) {
		boolean isHave = false;
		for (int i = 0; i < deviceList.size(); i++) {
			if (device.getAddress().equals(deviceList.get(i).getAddress())) {
				isHave = true;
			}
		}
		if (!isHave) {
			deviceList.add(device);
			adapter.notifyDataSetChanged();
		}
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent data = new Intent();
			Bundle b = new Bundle();
			b.putParcelable(BluetoothDevice.EXTRA_DEVICE,
					deviceList.get(position));
			data.putExtras(b);
			setResult(RESULT_OK, data);
			finish();
		}

	};

	class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BleService.ACTION_DEVICE_FOUND.equals(action)) {
				Bundle data = intent.getExtras();
				final BluetoothDevice device = data
						.getParcelable(BluetoothDevice.EXTRA_DEVICE);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						addDevice(device);
					}
				});
			}
		}

	}

	public class DeviceAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return deviceList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.layout_devicelist, null);
			}
			TextView tv_name = (TextView) convertView
					.findViewById(R.id.layout_devicelist_name);
			tv_name.setText(deviceList.get(position).getName());
			TextView tv_add = (TextView) convertView
					.findViewById(R.id.layout_devicelist_address);
			tv_add.setText(deviceList.get(position).getAddress());
			return convertView;
		}

	}
}
