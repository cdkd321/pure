/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mygame.pure.ble;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;




import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author longke 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
@SuppressLint("NewApi")
public class BleService extends Service {
	private final static String TAG = BleService.class.getSimpleName();
	private BluetoothManager mBluetoothManager;
	private BluetoothAdapter mBluetoothAdapter;
	private String mBluetoothDeviceAddress;
	private BluetoothGatt mBluetoothGatt;
	private BluetoothGattService mGattService;
	private ArrayList<BluetoothDevice>  devices;
	public int mConnectionState = STATE_DISCONNECTED;
	private Runnable mCurrentTask;
	private long tempTime, tempTime1;
	public static final int STATE_DISCONNECTED = 0;
	public static final int STATE_CONNECTING = 1;
	public static final int STATE_CONNECTED = 2;
	public final static String ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
	public final static String ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
	public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
	public final static String ACTION_GATT_NOTIFICATION_OPEN = "com.example.bluetooth.le.ACTION_GATT_NOTIFICATION_OPEN";
	public final static String ACTION_GATT_NOTIFICATION_INEXISTENCE = "com.example.bluetooth.le.ACTION_GATT_NOTIFICATION_INEXISTENCE";
	public final static String ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
	public final static String ACTION_DEVICE_FOUND = "com.example.bluetooth.le.DEVICE_FOUND";
	public final static String EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA";
	public final static String ACTION_STATUS_WRONG = "com.example.bluetooth.le.ACTION_STATUS_WRONG";
	public final static String ACTION_TIME_TOOSHORT = "com.example.bluetooth.le.ACTION_TIME_TOOSHORT";
	public final static String ACTION_START = "com.example.bluetooth.le.ACTION_START";
	public final static String ACTION_CLEAR = "com.example.bluetooth.le.ACTION_CLEAR";

	public static final UUID MAIN_SERVICE = UUID
			.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
	public static final UUID SEND_DATA_CHAR = UUID
			.fromString("00002a37-0000-1000-8000-00805f9b34fb");
	public static final UUID RECEIVE_DATA_CHAR = UUID
			.fromString("0000fff1-494c-4f47-4943-544543480000");
	/*
	 * public static final UUID REALTIME_RECEIVE_DATA_CHAR = UUID
	 * .fromString("d0a2ff03-2996-d38b-e214-86515df5a1df");
	 */
	public static final UUID REALTIME_RECEIVE_DATA_CHAR = UUID
			.fromString("d0a2ff04-2996-d38b-e214-86515df5a1df");

	// 閿熸枻鎷烽�氱煡UUID
	public static final UUID UUID_DESCRIPTOR = UUID
			.fromString("00002902-0000-1000-8000-00805f9b34fb");
	private boolean isSendData = false;
	public int mCommand; // 瑜版挸澧犻惃鍕瘹閿燂拷?

	public static final int COMMAND_SYNC_SAVE = 0;
	public static final int COMMAND_ALARM = 1;
	public static final int COMMAND_EVENT = 2;
	public static final int COMMAND_WEARINFO = 3;

	// 娴溠冩惂閸戝搫宸堕弮鑸电閿燂拷?
	public static final int COMMAND_CLEAR = 4;
	// 濮濄儴绐�
	public static final int STEP_LENGTH = 5;
	// 娑斿懎娼楅幓鎰板晪
	public static final int LONG_TIME_SLEEP = 6;
	// 閹垫挸绱戠�圭偞妞傛导鐘虹翻
	public static final int OPEN_REAL_TIME = 7;
	// 閸忔娊妫寸�圭偞妞傛导鐘虹翻
	public static final int CLOSE_REAL_TIME = 8;
	private String tempData = "";
	private int TIME = 5000;
	// Implements callback methods for GATT events that the app cares about. For
	// example,
	// connection change and services discovered.
	private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

