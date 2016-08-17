package com.mirzaakhena.batchsystem.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.AccountBalance;
import com.mirzaakhena.batchsystem.model.AccountBalanceKey;

public interface AccountBalanceDao extends JpaRepository<AccountBalance, AccountBalanceKey> {

	@Query("FROM AccountBalance WHERE account.id = ?1 AND DATE_FORMAT(date, '%Y%m%d') <= ?2 ORDER BY date DESC")
	List<AccountBalance> findLastAccountBalanceBefore(Long accountId, String date, Pageable pageable);

	@Query("FROM AccountBalance WHERE account.id = ?1 AND (DATE_FORMAT(date, '%Y%m%d') BETWEEN ?2 AND ?3) ORDER BY date ASC")
	List<AccountBalance> findByAccountAndBeforeDate(Long accountId, String from, String until);

}
