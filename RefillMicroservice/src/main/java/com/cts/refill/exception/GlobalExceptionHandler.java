package com.cts.refill.exception;

import java.time.LocalDateTime;

import javax.naming.ServiceUnavailableException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.refill.entity.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ExceptionResponse> invalidTokenException(InvalidTokenException invalidTokenException) {
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(invalidTokenException.getMessage(), LocalDateTime.now(), HttpStatus.UNAUTHORIZED),
				HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(SubscriptionIdNotFoundException.class)
	public ResponseEntity<ExceptionResponse> subscriptionIdNotFoundException(SubscriptionIdNotFoundException subscriptionException) {
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(subscriptionException.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(feign.RetryableException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public ResponseEntity<ExceptionResponse>  serviceUnavailableException(ServiceUnavailableException serviceUnavailableException) {
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse("Temporarily service unavailable", LocalDateTime.now(),HttpStatus.SERVICE_UNAVAILABLE),HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(DrugQuantityNotAvailable.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public ResponseEntity<ExceptionResponse>  drugQuantityNotAvailable(DrugQuantityNotAvailable drugException) {
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse("DrugQuantityNotAvailable", LocalDateTime.now(),HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
	}
	
}
