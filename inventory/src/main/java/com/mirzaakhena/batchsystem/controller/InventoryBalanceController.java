package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.InventoryBalanceInputDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.AccountType;
import com.mirzaakhena.batchsystem.model.ParentInventory;
import com.mirzaakhena.batchsystem.service.AccountService;
import com.mirzaakhena.batchsystem.service.InventoryBalanceService;
import com.mirzaakhena.batchsystem.service.ParentInventoryService;
import com.mirzaakhena.batchsystem.tools.Tools;

/**
 * menampilkan inventory lengkap dengan general ledger
 * 
 * @author mirzaakhena
 *
 */
@RestController
@RequestMapping("/inventorybalance")
public class InventoryBalanceController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private ParentInventoryService inventoryService;

	@Autowired
	private InventoryBalanceService inventoryBalanceService;

	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@PathVariable Long accountId, @RequestParam String[] date) throws Exception {

		ParentInventory inventory = inventoryService.getById(accountId);
		if (inventory != null) {
			Account account = inventory.getAccount();
			String[] dateArr = Tools.validateDate(date);
			if (account.getAccountType() == AccountType.CHILD_AS_SUBACCOUNT) {
				return new ResponseEntity<>(inventoryBalanceService.getChilds(accountId, dateArr[0]), HttpStatus.OK);
			}
		} else {

			Account account = accountService.getById(accountId);
			if (account != null) {
				inventory = inventoryService.getById(account.getAccountParent().getId());
				if (inventory != null) {
					if (account.getAccountType() == AccountType.FINAL_ACCOUNT) {
						String[] dateArr = Tools.validateDate(date);
						return new ResponseEntity<>(inventoryBalanceService.getAll(accountId, dateArr[0], dateArr[1]), HttpStatus.OK);
					}
				}
			} 
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/unassigned", method = RequestMethod.GET)
	public ResponseEntity<?> getUnassignedBalance(@RequestParam String[] date) throws Exception {
		String[] dateArr = Tools.validateDate(date);
		return new ResponseEntity<>(inventoryBalanceService.getUnassignedBalance(dateArr[0]), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> submit(@RequestBody InventoryBalanceInputDto dto) throws Exception {
		inventoryBalanceService.addFromJournal(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
