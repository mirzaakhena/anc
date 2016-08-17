package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.SaleFinishedGoodsDto;
import com.mirzaakhena.batchsystem.service.SaleService;

@RestController
@RequestMapping("/sale")
public class SaleController {

	@Autowired
	private SaleService saleService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(saleService.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody SaleFinishedGoodsDto dto) throws Exception {
		saleService.sale(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}
