package com.mirzaakhena.batchsystem.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.AccountPayableDao;
import com.mirzaakhena.batchsystem.dto.AccountPayableDto;
import com.mirzaakhena.batchsystem.dto.JournalTransactionDto;
import com.mirzaakhena.batchsystem.dto.PaymentAccountPayableDto;
import com.mirzaakhena.batchsystem.dto.TransactionDto;
import com.mirzaakhena.batchsystem.model.AccountPayable;
import com.mirzaakhena.batchsystem.model.CatalogBank;
import com.mirzaakhena.batchsystem.model.CatalogCash;
import com.mirzaakhena.batchsystem.model.Journal;
import com.mirzaakhena.batchsystem.model.Supplier;
import com.mirzaakhena.batchsystem.tools.Tools;

@Service
public class AccountPayableService {

	@Autowired
	private AccountPayableDao dao;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private JournalService journalService;

	@Autowired
	private SubAccountRelationService subAccountRelationService;

	@Transactional(rollbackFor = Exception.class)
	public AccountPayable add(AccountPayableDto dto) throws Exception {

		Journal journal = journalService.getById(dto.getJournalId());
		if (journal == null) {
			throw new Exception("Journal with id " + dto.getJournalId() + " not found");
		}

		Supplier supplier = supplierService.getByCode(dto.getCode());
		if (supplier == null) {
			throw new Exception("Supplier not found");
		}

		Date date = Tools.DDMMYYYY.parse(dto.getPaymentDate());
		if (date.before(new Date())) {
			throw new Exception("Waktu pembayaran berikutnya harus lebih dari hari ini");
		}

		AccountPayable ap = new AccountPayable();
		ap.setSupplier(supplier);
		ap.setStartDate(journal.getDate());
		ap.setExpectationDate(date);
		ap.setJournal(journal);
		ap.setAmount(dto.getAmount());

		return dao.save(ap);
	}

	private AccountPayable add(Journal journal, Supplier supplier, Date date, BigDecimal amount) throws Exception {

		AccountPayable ap = new AccountPayable();
		ap.setSupplier(supplier);
		ap.setStartDate(journal.getDate());
		ap.setExpectationDate(date);
		ap.setJournal(journal);
		ap.setAmount(amount);

		return dao.save(ap);
	}

	@Transactional(rollbackFor = Exception.class)
	public void pay(PaymentAccountPayableDto pdto) throws Exception {

		AccountPayable lastAP = dao.findUnpaid(pdto.getId());

		BigDecimal total = BigDecimal.ZERO;

		JournalTransactionDto jDto = new JournalTransactionDto();

		jDto.setDescription("Pembayaran Utang");

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

					listTDto.add(new TransactionDto(code, payment.negate()));
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

					listTDto.add(new TransactionDto(code, payment.negate()));
				}
			}
		}

		if (total.compareTo(lastAP.getAmount()) > 0) {
			throw new Exception("nilai pembayaran harus lebih kecil atau sama dengan jumlah utang");
		}

		String code = lastAP.getSupplier().getAccount().getCode();

		// UTANG
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

			add(journal, lastAP.getSupplier(), date, newAmount);

		}

		lastAP.setActualDate(new Date());
		dao.save(lastAP);

	}

	public List<AccountPayable> getAll() {
		return dao.findAll();
	}

}
