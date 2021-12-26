package com.cts.refill.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.naming.ServiceUnavailableException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest(classes = GlobalExceptionHandlerTest.class)
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
	public void subscriptionIdNotFoundException()
	{
		assertEquals(globalExceptionHandler.subscriptionIdNotFoundException
				(new SubscriptionIdNotFoundException("subscriptionIdNotFoundException")).getStatusCode(),HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void serviceUnavailableException()
	{
		assertEquals(globalExceptionHandler.serviceUnavailableException
				(new ServiceUnavailableException("serviceUnavailableException")).getStatusCode(),HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@Test
	public void drugQuantityNotAvailable()
	{
		assertEquals(globalExceptionHandler.drugQuantityNotAvailable
				(new DrugQuantityNotAvailable("DrugQuantityNotAvailable")).getStatusCode(),HttpStatus.NOT_FOUND);
	}
	

}
