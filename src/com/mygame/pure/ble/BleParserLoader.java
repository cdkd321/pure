package com.mygame.pure.ble;

public class BleParserLoader {
	public static int waterParser(byte[] bytes){
		byte[] water = new byte[2];
		water[0] = bytes[2];
		water[1] = bytes[1];
		int waters=byteArrayToInt(water);
		return 0;
		
	}
	/**
	 * byte[]תint
	 * 
	 * @param bytes
	 * @return
	 */
	public static int byteArrayToInt(byte[] bytes) {
		int value = 0;
		// �ɸ�λ����λ
		for (int i = 0; i < bytes.length; i++) {
			int shift = (bytes.length - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift;// ���λ��
		}
		return value;
	}

}
