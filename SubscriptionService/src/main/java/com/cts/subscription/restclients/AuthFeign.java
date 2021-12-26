package com.cts.subscription.restclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cts.subscription.entity.TokenValid;

@FeignClient(name = "${authservice.client.name}", url = "${authservice.client.url}")
public interface AuthFeign {

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public ResponseEntity<TokenValid> getValidity(@RequestHeader("Authorization") final String token);
}
