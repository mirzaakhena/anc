package com.mirzaakhena.batchsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mirzaakhena.batchsystem.dao.ClientUserDao;
import com.mirzaakhena.batchsystem.model.Client;
import com.mirzaakhena.batchsystem.model.ClientUser;
import com.mirzaakhena.batchsystem.model.AccessRight;
import com.mirzaakhena.batchsystem.model.User;

@Service
public class ClientUserService {

	@Autowired
	private ClientUserDao dao;

	public ClientUser delegate(Client client, User user, AccessRight accessRight) {
		ClientUser cu = new ClientUser();
		cu.setClient(client);
		cu.setUser(user);
		cu.setAccessRight(accessRight);
		return dao.save(cu);
	}

	public List<AccessRight> getByUserAndClient(User user, Client client) {
		return dao.getByUserAndClient(user, client);
	}

	public void deleteByUserAndClient(User user, Client client) {
		dao.deleteByUserAndClient(user, client);
	}

}
