package com.cts.webportal.entity;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Id;

import lombok.Data;


@Data
public class SubscriptionDetails {
	@Id
	private Long subscriptionId;
	private Long prescriptionId;
	private int refillCycle;
	private int quantity;
	private String memberId;
	private LocalDate subscriptionDate;
	private String memberLocation;
	private String subscriptionStatus;
	private String drugName;
	private Date subscriptionEndDate;
}
