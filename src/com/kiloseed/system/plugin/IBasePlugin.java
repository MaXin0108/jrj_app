package com.kiloseed.system.plugin;

import android.content.Context;

import org.apache.cordova.CallbackContext;
import org.json.JSONException;

/**
 * Created by mac on 16/6/21.
 */
public interface IBasePlugin {
    /**
     * 获得上下文
     * @return
     */
    public Context doGetContext();

    /**
     * 成功调用并以统一格式返回数据
     * @param data
     * @param callbackContext
     * @throws JSONException
     */
    public void doSuccess(Object data,CallbackContext callbackContext)throws JSONException;
    /**
     * 成功调用并以统一格式返回数据
     * @param data
     * @param callbackContext
     * @throws JSONException
     */
    public void doError(String msg,Object data,CallbackContext callbackContext)throws JSONException;
    /**
     * 成功调用并以统一格式返回数据
     * @param data
     * @param callbackContext
     * @param isKeepCallBack
     * @throws JSONException
     */
    public void doSuccess(Object data,CallbackContext callbackContext,boolean isKeepCallBack)throws JSONException;
    /**
     * 成功调用并以统一格式返回数据
     * @param data
     * @param callbackContext
     * @param isKeepCallBack
     * @throws JSONException
     */
    public void doError(String msg,Object data,CallbackContext callbackContext,boolean isKeepCallBack)throws JSONException;
}
