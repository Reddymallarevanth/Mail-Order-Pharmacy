package com.cts.subscription.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.subscription.Repository.PrescriptionRepository;
import com.cts.subscription.Repository.SubscriptionRepository;
import com.cts.subscription.entity.DrugDetails;
import com.cts.subscription.entity.DrugLocationDetails;
import com.cts.subscription.entity.PrescriptionDetails;
import com.cts.subscription.entity.SubscriptionDetails;
import com.cts.subscription.entity.TokenValid;
import com.cts.subscription.exceptions.InvalidTokenException;
import com.cts.subscription.exceptions.SubscriptionListEmptyException;
import com.cts.subscription.restclients.AuthFeign;
import com.cts.subscription.restclients.DrugDetailClient;
import com.cts.subscription.restclients.RefillClient;
import com.cts.subscription.services.SubscriptionServiceImplementation;

@SpringBootTest(classes = SubscriptionServiceImplementaionTest.class)
public class SubscriptionServiceImplementaionTest {

	@InjectMocks
	SubscriptionServiceImplementation subscriptionServiceImplementation;

	@Mock
	private DrugDetailClient drugDetailClient;
	@Mock
	private RefillClient refillClient;
	@Mock
	private AuthFeign authFeign;
	@Mock
	PrescriptionRepository prescriptionRepo;
	@Mock
	SubscriptionRepository subscriptionRepo;

	@Test
	public void subscribe() {
		LocalDate date = LocalDate.now();
		PrescriptionDetails prescriptionDetails = new PrescriptionDetails((long) 45, "member", "salem", "LIC101", "LIC",
				date,  "paracetemol","weekly", 45, 10, "doctor");
		java.sql.Date date1 = new java.sql.Date(System.currentTimeMillis());
		java.sql.Date endDate = null;
		if (prescriptionDetails.getDosageDefinition().equalsIgnoreCase("weekly")) {
			long ltime = date1.getTime() + 7 * 24 * 60 * 60 * 1000;
			endDate = new java.sql.Date(ltime);
		}
		if (prescriptionDetails.getDosageDefinition().equalsIgnoreCase("Monthly")) {
			long ltime = date1.getTime() + 31 * 24 * 60 * 60 * 1000;
			endDate = new java.sql.Date(ltime);
		}
		SubscriptionDetails subscriptionDetails = new SubscriptionDetails(prescriptionDetails.getPrescriptionId(),
				prescriptionDetails.getCourseDuration(), prescriptionDetails.getQuantity(),
				prescriptionDetails.getMemberId(), date1, prescriptionDetails.getMemberLocation(),
				"active", prescriptionDetails.getDrugName(),endDate);
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);		
		when(authFeign.getValidity("token")).thenReturn(response);
		List<DrugLocationDetails> list = new ArrayList<DrugLocationDetails>();
		DrugLocationDetails drugLocationDetails = new DrugLocationDetails("45","salem",50,null);
		list.add(drugLocationDetails);
		DrugDetails drugDetails = new DrugDetails("drug1","paracetemol","Dr Reddy",new Date(),new Date(),list);
		 when(drugDetailClient.getDrugByName("token","member")).thenReturn(drugDetails);
		when(prescriptionRepo.save(prescriptionDetails)).thenReturn(prescriptionDetails);
		when(subscriptionRepo.save(subscriptionDetails)).thenReturn(subscriptionDetails);
		System.out.println(prescriptionDetails.getDrugName());
		assertEquals(new ResponseEntity<>("You have succesfully subscribed to " + prescriptionDetails.getDrugName(),
				HttpStatus.OK), subscriptionServiceImplementation.subscribe(prescriptionDetails, "token"));

	}
 
	@Test
	public void subscribefalse() {
		LocalDate date = LocalDate.now();

		PrescriptionDetails prescriptionDetails = new PrescriptionDetails((long) 45, "member", "salem", "45", "45",
				date, "member", "member", 45, 45, "member");

		TokenValid tokenValid = new TokenValid("uid", "uname", false);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> subscriptionServiceImplementation.subscribe(prescriptionDetails, "token"));
	}

	@Test
	public void unsubscribe() {
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillClient.isPendingPaymentDues("token", (long) 45)).thenReturn(true);
		subscriptionServiceImplementation.unsubscribe("asd", (long) 45, "token");

	}

	@Test
	public void unsubscribefalse() {
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillClient.isPendingPaymentDues("token", (long) 45)).thenReturn(false);
		subscriptionServiceImplementation.unsubscribe("asd", (long) 45, "token");

	}

	@Test
	public void getStatusInvalidToken() {
		TokenValid tokenValid = new TokenValid("uid", "uname", false);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> subscriptionServiceImplementation.unsubscribe("asd", (long) 45, "token"));
	}

	@Test
	public void getAllSubscriptionsTest() {
		List<SubscriptionDetails> list = new ArrayList<SubscriptionDetails>();
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		when(subscriptionRepo.findByMemberId("mem")).thenReturn(list);
		assertThrows(SubscriptionListEmptyException.class,
				() -> subscriptionServiceImplementation.getAllSubscriptions("mem", "token"));

	}

	@Test
	public void getAllSubscriptionsTestsucess() {
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		java.sql.Date endDate = null;
		
			long ltime = date.getTime() + 7 * 24 * 60 * 60 * 1000;
			endDate = new java.sql.Date(ltime);
		
		
		SubscriptionDetails subscriptionDetails = new SubscriptionDetails((long) 45, 45, 45, "member", date, "salem",
				"member", "member",endDate);
		//SubscriptionDetails subscriptionDetails = new SubscriptionDetails((long) 45, 45, 45, "member", date, "salem",
		//		"member", "member");

		List<SubscriptionDetails> list = new ArrayList<SubscriptionDetails>();
		list.add(subscriptionDetails);
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);		when(authFeign.getValidity("token")).thenReturn(response);
		when(subscriptionRepo.findByMemberId("mem")).thenReturn(list);
		subscriptionServiceImplementation.getAllSubscriptions("mem", "token");

	}

	@Test
	public void getAllSubscriptionsTestFalse() {
		TokenValid tokenValid = new TokenValid("uid", "uname", false);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> subscriptionServiceImplementation.getAllSubscriptions("asd", "token"));
	}

	@Test
	public void getDrugNameBySubscriptionIdTest() {
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		java.sql.Date endDate = null;
		
			long ltime = date.getTime() + 7 * 24 * 60 * 60 * 1000;
			endDate = new java.sql.Date(ltime);
			SubscriptionDetails subscriptionDetails = new SubscriptionDetails((long) 45, 45, 45, "member", date, "salem",
					"member", "member",endDate);
		List<SubscriptionDetails> list = new ArrayList<SubscriptionDetails>();
		list.add(subscriptionDetails);
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
	
		 when(subscriptionRepo.findById((long) 45)).thenReturn(Optional.of(subscriptionDetails));
		subscriptionServiceImplementation.getDrugNameBySubscriptionId((long) 45, "token");

	}

	@Test
	public void getDrugNameBySubscriptionIdTestFalse() {
		TokenValid tokenValid = new TokenValid("uid", "uname", false);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> subscriptionServiceImplementation.getDrugNameBySubscriptionId((long) 45, "token"));
	}

}
