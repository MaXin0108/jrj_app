package com.kiloseed.system.util;

import android.provider.Settings;

/**
 * Created by mac on 15/7/18.
 */
public class DeviceUtil {
    private static String deviceId; //uuid，设备号
    /**
     * Get the device's Universally Unique Identifier (UUID).
     *
     * @return
     */
    public static String getDeviceId(){
        return deviceId;
    }

    /**
     * 在SystemApplication中对设备号进行设置
     * @param id
     */
    public static void setUuid(String id){
        DeviceUtil.deviceId=id;
    }
    public static String getModel() {
        String model = android.os.Build.MODEL;
        return model;
    }

    public static String getProductName() {
        String productname = android.os.Build.PRODUCT;
        return productname;
    }

    public static String getManufacturer() {
        String manufacturer = android.os.Build.MANUFACTURER;
        return manufacturer;
    }
    /**
     * Get the OS version.
     *
     * @return
     */
    public static String getOSVersion() {
        String osversion = android.os.Build.VERSION.RELEASE;
        return osversion;
    }

    public static String getSDKVersion() {
        @SuppressWarnings("deprecation")
        String sdkversion = android.os.Build.VERSION.SDK;
        return sdkversion;
    }
}
