package com.kiloseed.system.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.kiloseed.system.util.ToastUtils;
import com.kiloseed.system.util.ActivityUtil;
import com.kiloseed.system.vo.ProcessMessageInfo;

public abstract class BaseStringAsyncTask<Result> extends BaseAsyncTask<ProcessMessageInfo, Result> {

	public BaseStringAsyncTask(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 发送提示信息
	 * @param message
	 */
	public void sendTips(String message){
		ProcessMessageInfo p=new ProcessMessageInfo();
		p.setState(1);
		p.setMessage(message);
		this.publishProgress(p);
	}
	/**
	 * 另外一种提示
	 * @param message
	 */
	public void toast(String message){
		ProcessMessageInfo p=new ProcessMessageInfo();
		p.setState(2);
		p.setMessage(message);
		this.publishProgress(p);
	}
	/**
	 * 网络连接中断
	 */
	public void connectFailToLogin(){
		ProcessMessageInfo p=new ProcessMessageInfo();
		p.setState(3);
		p.setMessage("网络连接中断。请连接网络，重新登录");
		this.publishProgress(p);
	}
	@Override
	protected void onProgressUpdate(ProcessMessageInfo... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		ProcessMessageInfo p=values[0];
		if(p!=null){
			if(p.getState()==1){
				this._loadingDialog.show();
				this._loadingDialog.setMessage(p.getMessage());
			}else if(p.getState()==2){
				ToastUtils.toast(this.context, p.getMessage());
			}else if(p.getState()==3){
				new AlertDialog.Builder(this.context).setTitle("提示").setMessage(p.getMessage())
				.setPositiveButton("确定"
						,new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								//AppManager.getSysParam().setNewDownloadUrl("http://172.17.235.159:8080/examples/HytApp.apk");
								Activity act=(Activity)(BaseStringAsyncTask.this.context);
								//ActivityUtil.goToActivity(act, new LoginActivity());
							}
						}
				) /* .setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						//UIHelper.goToActivity(LoginActivity.this, new ItemListActivity());
						//LoginActivity.this.finish();
						//login("true");
					}
				})*/
				.show();
			}
		}
	}
}
