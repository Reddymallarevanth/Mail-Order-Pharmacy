package com.cts.subscription.restclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


import com.cts.subscription.entity.DrugDetails;

@FeignClient(url="${drugservice.client.url}",name="${drugservice.client.name}")
public interface DrugDetailClient {

	@GetMapping("/searchDrugsByName/{name}")
	public DrugDetails getDrugByName(@RequestHeader("Authorization") final String token, @PathVariable("name") String name);
	
}
