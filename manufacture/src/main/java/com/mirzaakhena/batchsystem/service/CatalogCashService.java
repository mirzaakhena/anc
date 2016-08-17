package com.mirzaakhena.batchsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.CatalogCashDao;
import com.mirzaakhena.batchsystem.dto.CatalogCashDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.CatalogCash;

@Service
public class CatalogCashService extends BaseSubAccountService {

	@Autowired
	private CatalogCashDao dao;

	@Transactional(rollbackFor = Exception.class)
	public CatalogCash add(CatalogCashDto dto) throws Exception {

		Account account = createAccount(CatalogCash.class, dto.getName());

		CatalogCash cash = new CatalogCash();
		cash.setAccount(account);

		return dao.save(cash);
	}

	public CatalogCash getById(Long cashId) {
		return dao.findOne(cashId);
	}

	public List<CatalogCash> getAll() {
		return dao.findAll();
	}

}
