package com.mirzaakhena.batchsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.ExpenseCategoryDao;
import com.mirzaakhena.batchsystem.dto.BaseCategoryDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.ExpenseCategory;

@Service
public class ExpenseCategoryService extends BaseSubAccountService {

	@Autowired
	private ExpenseCategoryDao dao;

	@Transactional(rollbackFor=Exception.class)
	public ExpenseCategory add(BaseCategoryDto dto) throws Exception {
		
		Account account = createAccount(ExpenseCategory.class, dto.getName());
		
		ExpenseCategory expense = new ExpenseCategory();
		expense.setAccount(account);

		return dao.save(expense);
	}

	public ExpenseCategory getById(Long categoryId) {
		return dao.findOne(categoryId);
	}

	public List<ExpenseCategory> getAll() {
		return dao.findAll();
	}

}
