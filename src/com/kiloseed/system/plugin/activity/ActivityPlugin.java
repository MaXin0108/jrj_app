package com.kiloseed.system.plugin.activity;


import com.kiloseed.system.helper.Constant;
import com.kiloseed.system.helper.MUOContext;
import com.kiloseed.system.plugin.BasePlugin;
import com.kiloseed.system.util.ActivityUtil;
import com.kiloseed.system.util.LogUtil;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by mac on 15/7/19.
 */
public class ActivityPlugin extends BasePlugin {
    @Override
    public boolean doExecute(String action, JSONArray args, CallbackContext callbackContext) throws Exception {
        if (action.equals("switchToLogin")) {
            this.switchToLogin(args, callbackContext);
            return true;
        }
        return false;
    }
    /**
     * 切换到登录界面
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void switchToLogin(JSONArray args, CallbackContext callbackContext) throws Exception {

        ActivityUtil.switchToLogin(this.doGetContext());
        /*
        if(this.doGetContext() instanceof MainActivity){
            MainActivity mainActivity=(MainActivity)this.doGetContext();
            mainActivity.finish();
        }
        */

    }
}
