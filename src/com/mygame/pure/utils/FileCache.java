package com.mygame.pure.utils;

import java.io.File;

import android.content.Context;

import com.mygame.pure.log.MyLog;

public class FileCache {

	private static final String tag = FileCache.class.getSimpleName();

	private File cacheDir; // the directory to save images

	/**
	 * Constructor
	 * 
	 * @param context
	 *            The context related to this cache.
	 * */
	public FileCache(Context context) {
		// Find the directory to save cached images
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"fellowship");
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();

		MyLog.i(tag, "cache dir: " + cacheDir.getAbsolutePath());
	}

	public String getFilePath(String key) {
		return android.os.Environment.getExternalStorageDirectory() + "/"
				+ "fellowship" + "/" + key;
	}

	/**
	 * Search the specific image file with a unique key.
	 * 
	 * @param key
	 *            Should be unique.
	 * @return Returns the image file corresponding to the key.
	 * */
	public File getFile(String key) {
		File f = new File(cacheDir, key);
		MyLog.i(tag, "key：" + key);
		if (f.exists()) {
			MyLog.i(tag, "the file you wanted exists " + f.getAbsolutePath());
			return f;
		} else {
			MyLog.i(tag,
					"the file you wanted does not exists: "
							+ f.getAbsolutePath());
		}
		return null;
	}

	public File getFileDirCache() {
		return cacheDir;
	}

	/**
	 * Clear the cache directory on sdcard.
	 * */
	public void clear() {
		File[] files = cacheDir.listFiles();
		for (File f : files)
			f.delete();
	}
}