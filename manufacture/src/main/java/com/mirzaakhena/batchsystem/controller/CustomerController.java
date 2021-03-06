package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.PersonDto;
import com.mirzaakhena.batchsystem.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(customerService.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody PersonDto dto) throws Exception {
		return new ResponseEntity<>(customerService.add(dto), HttpStatus.CREATED);
	}
	
}
