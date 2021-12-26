package com.cts.drugservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.drugservice.entity.DrugLocationDetails;

@Repository
public interface DrugLocationRepository extends JpaRepository<DrugLocationDetails, String>{

	List<DrugLocationDetails> findByserialId(String string);

}
