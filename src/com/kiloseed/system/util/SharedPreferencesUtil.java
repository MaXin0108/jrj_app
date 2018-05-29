package com.kiloseed.system.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.kiloseed.system.helper.Constant;

public class SharedPreferencesUtil {
    /**
     * 保存信息
     * @param context
     * @param name
     * @param value
     */
    public static void saveInfo(Context context,String name,String value){
        //获取SharedPreferences对象
        SharedPreferences sharedPre=context.getSharedPreferences(Constant.SHAREDPREFERENCES_GROUP, context.MODE_PRIVATE);
        //获取Editor对象
        Editor editor=sharedPre.edit();
        //设置参数
        editor.putString(name, value);
        editor.commit();
    }
    /**
     * 获得信息
     * @param context
     * @param name
     * @return
     */
    public static String getInfo(Context context,String name){
        //获取SharedPreferences对象
        SharedPreferences sharedPre=context.getSharedPreferences(Constant.SHAREDPREFERENCES_GROUP, context.MODE_PRIVATE);
        return sharedPre.getString(name, "");
    }
}
