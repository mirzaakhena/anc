package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.BaseCategoryDto;
import com.mirzaakhena.batchsystem.service.ExpenseCategoryService;

@RestController
@RequestMapping("/expensecategory")
public class ExpenseCategoryController {

	@Autowired
	private ExpenseCategoryService incomeService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(incomeService.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody BaseCategoryDto dto) throws Exception {
		return new ResponseEntity<>(incomeService.add(dto), HttpStatus.CREATED);
	}

}
