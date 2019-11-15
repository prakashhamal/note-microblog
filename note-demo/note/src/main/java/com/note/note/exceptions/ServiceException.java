package com.note.note.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceException extends Exception {

	private static final long serialVersionUID = -1516239881228474317L;
	
	private int statusCode= HttpStatus.OK.value();
	public int getStatusCode(){return statusCode;}

	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(int statusCode, String message) {
		super(message);
		this.statusCode=statusCode;
	}
	
	public ServiceException(Throwable e) {
		super(e);
	}
}
