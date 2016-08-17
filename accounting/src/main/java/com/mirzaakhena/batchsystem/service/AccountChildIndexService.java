package com.mirzaakhena.batchsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mirzaakhena.batchsystem.dao.AccountChildIndexDao;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.AccountChildIndex;

@Service
public class AccountChildIndexService {

	@Autowired
	private AccountChildIndexDao dao;

	public AccountChildIndex getChildCurrentIndex(Account account) {
		AccountChildIndex aci = dao.findOneByAccountId(account.getId());

		if (aci == null) {
			aci = new AccountChildIndex();
			aci.setAccount(account);
			aci.setChildCount(0);
			aci.setChildCurrentIndex(0);
			dao.save(aci);
		}

		return aci;
	}

	public void incrementIndex(Account account) {

		AccountChildIndex aci = getChildCurrentIndex(account);

		int nextChildIndex = aci.getChildCurrentIndex() + 1;
		
		aci.setChildCurrentIndex(nextChildIndex);
		aci.setChildCount(aci.getChildCount() + 1);
		dao.save(aci);

	}

}
