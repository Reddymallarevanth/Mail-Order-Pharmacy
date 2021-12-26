package com.cts.refill.controller;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.refill.exception.DrugQuantityNotAvailable;
import com.cts.refill.exception.InvalidTokenException;
import com.cts.refill.service.RefillOrderService;
import com.cts.refill.service.RefillOrderSubscriptionService;

import feign.FeignException;

@SpringBootTest(classes = RefillControllerTest.class)
class RefillControllerTest {

	@InjectMocks
	RefillController refillController;

	@Mock
	public RefillOrderService service;

	@Mock
	RefillOrderSubscriptionService refillOrderSubscriptionService;

//	@MockBean
//	MockMvc mockMvc;

	@Test
	public void viewRefillStatusTest() throws Exception {

//		MvcResult result = mockMvc.perform(get("/viewRefillStatus/45")).andReturn();
//		assertThrows(SubscriptionIdNotFoundException.class, ()->result.getResponse());
//		Date date =new Date();
//		RefillOrder refillOrder = new RefillOrder(1,date,true,1,1,"1");
//		List<RefillOrder> list = new ArrayList<RefillOrder>();
//		list.add(refillOrder);
//		//when(service.getStatus(1, "token")).
		refillController.viewRefillStatus("token", 1);
	}

	@Test
	public void getRefillDuesAsOfDateTest() throws InvalidTokenException {
		refillController.getRefillDuesAsOfDate("memberId", "token", 45);
	}

	@Test
	public void getRefillDuesAsOfPayment() throws InvalidTokenException {
		refillController.getRefillPaymentDues("token",45 );
	}

	@Test
	public void requestAdhocRefill() throws InvalidTokenException, FeignException, ParseException, DrugQuantityNotAvailable {
		refillController.requestAdhocRefill("token",54,true,45,"salem");
	}

	@Test
	public void requestRefillSubscription() throws InvalidTokenException, ParseException {
		refillController.requestRefillSubscription("memberId", 4, "token", 45, 5);
	}

	@Test
	public void viewRefillOrderSubscriptionStatus() throws InvalidTokenException {
		refillController.viewRefillOrderSubscriptionStatus ("token");
	}

	@Test
	public void startTimer() throws InvalidTokenException {
		refillController.startTimer("token");
	}
	@Test
	public void deleteBySubscriptionId() throws InvalidTokenException {
		refillController.deleteBySubscriptionId("token",45);
	}


}
