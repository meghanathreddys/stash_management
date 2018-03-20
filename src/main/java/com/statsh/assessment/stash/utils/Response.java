package com.statsh.assessment.stash.utils;

public class Response {

	protected Object payload;
	protected int code = ResponseCode.SUCCESS.getCode();

	public int getCode() {
		return code;
	}

	public String getStatus() {
		return status;
	}

	protected String status = ResponseCode.SUCCESS.getDescription();

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public void setResponseCode(ResponseCode respCode) {
		code = respCode.getCode();
		status = respCode.getDescription();
	}
}
