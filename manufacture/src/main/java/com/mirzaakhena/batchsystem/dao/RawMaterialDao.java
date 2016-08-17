package com.mirzaakhena.batchsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.RawMaterial;

public interface RawMaterialDao extends JpaRepository<RawMaterial, Long> {

	@Query("FROM RawMaterial WHERE account.name LIKE ?1 ORDER BY account.name")
	List<RawMaterial> findAll(String name);

	@Query("FROM RawMaterial WHERE account.code = ?1")
	RawMaterial findByCode(String code);
	
}
