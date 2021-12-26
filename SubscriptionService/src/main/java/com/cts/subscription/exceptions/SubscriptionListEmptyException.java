package com.cts.subscription.exceptions;

@SuppressWarnings("serial")
public class SubscriptionListEmptyException extends RuntimeException {

	public SubscriptionListEmptyException(String message){
		super(message);
	}
}
