package com.mirzaakhena.batchsystem.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.JournalAccountBalance;
import com.mirzaakhena.batchsystem.model.JournalAccountBalanceKey;

public interface JournalAccountBalanceDao extends JpaRepository<JournalAccountBalance, JournalAccountBalanceKey> {

	@Query("FROM JournalAccountBalance WHERE accountBalance.account.id = ?1 AND (DATE_FORMAT(journal.date, '%Y%m%d') BETWEEN ?2 AND ?3)")
	List<JournalAccountBalance> findAll(Long accountId, String from, String until);
	
	@Query("FROM JournalAccountBalance WHERE (DATE_FORMAT(journal.date, '%Y%m%d') BETWEEN ?1 AND ?2)")
	List<JournalAccountBalance> findAll(String from, String until);

	@Query("FROM JournalAccountBalance WHERE accountBalance.account.id = ?1 AND DATE_FORMAT(journal.date, '%Y%m%d') <= ?2 ORDER BY journal.date DESC")
	List<JournalAccountBalance> findLastBefore(Long accountId, String date, Pageable pageable);

	@Query("FROM JournalAccountBalance WHERE DATE_FORMAT(journal.date, '%Y%m%d') >= ?1 ORDER BY journal.date DESC")
	List<JournalAccountBalance> findAllAfter(String date);
	
}
