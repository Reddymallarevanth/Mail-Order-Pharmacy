package com.cts.drugservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cts.drugservice.dao.DrugDetailsRepository;
import com.cts.drugservice.dao.DrugLocationRepository;
import com.cts.drugservice.entity.DrugDetails;
import com.cts.drugservice.entity.DrugLocationDetails;
import com.cts.drugservice.entity.Stock;
import com.cts.drugservice.entity.SuccessResponse;
import com.cts.drugservice.entity.TokenValid;
import com.cts.drugservice.restclients.AuthFeign;
import com.cts.drugservice.service.DrugDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class DrugControllerTest {

	@InjectMocks
	DrugController drugController;

	@Mock
	DrugDetailsService drugDetailsService;

	@Autowired
	DrugDetailsRepository drugDetailsRepository;

	@Autowired
	DrugLocationRepository drugLocationRepository;

	@MockBean
	AuthFeign authFeign;

	@Autowired
	MockMvc mockMvc;

	List<DrugLocationDetails> list = new ArrayList<DrugLocationDetails>();
	@Test
	public void testGetDrugById() throws Exception {
		list.add(new DrugLocationDetails("D1", "Chennai", 30, null));
		DrugDetails expected = new DrugDetails("D1", "Drug1", "manu1", new Date(), new Date(), list);
		ObjectMapper objectMapper = new ObjectMapper();
		String expectedValue = objectMapper.writeValueAsString(expected).substring(11, 13);
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("Bearer Token")).thenReturn(response);
		when(drugDetailsService.getDrugById("D1", "Bearer Token")).thenReturn(expected);
		MvcResult result = mockMvc.perform(get("/searchDrugsById/D1").header("Authorization", "Bearer Token"))
				.andReturn();
		String actualValue = result.getResponse().getContentAsString().substring(11, 13);
		assertEquals(expectedValue, actualValue);
	}
	@Test
	public void testGetDrugByName() throws Exception {
		list.add(new DrugLocationDetails("D1", "Chennai", 30, null));
		DrugDetails expected = new DrugDetails("D1", "Drug1", "manu1", new Date(), new Date(), list);
		ObjectMapper objectMapper = new ObjectMapper();
		String expectedValue = objectMapper.writeValueAsString(expected).substring(27, 32);
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("Bearer Token")).thenReturn(response);
		MvcResult result = mockMvc.perform(get("/searchDrugsByName/Drug1").header("Authorization", "Bearer Token"))
				.andReturn();
		String actualValue = result.getResponse().getContentAsString().substring(27, 32);
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testDispatchableDrugStock() throws Exception {
		Stock expectedStock = new Stock("D1","Drug1",new Date(),30);
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("Bearer Token")).thenReturn(response);
		when(drugDetailsService.getDispatchableDrugStock("D1", "Chennai", "Bearer token")).thenReturn(expectedStock);
		when(drugController.getDispatchableDrugStock("Bearer token", "D1", "Chennai")).thenReturn(expectedStock);
	}
	
	@Test
	public void testupdateQuantity() throws Exception {
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<SuccessResponse> expectedValue = new ResponseEntity<SuccessResponse>(new SuccessResponse("Refill done successfully"), HttpStatus.OK); 
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("Bearer Token")).thenReturn(response);
		when(drugDetailsService.updateQuantity("D1", "Chennai", 1, "Bearer token")).thenReturn(expectedValue);
		when(drugController.updateQuantity("D1", "Chennai", "Bearer token", 1)).thenReturn(expectedValue);
	}
	
	@Test
	public void testgetAllDrugs() throws Exception {
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		List<DrugDetails> expectedValue = new ArrayList<>();
		list.add(new DrugLocationDetails("D1", "Chennai", 30, null));
		DrugDetails expected = new DrugDetails("D1", "Drug1", "manu1", new Date(), new Date(), list);
		expectedValue.add(expected);
		when(authFeign.getValidity("Bearer Token")).thenReturn(response);
		when(drugDetailsService.getAllDrugs()).thenReturn(expectedValue);
		when(drugController.getAllDrugs()).thenReturn(expectedValue);
	}
}
