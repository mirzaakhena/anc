package com.mirzaakhena.batchsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirzaakhena.batchsystem.model.CatalogBank;

public interface CatalogBankDao extends JpaRepository<CatalogBank, Long> {

}
