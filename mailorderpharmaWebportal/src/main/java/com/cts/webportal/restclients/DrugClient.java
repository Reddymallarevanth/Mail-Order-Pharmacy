package com.cts.webportal.restclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.cts.webportal.entity.DrugDetails;

@FeignClient(url = "${drugservice.client.url}", name = "${drugservice.client.name}")
public interface DrugClient {

	@GetMapping("/getAllDrugs")
	public List<DrugDetails> getAllDrugs(); 
}
