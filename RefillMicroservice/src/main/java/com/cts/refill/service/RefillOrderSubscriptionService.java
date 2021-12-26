package com.cts.refill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.refill.entity.RefillOrderSubscription;
import com.cts.refill.exception.InvalidTokenException;

@Service
public interface RefillOrderSubscriptionService {

	public RefillOrderSubscription updateRefillOrderSubscription(long subId, String memberId, int quantity, int time,
			String token) throws InvalidTokenException;

	public List<RefillOrderSubscription> getall(String token) throws InvalidTokenException;

	public void deleteBySubscriptionId(long subscriptionId, String token) throws InvalidTokenException;

}
