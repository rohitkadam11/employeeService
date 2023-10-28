package com.example.rqchallenge.exception;

public class EmployeeCustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String errorDescrption;

	public EmployeeCustomException(String message, int errorCode, String errorDescrption) {
		// call the constructor of RuntimeException
		super(message);
		this.errorCode = errorCode;
		this.errorDescrption = errorDescrption;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorDescrption
	 */
	public String getErrorDescrption() {
		return errorDescrption;
	}

	/**
	 * @param errorDescrption the errorDescrption to set
	 */
	public void setErrorDescrption(String errorDescrption) {
		this.errorDescrption = errorDescrption;
	}

}
