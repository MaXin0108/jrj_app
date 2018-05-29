package com.kiloseed.system.plugin.db;

import android.content.ContentValues;

import com.kiloseed.system.helper.Constant;
import com.kiloseed.system.helper.MUOContext;
import com.kiloseed.system.plugin.BasePlugin;
import com.kiloseed.system.util.DBUtil;
import com.kiloseed.system.util.JsonUtil;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 15/7/13.
 */
public class DBPlugin extends BasePlugin {

    public boolean doExecute(String action, JSONArray args, CallbackContext callbackContext) throws Exception {
        DBAsyncTask synAsyncTask=new DBAsyncTask(action,args,callbackContext,this.doGetContext());
        Map map=new HashMap();
        synAsyncTask.execute(map);
        return true;
    }


}