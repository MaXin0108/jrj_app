package com.kiloseed.system.util;

import android.content.ContentValues;

import com.alibaba.fastjson.JSON;
import com.kiloseed.system.helper.Constant;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
	/**
	 * 将jsonObject转换为ContentValue
	 * @param jsonObject
	 * @return
	 */
	public static ContentValues convertJsonObjectToContentValue(JSONObject  jsonObject){
		ContentValues contentValues=new ContentValues();
		Iterator<String> iterator = jsonObject.keys();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value=null;
			try {
				value = jsonObject.getString(key);
			} catch (JSONException e) {
				LogUtil.e("获得"+key+"失败");
				LogUtil.e(e);
			}
			contentValues.put(key, value);
		}
		return contentValues;
	}
	/**
	 * 将jsonObject转换为Map
	 * @param jsonObject
	 * @return
	 */
	public static Map<String,String> convertJsonObjectToMap(JSONObject  jsonObject){
		Map<String,String> map=new HashMap<String,String>();
		Iterator<String> iterator = jsonObject.keys(); 
		while (iterator.hasNext()) {
			String key = iterator.next(); 
			String value=null;
			try {
				value = jsonObject.getString(key);
			} catch (JSONException e) {
				LogUtil.e("获得"+key+"失败");
				LogUtil.e(e);
			} 
            map.put(key, value); 
		}
		return map;
	}
	/**
	 * 将Map转为Json
	 * @param map
	 * @return
	 */
	public static JSONObject convertMapToJsonObject(Map<String,String>  map){
		JSONObject jsonObject=new JSONObject();
		for(Map.Entry<String, String> entry:map.entrySet()){
			try {
				jsonObject.put(entry.getKey(), entry.getValue());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				LogUtil.e("转换数据失败");
				LogUtil.e(e);
			}
		}
		return jsonObject;
	}

	/**
	 * 将数组对象转换为json对象
	 * @param array
	 * @return
	 */
	public static JSONArray convertArrayToJsonArray(Object[] array){
		JSONArray jsonArray=new JSONArray();
		if(array!=null){
			for(int i=0;i<array.length;i++){
				jsonArray.put(array[i]);
			}
		}
		return jsonArray;
	}

	/**
	 * 将对象转换为json对象
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	public static Object convertObjToJsonObj(Object obj) throws JSONException {
		if(obj==null) return null;
		if(obj instanceof  JSONObject || obj instanceof  JSONArray || "".equals(obj)
				|| obj instanceof  String){
			return obj;
		}

		String json=JSON.toJSONString(obj);

		if(obj.getClass().isArray() || obj instanceof List){
			JSONArray jsonArray=new JSONArray(json);
			return jsonArray;
		}else{
			JSONObject jsonObject=new JSONObject(json);
			return jsonObject;
		}
	}

	/**
	 * 将jsonOjbect中所有的属性添加到contentValue中
	 * @param params
	 * @param contentValues
	 */
	public static void doAddContentValueByJsonObject(JSONObject params,ContentValues contentValues) throws JSONException {
		Iterator<String> it = params.keys();
		while(it.hasNext()){
			String key=it.next();
			Object value=params.get(key);
			if(value==null){
				contentValues.putNull(key);
			}else if(value instanceof Integer){
				contentValues.put(key,(Integer)value);
			}else if(value instanceof Double){
				contentValues.put(key,(Double)value);
			}else if(value instanceof Date){
				contentValues.put(key, Constant.SDF_FULL.format(value));
			}else{
				contentValues.put(key,value.toString());
			}
		}
	}

	/**
	 * 将jsonArray转换为String数组
	 * @param args
	 * @return
	 */
	public static String[] doJsonArrayToStringArray(JSONArray args) throws JSONException {
		String[] array=new String[args.length()];
		for(int i=0;i<args.length();i++){
			array[i]=args.getString(i);
		}
		return array;
	}
}
