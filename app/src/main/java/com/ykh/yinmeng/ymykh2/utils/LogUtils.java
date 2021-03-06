package com.ykh.yinmeng.ymykh2.utils;

import android.util.Log;

/**
 * 日志工具
 * @author Angel
 *
 */
public class LogUtils {

	private static final String LOG_FORMAT = "%1$s\n%2$s";
	private static boolean isDebug = true;
	
	public static void d(String tag, Object... args) {
		log(Log.DEBUG, null, tag, args);
	}

	public static void i(String tag, Object... args) {
		log(Log.INFO, null, tag, args);
	}

	public static void w(String tag, Object... args) {
		log(Log.WARN, null, tag, args);
	}

	public static void e(Throwable ex) {
		log(Log.ERROR, ex, null);
	}

	public static void e(String tag, Object... args) {
		log(Log.ERROR, null, tag, args);
	}

	public static void e(Throwable ex, String tag, Object... args) {
		log(Log.ERROR, ex, tag, args);
	}

	private static void log(int priority, Throwable ex, String tag, Object... args) {
		
		if (isDebug == false) return;

		String log = "";
		if (ex == null) {
			if(args != null && args.length > 0){
				for(Object obj : args){
					log += String.valueOf(obj);
				}
			}
		} else {
			String logMessage = ex.getMessage();
			String logBody = Log.getStackTraceString(ex);
			log = String.format(LOG_FORMAT, logMessage, logBody);
		}
		Log.println(priority, tag, log);
	}

	public static boolean isDebug() {
		return isDebug;
	}

	public static void setDebug(boolean isDebug) {
		LogUtils.isDebug = isDebug;
	}
	
}