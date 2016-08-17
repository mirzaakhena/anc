package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.AccessRightDto;
import com.mirzaakhena.batchsystem.service.AccessRightService;

@RestController
@RequestMapping("/role")
public class AccessRightController {

	@Autowired
	private AccessRightService accessRightService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody AccessRightDto dto) throws Exception {		
		return new ResponseEntity<>(accessRightService.add(dto), HttpStatus.CREATED);
	}

}
