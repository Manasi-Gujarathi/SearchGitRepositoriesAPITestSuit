package com.git.apis.global.exception;


public class CustomException extends Exception {

	private static final long serialVersionUID = 7718828512143293558L;
	
	private String errorCode;
	private String errorDescription;

	public CustomException(String errorCode, String errorDescription) {
		super();
		this.errorCode = errorCode;
		this.errorDescription=errorDescription;

	}

	public CustomException(String errorCode, String errorDescription, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.errorDescription=errorDescription;
	}

	public CustomException(String errorCode, String errorDescription, String message) {
		super(message);
		this.errorCode = errorCode;
		this.errorDescription=errorDescription;
	}

	public CustomException(String errorCode, String errorDescription,Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.errorDescription=errorDescription;
	}

	public String getErrorCode() {
		return errorCode;
	}


	public String getErrorDescription() {
		return errorDescription;
	}

	@Override
	public String toString() {
		return "***********************************************************\nCustomException [ \nerrorCode=" + errorCode + ", \nerrorDescription=" + errorDescription + ", \nerrorMessage=\n"
				+ super.getMessage()+ "]\n***********************************************************\n";
	}


	
}