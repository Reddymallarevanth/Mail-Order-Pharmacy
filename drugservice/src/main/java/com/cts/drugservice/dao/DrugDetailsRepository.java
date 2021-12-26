package com.cts.drugservice.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.drugservice.entity.DrugDetails;

@Repository
public interface DrugDetailsRepository extends JpaRepository<DrugDetails, String>{

	public Optional<DrugDetails> findById(String id);
	
	public Optional<DrugDetails> findBydrugName(String name);

}