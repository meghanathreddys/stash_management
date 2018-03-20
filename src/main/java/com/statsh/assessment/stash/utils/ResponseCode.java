package com.statsh.assessment.stash.utils;

public enum ResponseCode {
	CREATED(201, "CREATED"), SUCCESS(200, "success"), DBEXCEPTION(300, "Data Exception"), SERVEREXCEPTION(505,
			"Server error occured"), INVALIDINPUT(100,
					"Invalid Input"), UNPROCESSABLEENTITY(402, "Unprocessable Entity");

	private ResponseCode(int code, String message) {
		this.code = code;
		this.description = message;
	}

	private int code;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCode() {
		return code;
	}
}
