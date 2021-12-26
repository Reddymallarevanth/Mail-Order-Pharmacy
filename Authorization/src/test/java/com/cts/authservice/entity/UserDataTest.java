
package com.cts.authservice.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserDataTest {

	UserData auth = new UserData();
	UserData auth1 = new UserData("ad", "ad", "ad","ad");

	@Test
	void testUid() {
		auth.setUname("Uid");
		assertEquals(auth.getUname(), "Uid");
	}

	@Test
	void testName() {
		auth.setUpassword("Name");
		assertEquals(auth.getUpassword(), "Name");
	}

	@Test
	void testIsValid() {
		auth.setUserid("a");
		assertEquals(auth.getUserid(),"a");
	}
	
	@Test
	void testToken() {
		auth.setAuthToken("ad");
		assertEquals(auth.getAuthToken(),"ad");
	}
	
	@Test
	void testToString() {
		String string = auth1.toString();
		assertEquals(auth1.toString(),string);
	}

}
