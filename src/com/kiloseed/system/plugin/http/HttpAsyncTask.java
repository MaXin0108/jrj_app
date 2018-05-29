package com.kiloseed.system.plugin.http;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import com.kiloseed.system.helper.Constant;
import com.kiloseed.system.task.BaseAsyncTask;
import com.kiloseed.system.task.PluginAsyncTask;
import com.kiloseed.system.util.HttpUtil;
import com.kiloseed.system.util.LogUtil;
import com.kiloseed.system.vo.CommonInfo;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 15/7/20.
 */
public class HttpAsyncTask extends PluginAsyncTask {
    public HttpAsyncTask(String action, JSONArray args,CallbackContext callbackContext,Context context){
        super(action,args,callbackContext,context);
    }
    @Override
    public boolean doExecute(String action, JSONArray args, CallbackContext callbackContext) throws Exception {
        if (action.equals("post")) {
            this.post(args, callbackContext);
            return true;
        }

        return false;
    }
    /**
     * 以post请求提交数据
     * 参数格式类似[system:"linde",action:"synCurDate",data:{"pName":"a","pValue":"a1"}]
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void post(JSONArray args, CallbackContext callbackContext) throws Exception {
        if(HttpUtil.checkNet(this.doGetContext())){
            JSONObject jsonObject=args.getJSONObject(0);
            String data= HttpUtil.post(jsonObject);
            this.success(data, callbackContext);
        }else{
            this.doError("网络连接不上，请稍后再试！",null,callbackContext);
        }
    }
}
