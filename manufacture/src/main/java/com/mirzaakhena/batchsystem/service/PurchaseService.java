package com.mirzaakhena.batchsystem.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.PurchaseDao;
import com.mirzaakhena.batchsystem.dto.JournalTransactionDto;
import com.mirzaakhena.batchsystem.dto.PurchaseRawMaterialDto;
import com.mirzaakhena.batchsystem.dto.PurchaseRawMaterialItemDto;
import com.mirzaakhena.batchsystem.dto.TransactionDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.CatalogBank;
import com.mirzaakhena.batchsystem.model.CatalogCash;
import com.mirzaakhena.batchsystem.model.Journal;
import com.mirzaakhena.batchsystem.model.Purchase;
import com.mirzaakhena.batchsystem.model.RawMaterial;
import com.mirzaakhena.batchsystem.model.Supplier;

@Service
public class PurchaseService {

	@Autowired
	private PurchaseDao dao;

	@Autowired
	private AccountPayableService accountPayableService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private RawMaterialService rawMaterialService;

	@Autowired
	private CatalogBankService catalogBankService;

	@Autowired
	private CatalogCashService catalogCashService;

	@Autowired
	private JournalService journalService;

	@Autowired
	private InventoryBalanceService inventoryBalanceService;

	@Autowired
	private ParentInventoryService inventoryService;

	@Autowired
	private SubAccountRelationService subAccountRelationService;

	@Transactional(rollbackFor = Exception.class)
	public Purchase purchase(PurchaseRawMaterialDto dto) throws Exception {

		// raw material +?
		// kas -?
		// bank -?
		// utang +?

		JournalTransactionDto jDto = new JournalTransactionDto();

		jDto.setDescription("Pembelian Raw Material. " + dto.getExtraDescription());

		List<TransactionDto> listTDto = new ArrayList<>();
		jDto.setTransactions(listTDto);

		BigDecimal totalPembelian = BigDecimal.ZERO;

		// Raw Material
		for (PurchaseRawMaterialItemDto trDto : dto.getListRawMaterial()) {

			String code = trDto.getCode();

			if (!subAccountRelationService.isSubAccountOf(code, RawMaterial.class)) {
				throw new Exception("account with code " + code + " is not raw material");
			}

			if (trDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
				throw new Exception("raw material amount must > 0");
			}

			BigDecimal totalPrice = trDto.getPrice().multiply(new BigDecimal(trDto.getQuantity()));

			totalPembelian = totalPembelian.add(totalPrice);
			listTDto.add(new TransactionDto(code, totalPrice));

		}

		// Kas
		{
			String code = dto.getCash().getCode();

			if (code != null) {

				if (!subAccountRelationService.isSubAccountOf(code, CatalogCash.class)) {
					throw new Exception("account with code " + code + " is not cash account");
				}

				if (dto.getCash().getAmount().compareTo(BigDecimal.ZERO) < 0) {
					throw new Exception("cash amount must > 0");
				}

				if (dto.getCash().getAmount().compareTo(BigDecimal.ZERO) > 0) {
					listTDto.add(new TransactionDto(code, dto.getCash().getAmount().negate()));
				}

			}
		}

		// Bank
		{
			String code = dto.getBank().getCode();

			if (code != null) {

				if (!subAccountRelationService.isSubAccountOf(code, CatalogBank.class)) {
					throw new Exception("account with code " + code + " is not bank account");
				}

				if (dto.getBank().getAmount().compareTo(BigDecimal.ZERO) < 0) {
					throw new Exception("bank amount must > 0");
				}

				if (dto.getBank().getAmount().compareTo(BigDecimal.ZERO) > 0) {
					listTDto.add(new TransactionDto(code, dto.getBank().getAmount().negate()));
				}

			}
		}

		Supplier supplier = null;
		BigDecimal totalUtang = BigDecimal.ZERO;
		
		// Utang
		{
			String code = dto.getAccountPayable().getCode();

			if (code != null) {

				if (!subAccountRelationService.isSubAccountOf(code, Supplier.class)) {
					throw new Exception("account with code " + code + " is not supplier account");
				}

				BigDecimal amount = totalPembelian.subtract(dto.getBank().getAmount().add(dto.getCash().getAmount()));

				if (amount.compareTo(BigDecimal.ZERO) < 0) {
					throw new Exception("account payable amount must > 0");
				}

				if (amount.compareTo(BigDecimal.ZERO) > 0) {
					totalUtang = amount;
					listTDto.add(new TransactionDto(code, amount));
				}

				supplier = supplierService.getByCode(code);

			}
		}

		Journal journal = journalService.add(jDto);

		for (PurchaseRawMaterialItemDto trDto : dto.getListRawMaterial()) {
			Account account = accountService.getByCode(trDto.getCode());
			inventoryBalanceService.in(journal, account, journal.getDate(), trDto.getPrice(), trDto.getQuantity());
		}

		if (supplier != null) {
			dto.getAccountPayable().setJournalId(journal.getId());
			dto.getAccountPayable().setAmount(totalUtang);
			accountPayableService.add(dto.getAccountPayable());
		}

		Purchase purchase = new Purchase();
		purchase.setJournal(journal);
		purchase.setAmount(totalPembelian);
		purchase.setSupplier(supplier);
		
		return dao.save(purchase);

	}

	public List<Purchase> getAll() {
		return dao.findAll();
	}

}
