package com.mirzaakhena.batchsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.CustomerDao;
import com.mirzaakhena.batchsystem.dto.PersonDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.Customer;

@Service
public class CustomerService extends BaseSubAccountService {

	@Autowired
	private CustomerDao dao;

	@Transactional(rollbackFor=Exception.class)
	public Customer add(PersonDto dto) throws Exception {
		
		Account account = createAccount(Customer.class, dto.getName());
		
		Customer customer = new Customer();
		customer.setAccount(account);		
		customer.setAddress(dto.getAddress());
		customer.setPhone(dto.getPhone());

		return dao.save(customer);

	}

	public Customer getById(Long personId) {
		return dao.findOne(personId);
	}

	public List<Customer> getAll() {
		return dao.findAll();
	}

	public Customer getByCode(String code) {
		return dao.findByCode(code);
	}

}
