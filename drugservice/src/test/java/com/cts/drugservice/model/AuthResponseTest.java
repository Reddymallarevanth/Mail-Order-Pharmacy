package com.cts.drugservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.cts.drugservice.entity.AuthResponse;

class AuthResponseTest {

	AuthResponse auth = new AuthResponse();
	AuthResponse auth2 = new AuthResponse("Uid","Name",true);
	@Test
	void testUid() {
		auth.setUid("Uid");
		assertEquals(auth.getUid(), "Uid");
	}

	@Test
	void testName() {
		auth.setName("Name");
		assertEquals(auth.getName(), "Name");
	}

	@Test
	void testIsValid() {
		auth.setValid(true);
		assertEquals(auth.isValid(), true);
	}
	
	@Test
	void testToString() {
		String str = auth2.toString();
		assertEquals(auth2.toString(), str);
	}
}
