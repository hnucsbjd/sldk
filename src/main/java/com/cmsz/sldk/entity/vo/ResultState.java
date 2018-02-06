package com.cmsz.sldk.entity.vo;

public class ResultState {
	
	ResultStatusEnum result;
	String data;
	
	public ResultStatusEnum getResult() {
		return result;
	}
	public void setResult(ResultStatusEnum result) {
		this.result = result;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
