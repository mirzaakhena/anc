package com.mirzaakhena.batchsystem.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.JournalDao;
import com.mirzaakhena.batchsystem.dto.JournalTransactionDto;
import com.mirzaakhena.batchsystem.dto.TransactionDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.AccountBalance;
import com.mirzaakhena.batchsystem.model.AccountSide;
import com.mirzaakhena.batchsystem.model.AccountType;
import com.mirzaakhena.batchsystem.model.Journal;
import com.mirzaakhena.batchsystem.tools.Tools;

@Service
public class JournalService {

	@Autowired
	private AccountService accountService;

	// @Autowired
	// private InventoryService inventoryService;

	@Autowired
	private AccountBalanceService accountBalanceService;

	@Autowired
	private JournalAccountBalanceService journalAccountBalanceService;

	@Autowired
	private JournalDao dao;

	@Transactional(rollbackFor = Exception.class)
	public Journal add(JournalTransactionDto dto) throws Exception {

		Journal journal = new Journal();

		Date now = new Date();
		journal.setDate(now);
		journal.setDescription(dto.getDescription());

		dao.save(journal);

		BigDecimal sum = BigDecimal.ZERO;

		if (dto.getDescription().trim().length() == 0) {
			throw new Exception("Description still empty");
		}

		if (dto.getTransactions().isEmpty()) {
			throw new Exception("No Transaction");
		}

		StringBuffer sbAct = new StringBuffer();
		StringBuffer sbPsv = new StringBuffer();

		for (TransactionDto tDto : dto.getTransactions()) {

			Account account = accountService.getByCode(tDto.getCode());

			if (account == null) {
				throw new Exception(String.format("Account code %s not found", tDto.getCode()));
			}

			if (account.getAccountType() != AccountType.FINAL_ACCOUNT) {
				throw new Exception(String.format("Account with code %s may have a child", tDto.getCode()));
			}

			BigDecimal accumulation = BigDecimal.ZERO;

			AccountBalance accountBalance = null;

			if (tDto.getAmount().compareTo(BigDecimal.ZERO) == 0) {
				throw new Exception(String.format("amount %d must not equals zero", tDto.getAmount().intValue()));
			}

			accumulation = tDto.getAmount();
			accountBalance = accountBalanceService.add(account, tDto.getAmount(), now);

			if (account.getAccountSide() == AccountSide.ACTIVA && accumulation.compareTo(BigDecimal.ZERO) > 0) {
				sbAct.append("+");
				sbAct.append(accumulation);
				sbAct.append(" ");
			}

			else

			if (account.getAccountSide() == AccountSide.ACTIVA && accumulation.compareTo(BigDecimal.ZERO) < 0) {
				sbAct.append(accumulation);
				sbAct.append(" ");
			}

			else

			if (account.getAccountSide() == AccountSide.PASSIVA && accumulation.compareTo(BigDecimal.ZERO) < 0) {
				sbPsv.append(accumulation);
				sbPsv.append(" ");
			}

			else

			if (account.getAccountSide() == AccountSide.PASSIVA && accumulation.compareTo(BigDecimal.ZERO) > 0) {
				sbPsv.append("+");
				sbPsv.append(accumulation);
				sbPsv.append(" ");
			}

			journalAccountBalanceService.add(journal, accountBalance);


			if (account.getAccountSide() == AccountSide.ACTIVA) {
				sum = sum.add(accumulation);
			}

			else

			if (account.getAccountSide() == AccountSide.PASSIVA) {
				sum = sum.subtract(accumulation);
			}

		}

		if (sum.compareTo(BigDecimal.ZERO) != 0) {

			if (sbAct.toString().isEmpty()) {
				sbAct.append("0 ");
			}

			else

			if (sbPsv.toString().isEmpty()) {
				sbPsv.append("0");
			}

			throw new Exception(String.format("activa and passiva is not balance %s= %s", sbAct.toString(), sbPsv.toString()));
		}
		
		return journal;

	}

	public List<Journal> getAll(String date1, String date2) throws Exception {

		Date d1 = Tools.DDMMYYYY.parse(date1);
		Date d2 = Tools.DDMMYYYY.parse(date2);

		List<Journal> listJt;

		if (d1.before(d2)) {
			listJt = dao.findByDate(Tools.YYYYMMDD.format(d1), Tools.YYYYMMDD.format(d2));
		} else {
			listJt = dao.findByDate(Tools.YYYYMMDD.format(d2), Tools.YYYYMMDD.format(d1));
		}

		// for (Journal journal : listJt) {
		// journal.getListJournalAccountBalance().sort((x, y) -> {
		// if (x.getAccountBalance().getDebit() != null &&
		// y.getAccountBalance().getDebit() == null) {
		// return -1;
		// }
		// return
		// x.getAccountBalance().getAccount().getCode().compareTo(y.getAccountBalance().getAccount().getCode());
		// });
		// }

		return listJt;
	}

	public Journal getById(Long id) {
		return dao.findOne(id);
	}

}
