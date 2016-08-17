package com.mirzaakhena.batchsystem.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.InventoryBalance;
import com.mirzaakhena.batchsystem.model.InventoryBalanceKey;

public interface InventoryBalanceDao extends JpaRepository<InventoryBalance, InventoryBalanceKey> {

	@Query("FROM InventoryBalance WHERE account.id = ?1 AND DATE_FORMAT(journal.date, '%Y%m%d') <= ?2 ORDER BY date DESC")
	List<InventoryBalance> findLastInventory(Long accountId, String beforeDate, Pageable pageable);
	
	@Query("FROM InventoryBalance WHERE account.id = ?1 AND (DATE_FORMAT(journal.date, '%Y%m%d') BETWEEN ?2 AND ?3) ORDER BY date ASC")
	List<InventoryBalance> findAll(Long accountId, String from, String until);

	@Query("FROM InventoryBalance WHERE journal.id = ?1 ORDER BY date ASC")
	List<InventoryBalance> findByJournal(Long journalId);
	
}
