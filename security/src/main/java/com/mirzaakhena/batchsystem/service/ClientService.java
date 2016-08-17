package com.mirzaakhena.batchsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mirzaakhena.batchsystem.dao.ClientDao;
import com.mirzaakhena.batchsystem.dto.ClientDto;
import com.mirzaakhena.batchsystem.model.Client;

@Service
public class ClientService {

	@Autowired
	private ClientDao dao;

	public Client add(ClientDto dto) {
		Client client = new Client();
		client.setName(dto.getName());
		return dao.save(client);
	}

	public Client getById(Long clientId) {
		return dao.findOne(clientId);
	}

}
