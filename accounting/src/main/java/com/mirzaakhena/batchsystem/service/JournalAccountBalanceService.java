package com.mirzaakhena.batchsystem.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.JournalAccountBalanceDao;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.AccountBalance;
import com.mirzaakhena.batchsystem.model.JournalAccountBalance;
import com.mirzaakhena.batchsystem.model.Journal;
import com.mirzaakhena.batchsystem.tools.Tools;

@Service
public class JournalAccountBalanceService {

	@Autowired
	private JournalAccountBalanceDao dao;

	@Autowired
	private AccountService accountService;

	@Transactional
	public JournalAccountBalance add(Journal journal, AccountBalance accountBalance) {
		JournalAccountBalance generalLedger = new JournalAccountBalance();
		generalLedger.setAccountBalance(accountBalance);
		generalLedger.setJournal(journal);
		return dao.save(generalLedger);
	}

	public List<JournalAccountBalance> getAll(Long accountId, String from, String until) throws Exception {

		Date d1 = Tools.DDMMYYYY.parse(from);
		Date d2 = Tools.DDMMYYYY.parse(until);

		if (d1.after(d2)) {
			Date t = d1;
			d1 = d2;
			d2 = t;
		}

		return dao.findAll(accountId, Tools.YYYYMMDD.format(d1), Tools.YYYYMMDD.format(d2));

	}

	public List<JournalAccountBalance> getAll(String from, String until) throws Exception {

		Date d1 = Tools.DDMMYYYY.parse(from);
		Date d2 = Tools.DDMMYYYY.parse(until);

		if (d1.after(d2)) {
			Date t = d1;
			d1 = d2;
			d2 = t;
		}

		return dao.findAll(Tools.YYYYMMDD.format(d1), Tools.YYYYMMDD.format(d2));

	}
	
	public List<JournalAccountBalance> getAllAfter(Date date) throws Exception {
		return dao.findAllAfter(Tools.YYYYMMDD.format(date));
	}

	public List<JournalAccountBalance> getChild(Long accountId) {

		List<JournalAccountBalance> listJAB = new ArrayList<>();

		List<Account> listAccount = accountService.getChild(accountId);
		for (Account account : listAccount) {
			List<JournalAccountBalance> list = dao.findLastBefore(account.getId(), Tools.YYYYMMDD.format(new Date()), new PageRequest(0, 1));
			if (list == null || list.isEmpty()) {
				// mau ditampilkan juga kah?
			} else {
				listJAB.add(list.get(0));
			}
		}
		return listJAB;

	}

}
