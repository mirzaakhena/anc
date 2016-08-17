package com.mirzaakhena.batchsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.FinishedGoods;

public interface FinishedGoodsDao extends JpaRepository<FinishedGoods, Long> {

	@Query("FROM FinishedGoods WHERE account.code=?1")
	List<FinishedGoods> findByCode(String code);

	@Query("FROM FinishedGoods WHERE account.name LIKE ?1 ORDER BY account.name")
	List<FinishedGoods> findAll(String name);

}
