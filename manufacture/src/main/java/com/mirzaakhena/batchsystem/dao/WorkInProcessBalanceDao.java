package com.mirzaakhena.batchsystem.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.WorkInProcessBalance;

public interface WorkInProcessBalanceDao extends JpaRepository<WorkInProcessBalance, Long> {

	@Query("FROM WorkInProcessBalance WHERE workInProcess.account.code = ?1 ORDER BY journalStart.date DESC")
	List<WorkInProcessBalance> findLast(String wipCode, Pageable pageable);

	@Query("FROM WorkInProcessBalance WHERE active = true ORDER BY journalStart.date DESC")
	List<WorkInProcessBalance> findAllFinished();

	@Query("FROM WorkInProcessBalance WHERE workInProcess.id = ?1 AND (DATE_FORMAT(date, '%Y%m%d') BETWEEN ?2 AND ?3) ORDER BY date ASC")
	List<WorkInProcessBalance> findByDateRange(Long wipId, String from, String until);

}
