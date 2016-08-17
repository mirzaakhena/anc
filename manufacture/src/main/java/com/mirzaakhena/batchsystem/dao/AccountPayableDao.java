package com.mirzaakhena.batchsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.AccountPayable;

public interface AccountPayableDao extends JpaRepository<AccountPayable, Long> {

	@Query("FROM AccountPayable WHERE id = ?1 AND actualDate = NULL")
	AccountPayable findUnpaid(Long id);
	

}
