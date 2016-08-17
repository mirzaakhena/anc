package com.mirzaakhena.batchsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.AccountChildIndex;

public interface AccountChildIndexDao extends JpaRepository<AccountChildIndex, Long> {

	@Query("FROM AccountChildIndex WHERE account.id = ?1")
	AccountChildIndex findOneByAccountId(Long accountId);

}
