package com.kiloseed.system.plugin.syn;

import android.content.Context;


import com.kiloseed.system.helper.Constant;
import com.kiloseed.system.task.BaseAsyncTask;
import com.kiloseed.system.task.PluginAsyncTask;
import com.kiloseed.system.util.HttpUtil;
import com.kiloseed.system.util.LogUtil;
import com.kiloseed.system.util.SynUtil;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by mac on 15/7/20.
 */
public class SynAsyncTask  extends PluginAsyncTask {
    public SynAsyncTask(String action, JSONArray args,CallbackContext callbackContext,Context context){
        super(action,args,callbackContext,context);
    }
    @Override
    public boolean doExecute(String action, JSONArray args, CallbackContext callbackContext) throws Exception {
        if (action.equals("synAndQueryData")) {
            this.synAndQueryData(args, callbackContext);
            return true;
        }else if (action.equals("syn")) {
            this.syn(args, callbackContext);
            return true;
        }

        return false;
    }
    /**
     * 同步并查询数据。sql为sql语句。params为参数，目前类型为字符串
     * 参数格式类似[{system:"linde",action:"synCurDate","data":{},"sql":"select * from T1 where a1=? and a2=?",params:["p1","p2":]}]
     * data中的lastSynDate会自动补充
     * 返回业务数据的格式类似{"p1":"v1","p2":"v2"};
     * 提交到链接数据 data= {p1:"",p2:"",lastSynDate,""},其中p1和p2来自data中的内容，这里只是为了举例。lastSynDate系统自动加上
     * 同步返回数据  {success:true,lastSynDate:"2015-01-01 01:01:01",data:{content:[tableName:"表名",keys:"主键",dataList:[{f1:"v1",f2:"v2"}]]}}
     * 同步链接参数：data={userName:””,carNo:””,lastSynDate:””}
     如果lastSynDate为空，值为null
     返回：{success:false,msg:"",data:{
     synDate:”同步时间,到秒”
     ,content:[
     {
     “tableName”:”表名”,
     “keys”:”以逗号分割的字段名"
     dataList”:[
     {“字段名”:字段值}
     ]
     }
     ]
     }}
     *
     *
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void synAndQueryData(JSONArray args, CallbackContext callbackContext) throws Exception {
        JSONObject jsonObject=args.getJSONObject(0);
        List<Map<String,String>> list=SynUtil.synData(jsonObject,doGetContext());
        this.doSuccess(list, callbackContext);
    }
    /**
     * 同步。
     * 参数格式类似[{system:"linde",action:"synCurDate","data":{}]
     * data中的lastSynDate会自动补充
     * 返回业务数据的格式类似{"p1":"v1","p2":"v2"};
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void syn(JSONArray args, CallbackContext callbackContext) throws Exception {
        JSONObject jsonObject=args.getJSONObject(0);
        SynUtil.syn(jsonObject, doGetContext());
        this.doSuccess("", callbackContext);
    }

}
