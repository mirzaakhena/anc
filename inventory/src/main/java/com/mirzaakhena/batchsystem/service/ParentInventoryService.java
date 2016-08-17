package com.mirzaakhena.batchsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.ParentInventoryDao;
import com.mirzaakhena.batchsystem.dto.ParentInventoryDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.AccountSide;
import com.mirzaakhena.batchsystem.model.AccountType;
import com.mirzaakhena.batchsystem.model.ParentInventory;

@Service
public class ParentInventoryService {

	@Autowired
	private ParentInventoryDao dao;

	@Autowired
	private AccountService accountService;

	@Transactional
	public ParentInventory add(ParentInventoryDto dto) throws Exception {

		Account account = accountService.getByCode(dto.getAccountCode());
		if (account.getAccountType() != AccountType.CHILD_AS_SUBACCOUNT) {
			throw new Exception("Account is not mark as CHILD_AS_SUBACCOUNT");
		}

		if (account.getAccountSide() == AccountSide.PASSIVA) {
			throw new Exception("Account must in ACTIVA side");
		}

		ParentInventory inv = new ParentInventory();
		inv.setAccount(account);
		return dao.save(inv);
	}

	public ParentInventory getById(Long inventoryId) {
		return dao.findOne(inventoryId);
	}

	public List<ParentInventory> getAll() {
		return dao.findAll();
	}

}
