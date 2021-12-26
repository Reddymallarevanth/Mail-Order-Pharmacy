package com.cts.subscription.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class SubscriptionDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subscriptionId;

	private Long prescriptionId;

	private String drugName;

	private int refillCycle;

	private int quantity;

	private String memberId;

	private Date subscriptionDate;

	private String memberLocation;

	private String subscriptionStatus;

	private Date subscriptionEndDate;

	public SubscriptionDetails(Long prescriptionId, int refillCycle, int quantity, String memberId,
			Date subscriptionDate, String memberLocation, String subscriptionStatus, String drugName,
			Date subscriptionEndDate) {
		super();
		this.prescriptionId = prescriptionId;
		this.memberId = memberId;
		this.subscriptionDate = subscriptionDate;
		this.memberLocation = memberLocation;
		this.subscriptionStatus = subscriptionStatus;
		this.refillCycle = refillCycle;
		this.quantity = quantity;
		this.drugName = drugName;
		this.subscriptionEndDate = subscriptionEndDate;
	}
}
