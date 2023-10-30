package com.example.rqchallenge.exception;

public class EmployeeCustomExceptionModel {
	private String status;
	private int statusCode;
	private String message;

	public EmployeeCustomExceptionModel(String status, int errorCode2, String message) {
		this.status = status;
		this.statusCode = errorCode2;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
