package com.kiloseed.system.activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.kiloseed.system.helper.Constant;
import com.kiloseed.system.util.ProgressDialogUtil;
import com.kiloseed.system.util.UpgradeUtil;
import com.kiloseed.system.widget.IProgressDialog;

import org.apache.cordova.CordovaActivity;

/**
 * Created by mac on 16/4/6.
 */
public class BaseActivity  extends CordovaActivity implements IProgressDialog {
    protected ProgressDialog _loadingDialog;
    public void onCreate(Bundle savedInstanceState)
    {
        if(Constant.isOpenUpgradeOnStart){//如果默认升级，调用升级方法
            UpgradeUtil.upgrade(this);
        }
        super.onCreate(savedInstanceState);
        // Set by <content src="index.html" /> in config.xml

    }
    public ProgressDialog getProgressDialog() {
        // TODO Auto-generated method stub
        if(_loadingDialog==null){
            _loadingDialog = ProgressDialogUtil.createProgressDialog(this);
        }
        return _loadingDialog;
    }
}
