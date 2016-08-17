package com.mirzaakhena.batchsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirzaakhena.batchsystem.model.Purchase;

public interface PurchaseDao extends JpaRepository<Purchase, Long> {

}
