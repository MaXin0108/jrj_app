package com.kiloseed.system.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kiloseed.system.helper.DatabaseHelper;

public class DBUtil {
	private final static int write=1;
	private final static int read=0;
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static SQLiteDatabase getDatabase(Context context,int isWrite){
		SQLiteDatabase mSQLiteDatabase=null;
		DatabaseHelper database = new DatabaseHelper(context);
		if(isWrite==1){
			mSQLiteDatabase=database.getWritableDatabase();
		}else{
			mSQLiteDatabase=database.getReadableDatabase();
		}
		return mSQLiteDatabase;
	}
	/**
	 * 新增
	 * @param context
	 * @param tableName
	 * @param contentValue
	 */
	public static long doInsert(Context context,String tableName,ContentValues contentValue){
		SQLiteDatabase db = getDatabase(context,write);
		long ret=db.insert(tableName, null, contentValue);
		db.close();
		return ret;
	}
	/**
	 * 执行sql语句
	 * @param context
	 * @param sql
	 * @param args
	 */
	public static void doExecSql(Context context,String sql,String[]  args){
		SQLiteDatabase db = getDatabase(context,write);
		db.execSQL(sql, args);
		db.close();
	}
	/**
	 * 修改
	 * @param context
	 * @param tableName
	 * @param contentValue
	 * @param idName
	 * @param idValue
	 */
	public static long doUpdate(Context context,String tableName,ContentValues contentValue,String idName,String idValue){
		SQLiteDatabase db = getDatabase(context,write);
		long ret=db.update(tableName, contentValue, idName+"=?", new String[]{idValue});
		db.close();
		return ret;
	}
	/**
	 * 保存数据，如果找到，修改，如果没有找到新增
	 * @param context
	 * @param tableName
	 * @param contentValue
	 * @param idName
	 * @param idValue
	 * @return
	 */
	public static long doSave(Context context,String tableName,ContentValues contentValue,String idName,String idValue){
		List<Map<String,String>> list=DBUtil.doQuery(context, "select * from "+tableName+" where "+idName+"=?", new String[]{idValue});
		long ret=0;
		if(list.size()==0){
			ret=DBUtil.doInsert(context, tableName, contentValue);
		}else{
			Map<String,String> map=list.get(0);
			ret=DBUtil.doUpdate(context,tableName, contentValue, idName, idValue);
		}
		return ret;
	}
	/**
	 * 删除数据，根据唯一条件，比如根据ID
	 * @param context
	 * @param tableName
	 * @param pName
	 * @param pValue
	 */
	public static long doDelete(Context context,String tableName,String pName,String pValue){
		SQLiteDatabase db = getDatabase(context,write);
		long ret=db.delete(tableName, pName+"=?", new String[]{pValue});
		db.close();
		return ret;
	}
	
	/**
	 * 查询数据库
	 * @param context
	 * @param sql
	 * @param args
	 * @return
	 */
	public static List<Map<String,String>> doQuery(Context context,String sql,String[]  args){
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		SQLiteDatabase db = getDatabase(context,read);
		Cursor cursor = db.rawQuery(sql, args);
		for (cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
			int colCount=cursor.getColumnCount();
			Map<String,String> map=new HashMap<String,String>();
			for(int i=0;i<colCount;i++){
				String colName=cursor.getColumnName(i);
				map.put(colName.toUpperCase(), cursor.getString(i));
			}
			list.add(map);
		}
		if(cursor!=null){
			cursor.close();
		}
		db.close();
		return list;
	}
	/**
	 * 只查询其中一条数据 
	 * @param context
	 * @param sql
	 * @param args
	 * @return
	 */
	public static Map<String,String> doQueryOne(Context context,String sql,String[]  args){
		List<Map<String,String>> list=doQuery(context, sql, args);
		if(list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}
	/**
	 * 根据一个条件查询数据 ,只返回一条数据，比如根据ID
	 * @param context
	 * @param tableName
	 * @param pName
	 * @param pValue
	 * @return
	 */
	public static Map<String,String> doFindOne(Context context,String tableName,String pName,String pValue){
		String sql="select * from "+tableName+" where "+pName+"=? ";
		List<Map<String,String>> list=doQuery(context,sql,new String[]{pValue});
		if(list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}
	/**
	 * 根据一个条件查询数据 ,返回多条数据
	 * @param context
	 * @param tableName
	 * @param pName
	 * @param pValue
	 * @return
	 */
	public static List<Map<String,String>> doFind(Context context,String tableName,String pName,String pValue){
		String sql="select * from "+tableName+" where "+pName+"=? ";
		List<Map<String,String>> list=doQuery(context,sql,new String[]{pValue});
		return list;
	}
}
