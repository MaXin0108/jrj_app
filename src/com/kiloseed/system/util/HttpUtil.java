package com.kiloseed.system.util;

import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alibaba.fastjson.JSON;
import com.kiloseed.system.helper.Constant;
import com.kiloseed.system.helper.SystemHelper;
import com.kiloseed.system.vo.CommonInfo;


public class HttpUtil {
	
	private static HttpClient  _httpClient=null; 
	public static void clearHttpClient(){
		_httpClient=null;
	}
	/**
	 * 获得httpclient
	 * @return
	 */
	public static HttpClient getHttpClient(){
		if(_httpClient==null){
			
			//_httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000); //设置连接超时30秒
			//_httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,30000); // 读取超时
			BasicHttpParams httpParams=new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 300);
			HttpConnectionParams.setSoTimeout(httpParams, 3000);
			_httpClient= new DefaultHttpClient(httpParams); 
		}
		return _httpClient;
	}
	public static String encode(String str){
		return java.net.URLEncoder.encode(str);
	}

	/**
	 * 判断当前是否连接到网络
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context){
		/*根据系统服务获取手机连接管理对象*/  
	    ConnectivityManager cm = (ConnectivityManager)context.  
	            getSystemService(Context.CONNECTIVITY_SERVICE);  
	    if(cm!=null){  
	        NetworkInfo info = cm.getActiveNetworkInfo();  
	        if(info!=null && info.isConnected()){     
	            //判断当前网络是否连接  
	            if(info.getState()==NetworkInfo.State.CONNECTED){  
	                return true;  
	            }else {
	            	NetworkInfo netInfo = cm.getNetworkInfo(1);
	                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
	                	return true;
	            }
	        }  
	    }  
	    return false;  
	}

	/**
	 * 获得IP
	 * 
	 * @param context
	 */
	public static String getAddressInfo(Context context){
		StringBuffer sb=new StringBuffer();
		ConnectivityManager connectivity = (ConnectivityManager)context.  
		            getSystemService(Context.CONNECTIVITY_SERVICE);  
		try {  
			Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface ni = en.nextElement();
				sb.append("网络连接:" + ni.getDisplayName());
				byte[] mac = ni.getHardwareAddress();
				if(mac!=null){
					sb.append(" mac:");
					for (int idx=0; idx<mac.length; idx++){
						sb.append(String.format("%02X:", mac[idx])); 
					}
				}
				Enumeration<InetAddress> enIp = ni.getInetAddresses();
				while (enIp.hasMoreElements()) {
					InetAddress inet = enIp.nextElement();
					if (!inet.isLoopbackAddress() 	) {
						sb.append(" 主机名:").append(inet.getHostName());
						String sAddr = inet.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr); 
                        sb.append(" IP:");
                        if (isIPv4) {
                        	sb.append(sAddr);
                        }else{
                            int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                            sb.append(delim<0 ? sAddr : sAddr.substring(0, delim));
                        }
					}
				}
			}
		} catch (SocketException e) {  
			LogUtil.e(e);
		}  
	    return sb.toString();
	}
	
	/**
	 * get请求
	 * @param url
	 * @return
	 */
	public static String get(String url) throws Exception{
		HttpGet getMethod = new HttpGet(url);  
		HttpResponse response = getHttpClient().execute(getMethod); //发起GET请求  
		int status =response.getStatusLine().getStatusCode();
		//if(status==200){ //200表示访问成功
		return EntityUtils.toString(response.getEntity(), "utf-8");
	}
	/**
	 * 获得附件，并返回附件路径
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getFile(String url){
		return getFile(url,Constant.DATA_DIRECTORY);
	}
	/**
	 * 获得附件，并返回附件路径
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getFile(String url,String dirpath){
		String path="";
		try {
			HttpGet getMethod = new HttpGet(url);  
			HttpResponse response = getHttpClient().execute(getMethod); //发起GET请求  
			int status =response.getStatusLine().getStatusCode();
			path = "";
			if(status==200){
				HttpEntity  entity=response.getEntity();
				InputStream in=entity.getContent();
				path=FileUtils.writeFile(in,dirpath);
			}else{
				throw new RuntimeException("读取失败");
			}
		}catch(HttpHostConnectException e){
			throw new RuntimeException("连接不到服务器",e);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("读取失败",e);
		} 
		return path;
	}
	/**
	 * 获得附件，并返回附件路径
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getFileWithContext(Context context,String url) throws Exception{
		return getFileWithContext(context,url,Constant.DATA_DIRECTORY);
	}
	/**
	 * 获得附件，并返回附件路径
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getFileWithContext(Context context,String url,String dirpath) throws Exception{
		String path="";
		try {
			HttpGet getMethod = new HttpGet(url);  
			HttpResponse response = getHttpClient().execute(getMethod); //发起GET请求  
			int status =response.getStatusLine().getStatusCode();
			path = "";
			if(status==200){
				HttpEntity  entity=response.getEntity();
				InputStream in=entity.getContent();
				path=FileUtils.writeFile(in,dirpath);
			}else{
				throw new RuntimeException("读取失败");
			}
		}catch(HttpHostConnectException e){
			//IPUtil.changeIP(context);
			throw e;
			//throw new RuntimeException("连接不到服务器",e);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
			//throw new RuntimeException("读取失败",e);
		} 
		return path;
	}
	
	public static String APPLICATION_JSON="application/json";
	
	/**
	 * 以json格式提交
	 * @param url
	 * @param json
	 * @return
	 */
	public static String postJson(String url,String json) throws Exception{
		HttpPost postMethod = new HttpPost(url);
		StringEntity se = new StringEntity(json.toString());
		postMethod.setEntity(se);
		postMethod.setHeader("Accept", "application/json");
		postMethod.setHeader("Content-type", "application/json");
		HttpResponse response = getHttpClient().execute(postMethod); //执行POST方法
		int status =response.getStatusLine().getStatusCode();
		return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
	}
	/**
	 * post请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url,List<NameValuePair> params) throws Exception{
		HttpPost postMethod = new HttpPost(url);
		postMethod.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpResponse response = getHttpClient().execute(postMethod); //执行POST方法
		int status =response.getStatusLine().getStatusCode();
		return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
	}
	/**
	 *  附件上传
	 * @param url
	 * @param filePath
	 * @return
	 */
	public static String postFile(Context context,String url,String paramName,String filePath)  throws Exception{
		try {
			MultipartEntity mpEntity = new MultipartEntity(); 
			FileBody file = new FileBody(new File(filePath));  
			mpEntity.addPart(paramName, file);
			HttpPost post = new HttpPost(url); 
			post.setEntity(mpEntity);  
			HttpResponse response = getHttpClient().execute(post); 
			int status =response.getStatusLine().getStatusCode();
			return EntityUtils.toString(response.getEntity(), "utf-8");
		}catch(HttpHostConnectException e){
			//IPUtil.changeIP(context);
			throw e;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

	/**
	 * 提交数据到服务器
	 * 参数格式类似[system:"linde",action:"synCurDate",data:{"pName":"a","pValue":"a1"}]
	 * @param jsonObject
	 * @return
	 */
	public static String post(JSONObject jsonObject) throws Exception {
		String system=jsonObject.getString("system");
		String action=jsonObject.getString("action");
		String data=jsonObject.optString("data");
		String url= SystemHelper.convertURLBySys(system,action);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("data", data));
		LogUtil.i("[url]["+url+"]\n[data]["+data+"]");
		String ret=post(url,nvps);
		LogUtil.i("[ret]["+ret+"]");
		return ret;
	}

	/**
	 * 提交数据到服务器
	 * @param jsonObject
	 * @return
	 * @throws Exception
	 */
	public static CommonInfo postAndReturnObj(JSONObject jsonObject) throws Exception {
		String str=post(jsonObject);
		JSONObject retObject=new JSONObject(str);
		CommonInfo commonInfo=new CommonInfo();
		commonInfo.setSuccess(retObject.getBoolean("success"));
		commonInfo.setMsg(retObject.optString("msg"));
		commonInfo.setData(retObject.optString("data"));
		return commonInfo;
	}

}
