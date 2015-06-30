package com.mygame.pure.ble;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;

import com.lidroid.xutils.exception.DbException;
import com.mygame.pure.SelfDefineApplication;
import com.mygame.pure.bean.BltModel;
import com.mygame.pure.utils.Constants;
import com.mygame.pure.utils.DateUtil;
import com.mygame.pure.utils.DbUtils;

public class BleParserLoader {
	public static int waterParser(byte[] bytes, Context context, String mac) {
		byte[] water = new byte[2];
		water[0] = bytes[2];
		water[1] = bytes[1];
		int waters = byteArrayToInt(water);
		DbUtils db = DbUtils.create(context);
		db.configAllowTransaction(true);
		db.configDebug(true);
		BltModel model = new BltModel();
		model.setDate(DateUtil.getCurrentDate());
		model.setWater(waters + "");
		model.setBltid(mac);
		model.setModelstate(SelfDefineApplication.getInstance().selectPostion);
		SimpleDateFormat df = new SimpleDateFormat("HH");
		model.setHour(df.format(new Date()));
		try {
			db.saveBindingId(model);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Intent intent = new Intent(Constants.UPDATE_OK);
		intent.putExtra("selectPostion",
				SelfDefineApplication.getInstance().selectPostion);
		intent.putExtra("waters", waters);
		context.sendBroadcast(intent);
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
