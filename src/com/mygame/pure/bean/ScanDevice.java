package com.mygame.pure.bean;

import android.bluetooth.BluetoothDevice;

public class ScanDevice {
	private BluetoothDevice device;
	private int rssi;

	public int getRssi() {
		return rssi;
	}

	public BluetoothDevice getDevice() {
		return device;
	}

	public void setDevice(BluetoothDevice device) {
		this.device = device;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

}
