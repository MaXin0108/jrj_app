package com.kiloseed.system.helper;

import android.app.Application;
import android.provider.Settings;

import com.kiloseed.system.util.DeviceUtil;
import com.kiloseed.system.util.LogUtil;



/**
 * Created by mac on 15/7/18.
 */
public class SystemApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();//执行初始化方法
    }

    /**
     * 执行初始化
     */
    public void init(){
        //设置设备id
        String deviceId=Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        DeviceUtil.setUuid(deviceId);
        LogUtil.i("D:" + DeviceUtil.getDeviceId());
        LogUtil.i("M:"+DeviceUtil.getModel());
        LogUtil.i("MA:"+DeviceUtil.getManufacturer());
        LogUtil.i("OSV:"+DeviceUtil.getOSVersion());
        LogUtil.i("PN:"+DeviceUtil.getProductName());

    }
}

