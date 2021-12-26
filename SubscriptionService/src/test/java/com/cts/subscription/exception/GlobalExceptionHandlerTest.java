package com.cts.subscription.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.cts.subscription.exceptions.GlobalExceptionHandler;
import com.cts.subscription.exceptions.InvalidTokenException;
import com.cts.subscription.exceptions.SubscriptionListEmptyException;

@SpringBootTest
public class GlobalExceptionHandlerTest {

	
	@InjectMocks
	GlobalExceptionHandler globalExceptionHandler;
	
	@Test
	public void invalidTokenException()
	{
		assertEquals(globalExceptionHandler.invalidTokenException
				(new InvalidTokenException("invalidTokenException")).getStatusCode(),HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	public void subscriptionListEmptyException()
	{
		assertEquals(globalExceptionHandler.subscriptionListEmptyException
				(new SubscriptionListEmptyException("SubscriptionListEmptyException")).getStatusCode(),HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void serviceUnavailableException()
	{
		assertEquals(globalExceptionHandler.serviceUnavailableException
				().getStatus(),HttpStatus.SERVICE_UNAVAILABLE);
	}
}
