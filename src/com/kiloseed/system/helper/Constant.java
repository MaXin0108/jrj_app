package com.kiloseed.system.helper;

import java.text.SimpleDateFormat;

public class Constant {

	public final static String DB_NAME="fundapp.db"; //数据文件名称
	public final static int DB_VERSION=1;
	public static final String DATA_DIRECTORY = "/sdcard/.fund";  //数据库文件的目录

	//用于根据system找到URL_PREFIX，作为提交的链接的前缀.实现类见SystemHelper.java
	public static final String URL_SYSTEM="fund";
//	public static final String URL_PREFIX="http://192.168.1.127:8085/"; //生产环境
//	public static final String URL_PREFIX="http://139.196.194.174:8888/"; //测试环境
//  public static final String URL_PREFIX="http://52.14.146.214:8080/"; //生产环境
	public static final String URL_PREFIX=""; //生产环境

	/**版本升级链接，UPGRADE_URL值为相对于URL_PREFIX的值
	当前版本号来自AndroidManifest.xml中的android:versionName
	提交到该链接的信息为data={version:"1.0.1"}
	返回的请求数据为{success:true,msg:"",data:{downloadUrl:"",isNeedUpgrade:true,newVersion:"1.0.1"}}
	 isNeedUpgrade 为true,表示需要升级.newVersion表示要升级到的版本号。
	 downloadUrl表示升级的链接。
	*/
	public static final String VERSION="0.0.1";
	public static final String UPGRADE_URL="app/upgrade";//升级url
	public static final String PACKAGE_NAME="com.kiloseed.fund";//应用名
	public static final boolean isOpenUpgradeOnStart=false;  //是否在应用打开的时候进行软件升级

	public final static String TAG="com.kiloseed"; //日志输出Tag


	/*************************************以下为不允许修改内容************************************************/
	public static final String UPGRADE_CONSTANT="upgradeDownloadUrl";//升级url常量
	public final static String TASK_CONTEXT="context"; //传入的异步线程中的context

	public static final String SHAREDPREFERENCES_GROUP="spconfig";//SharedPreferences中默认分组
	
	public final static SimpleDateFormat SDF=new SimpleDateFormat("MM月dd日 HH点mm分");  //"yyyy-MM-dd HH:mm:ss"
	public final static SimpleDateFormat SDF_FULL=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //"yyyy-MM-dd HH:mm:ss"
	public final static SimpleDateFormat SDF_NO_SEC=new SimpleDateFormat("yyyy-MM-dd HH:mm");  //"yyyy-MM-dd HH:mm:ss"
	public final static SimpleDateFormat SDF_HMS=new SimpleDateFormat("HH:mm:ss");  //"HH:mm:ss"
	public final static SimpleDateFormat SDF_SIMPLE=new SimpleDateFormat("yyyy-M-d H:m");  //yyyy-M-d H:m
	public static String SDF_SYN_STR="yyyy-MM-ddHH:mm:ss"; 
	public static SimpleDateFormat SDF_SYN=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //日期格式

	public final static String NOTICE_DUMPUSER="@0000000"; //退出的时候，取消

	//打印插件开启蓝牙返回处理的请求码
	public static final int REQUEST_ENABLE_BLUETOOTH=10000;  //请求启用蓝牙返回码

	//打印插件，返回内容类型定义
	public static final String PRINT_TYPE_00="00"; //打印成功
	public static final String PRINT_TYPE_01="01"; //打印机出错，比如缺纸
	public static final String PRINT_TYPE_02="02"; //没有可以连接的打印机,打印机返回配对信息列表
	public static final String PRINT_TYPE_03="03"; //返回扫描出的蓝牙设备列表
	public static final String PRINT_TYPE_04="04"; //完成扫描

	public static final String PRINT_INFO_ADDRESS="PRINT_INFO_ADDRESS"; //保存的打印机地址
	public static final String PRINT_INFO_NAME="PRINT_INFO_NAME"; //保存的打印机名称

	public static final int PRINT_INDEX=0; //打印机序号，默认打印机为0

}
