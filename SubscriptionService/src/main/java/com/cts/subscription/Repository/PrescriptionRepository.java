package com.cts.subscription.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.subscription.entity.PrescriptionDetails;

public interface PrescriptionRepository extends JpaRepository<PrescriptionDetails,Long> {

}
