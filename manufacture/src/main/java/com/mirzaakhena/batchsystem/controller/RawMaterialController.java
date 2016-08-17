package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.RawMaterialDto;
import com.mirzaakhena.batchsystem.service.RawMaterialService;

@RestController
@RequestMapping("/rawmaterial")
public class RawMaterialController {

	@Autowired
	private RawMaterialService rawMaterialService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@RequestParam(defaultValue = "") String name) {
		return new ResponseEntity<>(rawMaterialService.getAll(name), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody RawMaterialDto dto) throws Exception {
		return new ResponseEntity<>(rawMaterialService.add(dto), HttpStatus.CREATED);
	}
	
}
