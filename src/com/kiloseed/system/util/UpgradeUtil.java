package com.kiloseed.system.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;


import com.kiloseed.system.helper.Constant;
import com.kiloseed.system.plugin.version.CheckNewVersionAsyncTask;
import com.kiloseed.system.plugin.version.UpgradeAsyncTask;
import com.kiloseed.system.vo.CommonInfo;


/**
 * 系统升级服务
 * @author zhangzl
 *
 */
public class UpgradeUtil {
	
	/**
	 * 根据路径，获取升级包
	 * @param context
	 * @param url
	 * @return
	 */
	public static String getApk(Context context,String url) throws Exception{
		return HttpUtil.getFileWithContext(context, url);
	}
	/**
	 * 安装升级包
	 * @param context
	 * @param apkPath
	 */
	public static void  installApk(Context context,String apkPath)
    {
        File apkfile = new File(apkPath);
        if (!apkfile.exists())
        {
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        context.startActivity(i);
    }
    /**
     * 升级到新版本
     */
    public static void upgrade(Context context,String url){
        UpgradeAsyncTask upgradeAsyncTask=new UpgradeAsyncTask(context){
            @Override
            protected void onPostExecute(CommonInfo result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                if(result.isSuccess()){

                }else{
                    //失败的时候给予提示
                    ToastUtils.toast(context,result.getMsg());
                }
            }
        };
        Map map=new HashMap();
        map.put(Constant.TASK_CONTEXT, context);
        map.put(Constant.UPGRADE_CONSTANT, url);
        upgradeAsyncTask.execute(map);
    }
    /**
     * 升级
     * @param context
     */
    public static void upgrade(Context context){
        //检测是否有新版本
        CheckNewVersionAsyncTask checkNewVersionAsyncTask=new CheckNewVersionAsyncTask(context){
            @Override
            protected void onPostExecute(CommonInfo result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                if (result.isSuccess()) {
                    //有新版本，升级
                    String url = result.getData();
                    UpgradeUtil.upgrade(context,url);
                }
            }
        };
        Map map=new HashMap();
        map.put(Constant.TASK_CONTEXT, context);
        checkNewVersionAsyncTask.execute(map);
    }

    /**
     * 获得当前版本号，当前版本好来自AndroidManifest.xml中的android:versionName
     * @param context
     * @return
     */
    public static String getVersion(Context context){
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            //versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return versionName;
    }
}
