package com.mygame.pure.utils;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
 
public class ShareUtil {

    private static final String TAG = "ShareUtil";
	
	public static final String fname = Environment.getExternalStorageDirectory().getPath() 
			+ File.separator + "#Package#";
	public static final String png = "zhiqu_share.png";
	
	public static final void snapshot(Context context, View v, int height) throws Exception {
		
		if (checkSDCard()) {
	//		View view = v.getRootView();
	//		view.setDrawingCacheEnabled(true);
	//		view.buildDrawingCache();
			v.setDrawingCacheEnabled(true);
			v.buildDrawingCache();
			Bitmap bitmap = v.getDrawingCache();

	//		int height = App.statusBarHigh;
			bitmap = Bitmap.createBitmap(bitmap, 0, height, bitmap.getWidth(),
					bitmap.getHeight() - height);

			String pngName = fname.replace("#Package#",
					context.getPackageName());
			File file = new File(pngName);
			if (!file.exists()) {
				file.mkdirs();
			}
			pngName = pngName + File.separator + png;
			if (bitmap != null) {

				Log.v(TAG, "path: " + pngName);
				FileOutputStream out = null;
				try {
					out = new FileOutputStream(pngName);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
					Log.v(TAG, "snapshot output done.");
				} catch (Exception e) {
					Log.e(TAG, "snapshot error.");
				} finally {

					try {
						out.close();
					} catch (Exception e) {
						Log.e(TAG, "" + e);
					}
				}
			} else {
				Log.e(TAG, "bitmap is NULL!");
			}
		}
	}
	
    public static final void systemShare(Context context, String s, int type) {
		
    	Intent intent = null;
    	if (type == 1) {
			String pngName = fname.replace("#Package#", context.getPackageName()) + File.separator + png;
			
			Log.v(TAG, "systemShare path: " + pngName);
			intent = new Intent(Intent.ACTION_SEND);  
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
	        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(pngName)));  
	        intent.putExtra(Intent.EXTRA_TEXT, s);  
	        intent.putExtra(Intent.EXTRA_SUBJECT, "");  
	        intent.setType("image/*");  
			
	        
    	} else if (type == 0){ // 暂无本地分享
//    		Log.v(TAG, "start ShareActivity");
//    		Intent i = new Intent(context, ShareActivity.class);
//			i.putExtra("external_string", s);
//			context.startActivity(i);
    	}
//    	if (intent != null) {
//    	    context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.share_title)));
//    	}
	}
    
	public static boolean checkSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else {
			return false;
		}
	}
}
