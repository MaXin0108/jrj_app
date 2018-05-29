package com.kiloseed.system.plugin.version;

import android.content.Context;

import com.kiloseed.system.helper.Constant;
import com.kiloseed.system.task.BaseStringAsyncTask;
import com.kiloseed.system.util.HttpUtil;
import com.kiloseed.system.util.LogUtil;
import com.kiloseed.system.util.UpgradeUtil;
import com.kiloseed.system.vo.CommonInfo;
import com.kiloseed.jrj.R;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by mac on 15/8/19.
 */
public class CheckNewVersionAsyncTask extends BaseStringAsyncTask<CommonInfo> {

    public CheckNewVersionAsyncTask(Context ctx) {
        super(ctx);
    }

    @Override
    public int getWaitingMsgResource() {
        return R.string.waiting_msg_check_upgrade;
    }

    @Override
    protected CommonInfo doInBackground(Map... params) {
        CommonInfo ret = new CommonInfo();
        ret.setSuccess(false);//默认false
        try {
            Map map = params[0];
            Context context = (Context) map.get(Constant.TASK_CONTEXT);

            if (HttpUtil.checkNet(context)) {//有网络情况下
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("system", Constant.URL_SYSTEM);
                jsonObject.put("action", Constant.UPGRADE_URL);
                JSONObject dataObject = new JSONObject();
                String version= UpgradeUtil.getVersion(context);
                dataObject.put("version", version);
                jsonObject.put("data",dataObject);
                ret = HttpUtil.postAndReturnObj(jsonObject);
                if (ret.isSuccess()) {
                    JSONObject jo = new JSONObject(ret.getData());
                    boolean isNeedUpgrade=jo.getBoolean("isNeedUpgrade");
                    if(isNeedUpgrade){
                        ret.setSuccess(true);
                        String downloadUrl=jo.getString("downloadUrl");
                        ret.setData(downloadUrl);
                    }else{
                        ret.setSuccess(false); //不需要升级
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.e(e);

        }
        return ret;
    }
}