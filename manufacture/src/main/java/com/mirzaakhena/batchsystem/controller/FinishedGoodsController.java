package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.FinishedGoodsDto;
import com.mirzaakhena.batchsystem.service.FinishedGoodsService;

@RestController
@RequestMapping("/finishedgoods")
public class FinishedGoodsController {

	@Autowired
	private FinishedGoodsService finishedGoodsService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@RequestParam(defaultValue = "") String name) {
		return new ResponseEntity<>(finishedGoodsService.getAll(name), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody FinishedGoodsDto dto) throws Exception {
		return new ResponseEntity<>(finishedGoodsService.add(dto), HttpStatus.CREATED);
	}

}
