package com.cts.refill.restclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url="${subscription.client.url}",name="${subscription.client.name}")
public interface SubscriptionClient {
	
	@GetMapping("/getDrugName/{sId}")
	public ResponseEntity<String> getDrugNameBySubscriptionId(@PathVariable("sId") Long sId,@RequestHeader("Authorization") String token);

}
