package com.zzj.z_reader.log;

import android.util.Log;

/**
 * title:打印信息
 * @author zzj 
 * @date 2014-7-29
 */
public class MyLog {

	public static void warnLog(String tag, String msg) {
		Log.w(tag, msg);
	}

	public static void infoLog(String tag, String msg) {
		Log.i(tag, msg);
	}

	public static void debugLog(String tag, String msg) {
		Log.d(tag, msg);
	}

	public static void errorLog(String tag, String msg) {
		Log.e(tag, msg);
	}
}
