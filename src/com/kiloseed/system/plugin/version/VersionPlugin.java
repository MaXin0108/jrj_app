package com.kiloseed.system.plugin.version;


import com.kiloseed.system.plugin.BasePlugin;
import com.kiloseed.system.util.UpgradeUtil;


import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzl on 16/7/7.
 */
public class VersionPlugin extends BasePlugin {

    @Override
    public boolean doExecute(String action, JSONArray args, CallbackContext callbackContext) throws Exception {
        if (action.equals("upgrade")) {
            this.upgrade(args, callbackContext);

            return true;
        }else if(action.equals("getVersion")) {
            this.getVersion(args, callbackContext);
            return true;
        }else if(action.equals("getCurAndLatestVersion")) {//获得当前版本和最新版本
            this.privateExecute(action,args, callbackContext);
            return true;
        }
        return true;
    }

    /**
     * 通过异步线程执行方法,具体方法见VersionAsyncTask
     * @param action
     * @param args
     * @param callbackContext
     * @return
     */
    private boolean privateExecute(String action, JSONArray args, CallbackContext callbackContext)  {
        Map map=new HashMap();
        VersionAsyncTask asyncTask=new VersionAsyncTask(action,args,callbackContext,this.doGetContext());
        asyncTask.execute(map);
        return true;
    }
    /**
     * 升级应用
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void upgrade(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        UpgradeUtil.upgrade(this.doGetContext());
        this.doSuccess(jsonObject,callbackContext);
    }
    /**
     * 获得版本信息，当前版本好来自AndroidManifest.xml中的android:versionName
     * 参数格式类似[{}]
     * 返回业务数据的格式类似{"version":"1.0.1"};
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void getVersion(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        String version= UpgradeUtil.getVersion(this.doGetContext());
        jsonObject.put("version",version);
        this.doSuccess(jsonObject,callbackContext);
    }
}
