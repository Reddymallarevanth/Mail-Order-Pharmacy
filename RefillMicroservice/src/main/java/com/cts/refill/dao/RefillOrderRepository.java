package com.cts.refill.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.refill.entity.RefillOrder;

@Repository
public interface RefillOrderRepository extends JpaRepository<RefillOrder,Integer> {

}
