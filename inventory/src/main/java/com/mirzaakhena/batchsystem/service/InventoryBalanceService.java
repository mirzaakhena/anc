package com.mirzaakhena.batchsystem.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mirzaakhena.batchsystem.dao.InventoryBalanceDao;
import com.mirzaakhena.batchsystem.dto.InventoryBalanceDto;
import com.mirzaakhena.batchsystem.dto.InventoryBalanceInputDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.AccountPosition;
import com.mirzaakhena.batchsystem.model.ParentInventory;
import com.mirzaakhena.batchsystem.model.InventoryBalance;
import com.mirzaakhena.batchsystem.model.Journal;
import com.mirzaakhena.batchsystem.model.JournalAccountBalance;
import com.mirzaakhena.batchsystem.model.JournalAccountSign;
import com.mirzaakhena.batchsystem.model.JournalInventory;
import com.mirzaakhena.batchsystem.tools.Tools;

@Service
public class InventoryBalanceService {

	@Autowired
	private ParentInventoryService parentInventoryService;

	@Autowired
	protected InventoryBalanceDao dao;

	@Autowired
	protected AccountService accountService;

	@Autowired
	private JournalService journalService;

	@Autowired
	private JournalAccountBalanceService journalAccountBalanceService;

	public InventoryBalance getLastInventory(Long accountId, Date date) {
		List<InventoryBalance> inventories = dao.findLastInventory(accountId, Tools.YYYYMMDD.format(date), new PageRequest(0, 1));
		return inventories == null || inventories.isEmpty() ? null : inventories.get(0);
	}

	public InventoryBalance in(Journal journal, Account account, Date date, BigDecimal price, double quantity) throws Exception {

		if (quantity < 0) {
			throw new Exception("quantity must > 0");
		}

		if (parentInventoryService.getById(account.getAccountParent().getId()) == null) {
			throw new Exception("account " + account.getCode() + " is not inventory");
		}
		
		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new Exception("price must > 0");
		}

		BigDecimal lastBalanceTotalPrice = BigDecimal.ZERO;
		double lastBalanceQuantity = 0D;
		InventoryBalance lastIS = getLastInventory(account.getId(), new Date());
		if (lastIS != null) {
			lastBalanceTotalPrice = lastIS.getBalanceTotalPrice();
			lastBalanceQuantity = lastIS.getBalanceQuantity();
		}

		InventoryBalance is = new InventoryBalance();
		is.setAccount(account);
		is.setJournal(journal);
		is.setDate(date);

		is.setAmountQuantity(quantity);
		is.setAmountPrice(price);
		is.setAmountTotalPrice(is.getAmountPrice().multiply(new BigDecimal(is.getAmountQuantity())));

		is.setBalanceQuantity(lastBalanceQuantity + is.getAmountQuantity());
		is.setBalanceTotalPrice(lastBalanceTotalPrice.add(is.getAmountTotalPrice()));
		is.setBalancePrice(is.getBalanceTotalPrice().divide(new BigDecimal(is.getBalanceQuantity()), 2, RoundingMode.HALF_DOWN));

		return dao.save(is);
	}

	public InventoryBalance out(Journal journal, Account account,Date date, double quantity) throws Exception {

		if (quantity < 0) {
			throw new Exception("quantity must > 0");
		}
		
		if (parentInventoryService.getById(account.getAccountParent().getId()) == null) {
			throw new Exception("account " + account.getCode() + " is not inventory");
		}

		BigDecimal lastBalanceTotalPrice = BigDecimal.ZERO;
		double lastBalanceQuantity = 0D;
		InventoryBalance lastIS = getLastInventory(account.getId(), new Date());
		if (lastIS == null || quantity > lastIS.getBalanceQuantity()) {
			throw new Exception("unavailable inventory of " + account.getName());
		} else {
			lastBalanceTotalPrice = lastIS.getBalanceTotalPrice();
			lastBalanceQuantity = lastIS.getBalanceQuantity();
		}

		InventoryBalance is = new InventoryBalance();
		is.setAccount(account);
		is.setJournal(journal);
		is.setDate(date);

		is.setAmountQuantity(-quantity);
		is.setAmountPrice(lastIS.getBalancePrice());
		is.setAmountTotalPrice(is.getAmountPrice().multiply(new BigDecimal(is.getAmountQuantity())));

		is.setBalanceQuantity(lastBalanceQuantity + is.getAmountQuantity());
		is.setBalanceTotalPrice(lastBalanceTotalPrice.add(is.getAmountTotalPrice()));

		if (is.getBalanceQuantity() == 0) {
			is.setBalancePrice(BigDecimal.ZERO);
		} else {
			is.setBalancePrice(is.getBalanceTotalPrice().divide(new BigDecimal(is.getBalanceQuantity()), 2, RoundingMode.HALF_DOWN));
		}

		return dao.save(is);

	}

	public List<InventoryBalance> getAll(Long accountId, String from, String until) throws Exception {

		Date d1 = Tools.DDMMYYYY.parse(from);
		Date d2 = Tools.DDMMYYYY.parse(until);

		if (d1.after(d2)) {
			Date t = d1;
			d1 = d2;
			d2 = t;
		}

		return dao.findAll(accountId, Tools.YYYYMMDD.format(d1), Tools.YYYYMMDD.format(d2));
	}

	public List<InventoryBalance> getChilds(Long accountId, String date) throws Exception {

		Date date_;
		if (date == null || date.trim().length() == 0) {
			date_ = new Date();
		} else {
			date_ = Tools.DDMMYYYY.parse(date);
		}

		List<InventoryBalance> listAB = new ArrayList<>();

		for (Account account : accountService.getChild(accountId)) {
			InventoryBalance ab = getLastInventory(account.getId(), date_);
			if (ab == null) {
				InventoryBalance inv = new InventoryBalance();
				Journal journal = new Journal();
				journal.setDate(date_);
				journal.setDescription("");
				inv.setAccount(account);
				inv.setJournal(journal);
				listAB.add(inv);
			} else {
				listAB.add(ab);
			}
		}

		return listAB;

	}

	public InventoryBalance getLastInventory(Account account) throws Exception {
		return getLastInventory(account.getId(), new Date());
	}

