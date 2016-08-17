package com.mirzaakhena.batchsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mirzaakhena.batchsystem.dto.FinishWIPDto;
import com.mirzaakhena.batchsystem.dto.StartWIPDto;
import com.mirzaakhena.batchsystem.model.WorkInProcess;
import com.mirzaakhena.batchsystem.service.WorkInProcessBalanceService;
import com.mirzaakhena.batchsystem.service.WorkInProcessService;
import com.mirzaakhena.batchsystem.tools.Tools;

@RestController
@RequestMapping("/workinprocessbalance")
public class WorkInProcessBalanceController {

	@Autowired
	private WorkInProcessBalanceService wipBalService;

	@Autowired
	private WorkInProcessService wipService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(wipBalService.getAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public ResponseEntity<?> startProduction(@RequestBody StartWIPDto dto) throws Exception {
		wipBalService.in(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public ResponseEntity<?> finishProduction(@RequestBody FinishWIPDto dto) throws Exception {
		wipBalService.out(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/last/{wipId}", method = RequestMethod.GET)
	public ResponseEntity<?> lastProduction(@PathVariable Long wipId) throws Exception {
		WorkInProcess wip = wipService.getById(wipId);
		if (wip == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(wipBalService.getLastBalance(wip.getCode()), HttpStatus.OK);
	}

	@RequestMapping(value = "/detail/{wipId}", method = RequestMethod.GET)
	public ResponseEntity<?> getDetail(@PathVariable Long wipId, @RequestParam String[] date) throws Exception {
		String[] dateArr = Tools.validateDate(date);
		return new ResponseEntity<>(wipBalService.getDetail(wipId, dateArr[0], dateArr[1]), HttpStatus.OK);
	}

}
