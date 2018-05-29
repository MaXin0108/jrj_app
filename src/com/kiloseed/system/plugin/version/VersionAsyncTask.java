package com.kiloseed.system.plugin.version;

import android.content.Context;

import com.kiloseed.system.helper.Constant;
import com.kiloseed.system.task.PluginAsyncTask;
import com.kiloseed.system.util.HttpUtil;
import com.kiloseed.system.util.UpgradeUtil;
import com.kiloseed.system.vo.CommonInfo;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangzl on 16/7/11.
 */
public class VersionAsyncTask extends PluginAsyncTask  {
    public VersionAsyncTask(String action, JSONArray args,CallbackContext callbackContext,Context context){
        super(action,args,callbackContext,context);
    }
    @Override
    public boolean doExecute(String action, JSONArray args, CallbackContext callbackContext) throws Exception {
        if (action.equals("getCurAndLatestVersion")) {
            this.getCurAndLatestVersion(args, callbackContext);
            return true;
        }
        return false;
    }
    /**
     * 获得当前版本和最新版本信息
     * 参数格式类似[]
     * 返回业务数据的格式类似{downloadUrl:"",isNeedUpgrade:true,newVersion:"1.0.1",curVersion:"1.0.0"};
     * Constant.UPGRADE_URL参数要求参见Constant类中的说明
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void getCurAndLatestVersion(JSONArray args, CallbackContext callbackContext) throws Exception {
        String version= UpgradeUtil.getVersion(context);
        if (HttpUtil.checkNet(context)) {//有网络情况下
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("system", Constant.URL_SYSTEM);
            jsonObject.put("action", Constant.UPGRADE_URL);
            JSONObject dataObject = new JSONObject();

            dataObject.put("version", version);
            jsonObject.put("data",dataObject);

            CommonInfo ret = HttpUtil.postAndReturnObj(jsonObject);
            JSONObject jo = new JSONObject(ret.getData());
            jo.put("curVersion",version); //加入当前版本信息
            if (ret.isSuccess()) {
                this.doSuccess(jo,callbackContext);
            }else{
                this.doError(ret.getMsg(), jo,callbackContext);
            }
        }else{
            JSONObject jo = new JSONObject();
            jo.put("downloadUrl", "");
            jo.put("isNeedUpgrade", false);
            jo.put("newVersion","");
            jo.put("curVersion",version);
            this.doSuccess(jo,callbackContext);
        }
    }
}
