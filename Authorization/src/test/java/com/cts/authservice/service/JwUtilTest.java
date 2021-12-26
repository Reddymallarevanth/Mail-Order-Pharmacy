package com.cts.authservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.cts.authservice.dao.UserDAO;

@SpringBootTest
public class JwUtilTest {

	@Mock
	UserDetails userdetails;

	@InjectMocks
	JwtUtil jwtUtil;

	@Mock
	UserDAO userservice;
	
	@Mock
	CustomerDetailsService customerDetailsService;
	

	@Test
	public void generateTokenTest() {
		userdetails = new User("admin", "admin", new ArrayList<>());
		String generateToken = jwtUtil.generateToken(userdetails);
		assertNotNull(generateToken);
	}

	@Test
	public void validateTokenTest() {
		userdetails = new User("admin", "admin", new ArrayList<>());
		String generateToken = jwtUtil.generateToken(userdetails);
		Boolean validateToken = jwtUtil.validateToken(generateToken);
		assertEquals(true, validateToken);
	}

	@Test
	public void validateTokenWithNameTest() {
		userdetails = new User("admin", "admin", new ArrayList<>());
		String generateToken = jwtUtil.generateToken(userdetails);
		Boolean validateToken = jwtUtil.validateToken(generateToken); //, userdetails);
		assertEquals(true, validateToken);
	}

//	@Test
//	public void validateTokenWithNameFalseTest() {
//		userdetails = new User("admin3", "admin3", new ArrayList<>());
//		System.out.println("//////////");
//
//		// userdetails = customerDetailsService.loadUserByUsername("admin3");
//			System.out.println("///asdd//////");
//		when(customerDetailsService.loadUserByUsername("admin")).thenReturn(); 
////		UserDetails user1 = new User("admin1", "admin1", new ArrayList<>());
//		String generateToken = jwtUtil.generateToken(userdetails);
//		System.out.println("//////////");
//		System.out.println(generateToken); 
////		System.out.println(jwtUtil.generateToken(userdetails));
////		String generateToken = jwtUtil.generateToken(userdetails);
//		
//		when(Jwts.parser().setSigningKey("secretkey")).thenThrow(NullPointerException.class);
//		
//		Boolean validateToken = jwtUtil.validateToken("admin"); //, user1);
//	//	System.out.println(generateToken);
//		assertEquals(false, validateToken);
//	}

}
