package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.mirzaakhena.batchsystem.dto.JournalTransactionDto;
import com.mirzaakhena.batchsystem.model.Views;
import com.mirzaakhena.batchsystem.service.JournalService;
import com.mirzaakhena.batchsystem.tools.Tools;

/**
 * 
 * menampilkan journal lengkap dengan deskripsi dan account balance
 * 
 * @author mirzaakhena
 *
 */
@RestController
@RequestMapping("/journal")
public class JournalController {

	@Autowired
	private JournalService journalService;

	@JsonView(Views.JournalView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@RequestParam String[] date) throws Exception {
		String[] dateArr = Tools.validateDate(date);
		return new ResponseEntity<>(journalService.getAll(dateArr[0], dateArr[1]), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody JournalTransactionDto dto) throws Exception {
		journalService.add(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	

}
