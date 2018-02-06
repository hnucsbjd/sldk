package com.cmsz.sldk.entity.vo;

public enum ResultStatusEnum {
	/**
	 * 200 成功
	 */
	SUCCESS(200, "成功"),
	
	/**
	 * 300 失败
	 */
	ERROR(300, "失败");
	
	private int status;
	
	private String msg;
	
	ResultStatusEnum(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}
	
	
}
