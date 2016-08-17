package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.CatalogCashDto;
import com.mirzaakhena.batchsystem.service.CatalogCashService;

@RestController
@RequestMapping("/catalogcash")
public class CatalogCashController {

	@Autowired
	private CatalogCashService cashService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(cashService.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody CatalogCashDto dto) throws Exception {
		return new ResponseEntity<>(cashService.add(dto), HttpStatus.CREATED);
	}

}
