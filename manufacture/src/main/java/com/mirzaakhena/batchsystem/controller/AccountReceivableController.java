package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.PaymentAccountReceivableDto;
import com.mirzaakhena.batchsystem.service.AccountReceivableService;

@RestController
@RequestMapping("/accountreceivable")
public class AccountReceivableController {

	@Autowired
	private AccountReceivableService accountReceivableService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(accountReceivableService.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody PaymentAccountReceivableDto dto) throws Exception {
		accountReceivableService.pay(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
