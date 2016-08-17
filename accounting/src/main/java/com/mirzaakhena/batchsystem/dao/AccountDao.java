package com.mirzaakhena.batchsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.AccountSide;

public interface AccountDao extends JpaRepository<Account, Long> {

	Account findByCode(String code);

	Account findById(Long accountId);
	
	@Query("FROM Account WHERE accountParent = null")
	List<Account> findRootAccountParent();
	
	@Query("FROM Account WHERE level <= ?1 ORDER BY id ASC")
	List<Account> findAllUnderLevel(int level);

	@Query("FROM Account WHERE accountParent.id = ?1 ORDER BY id ASC")
	List<Account> findChild(Long accountId);

	@Query("FROM Account WHERE level = ?1 AND accountSide = ?2 ORDER BY id ASC")
	List<Account> findAllWithLevelAndSide(int level, AccountSide side);
	

}
