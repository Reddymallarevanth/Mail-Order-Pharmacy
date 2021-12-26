package com.cts.drugservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.drugservice.dao.DrugDetailsRepository;
import com.cts.drugservice.dao.DrugLocationRepository;
import com.cts.drugservice.entity.DrugDetails;
import com.cts.drugservice.entity.DrugLocationDetails;
import com.cts.drugservice.entity.Stock;
import com.cts.drugservice.entity.SuccessResponse;
import com.cts.drugservice.exception.DrugNotFoundException;
import com.cts.drugservice.exception.InvalidTokenException;
import com.cts.drugservice.exception.StockNotFoundException;
import com.cts.drugservice.restclients.AuthFeign;

@Service
public class DrugDetailsServiceImpl implements DrugDetailsService {

	@Autowired
	private DrugDetailsRepository drugRepo;

	@Autowired
	private DrugLocationRepository locationRepo;

	@Autowired
	private AuthFeign authFeign;

	@Override
	public DrugDetails getDrugById(String id, String token) throws InvalidTokenException, DrugNotFoundException {
		//Returns a drugdetails object given its id
		DrugDetails drugDetails = null;
		if (authFeign.getValidity(token).getBody().isValid()) {
			drugDetails = drugRepo.findById(id).orElseThrow(() -> new DrugNotFoundException("Drug Not Found"));
		} else
			throw new InvalidTokenException("Invalid Credentials");
		return drugDetails;
	}

	@Override
	public DrugDetails getDrugByName(String name, String token) throws InvalidTokenException, DrugNotFoundException {
		//Returns a drugdetails object given its name
		if (authFeign.getValidity(token).getBody().isValid()) {
			try {
				return drugRepo.findBydrugName(name).get();
			} catch (Exception e) {
				throw new DrugNotFoundException("Drug Not Found");
			}
		} else
			throw new InvalidTokenException("Invalid Credentials");
	}

	@Override
	public Stock getDispatchableDrugStock(String id, String location, String token)
			throws InvalidTokenException, StockNotFoundException, DrugNotFoundException {
		// get stock of the drug that is dispatchable in given location
		if (authFeign.getValidity(token).getBody().isValid()) {
			DrugDetails details = null;
			try {
				details = drugRepo.findById(id).get();
			} catch (Exception e) {

				throw new DrugNotFoundException("Drug Not Found");
			}
			Stock stock = null;
			for (DrugLocationDetails dld : details.getDruglocationQuantities()) {
				if (dld.getLocation().equals(location)) {
					stock = new Stock(id, details.getDrugName(), details.getExpiryDate(), dld.getQuantity());
				}
			}
			if (stock == null)
				throw new StockNotFoundException("Stock Unavailable at your location");
			else
				return stock;
		}
		throw new InvalidTokenException("Invalid Credentials");
	}

	@Override
	public ResponseEntity<SuccessResponse> updateQuantity(String drugName, String location, int quantity, String token)
			throws InvalidTokenException, DrugNotFoundException, StockNotFoundException {
		//update quantity of drugs
		// invoked by refill service after placing a refill order
		if (authFeign.getValidity(token).getBody().isValid()) {
			DrugDetails details = new DrugDetails();
			try {
				details = drugRepo.findBydrugName(drugName).get();
			} catch (Exception e) {

				throw new DrugNotFoundException("Drug Not Found");
			}
			List<DrugLocationDetails> dummy = details.getDruglocationQuantities().stream()
					.filter(x -> x.getLocation().equalsIgnoreCase(location)).collect(Collectors.toList());

			if (dummy.size() == 0) {
				throw new StockNotFoundException("Stock Unavailable at your location");
			}

			else if (dummy.get(0).getQuantity() >= quantity) {

				DrugLocationDetails tempDetails = locationRepo.findByserialId(dummy.get(0).getSerialId()).get(0);
				int val = tempDetails.getQuantity() - quantity;
				tempDetails.setQuantity(val);
				locationRepo.save(tempDetails);
				return new ResponseEntity<SuccessResponse>(new SuccessResponse("Refill Done Successfully"),
						HttpStatus.OK);
			} else
				throw new StockNotFoundException("Stock Unavailable at your location");
		}
		throw new InvalidTokenException("Invalid Credentials");
	}

	@Override
	public List<DrugDetails> getAllDrugs() {
		// Get all drugs present in the database
		List<DrugDetails> drugList = drugRepo.findAll();
		return drugList;
	}
}
