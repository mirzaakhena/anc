package com.mirzaakhena.batchsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.WorkInProcess;

public interface WorkInProcessDao extends JpaRepository<WorkInProcess, Long> {

	@Query("FROM WorkInProcess WHERE account.code = ?1")
	public WorkInProcess findByWIPCode(String code);

	@Query("FROM WorkInProcess WHERE account.name LIKE ?1 ORDER BY account.name")
	List<WorkInProcess> findAll(String name);

}
