package com.kiloseed.system.plugin.http;


import com.kiloseed.system.helper.Constant;
import com.kiloseed.system.helper.MUOContext;
import com.kiloseed.system.plugin.BasePlugin;
import com.kiloseed.system.util.ActivityUtil;
import com.kiloseed.system.util.HttpUtil;
import com.kiloseed.system.util.LogUtil;
import com.kiloseed.system.util.ToastUtils;
import com.kiloseed.system.vo.CommonInfo;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 15/7/13.
 */
public class HttpPlugin extends BasePlugin {
    @Override
    public boolean doExecute(String action, JSONArray args, CallbackContext callbackContext) throws Exception {
        HttpAsyncTask synAsyncTask=new HttpAsyncTask(action,args,callbackContext,this.doGetContext());
        Map map=new HashMap();
        synAsyncTask.execute(map);
        return true;
    }

}