//	@Transactional
//	public void addFromJournal(Long journalId, String accountCode, double quantity) throws Exception {
//
//		Account account = accountService.getByCode(accountCode);
//		if (account == null) {
//			throw new Exception("account code not found");
//		}
//
//		Inventory inventory = inventoryService.getById(account.getAccountParent().getId());
//		if (inventory == null) {
//			throw new Exception("account is not registered as inventory");
//		}
//
//		Journal journal = journalService.getById(journalId);
//		if (journal == null) {
//			throw new Exception("Journal not found");
//		}
//
//		boolean ketemu = false;
//		BigDecimal price = BigDecimal.ZERO;
//		for (JournalAccountBalance jab : journal.getListJournalAccountBalance()) {
//			if (jab.getAccountBalance().getAccount().getCode().equals(accountCode)) {
//				if (jab.getAccountBalance().getAmount().compareTo(BigDecimal.ZERO) > 0) {
//					price = jab.getAccountBalance().getAmount().divide(new BigDecimal(quantity));
//				}
//				ketemu = true;
//				break;
//			}
//		}
//
//		if (!ketemu) {
//			throw new Exception("inventory not found in journal with id " + journalId);
//		}
//
//		if (price.compareTo(BigDecimal.ZERO) == 0) {
//			out(journal, account, quantity);
//		} else {
//			in(journal, account, price, quantity);
//		}
//
//	}

	public List<JournalInventory> getUnassignedBalance(String date) throws Exception {

		// ambil semua account yg account parentnya teregsiter pada inventory.
		// dari account yg terpilih itu, cari journal_account_balance yg punya
		// account itu
		// dari journal_account_balance yg terpilih itu, ambil journalnya saja
		// dari journal yg terpilih itu ambil inventory balance yg TIDAK PUNYA
		// journal itu,

		Map<String, Account> mapInv = new HashMap<>();

		for (ParentInventory inv : parentInventoryService.getAll()) {
			mapInv.put(inv.getAccount().getCode(), inv.getAccount());
		}

		List<JournalInventory> listResult = new ArrayList<>();
		List<Journal> listJournal = new ArrayList<>();

		for (JournalAccountBalance jab : journalAccountBalanceService.getAllAfter(Tools.DDMMYYYY.parse(date))) {
			String accountCode = jab.getAccountBalance().getAccount().getAccountParent().getCode();
			if (mapInv.containsKey(accountCode)) {
				List<InventoryBalance> invBal = dao.findByJournal(jab.getJournal().getId());
				if (invBal == null || invBal.isEmpty()) {
					listJournal.add(jab.getJournal());
				}
			}
		}

		for (Journal journal : listJournal) {

			JournalInventory jin = new JournalInventory();
			listResult.add(jin);

			jin.setDate(journal.getDate());
			jin.setDescription(journal.getDescription());
			jin.setId(journal.getId());
			jin.setAccounts(new ArrayList<>());

			for (JournalAccountBalance jab : journal.getListJournalAccountBalance()) {
				Account a = jab.getAccountBalance().getAccount();
				if (mapInv.containsKey(a.getAccountParent().getCode())) {
					JournalAccountSign jas = new JournalAccountSign();
					jas.setCode(a.getCode());
					jas.setName(a.getName());
					jas.setPos(jab.getAccountBalance().getAmount().compareTo(BigDecimal.ZERO) > 0 ? AccountPosition.DEBET : AccountPosition.CREDIT);
					jin.getAccounts().add(jas);
				}
			}

		}

		return listResult;

	}

	@Transactional(rollbackOn = Exception.class)
	public void addFromJournal(InventoryBalanceInputDto dto) throws Exception {

		Journal journal = journalService.getById(dto.getJournalId());
		if (journal == null) {
			throw new Exception("Journal not found");
		}

		Map<String, InventoryBalanceDto> map = new HashMap<>();

		for (InventoryBalanceDto ibd : dto.getAccounts()) {

			Account account = accountService.getByCode(ibd.getCode());
			if (account == null) {
				throw new Exception("account code not found");
			}

			ParentInventory inventory = parentInventoryService.getById(account.getAccountParent().getId());
			if (inventory == null) {
				throw new Exception("account is not registered as inventory");
			}

			map.put(ibd.getCode(), ibd);
			
		}

		Date now = new Date();
		
		for (JournalAccountBalance jab : journal.getListJournalAccountBalance()) {
			String code = jab.getAccountBalance().getAccount().getCode();

			if (map.containsKey(code)) {

				double qty = map.get(code).getQuantity(); 
				
				if (qty <= 0) {
					throw new Exception("quantity must > 0");
				}

				if (jab.getAccountBalance().getAmount().compareTo(BigDecimal.ZERO) > 0) {

					BigDecimal price = jab.getAccountBalance().getAmount().divide(new BigDecimal(qty), 10, RoundingMode.HALF_DOWN);
					in(journal, jab.getAccountBalance().getAccount(), now, price, qty);
				} else {
					out(journal, jab.getAccountBalance().getAccount(), now, qty);
				}

			} 

		}

	}

}
