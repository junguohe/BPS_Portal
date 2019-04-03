package com.erry.common;

public class SessionTimeOutException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SessionTimeOutException(String message) {
		super(message);
	}
	
}
