package com.kiloseed.system.task;

import android.app.ProgressDialog;
import android.content.Context;

import com.kiloseed.system.widget.IProgressDialog;


public abstract class BaseAsyncTask< Progress, Result> extends BaseTopAsyncTask< Progress, Result> {
	protected  ProgressDialog _loadingDialog;
	public BaseAsyncTask(Context ctx){
		super(ctx);

		if(ctx instanceof IProgressDialog){
			_loadingDialog=((IProgressDialog)ctx).getProgressDialog();
		}

		
	}
	protected boolean isHide = false;
	
	/**
	 * 获得等待时候提示信息
	 * @return
	 */
	public abstract int getWaitingMsgResource();
	@Override
	protected void onPostExecute(Result result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(_loadingDialog!=null && _loadingDialog.isShowing() && !isHide){
			_loadingDialog.dismiss();
			//AppManager.removeProgressDialog(_loadingDialog);
		}
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if(_loadingDialog!=null&& !isHide){
			_loadingDialog.setMessage(context.getResources().getString(getWaitingMsgResource()));
			_loadingDialog.show();
		}
		
		//AppManager.putProgressDialog(loadingDialog);
	}
	public boolean isHide() {
		return isHide;
	}
	public void setHide(boolean isHide) {
		this.isHide = isHide;
	}
	

}
