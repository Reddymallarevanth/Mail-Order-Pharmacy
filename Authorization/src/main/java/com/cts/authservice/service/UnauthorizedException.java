package com.cts.authservice.service;

@SuppressWarnings("serial")
public class UnauthorizedException extends RuntimeException {
	
	public UnauthorizedException(String message) {
		super(message);
	}

}
