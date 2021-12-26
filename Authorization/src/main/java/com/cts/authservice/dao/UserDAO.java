package com.cts.authservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.authservice.entity.UserData;


//JPA Repository which interacts with database
@Repository
public interface UserDAO extends JpaRepository<UserData, String> {

}
