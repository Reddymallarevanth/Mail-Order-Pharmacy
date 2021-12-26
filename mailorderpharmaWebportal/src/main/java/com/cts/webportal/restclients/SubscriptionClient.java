package com.cts.webportal.restclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.webportal.entity.PrescriptionDetails;
import com.cts.webportal.entity.SubscriptionDetails;

@FeignClient(name = "${subscriptionservice.client.name}",url = "${subscriptionservice.client.url}")
public interface SubscriptionClient {

	@PostMapping("/subscribe")
	public String subscribe(@RequestHeader("Authorization") String token,
			@RequestBody PrescriptionDetails prescriptionDetails);
	
	@PostMapping("/unsubscribe/{mId}/{sId}")
	public String unsubscribe(@RequestHeader("Authorization") String token,
			@PathVariable("mId") String memberId, @PathVariable("sId") Long subscriptionId);
	
	@GetMapping("/getAllSubscriptions/{mId}")
	public List<SubscriptionDetails> getAllSubscriptionsforMember(@RequestHeader("Authorization") String token,
			@PathVariable("mId") String mId);

	@GetMapping("/getDrugName/{sId}")
	public String getDrugNameBySubscriptionId(@RequestHeader("Authorization") String token,
			@PathVariable("sId") Long sId);
}
