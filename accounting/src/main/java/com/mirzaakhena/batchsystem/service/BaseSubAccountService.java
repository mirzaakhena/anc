package com.mirzaakhena.batchsystem.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mirzaakhena.batchsystem.dto.AccountCreateDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.SubAccountRelation;

public abstract class BaseSubAccountService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private SubAccountRelationService subAccountRelationService;

	public Account createAccount(Class<?> klazz, String name) throws Exception {
		SubAccountRelation sar = subAccountRelationService.get(klazz);

		AccountCreateDto acDTO = new AccountCreateDto();
		acDTO.setAccountCode(sar.getParentAccount().getCode().concat(".x"));
		acDTO.setName(name);
		return accountService.add(acDTO);
	}

}
