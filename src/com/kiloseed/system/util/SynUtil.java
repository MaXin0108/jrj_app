package com.kiloseed.system.util;

import android.content.ContentValues;
import android.content.Context;

import com.kiloseed.system.exception.BizException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by mac on 15/7/19.
 */
public class SynUtil {
    /**
     * 同步并查询数据。sql为sql语句。params为参数，目前类型为字符串
     * 参数格式类似[{system:"linde",action:"synCurDate","data":{},"sql":"select * from T1 where a1=? and a2=?",params:["p1","p2":]}]
     * @param jsonObject
     * @throws Exception
     */
    public static List<Map<String,String>> synData(JSONObject jsonObject,Context context) throws Exception{
        syn(jsonObject,context);
        String sql=jsonObject.getString("sql");
        JSONArray params=jsonObject.getJSONArray("params");
        String[] paramsArray=JsonUtil.doJsonArrayToStringArray(params);
        List<Map<String,String>> list=DBUtil.doQuery(context, sql, paramsArray);
        return list;
    }

    /**
     *
     * @param jsonObject
     * @param context
     * 对应连接为action,data为要提交的数据,lastSynDate为上次同步时间
     * @throws Exception
     */
    public static void syn(JSONObject jsonObject,Context context) throws Exception{
        if(HttpUtil.checkNet(context)){
            String system=jsonObject.getString("system");
            String action=jsonObject.getString("action");
            String data=jsonObject.optString("data");
            String lastSynDate=getLastSynDate(system,action,data,context);
            jsonObject.put("lastSynDate",lastSynDate==null?"1970-01-01 00:00:00":lastSynDate);
            String content=HttpUtil.post(jsonObject);
            saveSynData(system,action,data,content,context);
        }
    }

    /**
     * 保存同步数据
     * @param data
     */
    public static void saveSynData(String system,String action,String data,String content,Context context) throws Exception{
        String a = content;
        JSONObject jsonObject=new JSONObject(content);
        boolean success=jsonObject.getBoolean("success");
        if(!success){
            throw new BizException("同步异常");
        }
        String lastSynDate=jsonObject.getString("lastSynDate");
        String jsondata=jsonObject.getString("data");
        JSONObject jsonObjectData=new JSONObject(jsondata);
        JSONArray contentJsonArray=jsonObjectData.getJSONArray("content");
        for(int i=0;i<contentJsonArray.length();i++){
            JSONObject tableJsonObject=contentJsonArray.getJSONObject(i);
            saveTableJsonObject(tableJsonObject,context);
        }
        //修改最后同步时间
        Map<String,String> map=DBUtil.doQueryOne(context
                ,"select * from C_SYN where SYN_SYSTEM=? and SYN_ACTION=? and SYN_DATA=?", new String[]{system, action, data});
        if(map==null){
            DBUtil.doExecSql(context,"INSERT INTO C_SYN(LAST_SYN_DATE,SYN_SYSTEM,SYN_ACTION,SYN_DATA) values(?,?,?,?)"
                    ,new String[]{lastSynDate,system, action, data});
        }else{
            DBUtil.doExecSql(context,"update C_SYN set LAST_SYN_DATE=?  where SYN_SYSTEM=? and SYN_ACTION=? and SYN_DATA=? "
                    ,new String[]{lastSynDate,system, action, data});
        }
    }


    /**
     * 保存table的json数据
     * @param tableJsonObject
     */
    public static void saveTableJsonObject(JSONObject tableJsonObject,Context context) throws Exception{
        String tableName=tableJsonObject.getString("tableName");
        String keys=tableJsonObject.getString("keys");
        JSONArray dataList=tableJsonObject.getJSONArray("dataList");
        for(int i=0;i<dataList.length();i++){
            JSONObject oneData=dataList.getJSONObject(i);
            ContentValues contentValues=JsonUtil.convertJsonObjectToContentValue(oneData);
            String keyValue=oneData.getString(keys); //根据单一主键处理
            DBUtil.doSave(context,tableName,contentValues,keys,keyValue);
        }
    }
    /**
     * 获得上次同步时间
     * @param system
     * @param action
     * @param data
     * @param context
     * @return
     */
    public static String getLastSynDate(String system,String action,String data,Context context){
        Map<String,String> map=DBUtil.doQueryOne(context, "select * from C_SYN where SYN_SYSTEM=? and SYN_ACTION=? and SYN_DATA=?", new String[]{system, action, data});
        return map!=null?map.get("LAST_SYN_DATE"):null;
    }
}
