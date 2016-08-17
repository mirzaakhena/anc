package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.RegisterDto;
import com.mirzaakhena.batchsystem.service.UserManagementService;

@RestController
public class UserManagementController {

	@Autowired
	private UserManagementService userManagementService;

	@RequestMapping(value= "/register", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody RegisterDto dto) throws Exception {
		userManagementService.register(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
