package com.cts.webportal.exceptions;

@SuppressWarnings("serial")
public class InvalidTokenException extends Exception {

	public InvalidTokenException(String message){
		super(message);
	}
}
