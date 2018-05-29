package com.kiloseed.system.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	public DatabaseHelper(Context context){
		super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/*
		 * C_USER用户表
		 * USERID用户id,PASSWORD密码,LAST_LOGIN_DATE_NETWORK联网登录成功时间，LAST_LOGIN_DATE_NETWORK最后登录时间
		 * IS_AUTO_LOGIN是否自动登录
		 */

		db.execSQL("CREATE TABLE C_USER ( USERID VARCHAR(100) NOT NULL PRIMARY KEY,SITECODE VARCHAR(50),USERNAME VARCHAR(50),PASSWORD VARCHAR(50),LAST_LOGIN_DATE_NETWORK VARCHAR(20),LAST_LOGIN_DATE  VARCHAR(20),IS_AUTO_LOGIN VARCHAR(1),SITEID VARCHAR(11),EMAIL VARCHAR(50),NAME VARCHAR(50),SITENAME VARCHAR(50))");
		// TODO Auto-generated method stub
		/*
		 *C_LOGIN_LOG 登录日志表
		 *
		 *
		 */
		db.execSQL("CREATE TABLE C_LOGIN_LOG ( ID integer NOT NULL PRIMARY KEY autoincrement ,TYPE VARCHAR(10),RESOURCE VARCHAR(10),UNAME VARCHAR(20),IP VARCHAR(20),TIME VARCHAR(50),AGENT VARCHAR(255),SITE_CODE VARCHAR(50),SITE_NAME VARCHAR(50))");
		/**
		 * C_LASTFRESH 各模块最后更新时间表
		 */
		db.execSQL("CREATE TABLE C_LASTFRESH ( ID integer NOT NULL PRIMARY KEY autoincrement ,CODE VARCHAR(30),FRESHTIME VARCHAR(30),ISFIRST VARCHAR(11),SITEID VARCHAR(11) )");
		/**
		 * C_SYN同步表
		 * SYN_SYSTEM同步系统，SYN_ACTION同步链接,SYN_DATA同步数据参数,LAST_SYN_DATE上次同步时间
		 * 根据SYN_SYSTEM、SYN_ACTION、SYN_DATA三个参数获得上次同步时间，如果找不到，上次同步时间为1970-01-01
		 * 同步获得数据时候，也获得服务器的时间作为上次同步时间。这样保证同步时间的正确性。
		 */
		db.execSQL("CREATE TABLE C_SYN ( SYN_SYSTEM VARCHAR(20),SYN_ACTION VARCHAR(80),SYN_DATA VARCHAR(100),LAST_SYN_DATE  VARCHAR(20))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub


	}

}
