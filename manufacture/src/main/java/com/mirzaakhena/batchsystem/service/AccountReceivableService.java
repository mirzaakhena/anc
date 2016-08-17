package com.mirzaakhena.batchsystem.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.AccountReceivableDao;
import com.mirzaakhena.batchsystem.dto.AccountReceivableDto;
import com.mirzaakhena.batchsystem.dto.JournalTransactionDto;
import com.mirzaakhena.batchsystem.dto.PaymentAccountReceivableDto;
import com.mirzaakhena.batchsystem.dto.TransactionDto;
import com.mirzaakhena.batchsystem.model.AccountReceivable;
import com.mirzaakhena.batchsystem.model.CatalogBank;
import com.mirzaakhena.batchsystem.model.CatalogCash;
import com.mirzaakhena.batchsystem.model.Customer;
import com.mirzaakhena.batchsystem.model.Journal;
import com.mirzaakhena.batchsystem.tools.Tools;

@Service
public class AccountReceivableService {

	@Autowired
	private AccountReceivableDao dao;

	@Autowired
	private CustomerService supplierService;

	@Autowired
	private JournalService journalService;

	@Autowired
	private SubAccountRelationService subAccountRelationService;

	@Transactional(rollbackFor = Exception.class)
	public AccountReceivable add(AccountReceivableDto dto) throws Exception {

		Journal journal = journalService.getById(dto.getJournalId());
		if (journal == null) {
			throw new Exception("Journal with id " + dto.getJournalId() + " not found");
		}

		Customer customer = supplierService.getByCode(dto.getCode());
		if (customer == null) {
			throw new Exception("Customer not found");
		}

		Date date = Tools.DDMMYYYY.parse(dto.getPaymentDate());
		if (date.before(new Date())) {
			throw new Exception("Waktu pembayaran berikutnya harus lebih dari hari ini");
		}

		AccountReceivable ap = new AccountReceivable();
		ap.setCustomer(customer);
		ap.setStartDate(journal.getDate());
		ap.setExpectationDate(date);
		ap.setJournal(journal);
		ap.setAmount(dto.getAmount());

		return dao.save(ap);
	}

	private AccountReceivable add(Journal journal, Customer customer, Date date, BigDecimal amount) throws Exception {

		AccountReceivable ap = new AccountReceivable();
		ap.setCustomer(customer);
		ap.setStartDate(journal.getDate());
		ap.setExpectationDate(date);
		ap.setJournal(journal);
		ap.setAmount(amount);

		return dao.save(ap);
	}

	@Transactional(rollbackFor = Exception.class)
	public void pay(PaymentAccountReceivableDto pdto) throws Exception {

		AccountReceivable lastAP = dao.findUnpaid(pdto.getId());

		BigDecimal total = BigDecimal.ZERO;

		JournalTransactionDto jDto = new JournalTransactionDto();

		jDto.setDescription("Pembayaran Piutang");

		List<TransactionDto> listTDto = new ArrayList<>();
		jDto.setTransactions(listTDto);

		// Kas
		{
			String code = pdto.getCash().getCode();
			if (code != null) {
				if (!subAccountRelationService.isSubAccountOf(code, CatalogCash.class)) {
					throw new Exception("account with code " + code + " is not cash account");
				}

				if (pdto.getCash().getAmount().compareTo(BigDecimal.ZERO) < 0) {
					throw new Exception("cash amount must > 0");
				}

				if (pdto.getCash().getAmount().compareTo(BigDecimal.ZERO) > 0) {

					BigDecimal payment = pdto.getCash().getAmount();
					total = total.add(payment);

					listTDto.add(new TransactionDto(code, payment));
				}
			}
		}

		// Bank
		{
			String code = pdto.getBank().getCode();
			if (code != null) {
				if (!subAccountRelationService.isSubAccountOf(code, CatalogBank.class)) {
					throw new Exception("account with code " + code + " is not bank account");
				}

				if (pdto.getBank().getAmount().compareTo(BigDecimal.ZERO) < 0) {
					throw new Exception("bank amount must > 0");
				}

				if (pdto.getBank().getAmount().compareTo(BigDecimal.ZERO) > 0) {

					BigDecimal payment = pdto.getBank().getAmount();
					total = total.add(pdto.getBank().getAmount());

					listTDto.add(new TransactionDto(code, payment));
				}
			}
		}

		if (total.compareTo(lastAP.getAmount()) > 0) {
			throw new Exception("nilai pembayaran harus lebih kecil atau sama dengan jumlah piutang");
		}

		String code = lastAP.getCustomer().getAccount().getCode();

		// PIUTANG
		{
			listTDto.add(new TransactionDto(code, total.negate()));
		}

		Journal journal = journalService.add(jDto);

		BigDecimal newAmount = lastAP.getAmount().subtract(total);
		if (newAmount.compareTo(BigDecimal.ZERO) > 0) {

			Date date = Tools.DDMMYYYY.parse(pdto.getNextPaymentDate());

			if (date.before(new Date())) {
				throw new Exception("Waktu pembayaran berikutnya harus lebih dari hari ini");
			}

			add(journal, lastAP.getCustomer(), date, newAmount);

		}

		lastAP.setActualDate(new Date());
		dao.save(lastAP);

	}

	public List<AccountReceivable> getAll() {
		return dao.findAll();
	}

}
