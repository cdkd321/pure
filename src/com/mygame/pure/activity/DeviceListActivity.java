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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mygame.pure.R;
import com.mygame.pure.SelfDefineApplication;
import com.mygame.pure.ble.BleService;

public class DeviceListActivity extends Activity {
	private List<BluetoothDevice> deviceList;
	private ListView lv;
	private TextView bu_cancle;
	private TextView bund_text;
	private DeviceAdapter adapter;
	private MyReceiver receiver = new MyReceiver();
	private IntentFilter filter = new IntentFilter(
			BleService.ACTION_DEVICE_FOUND);

	private UUID uuid;
	private TextView connectedName;
	private RelativeLayout connectedMacLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bluetooth);
		String uid = getIntent().getExtras().getString("uid");

		// if (uid != null){
		// uuid = UUID.fromString(uid);
		// }
		init();
	}

	private void init() {
		deviceList = new ArrayList<BluetoothDevice>();
		bu_cancle = (TextView) findViewById(R.id.cancel);
		connectedName = (TextView) findViewById(R.id.name);
		bund_text = (TextView) findViewById(R.id.bund_text);
		lv = (ListView) findViewById(R.id.available_device);
		connectedMacLayout = (RelativeLayout) findViewById(R.id.connectedMacLayout);
		bu_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		lv.setOnItemClickListener(onItemClickListener);
		connectedMacLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		// connectedName.setText(text)

		/*
		 * List<HashMap<String, String>> data = new
		 * ArrayList<HashMap<String,String>>(); for (int i = 0; i < 5; i++) {
		 * 
		 * HashMap<String, String> itemHashMap = new HashMap<String, String>();
		 * // itemHashMap.put("signatureStrenth","信号强度"+i);
		 * itemHashMap.put("name","设备名字"+i); data.add(itemHashMap); }
		 */
		adapter = new DeviceAdapter();
		lv.setAdapter(adapter);

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
		SelfDefineApplication.getInstance().mService.scan(true,
				new UUID[] { uuid });
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

			ViewHolder holder = null;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item, null);
				holder = new ViewHolder();
				// holder.signatureStrenth = (TextView)
				// convertView.findViewById(R.id.signal_strength);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// holder.signatureStrenth.setText(itemData.get("signatureStrenth"));
			holder.name.setText(deviceList.get(position).getName());
			return convertView;
		}

	}

	class ViewHolder {
		// private TextView signatureStrenth;
		private TextView name;
	}
}
