package com.mirzaakhena.batchsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mirzaakhena.batchsystem.dao.SubAccountRelationDao;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.SubAccountRelation;

@Service
public class SubAccountRelationService {

	@Autowired
	private SubAccountRelationDao dao;

	public SubAccountRelation set(Class<?> klazz, Account parentAccount) {
		SubAccountRelation sar = new SubAccountRelation();
		sar.setClassName(klazz.getSimpleName());
		sar.setParentAccount(parentAccount);
		return dao.save(sar);
	}

	public SubAccountRelation get(Class<?> klazz) {
		List<SubAccountRelation> sar = dao.findByClassName(klazz.getSimpleName(), new PageRequest(0, 1));
		return sar == null || sar.isEmpty() ? null : sar.get(0);
	}

	public boolean isSubAccountOf(String code, Class<?> klazz) {
		return get(klazz).getParentAccount().getCode().equals(code.substring(0, code.lastIndexOf(".")));
	}

}
