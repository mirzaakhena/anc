package com.mirzaakhena.batchsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockFinishGoodsService {

	@Autowired
	private JournalService journalService;

	@Transactional(rollbackFor = Exception.class)
	public void pay() throws Exception {

		// akun utang x sebesar -a
		// akun kas y sebesar -b
		// akun bank z sebesar -c

	}

}
