package com.mirzaakhena.batchsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirzaakhena.batchsystem.model.ExpenseCategory;

public interface ExpenseCategoryDao extends JpaRepository<ExpenseCategory, Long> {

}
