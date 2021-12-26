package com.cts.refill.restclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cts.refill.entity.TokenValid;

@FeignClient(name = "${authservice.client.name}", url = "${authservice.client.url}")
public interface AuthFeign {

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public TokenValid getValidity(@RequestHeader("Authorization") final String token);
}
