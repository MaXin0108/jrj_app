package com.kiloseed.system.util;

import android.app.ProgressDialog;
import android.content.Context;

/** 
 * @author guoq 
 * @e-mail guoq@kiloseed.com
 * @tel 13162939792 
 * @date 2014年12月21日 下午7:53:22 
 * @description 
 */
public class ProgressDialogUtil {
	/**
	 * 获得进度
	 * @param context
	 * @param resouce
	 * @return
	 */
	public static ProgressDialog getProgressDialog(Context context,int resouce){
		ProgressDialog loadingDialog = new ProgressDialog(context);
		loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		loadingDialog.setMessage(context.getResources().getString(resouce));
		loadingDialog.setCancelable(true);
		loadingDialog.setCanceledOnTouchOutside(false);
		return loadingDialog;
	}
	/**
	 * 创建上下文
	 * @param context
	 * @return
	 */
	public static ProgressDialog createProgressDialog(Context context){
		ProgressDialog loadingDialog = new ProgressDialog(context);
		loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		loadingDialog.setCancelable(false);
		loadingDialog.setCanceledOnTouchOutside(false);
		return loadingDialog;
	}
}	
