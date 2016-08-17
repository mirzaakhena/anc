package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.PaymentAccountPayableDto;
import com.mirzaakhena.batchsystem.service.AccountPayableService;

@RestController
@RequestMapping("/accountpayable")
public class AccountPayableController {

	@Autowired
	private AccountPayableService accountPayableService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(accountPayableService.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody PaymentAccountPayableDto dto) throws Exception {
		accountPayableService.pay(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
