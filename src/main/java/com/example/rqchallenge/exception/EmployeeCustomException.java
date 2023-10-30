package com.example.rqchallenge.exception;

public class EmployeeCustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String errorDescrption;

	public EmployeeCustomException(String message, int errorCode, String errorDescrption) {
		super(message);
		this.errorCode = errorCode;
		this.errorDescrption = errorDescrption;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescrption() {
		return errorDescrption;
	}

	public void setErrorDescrption(String errorDescrption) {
		this.errorDescrption = errorDescrption;
	}
}
