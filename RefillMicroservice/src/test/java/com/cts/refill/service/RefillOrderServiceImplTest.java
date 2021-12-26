package com.cts.refill.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.refill.dao.RefillOrderRepository;
import com.cts.refill.entity.RefillOrder;
import com.cts.refill.entity.RefillOrderSubscription;
import com.cts.refill.entity.TokenValid;
import com.cts.refill.exception.DrugQuantityNotAvailable;
import com.cts.refill.exception.InvalidTokenException;
import com.cts.refill.exception.SubscriptionIdNotFoundException;
import com.cts.refill.restclients.AuthFeign;
import com.cts.refill.restclients.DrugDetailClient;
import com.cts.refill.restclients.SubscriptionClient;

import feign.FeignException;

@SpringBootTest(classes = RefillOrderServiceImplTest.class)
public class RefillOrderServiceImplTest {

	@InjectMocks
	RefillOrderServiceImpl refillOrderServiceImpl;

	@Mock
	RefillOrderSubscriptionServiceImpl refillOrderSubscriptionService;

	@Mock
	DrugDetailClient drugDetailClient;

	@Mock
	SubscriptionClient subscriptionClient;

	@Mock
	RefillOrderRepository refillOrderRepository;

	@Mock
	private AuthFeign authFeign;

