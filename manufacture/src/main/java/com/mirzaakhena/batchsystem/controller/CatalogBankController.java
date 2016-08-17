package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.CatalogBankDto;
import com.mirzaakhena.batchsystem.service.CatalogBankService;

@RestController
@RequestMapping("/catalogbank")
public class CatalogBankController {

	@Autowired
	private CatalogBankService bankService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(bankService.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody CatalogBankDto dto) throws Exception {
		return new ResponseEntity<>(bankService.add(dto), HttpStatus.CREATED);
	}

}