		// 鏉╃偞甯撮悩璁规嫹??
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status,
				int newState) {
			String intentAction;
			if (status == BluetoothGatt.GATT_SUCCESS) {
				if (newState == BluetoothProfile.STATE_CONNECTED) {
					intentAction = ACTION_GATT_CONNECTED;
					mConnectionState = STATE_CONNECTED;
					broadcastUpdate(intentAction);
					Log.i(TAG, "Connected to GATT server.");
					share.edit().putString("LAST_CONNECT_MAC", gatt.getDevice().getAddress()).commit();
					share.edit().putString("LAST_CONNECT_NAME", gatt.getDevice().getName()).commit();
					Log.i(TAG, "Attempting to start service discovery:"
							+ mBluetoothGatt.discoverServices());
					

					// Attempts to discover services after successful
					// connection.

				} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
					intentAction = ACTION_GATT_DISCONNECTED;
					mConnectionState = STATE_DISCONNECTED;
					gatt.close();
					Log.i(TAG, "Disconnected from GATT server.");
					broadcastUpdate(intentAction);
				}
			} else {
				intentAction=ACTION_STATUS_WRONG;
				mConnectionState = STATE_DISCONNECTED;
				broadcastUpdate(intentAction);
				Log.w(TAG, "onConnectionStateChange: " + status);
				operateStatusWrong();
			}
		}

		// 閸欐垹骞囬張宥呭
		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
				List<BluetoothGattService> list = gatt.getServices();
				mGattService = mBluetoothGatt.getService(MAIN_SERVICE);
				if (mGattService != null) {
					
					final ArrayList<BluetoothGattCharacteristic> characs=(ArrayList<BluetoothGattCharacteristic>) mGattService.getCharacteristics();
					if(characs.get(0)!=null){
						
						mHandler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								enableNotification4(characs.get(0));
							}
						}, 1000);
						
						//enableNotification4(characs.get(1));
					}
						
                  /*  //閿熸枻鎷烽�氶敓鏂ゆ嫹
					BluetoothGattCharacteristic receiveMcharac = mGattService
							.getCharacteristic(SEND_DATA_CHAR);
					enableNotification4(receiveMcharac);*/

				} else {
					broadcastUpdate(ACTION_GATT_NOTIFICATION_INEXISTENCE);
					disconnect();
				}
			} else {
				mConnectionState = STATE_DISCONNECTED;
				Log.w(TAG, "onServicesDiscovered received: " + status);
				operateStatusWrong();
			}
		}

		// 閻㈢敻鍣洪悧鐟扮窙閺佺増宓佺拠璇插絿
		@Override
		public void onCharacteristicRead(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				// broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic,gatt);
			} else {
				operateStatusWrong();
			}
		}

		// 閻楃懓绶涢弫鐗堝祦鐠囪褰�
		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) {
			/*
			 * Log.i(TAG, "閻楃懓绶�"); final StringBuilder stringBuilder = new
			 * StringBuilder( characteristic.getValue().length);
			 * 
			 * for (byte byteChar : characteristic.getValue()) {
			 * stringBuilder.append(String.format("%02X ", byteChar)); }
			 * if(!tempData.equals(stringBuilder.toString().trim())){
			 * tempData=stringBuilder.toString().trim();
			 */
			broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic, gatt);
			/*
			 * }else{ tempData=stringBuilder.toString().trim(); }
			 */

		}

		public void onDescriptorWrite(final BluetoothGatt gatt,
				BluetoothGattDescriptor descriptor, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				final UUID uuid = descriptor.getCharacteristic().getUuid();
				mBluetoothGatt = gatt;
				
				if (status == 0) {
					// 閿熸枻鎷峰閿熸枻鎷烽敓鏂ゆ嫹notify閿熼ズ鍖℃嫹閿熸枻鎷�
					mCurrentTask = null;
					mCurrentTask = new Thread() {
						public void run() {
						  //ComMandContoller.sendGetHostoryDateCommand(SEND_DATA_CHAR, mGattService, gatt);
						}

					};
					mHandler.postDelayed(mCurrentTask, 300);
				} else {
					// Log.e("onDescriptorWrite  enable error : " + status);
					// sendErrorMessage(status);
				}
			} else {
				operateStatusWrong();
			}
		};
		
		// write data success callback
		public void onCharacteristicWrite(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {

		};

		// 閿熸枻鎷峰彇閿熸枻鎷烽敓鑴氱尨鎷峰己閿熸枻鎷�
		public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {

		};

	};

	

	// 閽冩繄澧幍顐ｅ伎閸ョ偠鐨�.
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device, int rssi,
				byte[] scanRecord) {
			Log.d(TAG, "onScanResult() - device=" + device + ", rssi=" + rssi);
			if(!devices.contains(device)){
				devices.add(device);
			}
			String name = device.getName();
			Bundle data = new Bundle();
			data.putParcelable(BluetoothDevice.EXTRA_DEVICE, device);
			data.putInt("rssi", rssi);
			Intent i = new Intent(ACTION_DEVICE_FOUND);
			i.putExtras(data);
			sendBroadcast(i);
		}
		
	};

	private Handler mHandler;
	private ArrayList<Byte> mCache;
	private int mChecksum;
	private SharedPreferences share;

	private void operateStatusWrong() {
		close();
		broadcastUpdate(ACTION_GATT_DISCONNECTED);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initialize();
		mHandler = new Handler();
		devices=new ArrayList<BluetoothDevice>();
		handler.postDelayed(runnable, TIME); // 姣忛殧1s鎵ц
		share = super.getSharedPreferences("longke",
				Activity.MODE_PRIVATE); // 鎸囧畾鎿嶄綔鐨勬枃浠跺悕
		
	}

	private void broadcastUpdate(final String action) {
		final Intent intent = new Intent(action);
		sendBroadcast(intent);
	}

	private void broadcastUpdate(final String action,
			final BluetoothGattCharacteristic characteristic,
			final BluetoothGatt gatt) {
		final byte[] data = characteristic.getValue();
		final StringBuilder stringBuilder = new StringBuilder(
				data.length);
		
		for (byte byteChar : data) {
			stringBuilder.append(String.format("%02X ", byteChar));
		}
		if(stringBuilder.toString().startsWith("CC")){
			BleParserLoader.waterParser(data,getBaseContext(),gatt.getDevice().getAddress());
		}
		if(stringBuilder.toString().startsWith("55")){
			broadcastUpdate(ACTION_TIME_TOOSHORT);
		}
		if(stringBuilder.toString().startsWith("AA")){
			broadcastUpdate(ACTION_START);
		}
		
		Log.i(TAG, "閺�璺哄煂" + stringBuilder.toString().trim());

	}

	protected String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
			stringBuilder.append(" ");
		}
		return stringBuilder.toString();
	}

	public class LocalBinder extends Binder {
		public BleService getService() {
			return BleService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// After using a given device, you should make sure that
		// BluetoothGatt.close() is called
		// such that resources are cleaned up properly. In this particular
		// example, close() is
		// invoked when the UI is disconnected from the Service.
		close();
		return super.onUnbind(intent);
	}

	private final IBinder mBinder = new LocalBinder();

	/**
	 * 閸掓繂顫愰崠鏍憫閻楁瑱鎷�?閿熶粙鍘ら敓锟�?.
	 * 
	 * @return Return true if the initialization is successful.
	 */
	public boolean initialize() {
		// For API level 18 and above, get a reference to BluetoothAdapter
		// through
		// BluetoothManager.
		if (mBluetoothManager == null) {
			mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
			if (mBluetoothManager == null) {
				Log.e(TAG, "Unable to initialize BluetoothManager.");
				return false;
			}
		}

		mBluetoothAdapter = mBluetoothManager.getAdapter();
		if (mBluetoothAdapter == null) {
			Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
			return false;
		}

		return true;
	}

	/**
	 * 鏉╃偞甯撮拑婵堝閺堝秴濮�
	 * 
	 * @param address
	 *            The device address of the destination device.
	 * 
	 * @return Return true if the connection is initiated successfully. The
	 *         connection result is reported asynchronously through the
	 *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
	 *         callback.
	 */
	public boolean connect(final String address) {
		if (mBluetoothAdapter == null || address == null) {
			Log.w(TAG,
					"BluetoothAdapter not initialized or unspecified address.");
			return false;
		}
		final BluetoothDevice device = mBluetoothAdapter
				.getRemoteDevice(address);
		if (device == null) {
			Log.w(TAG, "Device not found.  Unable to connect.");
			return false;
		}
		
		// We want to directly connect to the device, so we are setting the
		// autoConnect
		// parameter to false.
		mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(TAG, "Trying to create a new connection.");
		mBluetoothDeviceAddress = address;
		mConnectionState = STATE_CONNECTING;
		System.out.println("device.getBondState==" + device.getBondState());
		return true;
	}

	/**
	 * Disconnects an existing connection or cancel a pending connection. The
	 * disconnection result is reported asynchronously through the
	 * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
	 * callback.
	 */
	public void disconnect() {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.close();
		mBluetoothGatt.disconnect();

		mConnectionState = STATE_DISCONNECTED;
	}

	/**
	 * After using a given BLE device, the app must call this method to ensure
	 * resources are released properly.
	 */
	public void close() {
		if (mBluetoothGatt == null) {
			return;
		}
		mBluetoothGatt.close();
		mBluetoothGatt = null;
		mConnectionState = STATE_DISCONNECTED;
		broadcastUpdate(ACTION_GATT_DISCONNECTED);
	}

	/**
	 * Request a read on a given {@code BluetoothGattCharacteristic}. The read
	 * result is reported asynchronously through the
	 * {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
	 * callback.
	 * 
	 * @param characteristic
	 *            The characteristic to read from.
	 */
	public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.readCharacteristic(characteristic);
	}

	/**
	 * Enables or disables notification on a give characteristic.
	 * 
	 * @param characteristic
	 *            Characteristic to act on.
	 * @param enabled
	 *            If true, enable notification. False otherwise.
	 */
	public void setCharacteristicNotification(
			BluetoothGattCharacteristic characteristic, boolean enabled) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

		// This is specific to Heart Rate Measurement.
		// if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
		// BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
		// UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
		// descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
		// mBluetoothGatt.writeDescriptor(descriptor);
		// }
	}

	public boolean enableNotification(BluetoothGattCharacteristic characteristic) {
		Log.i(TAG, "enableNotification------------>" + characteristic.getUuid());

		boolean result = mBluetoothGatt.setCharacteristicNotification(
				characteristic, true);
		Log.i(TAG, "setCharacteristicNotification===>" + result);
		BluetoothGattDescriptor clientConfig = characteristic
				.getDescriptor(UUID_DESCRIPTOR);

		if (clientConfig == null) {
			Log.e(TAG, "clientConfig is null");
			return false;
		}

		clientConfig.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);

		return mBluetoothGatt.writeDescriptor(clientConfig);
	}

	public boolean enableNotification4(
			BluetoothGattCharacteristic characteristic) {
		Log.i(TAG, "enableNotification------------>" + characteristic.getUuid());
		if (mBluetoothGatt == null) {
			return false;
		}
		boolean result = mBluetoothGatt.setCharacteristicNotification(
				characteristic, true);
		Log.i(TAG, "setCharacteristicNotification===>" + result);

		BluetoothGattDescriptor clientConfig = characteristic
				.getDescriptor(UUID_DESCRIPTOR);

		if (clientConfig == null) {
			Log.e(TAG, "clientConfig is null");
			return false;
		}
		String intentAction = ACTION_GATT_CONNECTED;
		mConnectionState = STATE_CONNECTED;
		broadcastUpdate(intentAction);
		clientConfig
				.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
		return mBluetoothGatt.writeDescriptor(clientConfig);
	}

	public boolean writeData(byte[] data, UUID servide, UUID charac) {
		BluetoothGattService mainService = mBluetoothGatt.getService(servide);
		if (mainService == null) {
			Log.e(TAG, "service not found!");
			return false;
		}
		BluetoothGattCharacteristic txCharac = mainService
				.getCharacteristic(charac);
		if (txCharac == null) {
			Log.e(TAG, "HEART RATE MEASUREMENT charateristic not found!");
			return false;
		}
		txCharac.setValue(data);
		return mBluetoothGatt.writeCharacteristic(txCharac);
	}

	public void disableNotification(UUID uService, UUID uCharac) {
		if (mBluetoothGatt == null) {
			return;
		}
		// TODO Auto-generated method stub
		BluetoothGattService mainService = mBluetoothGatt.getService(uService);
		if (mainService == null) {
			// disconnect(device);
			return;
		}
		BluetoothGattCharacteristic mainCharac = mainService
				.getCharacteristic(uCharac);
		if (mainCharac == null) {
			return;
		}
		if (!mBluetoothGatt.setCharacteristicNotification(mainCharac, false))
			return;
		BluetoothGattDescriptor clientConfig = mainCharac
				.getDescriptor(UUID_DESCRIPTOR);
		if (clientConfig == null)
			return;
		clientConfig
				.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
		mBluetoothGatt.writeDescriptor(clientConfig);
	}

	public void scan(boolean start, UUID[] uid) {
		// TODO Auto-generated method stub
		if (start) {
			mBluetoothAdapter.startLeScan(mLeScanCallback);
		} else {
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
			// reSetIsConnect();
		}
	}
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@SuppressLint("NewApi")
	public static void sendCommand(UUID characteristicID,
			BluetoothGattService mGattService, BluetoothGatt mBluetoothGatt,
			byte[] bytes) {
		BluetoothGattCharacteristic chara = mGattService
				.getCharacteristic(characteristicID);
		if (chara == null) {
			return;
		}
		chara.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
		chara.setValue(bytes);
		boolean status = mBluetoothGatt.writeCharacteristic(chara);
		if (bytes != null) {

		}
	}// end of sendCommand

	// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
	public static void sendCommand(UUID characteristicID,
			BluetoothGattService mGattService, BluetoothGatt mBluetoothGatt,byte cmd,String json) {
		switch (cmd) {
		case 0x40:
			
			break;

		default:
			break;
		}
		byte[] time = new byte[] { (byte) 0x40, 0x04, 0x03, (byte) 0x47 };
		sendCommand(characteristicID, mGattService, mBluetoothGatt, time);
		final StringBuilder stringBuilder = new StringBuilder(time.length);
		for (byte byteChar : time) {
			stringBuilder.append(String.format("%02X ", byteChar));
		}
		Log.i(TAG, "閿熸枻鎷烽敓閰碉綇鎷�" + stringBuilder.toString().trim());
	}
	Handler handler = new Handler();
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// handler鑷甫鏂规硶瀹炵幇瀹氭椂鍣�
			try {
				handler.postDelayed(this, TIME);
                if(mBluetoothGatt!=null){
                	broadcastUpdate(ACTION_CLEAR);
                	mBluetoothAdapter.stopLeScan(mLeScanCallback);
                	mBluetoothAdapter.startLeScan(mLeScanCallback);
                	if(mConnectionState == STATE_DISCONNECTED){
                		if(!TextUtils.isEmpty(share.getString("LAST_CONNECT_MAC", ""))){
                			connect(share.getString("LAST_CONNECT_MAC", ""));
                		}
                		
                	}
                }else{
					
					initialize();
					broadcastUpdate(ACTION_CLEAR);
                	mBluetoothAdapter.stopLeScan(mLeScanCallback);
                	mBluetoothAdapter.startLeScan(mLeScanCallback);
					if(!TextUtils.isEmpty(share.getString("LAST_CONNECT_MAC", ""))){
						connect(share.getString("LAST_CONNECT_MAC", ""));
					}
					
				
				
			     }
				
				
				;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("exception...");
			}
		}
	};


}
