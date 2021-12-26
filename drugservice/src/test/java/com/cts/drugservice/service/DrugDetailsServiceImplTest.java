package com.cts.drugservice.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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

import com.cts.drugservice.dao.DrugDetailsRepository;
import com.cts.drugservice.dao.DrugLocationRepository;
import com.cts.drugservice.entity.DrugDetails;
import com.cts.drugservice.entity.DrugLocationDetails;
import com.cts.drugservice.entity.SuccessResponse;
import com.cts.drugservice.entity.TokenValid;
import com.cts.drugservice.exception.DrugNotFoundException;
import com.cts.drugservice.exception.InvalidTokenException;
import com.cts.drugservice.exception.StockNotFoundException;
import com.cts.drugservice.restclients.AuthFeign;

@SpringBootTest(classes = DrugDetailsServiceImplTest.class)
class DrugDetailsServiceImplTest {

	@InjectMocks
	DrugDetailsServiceImpl drugDetailsServiceImpl;

	@Mock
	AuthFeign authFeign;

	@Mock
	private DrugDetailsRepository drugRepo;

	@Mock
	private DrugLocationRepository locationRepo;

	Date date = new Date();
	List<DrugLocationDetails> list = new ArrayList<DrugLocationDetails>();
	DrugDetails drugDetail = new DrugDetails("drug", "drug", "drug", date, date, list);

