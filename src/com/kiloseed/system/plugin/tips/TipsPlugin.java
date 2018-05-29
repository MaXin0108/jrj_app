package com.kiloseed.system.plugin.tips;


import com.kiloseed.system.activity.BaseActivity;
import com.kiloseed.system.helper.MUOContext;
import com.kiloseed.system.plugin.BasePlugin;
import com.kiloseed.system.util.ActivityUtil;
import com.kiloseed.system.util.ToastUtils;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by mac on 15/8/4.
 */
public class TipsPlugin  extends BasePlugin {
    @Override
    public boolean doExecute(String action, JSONArray args, CallbackContext callbackContext) throws Exception {
        if (action.equals("showModalDialog")) {
            this.showModalDialog(args, callbackContext);
            return true;
        }else if (action.equals("hiddenModalDialog")) {
            this.hiddenModalDialog(args, callbackContext);
            return true;
        }else if (action.equals("showTips")) {
            this.showTips(args, callbackContext);
            return true;
        }

        return false;
    }
    private static int countModalDialog=0;
    /**
     * 显示模态提示框
     * 参数格式类似["提示内容"]
     * @param args
     * @param callbackContext
     * @throws Exception
     */
    private void showModalDialog(JSONArray args, CallbackContext callbackContext) throws Exception {
        countModalDialog++;
        String waitingMsg="";
        if(args.length()>0){
            waitingMsg=args.getString(0);
        }
        BaseActivity ma=(BaseActivity)this.doGetContext();
        ma.getProgressDialog().setMessage(waitingMsg);
        //if(!ma.getProgressDialog().isShowing()){
        ma.getProgressDialog().show();
        //}
        this.doSuccess("",callbackContext);
    }
    /**
     * 隐藏模态提示框
     * @param args
     * @param callbackContext
     * @throws Exception
     */
    private void hiddenModalDialog(JSONArray args, CallbackContext callbackContext) throws Exception {
        countModalDialog--;
        if(countModalDialog<=0){
            BaseActivity ma=(BaseActivity)this.doGetContext();
            ma.getProgressDialog().hide();
            countModalDialog=0;
        }
        this.doSuccess("",callbackContext);
    }
    /**
     * 显示定时消失得提示
     * ["提示内容"]
     * @param args
     * @param callbackContext
     * @throws Exception
     */
    private void showTips(JSONArray args, CallbackContext callbackContext) throws Exception {
        String tips=args.getString(0);
        ToastUtils.toast(this.doGetContext(), tips);
        this.doSuccess("",callbackContext);
    }


}
