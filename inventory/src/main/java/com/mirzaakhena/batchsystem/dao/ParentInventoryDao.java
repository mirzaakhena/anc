package com.mirzaakhena.batchsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirzaakhena.batchsystem.model.ParentInventory;

public interface ParentInventoryDao extends JpaRepository<ParentInventory, Long> {

}
