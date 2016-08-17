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
import com.mirzaakhena.batchsystem.service.AccountService;

/**
 * 
 * menampilkan account saja
 * 
 * @author mirzaakhena
 *
 */
@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@JsonView(Views.AccountBalanceView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@RequestParam(defaultValue = "1") int level, @RequestParam(defaultValue = "") String side) throws Exception {
		if ("activa".equals(side.toLowerCase()) || "passiva".equals(side.toLowerCase()) || "".equals(side)) {
			return new ResponseEntity<>(accountService.getAll(level, side.toLowerCase()), HttpStatus.OK);
		}
		throw new Exception(String.format("side '%s' is undefined.", side));
	}

	@JsonView(Views.AccountBalanceView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getChild(@PathVariable Long id) throws Exception {
		return new ResponseEntity<>(accountService.getChild(id), HttpStatus.OK);
	}

}
