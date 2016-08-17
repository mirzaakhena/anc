package com.mirzaakhena.batchsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.SupplierDao;
import com.mirzaakhena.batchsystem.dto.PersonDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.Supplier;

@Service
public class SupplierService extends BaseSubAccountService {

	@Autowired
	private SupplierDao dao;

	@Transactional(rollbackFor=Exception.class)
	public Supplier add(PersonDto dto) throws Exception {

		Account account = createAccount(Supplier.class, dto.getName());
		
		Supplier supplier = new Supplier();
		supplier.setAccount(account);		
		supplier.setAddress(dto.getAddress());
		supplier.setPhone(dto.getPhone());

		return dao.save(supplier);
	}

	public Supplier getById(Long personId) {
		return dao.findOne(personId);
	}

	public List<Supplier> getAll() {
		return dao.findAll();
	}

	public Supplier getByCode(String code) {
		return dao.findByCode(code);
	}

}
