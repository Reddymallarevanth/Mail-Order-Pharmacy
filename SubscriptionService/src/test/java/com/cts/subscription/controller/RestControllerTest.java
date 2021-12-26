package com.cts.subscription.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.subscription.entity.PrescriptionDetails;
import com.cts.subscription.entity.SubscriptionDetails;
import com.cts.subscription.restcontroller.SubscriptionRestcontroller;
import com.cts.subscription.services.SubscriptionService;

@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTest {

	@InjectMocks
	SubscriptionRestcontroller subscriptionRestcontroller;

	@Mock
	private SubscriptionService subscriptionService;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void subscribeTest() throws Exception {
		PrescriptionDetails prescriptionDetails = new PrescriptionDetails(12001L, "admin", "chennai", "12001",
				"chennai", LocalDate.now(), "Drug1", "weekly", 1, 3, "prakash");
		ResponseEntity<String> res = new ResponseEntity<String>(
				"You have succesfully subscribed to " + prescriptionDetails.getDrugName(), HttpStatus.OK);
		when(subscriptionService.subscribe(prescriptionDetails, "Bearer Token")).thenReturn(res);
		assertEquals(res.getStatusCodeValue(),
				subscriptionRestcontroller.subscribe("Bearer Token", prescriptionDetails).getStatusCodeValue());
	}

	@Test
	public void getAllSubscriptionsTest() throws Exception {
		PrescriptionDetails prescriptionDetails = new PrescriptionDetails(12001L, "admin", "chennai", "12001",
				"chennai", LocalDate.now(), "Drug1", "weekly", 1, 3, "prakash");
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		Date endDate = null;
		if (prescriptionDetails.getDosageDefinition().equalsIgnoreCase("weekly")) {
			long ltime = date.getTime() + 7 * 24 * 60 * 60 * 1000;
			endDate = new Date(ltime);
		}
		if (prescriptionDetails.getDosageDefinition().equalsIgnoreCase("Monthly")) {
			long ltime = date.getTime() + 31l * 24l * 60l * 60l * 1000l;
			endDate = new Date(ltime);
		}
		SubscriptionDetails subscriptionDetails = new SubscriptionDetails(prescriptionDetails.getPrescriptionId(),
				prescriptionDetails.getCourseDuration(), prescriptionDetails.getQuantity(),
				prescriptionDetails.getMemberId(), date,
				prescriptionDetails.getMemberLocation(), "paid", prescriptionDetails.getDrugName(),endDate);
		List<SubscriptionDetails> list = new ArrayList<>();
		list.add(subscriptionDetails);
		ResponseEntity<String> res = new ResponseEntity<String>(
				"You have succesfully subscribed to " + prescriptionDetails.getDrugName(), HttpStatus.OK);
		when(subscriptionService.subscribe(prescriptionDetails, "Bearer Token")).thenReturn(res);
		when(subscriptionRestcontroller.getAllSubscriptionsforMember("Bearer Token", "admin")).thenReturn(list);

	}

	@Test
	public void getDrugNameTest() {
		PrescriptionDetails prescriptionDetails = new PrescriptionDetails(12001L, "admin", "chennai", "12001",
				"chennai", LocalDate.now(), "Drug1", "weekly", 1, 3, "prakash");
		ResponseEntity<String> res = new ResponseEntity<String>(
				"You have succesfully subscribed to - " + prescriptionDetails.getDrugName(), HttpStatus.OK);
		when(subscriptionService.subscribe(prescriptionDetails, "Bearer Token")).thenReturn(res);
		ResponseEntity<String> drugname = new ResponseEntity<String>(prescriptionDetails.getDrugName(), HttpStatus.OK);
		when(subscriptionService.getDrugNameBySubscriptionId(prescriptionDetails.getPrescriptionId(), "Bearer Token"))
				.thenReturn(drugname);
		when(subscriptionRestcontroller.getDrugNameBySubscriptionId("Bearer Token",
				prescriptionDetails.getPrescriptionId())).thenReturn(drugname);
	}

	@Test
	public void unsubscribeTest() {
		PrescriptionDetails prescriptionDetails = new PrescriptionDetails(12001L, "admin", "chennai", "12001",
				"chennai", LocalDate.now(), "Drug1", "weekly", 1, 3, "prakash");
		ResponseEntity<String> res = new ResponseEntity<String>(
				"You have succesfully subscribed to " + prescriptionDetails.getDrugName(), HttpStatus.OK);
		when(subscriptionService.subscribe(prescriptionDetails, "Bearer Token")).thenReturn(res);
		ResponseEntity<String> response = new ResponseEntity<String>("You have succesfully Unsubscribed",
				HttpStatus.OK);
		when(subscriptionService.unsubscribe(prescriptionDetails.getMemberId(), prescriptionDetails.getPrescriptionId(),
				"Bearer Token")).thenReturn(response);
		assertEquals(subscriptionRestcontroller
				.unsubscribe("Bearer Token", prescriptionDetails.getMemberId(), prescriptionDetails.getPrescriptionId())
				.getStatusCodeValue(), 200);
	}
}
