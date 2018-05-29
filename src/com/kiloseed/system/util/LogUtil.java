package com.kiloseed.system.util;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;
import com.kiloseed.system.helper.Constant;

public class LogUtil {
	public static SimpleDateFormat LOG_SDF=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	public static String getCurDate(){
		try {
			return "["+LOG_SDF.format(new Date())+"]";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "[]";
		}
	}
	/**
	 * 记录报错日志
	 * @param tr
	 */
	public static void e(Throwable tr){
		Log.e(Constant.TAG, getCurDate()+tr.getMessage(), tr);
		FileUtils.logInfo(getCurDate());
		FileUtils.logError(tr);
	}
	/**
	 * 记录日志
	 * @param msg
	 */
	public static void e(String msg){
		Log.i(Constant.TAG, getCurDate()+msg);
		FileUtils.logInfo(msg);
	}
	/**
	 * 记录日志
	 * @param msg
	 */
	public static void i(String msg){
		Log.i(Constant.TAG, getCurDate()+msg);
		FileUtils.logInfo(getCurDate()+msg);
	}
}
