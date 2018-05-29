package com.kiloseed.system.plugin.db;

import android.content.ContentValues;
import android.content.Context;

import com.kiloseed.system.task.PluginAsyncTask;
import com.kiloseed.system.util.DBUtil;
import com.kiloseed.system.util.JsonUtil;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by mac on 15/7/23.
 */
public class DBAsyncTask extends PluginAsyncTask {
    public DBAsyncTask(String action, JSONArray args,CallbackContext callbackContext,Context context){
        super(action,args,callbackContext,context);
    }
    public boolean doExecute(String action, JSONArray args, CallbackContext callbackContext) throws Exception {
        if (action.equals("doInsert")) {
            this.doInsert(args, callbackContext);
            return true;
        }else if (action.equals("doExecSql")) {
            this.doExecSql(args, callbackContext);
            return true;
        }else if (action.equals("doUpdate")) {
            this.doUpdate(args, callbackContext);
            return true;
        }else if (action.equals("doSave")) {
            this.doSave(args, callbackContext);
            return true;
        }else if (action.equals("doDelete")) {
            this.doDelete(args, callbackContext);
            return true;
        }else if (action.equals("doQuery")) {
            this.doQuery(args, callbackContext);
            return true;
        }else if (action.equals("doQueryOne")) {
            this.doQueryOne(args, callbackContext);
            return true;
        }else if (action.equals("doFindOne")) {
            this.doFindOne(args, callbackContext);
            return true;
        }else if (action.equals("doFind")) {
            this.doFind(args, callbackContext);
            return true;
        }

        return false;
    }

    /**
     * 新增数据。tableName为表名。params为参数，以key,value方式。
     * 参数格式类似[{"tableName":"T1",params:{"p1":"d1","p2":2}}]
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void doInsert(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=args.getJSONObject(0);
        String tableName=jsonObject.getString("tableName");
        JSONObject params=jsonObject.getJSONObject("params");

        ContentValues contentValues=new ContentValues();
        JsonUtil.doAddContentValueByJsonObject(params, contentValues);
        DBUtil.doInsert(this.doGetContext(), tableName, contentValues);
        this.doSuccess("",callbackContext);
    }

    /**
     * 执行sql语句。sql为sql语句。params为参数，目前类型为字符串
     * 参数格式类似[{"sql":"delete from T1 where a1=? and a2=?",params:["p1","p2":]}]
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void doExecSql(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=args.getJSONObject(0);
        String sql=jsonObject.getString("sql");
        JSONArray params=jsonObject.getJSONArray("params");
        String[] paramsArray=JsonUtil.doJsonArrayToStringArray(params);
        DBUtil.doExecSql(this.doGetContext(),sql,paramsArray);
        this.doSuccess("",callbackContext);
    }

    /**
     * 根据主键更新数据。tableName为表名。idName为主键名称，idValue为主键对应的值。params为要进行修改的值
     * 参数格式类似[{"tableName":"T1","idName":"a","idValue":"a1",params:{"p1":"d1","p2":2}}]
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void doUpdate(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=args.getJSONObject(0);
        String tableName=jsonObject.getString("tableName");
        String idName=jsonObject.getString("idName");
        String idValue=jsonObject.getString("idValue");

        JSONObject params=jsonObject.getJSONObject("params");
        ContentValues contentValues=new ContentValues();
        JsonUtil.doAddContentValueByJsonObject(params, contentValues);

        DBUtil.doUpdate(this.doGetContext(),tableName,contentValues,idName,idValue);
        this.doSuccess("",callbackContext);
    }

    /**
     * 保存数据，如果找到，修改，如果没有找到新增。tableName为表名。idName为主键名称，idValue为主键对应的值。params为要进行修改的值
     * 参数格式类似[{"tableName":"T1","idName":"a","idValue":"a1",params:{"p1":"d1","p2":2}}]
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void doSave(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=args.getJSONObject(0);
        String tableName=jsonObject.getString("tableName");
        String idName=jsonObject.getString("idName");
        String idValue=jsonObject.getString("idValue");

        JSONObject params=jsonObject.getJSONObject("params");
        ContentValues contentValues=new ContentValues();
        JsonUtil.doAddContentValueByJsonObject(params, contentValues);

        DBUtil.doSave(this.doGetContext(),tableName,contentValues,idName,idValue);
        this.doSuccess("",callbackContext);
    }

    /**
     * 删除数据，根据唯一条件，比如根据ID。tableName为表名。pName为主键名称，pValue为主键对应的值。params为要进行修改的值
     * 参数格式类似[{"tableName":"T1","pName":"a","pValue":"a1"}]
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void doDelete(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=args.getJSONObject(0);
        String tableName=jsonObject.getString("tableName");
        String pName=jsonObject.getString("pName");
        String pValue=jsonObject.getString("pValue");

        DBUtil.doDelete(this.doGetContext(), tableName, pName, pValue);
        this.doSuccess("",callbackContext);
    }
    /**
     * 根据sql语句查询。sql为sql语句。params为参数，目前类型为字符串
     * 参数格式类似[{"sql":"select * from T1 where a1=? and a2=?",params:["p1","p2":]}]
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void doQuery(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=args.getJSONObject(0);
        String sql=jsonObject.getString("sql");
        JSONArray params=jsonObject.getJSONArray("params");
        String[] paramsArray=JsonUtil.doJsonArrayToStringArray(params);
        List<Map<String,String>> list=DBUtil.doQuery(this.doGetContext(), sql, paramsArray);
        this.doSuccess(list,callbackContext);
    }
    /**
     * 根据sql语句查询,只查询其中一条数据,没有查询到返回null。sql为sql语句。params为参数，目前类型为字符串
     * 参数格式类似[{"sql":"select * from T1 where a1=? and a2=?",params:["p1","p2":]}]
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void doQueryOne(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=args.getJSONObject(0);
        String sql=jsonObject.getString("sql");
        JSONArray params=jsonObject.getJSONArray("params");
        String[] paramsArray=JsonUtil.doJsonArrayToStringArray(params);
        Map<String,String> one=DBUtil.doQueryOne(this.doGetContext(), sql, paramsArray);
        this.doSuccess(one,callbackContext);
    }
    /**
     * 根据一个条件查询数据 ,只返回一条数据，比如根据ID。tableName为表名。pName为主键名称，pValue为主键对应的值。params为要进行修改的值
     * 参数格式类似[{"tableName":"T1","pName":"a","pValue":"a1"}]
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void doFindOne(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=args.getJSONObject(0);
        String tableName=jsonObject.getString("tableName");
        String pName=jsonObject.getString("pName");
        String pValue=jsonObject.getString("pValue");

        Map<String,String> one=DBUtil.doFindOne(this.doGetContext(), tableName, pName, pValue);
        this.doSuccess(one,callbackContext);
    }
    /**
     * 根据一个条件查询数据 ,返回多条数据。tableName为表名。pName为主键名称，pValue为主键对应的值。params为要进行修改的值
     * 参数格式类似[{"tableName":"T1","pName":"a","pValue":"a1"}]
     * @param args
     * @param callbackContext
     * @throws JSONException
     */
    private void doFind(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject jsonObject=args.getJSONObject(0);
        String tableName=jsonObject.getString("tableName");
        String pName=jsonObject.getString("pName");
        String pValue=jsonObject.getString("pValue");

        List<Map<String,String>> list=DBUtil.doFind(this.doGetContext(), tableName, pName, pValue);
        this.doSuccess(list, callbackContext);
    }
}
