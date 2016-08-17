package com.mirzaakhena.batchsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

	@Query("FROM Customer WHERE account.code = ?1")
	Customer findByCode(String code);

}
