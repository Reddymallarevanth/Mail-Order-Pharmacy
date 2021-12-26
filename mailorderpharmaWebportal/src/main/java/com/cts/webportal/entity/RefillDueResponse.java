package com.cts.webportal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefillDueResponse {

	private String drugName;
	
	private long id;
	
	private long subscriptionId;
	
	private String memberId;
	
	private int refillQuantity;
	
	private int refillTime;
	
}
