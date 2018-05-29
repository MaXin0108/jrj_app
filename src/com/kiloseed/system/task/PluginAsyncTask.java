package com.kiloseed.system.task;

import android.content.Context;
import android.os.AsyncTask;

import com.kiloseed.system.plugin.BasePlugin;
import com.kiloseed.system.plugin.IBasePlugin;
import com.kiloseed.system.util.LogUtil;
import com.kiloseed.system.vo.CommonInfo;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 15/7/21.
 */
public abstract class PluginAsyncTask extends AsyncTask<Map, String, Map> implements IBasePlugin {
    protected Context context;
    protected String curAction;
    protected JSONArray curJsonArray;
    protected CallbackContext curCallbackContext;
    private CommonInfo commonInfo=new CommonInfo();
    public PluginAsyncTask(String action, JSONArray args,CallbackContext callbackContext,Context context){
        super();
        this.context=context;
        this.curAction=action;
        this.curJsonArray=args;
        this.curCallbackContext=callbackContext;
    }

    /**
     * 获得上下文
     * @return
     */
    public Context doGetContext(){
        return this.context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Map map) {
        super.onPostExecute(map);
        /*
        LogUtil.i("输出到前台数据:" + commonInfo.getData());
        if(commonInfo.isSuccess()){
            this.curCallbackContext.success(commonInfo.getData());
        }else{
            this.curCallbackContext.error(commonInfo.getData());
        }
        */
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Map doInBackground(Map... params) {
        try{
            this.doExecute(this.curAction,this.curJsonArray,this.curCallbackContext);
        }catch (Exception e){
            LogUtil.e(e);
            try {
                this.doCallBack(false,"sorry,出现问题了。。。",null,this.curCallbackContext);
            } catch (JSONException e1) {
                LogUtil.e(e);
            }
        }
        Map retMap=new HashMap();
        return retMap;
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
     * 完成方法回调
     * @param success
     * @param msg
     * @param data
     * @param callbackContext
     * @throws JSONException
     */
    public void doCallBack(boolean success,String msg,Object data,CallbackContext callbackContext) throws JSONException {
        this.doCallBack(success, msg, data, callbackContext, false);
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
            this.successKeepCallBack(returnValue, callbackContext, isKeepCallBack);
        }else{
            this.errorKeepCallBack(returnValue, callbackContext, isKeepCallBack);
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
