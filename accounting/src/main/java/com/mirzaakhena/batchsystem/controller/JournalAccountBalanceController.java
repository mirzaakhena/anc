package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.mirzaakhena.batchsystem.model.Views;
import com.mirzaakhena.batchsystem.service.AccountBalanceService;
import com.mirzaakhena.batchsystem.service.AccountService;
import com.mirzaakhena.batchsystem.service.JournalAccountBalanceService;
import com.mirzaakhena.batchsystem.tools.Tools;

/**
 * menampilkan general ledger lengkap dengan deskripsi dari journal
 * 
 * @author mirzaakhena
 *
 */
@RestController
@RequestMapping("/journalaccountbalance")
public class JournalAccountBalanceController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountBalanceService accountBalanceService;

	@Autowired
	private JournalAccountBalanceService journalAccountBalanceService;

	@JsonView(Views.JournalAccountBalanceView.class)
	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<?> getGeneralLedger(@PathVariable Long accountId, @RequestParam String[] date) throws Exception {
		String[] dateArr = Tools.validateDate(date);
		return new ResponseEntity<>(journalAccountBalanceService.getAll(accountId, dateArr[0], dateArr[1]), HttpStatus.OK);
	}

}
