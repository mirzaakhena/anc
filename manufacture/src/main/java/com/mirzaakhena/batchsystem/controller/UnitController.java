package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.UnitDto;
import com.mirzaakhena.batchsystem.service.UnitService;

@RestController
@RequestMapping("/unit")
public class UnitController {

	@Autowired
	private UnitService unitService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(unitService.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody UnitDto dto) throws Exception {
		return new ResponseEntity<>(unitService.add(dto), HttpStatus.CREATED);
	}
	
}
