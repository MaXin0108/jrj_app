package com.kiloseed.system.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @package: com.carestack.utils
 *
 * @fileName: ToastUtils.java
 *
 * @author ShiHuaJian
 *
 * @version: V1.0
 *
 * @date: 2014-5-16 上午11:49:31
 *
 * @describe: 
 */

public class ToastUtils {
	
	public static void toast(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void toast(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void toast(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}

}
