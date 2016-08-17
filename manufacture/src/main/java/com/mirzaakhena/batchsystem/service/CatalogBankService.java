package com.mirzaakhena.batchsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.CatalogBankDao;
import com.mirzaakhena.batchsystem.dto.CatalogBankDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.CatalogBank;

@Service
public class CatalogBankService extends BaseSubAccountService {

	@Autowired
	private CatalogBankDao dao;

	@Transactional(rollbackFor = Exception.class)
	public CatalogBank add(CatalogBankDto dto) throws Exception {

		Account account = createAccount(CatalogBank.class, dto.getName());

		CatalogBank bank = new CatalogBank();
		bank.setAccount(account);
		bank.setAddress(dto.getAddress());
		bank.setAccountNumber(dto.getAccountNumber());

		return dao.save(bank);
	}

	public CatalogBank getById(Long bankId) {
		return dao.findOne(bankId);
	}

	public List<CatalogBank> getAll() {
		return dao.findAll();
	}

}
