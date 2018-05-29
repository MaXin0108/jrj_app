package com.kiloseed.system.task;

import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;

/**
 * 顶级类
 * @author young
 *
 * @param <Progress>
 * @param <Result>
 */
public abstract class BaseTopAsyncTask < Progress, Result> extends AsyncTask<Map, Progress, Result> {
	protected Context context;
	public BaseTopAsyncTask(Context ctx){
		context=ctx;
	}
}
