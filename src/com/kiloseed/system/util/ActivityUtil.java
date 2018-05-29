package com.kiloseed.system.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;


import com.kiloseed.jrj.R;
import com.kiloseed.system.helper.MUOContext;
import com.kiloseed.system.widget.CustomDialog;


/**
 * Created by mac on 15/7/5.
 */
public class ActivityUtil {

    /**
     * 跳转到其它Activity
     * @param mContext
     * @param activity
     */
    public static void goToActivity(Context mContext, Activity activity) {
        Intent intent = new Intent(mContext, activity.getClass());
        mContext.startActivity(intent);

    }

    /** 显示提示框 */
    public static void showDialog(Context mContext, String title,
                                  String message, DialogInterface.OnClickListener listener) {
        CustomDialog customDialog = new CustomDialog.Builder(mContext)
                .setTitle(title).setMessage(message)
                .setPositiveButton(R.string.confirm, listener)
                .setNegativeButton(R.string.cancel, null).create();

        WindowManager.LayoutParams lp = customDialog.getWindow()
                .getAttributes();
        lp.width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.65);
        // lp.height = (int)
        // (mContext.getResources().getDisplayMetrics().heightPixels * 0.8);
        customDialog.getWindow().setAttributes(lp);

        customDialog.show();
    }

    /**
     * 切换到登录界面
     * @param mContext
     * @throws Exception
     */
    public static void switchToLogin(Context mContext) throws Exception {
        //ActivityUtil.goToActivity(mContext, new LoginActivity());
        MUOContext.clearAll();

    }
}
