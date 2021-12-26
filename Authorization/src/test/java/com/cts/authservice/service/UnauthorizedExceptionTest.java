package com.cts.authservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnauthorizedExceptionTest {
	
	
	
	@Test
	public void constructortest()
	{
		UnauthorizedException unauthorizedException =new UnauthorizedException("unauthorized");
		assertEquals("unauthorized", unauthorizedException.getMessage());
	}

}
