package com.mirzaakhena.batchsystem.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.SaleDao;
import com.mirzaakhena.batchsystem.dto.JournalTransactionDto;
import com.mirzaakhena.batchsystem.dto.SaleFinishedGoodsDto;
import com.mirzaakhena.batchsystem.dto.SaleFinishedGoodsItemDto;
import com.mirzaakhena.batchsystem.dto.TransactionDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.CatalogBank;
import com.mirzaakhena.batchsystem.model.CatalogCash;
import com.mirzaakhena.batchsystem.model.Customer;
import com.mirzaakhena.batchsystem.model.FinishedGoods;
import com.mirzaakhena.batchsystem.model.HPP;
import com.mirzaakhena.batchsystem.model.InventoryBalance;
import com.mirzaakhena.batchsystem.model.Journal;
import com.mirzaakhena.batchsystem.model.Pendapatan;
import com.mirzaakhena.batchsystem.model.Sale;
import com.mirzaakhena.batchsystem.model.SubAccountRelation;
import com.mirzaakhena.batchsystem.service.AccountService;
import com.mirzaakhena.batchsystem.service.InventoryBalanceService;
import com.mirzaakhena.batchsystem.service.ParentInventoryService;
import com.mirzaakhena.batchsystem.service.JournalService;
import com.mirzaakhena.batchsystem.service.SubAccountRelationService;

@Service
public class SaleService {

	@Autowired
	private SaleDao dao;
	
	@Autowired
	private AccountReceivableService accountReceivableService;
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private FinishedGoodsService finishGoodsService;

	@Autowired
	private CatalogBankService catalogBankService;

	@Autowired
	private CatalogCashService catalogCashService;

	@Autowired
	private JournalService journalService;

	@Autowired
	private SubAccountRelationService subAccountRelationService;

	@Autowired
	private ParentInventoryService inventoryService;

	@Autowired
	private InventoryBalanceService inventoryBalanceService;

	@Transactional(rollbackFor = Exception.class)
	public Sale sale(SaleFinishedGoodsDto dto) throws Exception {

		// finish goods -?
		// kas +?
		// bank +?
		// piutang +?
		// pendapatan +?
		// hpp +?

		JournalTransactionDto jDto = new JournalTransactionDto();

		jDto.setDescription("Penjualan Finish Goods. " + dto.getExtraDescription());

		List<TransactionDto> listTDto = new ArrayList<>();
		jDto.setTransactions(listTDto);

		BigDecimal hpp = BigDecimal.ZERO;
		BigDecimal pendapatan = BigDecimal.ZERO;

		// Finish Goods
		for (SaleFinishedGoodsItemDto trDto : dto.getListFinishedGoods()) {
			String code = trDto.getCode();
			if (!subAccountRelationService.isSubAccountOf(code, FinishedGoods.class)) {
				throw new Exception("account with code " + code + " is not finish goods");
			}

			if (trDto.getQuantity() <= 0) {
				throw new Exception("quantity of " + trDto.getCode() + " must > 0");
			}

			Account account = accountService.getByCode(code);
			InventoryBalance inv = inventoryBalanceService.getLastInventory(account);
			if (inv == null) {
				throw new Exception("inventory of " + account.getName() + " is not enough");
			}

			FinishedGoods finishGoods = finishGoodsService.getByCode(code);

			BigDecimal qty = new BigDecimal(trDto.getQuantity());
			BigDecimal totalPrice = inv.getBalancePrice().multiply(qty);
			hpp = hpp.add(totalPrice);
			pendapatan = pendapatan.add(finishGoods.getSalePrice().multiply(qty));

			listTDto.add(new TransactionDto(code, totalPrice.negate()));
		}

		// HPP
		{
			SubAccountRelation sar = subAccountRelationService.get(HPP.class);
			Account account = sar.getParentAccount();

			if (hpp.compareTo(BigDecimal.ZERO) <= 0) {
				throw new Exception("hpp amount must > 0");
			}

			listTDto.add(new TransactionDto(account.getCode(), hpp));

		}

		// PENDAPATAN
		{
			SubAccountRelation sar = subAccountRelationService.get(Pendapatan.class);
			Account account = sar.getParentAccount();

			if (pendapatan.compareTo(BigDecimal.ZERO) <= 0) {
				throw new Exception("pendapatan amount must > 0");
			}

			listTDto.add(new TransactionDto(account.getCode(), pendapatan));

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
					listTDto.add(new TransactionDto(code, dto.getCash().getAmount()));
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
					listTDto.add(new TransactionDto(code, dto.getBank().getAmount()));
				}

			}
		}

		Customer customer = null;
		BigDecimal totalPiutang = BigDecimal.ZERO;
		
		// Piutang
		{
			String code = dto.getAccountReceivable().getCode();

			if (code != null) {

				if (!subAccountRelationService.isSubAccountOf(code, Customer.class)) {
					throw new Exception("account with code " + code + " is not customer account");
				}

				BigDecimal amount = pendapatan.subtract(dto.getBank().getAmount().add(dto.getCash().getAmount()));

				if (amount.compareTo(BigDecimal.ZERO) < 0) {
					throw new Exception("account receivable amount must > 0");
				}

				if (amount.compareTo(BigDecimal.ZERO) > 0) {
					totalPiutang = amount;
					listTDto.add(new TransactionDto(code, amount));
				}
			}
			
			customer = customerService.getByCode(code);
		}

		Journal journal = journalService.add(jDto);

		for (SaleFinishedGoodsItemDto trDto : dto.getListFinishedGoods()) {
			Account account = accountService.getByCode(trDto.getCode());
			inventoryBalanceService.out(journal, account, journal.getDate(), trDto.getQuantity());
		}
		
		if (customer != null) {
			dto.getAccountReceivable().setJournalId(journal.getId());
			dto.getAccountReceivable().setAmount(totalPiutang);
			accountReceivableService.add(dto.getAccountReceivable());
		}
		
		Sale sale = new Sale();		
		sale.setJournal(journal);
		sale.setAmount(pendapatan);
		sale.setCustomer(customer);
		
		return dao.save(sale);

	}
	
	public List<Sale> getAll() {
		return dao.findAll();
	}
}
