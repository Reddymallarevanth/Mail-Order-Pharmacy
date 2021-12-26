package com.cts.subscription.entity;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.Test;



public class SubscriptionDetailsTest {

	SubscriptionDetails subscriptionDetails = new SubscriptionDetails(1200L, 1, 2, "member", Date.valueOf("2021-06-23"),
			"chennai", "paid", "drug1",Date.valueOf("2021-07-23"));
	SubscriptionDetails subscriptionDetails2 = new SubscriptionDetails();
	SubscriptionDetails subscriptionDetails3 = new SubscriptionDetails(1001L,12001L,"Drug1", 1, 2, "member", Date.valueOf("2021-08-21"),
			"chennai", "paid",Date.valueOf("2021-08-27"));

	@Test
	public void test() {
		subscriptionDetails2.setPrescriptionId(subscriptionDetails.getPrescriptionId());
		subscriptionDetails2.setRefillCycle(subscriptionDetails.getRefillCycle());
		subscriptionDetails2.setQuantity(subscriptionDetails.getQuantity());
		subscriptionDetails2.setMemberId(subscriptionDetails.getMemberId());
		subscriptionDetails2.setSubscriptionDate(subscriptionDetails.getSubscriptionDate());
		subscriptionDetails2.setMemberLocation(subscriptionDetails.getMemberLocation());
		subscriptionDetails2.setSubscriptionStatus(subscriptionDetails.getSubscriptionStatus());
		subscriptionDetails2.setDrugName(subscriptionDetails.getDrugName());
		subscriptionDetails2.setSubscriptionId(subscriptionDetails3.getSubscriptionId());
		assertEquals(subscriptionDetails2.getDrugName(),subscriptionDetails.getDrugName());
		assertEquals(subscriptionDetails2.getSubscriptionStatus(),subscriptionDetails.getSubscriptionStatus());
		assertEquals(subscriptionDetails2.getMemberLocation(),subscriptionDetails.getMemberLocation());
		assertEquals(subscriptionDetails2.getSubscriptionDate(),subscriptionDetails.getSubscriptionDate());
		assertEquals(subscriptionDetails2.getMemberId(),subscriptionDetails.getMemberId());
		assertEquals(subscriptionDetails2.getQuantity(),subscriptionDetails.getQuantity());
		assertEquals(subscriptionDetails2.getRefillCycle(),subscriptionDetails.getRefillCycle());
		assertEquals(subscriptionDetails2.getPrescriptionId(),subscriptionDetails.getPrescriptionId());
		assertEquals(subscriptionDetails2.getSubscriptionId(), subscriptionDetails3.getSubscriptionId());
	}
}
