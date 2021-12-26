package com.cts.subscription.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.java.Log;

@Component
@Log
public class FeignErrorDecoder implements ErrorDecoder {

	
	@Override
	public Exception decode(String methodKey, Response response) {

		switch (response.status()) {
		case 404: {
			log.info("Error took place when using Feign client to send HTTP Request");
			return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Drug is not available");
		}
		default:
			return new Exception(response.reason());
		}
	}

}