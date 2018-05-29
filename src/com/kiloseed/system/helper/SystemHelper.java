package com.kiloseed.system.helper;

/**
 * Created by mac on 15/7/19.
 */
public class SystemHelper {
    /**
     * 将system转换为对应服务器的地址
     * @param system
     * @param action
     * @return
     */
    public static String convertURLBySys(String system,String action){
        String url="";
        if(Constant.URL_SYSTEM.equals(system)){
            url=Constant.URL_PREFIX+action;
        }
        return url;
    }
}
