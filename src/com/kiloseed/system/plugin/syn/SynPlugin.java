package com.kiloseed.system.plugin.syn;

import com.kiloseed.system.helper.Constant;
import com.kiloseed.system.helper.MUOContext;
import com.kiloseed.system.plugin.BasePlugin;
import com.kiloseed.system.plugin.http.HttpAsyncTask;
import com.kiloseed.system.util.LogUtil;
import com.kiloseed.system.util.SynUtil;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 15/7/13.
 */
public class SynPlugin extends BasePlugin {

    @Override
    public boolean doExecute(String action, JSONArray args, CallbackContext callbackContext) throws Exception {
        SynAsyncTask synAsyncTask=new SynAsyncTask(action,args,callbackContext,this.doGetContext());
        Map map=new HashMap();
        synAsyncTask.execute(map);
        return true;
    }


}
