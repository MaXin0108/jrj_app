package com.kiloseed.system.plugin.version;

import android.content.Context;


import com.kiloseed.system.helper.Constant;
import com.kiloseed.system.task.BaseAsyncTask;
import com.kiloseed.system.util.LogUtil;
import com.kiloseed.system.util.UpgradeUtil;
import com.kiloseed.system.vo.CommonInfo;
import com.kiloseed.jrj.R;

import java.util.Map;

public class UpgradeAsyncTask  extends BaseAsyncTask<Integer, CommonInfo> {

	public UpgradeAsyncTask(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getWaitingMsgResource() {
		// TODO Auto-generated method stub
		return R.string.waiting_msg_upgrade;
	}

	@Override
	protected CommonInfo doInBackground(Map... params) {
		// TODO Auto-generated method stub
		Map map=params[0];
		Context context=(Context)map.get(Constant.TASK_CONTEXT);
		String upgradeDownloadUrl=(String)map.get(Constant.UPGRADE_CONSTANT);
		CommonInfo ci=new CommonInfo();
		ci.setSuccess(true);

		try {

			String path= UpgradeUtil.getApk(context, upgradeDownloadUrl);
			if(this._loadingDialog!=null){
				this._loadingDialog.dismiss();
			}
			UpgradeUtil.installApk(context, path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.e(e);
			ci.setSuccess(false);
			ci.setMsg("下载升级包失败");
		}
		return ci;
	}

	@Override
	protected void onPostExecute(CommonInfo result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
