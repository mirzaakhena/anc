package com.mirzaakhena.batchsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.Supplier;

public interface SupplierDao extends JpaRepository<Supplier, Long> {

	@Query("FROM Supplier WHERE account.code = ?1")
	Supplier findByCode(String code);

}
