package com.kiloseed.system.plugin;

import android.content.Context;
import android.util.Log;

import com.kiloseed.system.util.JsonUtil;
import com.kiloseed.system.util.LogUtil;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mac on 15/7/15.
 */
public abstract class BasePlugin extends CordovaPlugin implements IBasePlugin {
    /**
     * 统一的异常处理
     * @param action          The action to execute.
     * @param args            The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return
     * @throws JSONException
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try{
            return this.doExecute(action,args,callbackContext);
        }catch (Exception e){
            LogUtil.e(e);
            this.doCallBack(false,"sorry,出现问题了。。。",null,callbackContext);
        }
        return false;

    }

    /**
     * 抽象类
     * @param action
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     */
    public abstract boolean doExecute(String action, JSONArray args, CallbackContext callbackContext) throws Exception ;

    /**
     * 获得上下文
     * @return
     */
    public Context doGetContext(){
        return this.webView.getContext();
    }

    /**
     * 检查参数个数是否符合要求，如果不符合要求，返回报错信息
     * @param argsCount
     * @param args
     * @param callbackContext
     * @return
     */
    public boolean doCheckArgsCount(int argsCount,JSONArray args, CallbackContext callbackContext){
        if(args.length()>=argsCount){
            return true;
        }else{
            callbackContext.error("至少需要"+argsCount+"个参数.");
            return false;
        }
    }

    /**
     * 检查数组中第一个参数中，至少需要几个属性
     * @param argsCount
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     */
    public boolean doCheckArgsCountFirstArg(int argsCount,JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(!this.doCheckArgsCount(1,args,callbackContext)) return true; //参数个数检查不通过
        JSONObject jsonObject=args.getJSONObject(0);
        if(jsonObject.length()>argsCount){
            return true;
        }else{
            callbackContext.error("第一个数组参数中，至少需要"+argsCount+"个属性.");
            return false;
        }
    }
    /**
     * 将返回值打包成json格式字符串
     * @param success
     * @param msg
     * @param data
     * @return
     * @throws JSONException
     */
    public static String doPackageReturnValue(boolean success,String msg,Object data) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("success",success);
        jsonObject.put("msg", msg);

        Object obj=JsonUtil.convertObjToJsonObj(data);
        jsonObject.put("data",obj);
        return jsonObject.toString();
    }

    /**
     * 完成方法回调
     * @param success
     * @param msg
     * @param data
     * @param callbackContext
     * @throws JSONException
     */
    public void doCallBack(boolean success,String msg,Object data,CallbackContext callbackContext) throws JSONException {
        this.doCallBack(success,msg,data,callbackContext,false);
    }

    /**
     * 完成回调
     * @param success
     * @param msg
     * @param data
     * @param callbackContext
     * @param isKeepCallBack  是否保持回调
     * @throws JSONException
     */
    public void doCallBack(boolean success,String msg,Object data,CallbackContext callbackContext,boolean isKeepCallBack) throws JSONException {
        String returnValue=BasePlugin.doPackageReturnValue(success,msg,data);
        if(success){
            this.successKeepCallBack(returnValue,callbackContext,isKeepCallBack);
        }else{
            this.errorKeepCallBack(returnValue,callbackContext,isKeepCallBack);
        }
    }

    /**
     * 直接以成功返回处理结果
     * @param data
     * @param callbackContext
     */
    public void success(String data,CallbackContext callbackContext){
        this.successKeepCallBack(data, callbackContext, false);
    }
    /**
     * 成功返回并保持调用
     * @param data
     * @param callbackContext
     * @param isKeepCallBack 是否保持调用
     */
    public void successKeepCallBack(String data,CallbackContext callbackContext,boolean isKeepCallBack){
        LogUtil.i("输出到前台数据:" + data);
        PluginResult pr=new PluginResult(PluginResult.Status.OK, data);
        pr.setKeepCallback(isKeepCallBack);
        callbackContext.sendPluginResult(pr);
    }

    /**
     * 失败返回是否要保持调用
     * @param data
     * @param callbackContext
     * @param isKeepCallBack
     */
    public void errorKeepCallBack(String data,CallbackContext callbackContext,boolean isKeepCallBack){
        LogUtil.i("输出到前台数据:" + data);
        PluginResult pr=new PluginResult(PluginResult.Status.ERROR, data);
        pr.setKeepCallback(isKeepCallBack);
        callbackContext.sendPluginResult(pr);
    }
    /**
     * 成功调用并以统一格式返回数据
     * @param data
     * @param callbackContext
     * @throws JSONException
     */
    public void doSuccess(Object data,CallbackContext callbackContext)throws JSONException {
        this.doCallBack(true, "", data, callbackContext);
    }

    /**
     * 成功调用并以统一格式返回数据
     * @param data
     * @param callbackContext
     * @throws JSONException
     */
    public void doError(String msg,Object data,CallbackContext callbackContext)throws JSONException {
        this.doCallBack(false, msg, data, callbackContext);
    }
    /**
     * 成功调用并以统一格式返回数据
     * @param data
     * @param callbackContext
     * @param isKeepCallBack
     * @throws JSONException
     */
    public void doSuccess(Object data,CallbackContext callbackContext,boolean isKeepCallBack)throws JSONException {
        this.doCallBack(true, "", data, callbackContext,isKeepCallBack);
    }

    /**
     * 成功调用并以统一格式返回数据
     * @param data
     * @param callbackContext
     * @param isKeepCallBack
     * @throws JSONException
     */
    public void doError(String msg,Object data,CallbackContext callbackContext,boolean isKeepCallBack)throws JSONException {
        this.doCallBack(false,msg,data,callbackContext,isKeepCallBack);
    }
}
