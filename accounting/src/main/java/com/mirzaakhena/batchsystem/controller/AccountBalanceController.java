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
import com.mirzaakhena.batchsystem.tools.Tools;

/**
 * 
 * menampilkan account balance saja
 * 
 * @author mirzaakhena
 *
 */
@RestController
@RequestMapping("/accountbalance")
public class AccountBalanceController {

	@Autowired
	private AccountBalanceService accountBalanceService;

	@JsonView(Views.AccountBalanceView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@RequestParam(defaultValue = "") String date) throws Exception {
		return new ResponseEntity<>(accountBalanceService.getAll(date), HttpStatus.OK);
	}

	@JsonView(Views.AccountBalanceView.class)
	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<?> getBalance(@PathVariable Long accountId, @RequestParam(defaultValue = "null") String[] date) throws Exception {
		String[] dateArr = Tools.validateDate(date);
	
		if (dateArr[0].equals("null")) {
			return new ResponseEntity<>(accountBalanceService.getAll(accountId), HttpStatus.OK);
		}

		return new ResponseEntity<>(accountBalanceService.getAll(accountId, dateArr[0], dateArr[1]), HttpStatus.OK);
	}

	@JsonView({ Views.AccountBalanceView.class })
	@RequestMapping(value = "/{accountId}/child", method = RequestMethod.GET)
	public ResponseEntity<?> getChild(@PathVariable Long accountId, @RequestParam(defaultValue = "") String date) throws Exception {
		return new ResponseEntity<>(accountBalanceService.getChild(accountId, date), HttpStatus.OK);
	}

}
