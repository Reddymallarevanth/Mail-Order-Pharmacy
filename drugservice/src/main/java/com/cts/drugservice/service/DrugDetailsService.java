package com.cts.drugservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cts.drugservice.entity.DrugDetails;
import com.cts.drugservice.entity.Stock;
import com.cts.drugservice.entity.SuccessResponse;
import com.cts.drugservice.exception.DrugNotFoundException;
import com.cts.drugservice.exception.InvalidTokenException;
import com.cts.drugservice.exception.StockNotFoundException;


public interface DrugDetailsService {

	DrugDetails getDrugById(String id, String token) throws InvalidTokenException, DrugNotFoundException;

	DrugDetails getDrugByName(String name, String token) throws InvalidTokenException, DrugNotFoundException;

	Stock getDispatchableDrugStock(String id, String location, String token)
			throws InvalidTokenException, StockNotFoundException, DrugNotFoundException;

	ResponseEntity<SuccessResponse> updateQuantity(String id, String location, int quantity, String token)
			throws InvalidTokenException,DrugNotFoundException,StockNotFoundException;

	List<DrugDetails> getAllDrugs();

}
