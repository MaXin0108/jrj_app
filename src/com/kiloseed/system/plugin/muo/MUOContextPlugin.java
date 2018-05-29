package com.kiloseed.system.plugin.muo;

import com.kiloseed.system.helper.MUOContext;
import com.kiloseed.system.plugin.BasePlugin;
import com.kiloseed.system.util.JsonUtil;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by mac on 15/7/13.
 */
public class MUOContextPlugin extends BasePlugin {

    public boolean doExecute(String action, JSONArray args, CallbackContext callbackContext) throws Exception {
        if (action.equals("putString")) {
            if(!this.doCheckArgsCountFirstArg(1, args, callbackContext)) return true; //参数个数检查不通过
            this.putString(args, callbackContext);
            return true;
        }else if (action.equals("getString")) {
            this.getString(args, callbackContext);
            return true;
        }else if (action.equals("removeString")) {
            this.removeString(args, callbackContext);
            return true;
        }else if (action.equals("getAllKey")) {
            this.getAllKey(args, callbackContext);
            return true;
        }else if (action.equals("getAllData")) {
            this.getAllData(args, callbackContext);
            return true;
        }
        return false;
    }

    /**
     * 设置参数，传入数组,key为key，value作为要放入的内容
     * 参数格式类似[{"p1":"d1","p2":"d2"}]
     * 返回业务数据的格式为""
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void putString(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=args.getJSONObject(0);
        Iterator<String> it = jsonObject.keys();
        while(it.hasNext()){
            String key=it.next();
            String value=jsonObject.getString(key);
            MUOContext.putString(key,value);
        }
        this.doSuccess("",callbackContext);
    }

    /**
     * 获得参数。将参数数组中的内容全部放到json格式,对象的key为变量，value为MUO中对应的值
     * 参数格式类似["p1","p2"]
     * 返回业务数据的格式类似{"p1":"v1","p2":"v2"};
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void getString(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        for(int i=0;i<args.length();i++){
            String key = args.getString(i);
            String value=MUOContext.getString(key);
            jsonObject.put(key,value);
        }
        this.doSuccess(jsonObject,callbackContext);
    }
    /**
     * 删除参数。将参数数组中的内容全部放到json格式,对象的key为变量，value为MUO中对应的值
     * 参数格式类似["p1","p2"]
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void removeString(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        for(int i=0;i<args.length();i++){
            String key = args.getString(i);
            MUOContext.removeString(key);
        }
        this.doSuccess("",callbackContext);
    }
    /**
     * 获得所有参数key
     * 参数格式类似[],返回值data值为
     * 返回业务数据的格式类似["p1","p2"]
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void getAllKey(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String[] array=MUOContext.getAllKey();
        this.doSuccess(array,callbackContext);
    }
    /**
     * 获得所有参数
     * 参数格式类似[]
     * 返回业务数据的格式类似{"p1":"v1","p2":"v2"};
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void getAllData(JSONArray args, CallbackContext callbackContext) throws JSONException {
        Map<String,String> map=MUOContext.getAllData();
        this.doSuccess(map,callbackContext);
    }
}
