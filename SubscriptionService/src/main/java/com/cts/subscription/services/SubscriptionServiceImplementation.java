package com.cts.subscription.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.subscription.Repository.PrescriptionRepository;
import com.cts.subscription.Repository.SubscriptionRepository;
import com.cts.subscription.entity.PrescriptionDetails;
import com.cts.subscription.entity.SubscriptionDetails;
import com.cts.subscription.exceptions.InvalidTokenException;
import com.cts.subscription.exceptions.SubscriptionListEmptyException;
import com.cts.subscription.restclients.AuthFeign;
import com.cts.subscription.restclients.DrugDetailClient;
import com.cts.subscription.restclients.RefillClient;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SubscriptionServiceImplementation implements SubscriptionService {

	@Autowired
	private DrugDetailClient drugDetailClient;
	
	@Autowired
	private RefillClient refillClient;

	@Autowired
	private AuthFeign authFeign;

	@Autowired
	PrescriptionRepository prescriptionRepo;
	
	@Autowired
	SubscriptionRepository subscriptionRepo;

	@Override
	public ResponseEntity<String> subscribe(PrescriptionDetails prescriptionDetail, String token)
			throws InvalidTokenException, FeignException {
		log.info("Inside subscribe service method");
		if (authFeign.getValidity(token).getBody().isValid()) {
			log.info("subscribe service method- token is valid");
			drugDetailClient.getDrugByName(token, prescriptionDetail.getDrugName());
			PrescriptionDetails prescriptionDetails = prescriptionRepo.save(prescriptionDetail);
			log.info("prescription saved");
			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);
			Date endDate = null;
			if (prescriptionDetail.getDosageDefinition().equalsIgnoreCase("weekly")) {
				long ltime = date.getTime() + 7 * 24 * 60 * 60 * 1000;
				endDate = new Date(ltime);
			}
			if (prescriptionDetail.getDosageDefinition().equalsIgnoreCase("Monthly")) {
				long ltime = date.getTime() + 31l * 24l * 60l * 60l * 1000l;
				endDate = new Date(ltime);
			}
			SubscriptionDetails subscriptionDetail = new SubscriptionDetails(prescriptionDetails.getPrescriptionId(),
					prescriptionDetails.getCourseDuration(), prescriptionDetails.getQuantity(),
					prescriptionDetails.getMemberId(), date, prescriptionDetails.getMemberLocation(),
					"active", prescriptionDetails.getDrugName(),endDate);

			log.info("subs obj created");

			SubscriptionDetails subscriptionDetails = subscriptionRepo.save(subscriptionDetail);
			// inform refill service to start refilling for this subscription
			refillClient.requestRefillSubscription(token, subscriptionDetails.getSubscriptionId(),
					subscriptionDetails.getMemberId(), subscriptionDetails.getQuantity(),
					subscriptionDetails.getRefillCycle());

			log.info("subs obj saved");
		} else
			throw new InvalidTokenException("Invalid Credentials");

		return new ResponseEntity<>("You have succesfully subscribed to " + prescriptionDetail.getDrugName(),
				HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> unsubscribe(String mId, Long sId, String token)
			throws InvalidTokenException, FeignException {

		if (authFeign.getValidity(token).getBody().isValid()) {
			if (!refillClient.isPendingPaymentDues(token, sId)) {
				log.info("payment not clear");
				return new ResponseEntity<>("You have to clear your payment dues first.", HttpStatus.BAD_REQUEST);
			}
			subscriptionRepo.deleteById(sId);
			log.info("dleted ");
			// inform refill service to stop refilling for this sId
			refillClient.deleteRefillData(token, sId);
			log.info("delete refill success");
		} else
			throw new InvalidTokenException("Invalid Credentials");

		return new ResponseEntity<>("You have succesfully Unsubscribed", HttpStatus.OK);
	}

	@Override
	public List<SubscriptionDetails> getAllSubscriptions(String mId, String token)
			throws InvalidTokenException, SubscriptionListEmptyException {
		// gets a list  of subscriptions for a given member id
		log.info("get subscription for ");
		if (authFeign.getValidity(token).getBody().isValid()) {
			if (subscriptionRepo.findByMemberId(mId).isEmpty())
				throw new SubscriptionListEmptyException("Currently you do not have any subscriptions");
			return subscriptionRepo.findByMemberId(mId);
		} else
			throw new InvalidTokenException("Invalid Credentials");

	}

	@Override
	public ResponseEntity<String> getDrugNameBySubscriptionId(Long sId, String token) throws InvalidTokenException {
		// extracts Drug name for given subscription Id
		log.info("getDrugNameBySubscriptionId");
		String drugName;
		if (authFeign.getValidity(token).getBody().isValid()) {
			drugName = subscriptionRepo.findById(sId)
					.orElseThrow(() -> new SubscriptionListEmptyException("DrugNotFound")).getDrugName();
		} else
			throw new InvalidTokenException("Invalid Credentials");
		return new ResponseEntity<>(drugName, HttpStatus.OK);
	}
	
	
}
