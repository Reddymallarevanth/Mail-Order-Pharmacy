package com.cts.drugservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.drugservice.entity.ExceptionResponse;

import feign.RetryableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ExceptionResponse> invalidTokenException(InvalidTokenException invalidTokenException) {
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(invalidTokenException.getMessage(), LocalDateTime.now(), HttpStatus.UNAUTHORIZED),
				HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(DrugNotFoundException.class)
	public ResponseEntity<ExceptionResponse> drugNotFoundException(DrugNotFoundException drugNotFoundException) {
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(drugNotFoundException.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(StockNotFoundException.class)
	public ResponseEntity<ExceptionResponse> stockNotFoundException(StockNotFoundException stockNotFoundException) {
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(stockNotFoundException.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RetryableException.class)
	public ResponseEntity<ExceptionResponse> microServiceUnavailableException( ) {
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse("MicroServiceUnavailable", LocalDateTime.now(), HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}
}
