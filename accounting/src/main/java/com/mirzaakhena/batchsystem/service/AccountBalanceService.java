package com.mirzaakhena.batchsystem.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.AccountBalanceDao;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.AccountBalance;
import com.mirzaakhena.batchsystem.tools.Tools;

@Service
public class AccountBalanceService {

	@Autowired
	private AccountBalanceDao dao;

	@Autowired
	private AccountService accountService;

	public AccountBalance getLastAccountBalanceBefore(Long accountId, Date beforeDate) {
		List<AccountBalance> accountBalances = dao.findLastAccountBalanceBefore(accountId, Tools.YYYYMMDD.format(beforeDate), new PageRequest(0, 1));
		return accountBalances == null || accountBalances.isEmpty() ? null : accountBalances.get(0);
	}

	private AccountBalance updateAccountBalance(Account account, BigDecimal amount, Date date, boolean accumulate) {

		AccountBalance newAccountBalance;
		AccountBalance lastAccountBalance;

		lastAccountBalance = getLastAccountBalanceBefore(account.getId(), date);

		if (lastAccountBalance == null) {
			System.out.println(account.getId() + " " + account.getName() + "(" + account.getCode() + ")" + " data pertama " + amount);
			newAccountBalance = new AccountBalance();
			newAccountBalance.setDate(date);
			newAccountBalance.setAccount(account);
			newAccountBalance.setAmount(amount);
			newAccountBalance.setBalance(amount);
		} else {

			String lastDate = Tools.YYYYMMDD.format(lastAccountBalance.getDate());
			String currentDate = Tools.YYYYMMDD.format(date);

			if (accumulate && lastDate.equals(currentDate)) {

				System.out.println(account.getId() + " " + account.getName() + "(" + account.getCode() + ")" + " akumulasi " + amount);

				newAccountBalance = lastAccountBalance;
				newAccountBalance.setAmount(lastAccountBalance.getAmount().add(amount));
				newAccountBalance.setBalance(lastAccountBalance.getBalance().add(amount));
			} else {

				System.out.println(account.getId() + " " + account.getName() + "(" + account.getCode() + ")" + " data baru " + amount);

				newAccountBalance = new AccountBalance();
				newAccountBalance.setDate(date);
				newAccountBalance.setAccount(account);
				newAccountBalance.setAmount(amount);
				newAccountBalance.setBalance(lastAccountBalance.getBalance().add(amount));
			}

		}

		return dao.save(newAccountBalance);

	}

	@Transactional
	public AccountBalance add(Account account, BigDecimal amount, Date date) throws Exception {
		
		AccountBalance ab = updateAccountBalance(account, amount, date, false);

		Account accountParent = account.getAccountParent();
		while (accountParent != null) {
			updateAccountBalance(accountParent, amount, date, true);
			accountParent = accountParent.getAccountParent();
		}

		return ab;
	}

//	@Transactional
//	public AccountBalance in(Account account, BigDecimal amount, Date date) throws Exception {
//		
//		if (amount.compareTo(BigDecimal.ZERO) < 0) {
//			throw new Exception("amount must > 0");
//		}
//		
//		AccountBalance ab = updateAccountBalance(account, amount, date, false);
//
//		Account accountParent = account.getAccountParent();
//		while (accountParent != null) {
//			updateAccountBalance(accountParent, amount, date, true);
//			accountParent = accountParent.getAccountParent();
//		}
//
//		return ab;
//	}
//	
//	@Transactional
//	public AccountBalance out(Account account, BigDecimal amount, Date date) throws Exception {
//
//		if (amount.compareTo(BigDecimal.ZERO) < 0) {
//			throw new Exception("amount must > 0");
//		}
//
//		AccountBalance ab = updateAccountBalance(account, amount.negate(), date, false);
//
//		Account accountParent = account.getAccountParent();
//		while (accountParent != null) {
//			updateAccountBalance(accountParent, amount.negate(), date, true);
//			accountParent = accountParent.getAccountParent();
//		}
//
//		return ab;
//	}

	public List<AccountBalance> getAll(Long accountId, String from, String until) throws Exception {

		Date d1 = Tools.DDMMYYYY.parse(from);
		Date d2 = Tools.DDMMYYYY.parse(until);

		if (d1.after(d2)) {
			Date t = d1;
			d1 = d2;
			d2 = t;
		}

		return dao.findByAccountAndBeforeDate(accountId, Tools.YYYYMMDD.format(d1), Tools.YYYYMMDD.format(d2));
	}

	public List<AccountBalance> getAll(String date) throws Exception {
		Date date_;
		if (date == null || date.trim().length() == 0) {
			date_ = new Date();
		} else {
			date_ = Tools.DDMMYYYY.parse(date);
		}

		List<AccountBalance> listAB = new ArrayList<>();

		for (Account account : accountService.getAll(3)) {
			AccountBalance ab = getLastAccountBalanceBefore(account.getId(), date_);
			if (ab == null) {
				AccountBalance emptyAb = new AccountBalance();
				emptyAb.setAccount(account);
				emptyAb.setDate(date_);
				emptyAb.setAmount(BigDecimal.ZERO);
				listAB.add(emptyAb);
			} else {
				listAB.add(ab);
			}
		}

		return listAB;
	}

	public List<AccountBalance> getChild(Long accountId, String date) throws Exception {

		Date date_;
		if (date == null || date.trim().length() == 0) {
			date_ = new Date();
		} else {
			date_ = Tools.DDMMYYYY.parse(date);
		}

		List<AccountBalance> listAB = new ArrayList<>();

		for (Account account : accountService.getChild(accountId)) {
			AccountBalance ab = getLastAccountBalanceBefore(account.getId(), date_);
			if (ab == null) {
				AccountBalance emptyAb = new AccountBalance();
				emptyAb.setAccount(account);
				emptyAb.setDate(date_);
				emptyAb.setAmount(BigDecimal.ZERO);
				listAB.add(emptyAb);
			} else {
				listAB.add(ab);
			}
		}

		return listAB;
	}

	public AccountBalance getAll(Long accountId) {
		return getLastAccountBalanceBefore(accountId, new Date());
	}

}
