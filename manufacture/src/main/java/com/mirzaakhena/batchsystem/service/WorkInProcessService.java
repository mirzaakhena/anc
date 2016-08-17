package com.mirzaakhena.batchsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mirzaakhena.batchsystem.dao.WorkInProcessDao;
import com.mirzaakhena.batchsystem.model.FinishedGoods;
import com.mirzaakhena.batchsystem.model.WorkInProcess;

@Service
public class WorkInProcessService extends BaseSubAccountService {

	@Autowired
	private WorkInProcessDao dao;

	public void add(FinishedGoods fg) throws Exception {
		WorkInProcess wip = new WorkInProcess();
		wip.setAccount(createAccount(WorkInProcess.class, fg.getAccount().getName()));
		wip.setFinishedGoods(fg);
		dao.save(wip);
	}

	public WorkInProcess getByWIPCode(String wipCode) {
		return dao.findByWIPCode(wipCode);
	}
	
	public WorkInProcess getById(Long id) {
		return dao.findOne(id);
	}

	public List<WorkInProcess> getAll(String name) {
		return dao.findAll(name + "%");
	}
	
}