	@Test
	public void getStatus() throws SubscriptionIdNotFoundException, InvalidTokenException {

		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, new Date(), true, 45, 45, "54");
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.findAll()).thenReturn((ArrayList<RefillOrder>) list);
		refillOrderServiceImpl.getStatus(45, "token");

	}

	@Test
	public void getStatusInvalidToken() throws SubscriptionIdNotFoundException, InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderServiceImpl.getStatus(45, "token"));
	}

	@Test
	public void getStatusInvalidSubscriptionIdNotFoundException()
			throws SubscriptionIdNotFoundException, InvalidTokenException {
		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, new Date(), true, 45, 45, "54");
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.findAll()).thenReturn((ArrayList<RefillOrder>) list);
		assertThrows(SubscriptionIdNotFoundException.class, () -> refillOrderServiceImpl.getStatus(54, "token"));
	}

	@Test
	public void getRefillDuesAsOfDate() throws SubscriptionIdNotFoundException, InvalidTokenException {
		ArrayList<RefillOrderSubscription> list = new ArrayList<>();
		RefillOrderSubscription refillOrder = new RefillOrderSubscription(1, 2, "45", 45, 46);
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderSubscriptionService.getall("token")).thenReturn((ArrayList<RefillOrderSubscription>) list);
		// refillOrderServiceImpl.getRefillDuesAsOfDate("45", 45, "token");
		refillOrderServiceImpl.getRefillDuesAsOfDate("45", 45, "token");
		// refillOrderServiceImpl.getRefillDuesAsOfDate("45", 47, "token");
	}

	@Test
	public void getRefillDuesAsOfDateTwo() throws SubscriptionIdNotFoundException, InvalidTokenException {
		ArrayList<RefillOrderSubscription> list = new ArrayList<>();
		RefillOrderSubscription refillOrder = new RefillOrderSubscription(1, 2, "54", 45, 46);
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderSubscriptionService.getall("token")).thenReturn((ArrayList<RefillOrderSubscription>) list);
		// refillOrderServiceImpl.getRefillDuesAsOfDate("45", 45, "token");
		refillOrderServiceImpl.getRefillDuesAsOfDate("45", 45, "token");
		// refillOrderServiceImpl.getRefillDuesAsOfDate("45", 47, "token");
	}

	@Test
	public void getRefillDuesAsOfDateThree() throws SubscriptionIdNotFoundException, InvalidTokenException {
		ArrayList<RefillOrderSubscription> list = new ArrayList<>();
		RefillOrderSubscription refillOrder = new RefillOrderSubscription(1, 2, "54", 45, 46);
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderSubscriptionService.getall("token")).thenReturn((ArrayList<RefillOrderSubscription>) list);
		// refillOrderServiceImpl.getRefillDuesAsOfDate("45", 45, "token");
		refillOrderServiceImpl.getRefillDuesAsOfDate("45", 46, "token");
		// refillOrderServiceImpl.getRefillDuesAsOfDate("45", 47, "token");
	}

	@Test
	public void getRefillDuesAsOfDateInvalidToken() throws SubscriptionIdNotFoundException, InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> refillOrderServiceImpl.getRefillDuesAsOfDate("45", 47, "token"));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void requestAdhocRefill() throws SubscriptionIdNotFoundException, InvalidTokenException, FeignException,
			ParseException, DrugQuantityNotAvailable {
		RefillOrder refillOrder = new RefillOrder(1, new Date(), true, 45, 45, "54");

		ResponseEntity<String> entityname = new ResponseEntity<String>("45", HttpStatus.OK);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(subscriptionClient.getDrugNameBySubscriptionId((long) 45, "token")).thenReturn(entityname);
		ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
		when(drugDetailClient.updateQuantity("token", "45", "salem", 45)).thenReturn(responseEntity);
		// when(refillOrderServiceImpl.requestAdhocRefill(45, true, 45, "salem",
		// "token").responsevalue).then
		when(refillOrderRepository.save(refillOrder)).thenReturn(refillOrder);
		refillOrderServiceImpl.requestAdhocRefill((long) 45, true, 45, "salem", "token");
		// assertThrows(NullPointerException.class,()->refillOrderServiceImpl.requestAdhocRefill(45,true,45,
		// "location", "token"));
	}

	@Test
	public void requestAdhocRefilllInvalidTokenException()
			throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> refillOrderServiceImpl.requestAdhocRefill((long) 45, true, 45, "location", "token"));

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void requestAdhocRefillDrugQuantityNotAvailable() throws SubscriptionIdNotFoundException,
			InvalidTokenException, FeignException, ParseException, DrugQuantityNotAvailable {
		RefillOrder refillOrder = new RefillOrder(1, new Date(), true, 45, 45, "54");

		ResponseEntity<String> entityname = new ResponseEntity<String>("45", HttpStatus.OK);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(subscriptionClient.getDrugNameBySubscriptionId((long) 45, "token")).thenReturn(entityname);
		when(drugDetailClient.updateQuantity("token", "45", "salem", 45))
				.thenReturn(new ResponseEntity(HttpStatus.ACCEPTED));
		when(refillOrderRepository.save(refillOrder)).thenReturn(refillOrder);
		// refillOrderServiceImpl.requestAdhocRefill(45,true,45, "salem", "token");
		assertThrows(DrugQuantityNotAvailable.class,
				() -> refillOrderServiceImpl.requestAdhocRefill((long) 45, true, 45, "salem", "token"));
	}

	@Test
	public void requestRefill() throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, new Date(), true, 45, 45, "54");
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.save(refillOrder)).thenReturn(refillOrder);
		refillOrderServiceImpl.requestRefill(45, 45, "45", "token");
	}

	@Test
	public void requestRefillInvalidTokenException()
			throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderServiceImpl.requestRefill(45, 45, "45", "token"));

	}

	@Test
	public void getRefillDuesAsOfPayment()
			throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, new Date(), true, 45, 45, "54");
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.findAll()).thenReturn(list);
		refillOrderServiceImpl.getRefillPaymentDues(45, "token");
	}

	@Test
	public void getRefillDuesAsOfPaymentTrue()
			throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, new Date(), true, 45, 45, "54");
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.findAll()).thenReturn(list);
		refillOrderServiceImpl.getRefillPaymentDues(54, "token");
	}

	@Test
	public void getRefillDuesAsOfPaymentFalse()
			throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, new Date(), false, 45, 45, "54");
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.findAll()).thenReturn(list);
		refillOrderServiceImpl.getRefillPaymentDues(45, "token");
	}

	@Test
	public void getRefillDuesAsOfPaymentException()
			throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderServiceImpl.getRefillPaymentDues(45, "token"));

	}

	@Test
	public void updateRefill() throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		ArrayList<RefillOrderSubscription> list = new ArrayList<>();
		RefillOrderSubscription refillOrder = new RefillOrderSubscription(1, 2, "true", 45, 1);
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderSubscriptionService.getall("token")).thenReturn(list);
		refillOrderServiceImpl.updateRefill("token");
	}

	@Test
	public void updateRefillException() throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderServiceImpl.updateRefill("token"));

	}

	@Test
	public void startTimer() throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		ArrayList<RefillOrderSubscription> list = new ArrayList<>();
		RefillOrderSubscription refillOrder = new RefillOrderSubscription(1, 2, "true", 45, 1);
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		// when(refillOrderServiceImpl.startTimer("token").UpdateRefill("token")).thenReturn(list);
		refillOrderServiceImpl.startTimer("token");
	}

	@Test
	public void startTimerException() throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderServiceImpl.startTimer("token"));

	}

}
