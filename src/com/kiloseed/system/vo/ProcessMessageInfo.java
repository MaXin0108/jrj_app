package com.kiloseed.system.vo;

public class ProcessMessageInfo {
	private int state;//状态,1表示提示,2表示toast提示,3表示进入登录界面确认
	private String message;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
