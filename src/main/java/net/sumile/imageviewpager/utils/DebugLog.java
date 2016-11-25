package net.sumile.imageviewpager.utils;

import android.util.Log;

public class DebugLog {
	private static boolean debug = true;
	
	synchronized public static void on(boolean is) {
		debug = is;
	}
	
	public static void d(String tag, String msg) {
		if(debug)
			Log.d(tag, msg);
	}
	
	public static void e(String tag, String msg) {
		if(debug)
			Log.e(tag, msg);
	}

	public static void i(String tag, String msg) {
		if(debug)
			Log.i(tag, msg);
	}
	
	public static void v(String tag, String msg) {
		if(debug)
			Log.v(tag, msg);
	}
	public static void w(String tag, String msg) {
		if(debug)
			Log.w(tag, msg);
	}
}
