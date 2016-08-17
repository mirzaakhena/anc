package com.mirzaakhena.batchsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.IncomeCategoryDao;
import com.mirzaakhena.batchsystem.dto.BaseCategoryDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.IncomeCategory;

@Service
public class IncomeCategoryService extends BaseSubAccountService {

	@Autowired
	private IncomeCategoryDao dao;

	@Transactional(rollbackFor=Exception.class)
	public IncomeCategory add(BaseCategoryDto dto) throws Exception {
		
		Account account = createAccount(IncomeCategory.class, dto.getName());
		
		IncomeCategory income = new IncomeCategory();
		income.setAccount(account);
		return dao.save(income);
	}

	public IncomeCategory getById(Long categoryId) {
		return dao.findOne(categoryId);
	}

	public List<IncomeCategory> getAll() {
		return dao.findAll();
	}

}
