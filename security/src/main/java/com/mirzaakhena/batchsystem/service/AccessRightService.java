package com.mirzaakhena.batchsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mirzaakhena.batchsystem.dao.AccessRightDao;
import com.mirzaakhena.batchsystem.dto.AccessRightDto;
import com.mirzaakhena.batchsystem.model.AccessRight;

@Service
public class AccessRightService {

	@Autowired
	private AccessRightDao dao;

	public AccessRight add(AccessRightDto dto) {
		AccessRight ar = new AccessRight();
		ar.setAuthority(dto.getAuthority());
		return dao.save(ar);
	}

	public AccessRight getAccessRight(String authority) {
		return dao.findOneByAuthority(authority);
	}

	public AccessRight getById(Long authorityId) {
		return dao.findOne(authorityId);
	}

}
