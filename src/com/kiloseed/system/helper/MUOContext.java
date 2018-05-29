package com.kiloseed.system.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 15/7/13.
 */
public class MUOContext {
    private static Map<String,String> dataMap=new HashMap<String,String>();
    public static void putString(String key,String value){
        dataMap.put(key,value);
    }
    public static String getString(String key){
        return dataMap.get(key);
    }
    public static void removeString(String key){
        if(dataMap.containsKey(key)){
            dataMap.remove(key);
        }
    }
    public static String[] getAllKey(){
        return dataMap.keySet().toArray(new String[0]);
    }
    public static Map<String,String> getAllData(){
        return dataMap;
    }
    public static void clearAll(){
        dataMap.clear();
    }
}
