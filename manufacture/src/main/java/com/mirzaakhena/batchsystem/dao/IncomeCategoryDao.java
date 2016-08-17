package com.mirzaakhena.batchsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirzaakhena.batchsystem.model.IncomeCategory;

public interface IncomeCategoryDao extends JpaRepository<IncomeCategory, Long> {

}