	@Test
	public void getDrugByIdTest() throws InvalidTokenException, DrugNotFoundException {

		TokenValid tokenValid = new TokenValid("uid", "name", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(drugRepo.findById("D5")).thenReturn(null);
		assertThrows(NullPointerException.class, () -> drugDetailsServiceImpl.getDrugById("D5", "token"),
				"getDrugByIdTest");
	}

	@Test
	public void getDrugByIdTestDrugNotFoundException() throws InvalidTokenException, DrugNotFoundException {

		TokenValid tokenValid = new TokenValid("uid", "name", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(drugRepo.findById("D5")).thenReturn(Optional.empty());
		assertThrows(DrugNotFoundException.class, () -> drugDetailsServiceImpl.getDrugById("D5", "token"),
				"getDrugByIdTest");
	}

	@Test
	public void getDrugByIdTestSucess() throws InvalidTokenException, DrugNotFoundException {

		Date date = new Date();
		DrugDetails drugDetails = new DrugDetails("D5", "drug", "drug", date, date, list);
		TokenValid tokenValid = new TokenValid("uid", "name", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);

		when(drugRepo.findById("D5")).thenReturn(Optional.of(drugDetails));
		drugDetailsServiceImpl.getDrugById("D5", "token");
	}

	@Test
	public void getDrugByIdTestFalse() throws InvalidTokenException, DrugNotFoundException {
		TokenValid tokenValid = new TokenValid("uid", "name", false);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		// when(drugRepo.findById("D5")).thenReturn(null);
		assertThrows(InvalidTokenException.class, () -> drugDetailsServiceImpl.getDrugById("D5", "token"),
				"getDrugByIdTest");

	}

	@Test
	public void getDrugByNameFalse() {
		TokenValid tokenValid = new TokenValid("uid", "name", false);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> drugDetailsServiceImpl.getDrugByName("D5", "token"),
				"getDrugByIdTest");
	}

	@Test
	public void getDrugByNameSucess() throws DrugNotFoundException, InvalidTokenException {
		Date date = new Date();
		DrugDetails drugDetails = new DrugDetails("drug", "drug", "drug", date, date, list);
		TokenValid tokenValid = new TokenValid("uid", "name", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(drugRepo.findBydrugName("D5")).thenReturn(Optional.of(drugDetails));
		drugDetailsServiceImpl.getDrugByName("D5", "token");
	}

	@Test
	public void getDrugByNameDrugNotFoundException() {
		TokenValid tokenValid = new TokenValid("uid", "name", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(drugRepo.findBydrugName("D5")).thenReturn(null);
		assertThrows(DrugNotFoundException.class, () -> drugDetailsServiceImpl.getDrugByName("D5", "token"),
				"getDrugByIdTest");
	}

	@Test
	public void getDispatchableDrugStockFalse() {
		TokenValid tokenValid = new TokenValid("uid", "name", false);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> drugDetailsServiceImpl.getDispatchableDrugStock("D5", "salem", "token"), "getDrugByIdTest");
	}

	@Test
	public void getDispatchableDrugStockSuccess()
			throws DrugNotFoundException, InvalidTokenException, StockNotFoundException {
		Date date = new Date();
		DrugLocationDetails drugLocationDetails = new DrugLocationDetails("45", "salem", 45, null);
		list.add(drugLocationDetails);
		DrugDetails drugDetails = new DrugDetails("D5", "drug", "drug", date, date, list);
		TokenValid tokenValid = new TokenValid("uid", "name", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		Optional<DrugDetails> opt1 = Optional.of(drugDetails);
		when(drugRepo.findById("D5")).thenReturn(opt1);
		drugDetailsServiceImpl.getDispatchableDrugStock("D5", "salem", "token");

	}

	@Test
	public void getDispatchableDrugStockFalseDrugNotFoundException() {
		TokenValid tokenValid = new TokenValid("uid", "name", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(drugRepo.findById("D5")).thenReturn(null);
		assertThrows(DrugNotFoundException.class,
				() -> drugDetailsServiceImpl.getDispatchableDrugStock("D5", "salem", "token"), "getDrugByIdTest");
	}

	@Test
	public void getDispatchableDrugStockFalseStockNotFoundException() {
		Date date = new Date();
		DrugLocationDetails drugLocationDetails = new DrugLocationDetails("ad", "das", 45, null);
		list.add(drugLocationDetails);
		DrugDetails drugDetails = new DrugDetails("drug", "drug", "drug", date, date, list);
		Optional<DrugDetails> opt = Optional.of(drugDetails);
		TokenValid tokenValid = new TokenValid("uid", "name", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(drugRepo.findById("D5")).thenReturn(opt);
		assertThrows(StockNotFoundException.class,
				() -> drugDetailsServiceImpl.getDispatchableDrugStock("D5", "salem", "token"), "getDrugByIdTest");
	}

	@Test
	public void updateQuantityFalse() {
		TokenValid tokenValid = new TokenValid("uid", "name", false);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> drugDetailsServiceImpl.updateQuantity("token", "token", 45, "token"), "getDrugByIdTest");
	}

	@Test
	public void updateQuantity() throws DrugNotFoundException, InvalidTokenException, StockNotFoundException {
		Date date = new Date();
		list.add(new DrugLocationDetails("45", "salem", 45, new DrugDetails("drug", "drug", "drug", date, date, list)));
		DrugDetails drugDetails = new DrugDetails("drug", "drug", "drug", date, date, list);
		TokenValid tokenValid = new TokenValid("uid", "name", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		Optional<DrugDetails> opt = Optional.of(drugDetails);
		when(drugRepo.findBydrugName("drug")).thenReturn(opt);
		Optional<DrugLocationDetails> opt1 = Optional.of(
				new DrugLocationDetails("45", "salem", 45, new DrugDetails("drug", "drug", "drug", date, date, list)));
		when(locationRepo.findById("45")).thenReturn(opt1);
		SuccessResponse successResponse = new SuccessResponse();
		ResponseEntity<SuccessResponse> responseEntity = new ResponseEntity<SuccessResponse>(successResponse, HttpStatus.OK);
		when(locationRepo.findByserialId("45")).thenReturn(list);
		when(drugDetailsServiceImpl.updateQuantity("drug", "salem", 1, "token")).thenReturn(responseEntity);		
	}

	@Test
	public void updateQuantityDrugNotFoundException()
			throws DrugNotFoundException, InvalidTokenException, StockNotFoundException {
		Date date = new Date();
		list.add(new DrugLocationDetails("45", "salem", 45, new DrugDetails("drug", "drug", "drug", date, date, list)));
		TokenValid tokenValid = new TokenValid("uid", "name", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(drugRepo.findBydrugName("drug")).thenReturn(null);
		Optional<DrugLocationDetails> opt1 = Optional.of(
				new DrugLocationDetails("45", "salem", 45, new DrugDetails("drug", "drug", "drug", date, date, list)));
		when(locationRepo.findById("45")).thenReturn(opt1);
		assertThrows(DrugNotFoundException.class,
				() -> drugDetailsServiceImpl.updateQuantity("drug", "salem", 20, "token"), "");

	}

	@Test
	public void updateQuantityStockNotFoundException()
			throws DrugNotFoundException, InvalidTokenException, StockNotFoundException {
		Date date = new Date();
		list.add(new DrugLocationDetails("45", "chennai", 45,
				new DrugDetails("drug", "drug", "drug", date, date, list)));
		DrugDetails drugDetails = new DrugDetails("drug", "drug", "drug", date, date, list);
		TokenValid tokenValid = new TokenValid("uid", "name", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		Optional<DrugDetails> opt = Optional.of(drugDetails);
		when(drugRepo.findBydrugName("drug")).thenReturn(opt);
		Optional<DrugLocationDetails> opt1 = Optional.of(
				new DrugLocationDetails("45", "salem", 45, new DrugDetails("drug", "drug", "drug", date, date, list)));
		when(locationRepo.findById("45")).thenReturn(opt1);
		assertThrows(StockNotFoundException.class,
				() -> drugDetailsServiceImpl.updateQuantity("drug", "salem", 20, "token"), "");

	}

	@Test
	public void updateQuantityStockNotFoundExceptiontwo()
			throws DrugNotFoundException, InvalidTokenException, StockNotFoundException {
		Date date = new Date();
		list.add(new DrugLocationDetails("45", "salem", 4, new DrugDetails("drug", "drug", "drug", date, date, list)));
		DrugDetails drugDetails = new DrugDetails("drug", "drug", "drug", date, date, list);
		TokenValid tokenValid = new TokenValid("uid", "name", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		Optional<DrugDetails> opt = Optional.of(drugDetails);
		when(drugRepo.findBydrugName("drug")).thenReturn(opt);
		Optional<DrugLocationDetails> opt1 = Optional.of(	
				new DrugLocationDetails("45", "salem", 45, new DrugDetails("drug", "drug", "drug", date, date, list)));
		when(locationRepo.findById("45")).thenReturn(opt1);
		assertThrows(StockNotFoundException.class,
				() -> drugDetailsServiceImpl.updateQuantity("drug", "salem", 20, "token"), "");
	}
	
	@Test
	public void getAllDrugs() {
		list.add(new DrugLocationDetails("45", "salem", 4, new DrugDetails("drug", "drug", "drug", date, date, list)));
		List<DrugDetails> drugList = drugRepo.findAll();
		when(drugDetailsServiceImpl.getAllDrugs()).thenReturn(drugList);
	}
	
}
