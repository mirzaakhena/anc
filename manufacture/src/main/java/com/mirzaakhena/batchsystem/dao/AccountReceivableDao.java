package com.mirzaakhena.batchsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.AccountReceivable;

public interface AccountReceivableDao extends JpaRepository<AccountReceivable, Long> {

	@Query("FROM AccountReceivable WHERE id = ?1 AND actualDate = NULL")
	AccountReceivable findUnpaid(Long id);
	
}
