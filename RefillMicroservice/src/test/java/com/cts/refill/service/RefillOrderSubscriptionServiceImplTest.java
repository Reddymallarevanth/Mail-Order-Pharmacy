package com.cts.refill.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.refill.dao.RefillOrderSubscriptionRepository;
import com.cts.refill.entity.RefillOrderSubscription;
import com.cts.refill.entity.TokenValid;
import com.cts.refill.exception.InvalidTokenException;
import com.cts.refill.restclients.AuthFeign;

@SpringBootTest(classes = RefillOrderSubscriptionServiceImplTest.class)
class RefillOrderSubscriptionServiceImplTest {

	@InjectMocks
	RefillOrderSubscriptionServiceImpl refillOrderSubscriptionServiceImpl;

	@Mock
	RefillOrderSubscriptionRepository refillOrderSubscriptionRepository;

	@Mock
	AuthFeign authFeign;

	@Test
	public void updateRefillOrderSubscriptionTest() throws InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		refillOrderSubscriptionServiceImpl.updateRefillOrderSubscription(45, "memberId", 45, 4, "token");

	}

	@Test
	public void updateRefillOrderSubscriptionTestFalse() throws InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> refillOrderSubscriptionServiceImpl.updateRefillOrderSubscription(45, "memberId", 45, 4, "token"));

	}

	@Test
	public void getallTest() throws InvalidTokenException {
		ArrayList<RefillOrderSubscription> list = new ArrayList<>();
		RefillOrderSubscription refillOrder = new RefillOrderSubscription(1, 2, "true", 45, 46);
		list.add(refillOrder);

		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderSubscriptionRepository.findAll()).thenReturn(list);
		refillOrderSubscriptionServiceImpl.getall("token");

	}

	@Test
	public void getallTestFalse() throws InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderSubscriptionServiceImpl.getall("token"));

	}
	
	@Test
	public void deleteBySubscriptionIdTest() throws InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		refillOrderSubscriptionServiceImpl.deleteBySubscriptionId(45, "token");

	}
	@Test
	public void deleteBySubscriptionIdTestFalse() throws InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderSubscriptionServiceImpl.deleteBySubscriptionId(45, "token"));

	}
	
}
