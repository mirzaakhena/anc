package com.mirzaakhena.batchsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.mirzaakhena.batchsystem.model.Client;
import com.mirzaakhena.batchsystem.model.ClientUser;
import com.mirzaakhena.batchsystem.model.ClientUserKey;
import com.mirzaakhena.batchsystem.model.AccessRight;
import com.mirzaakhena.batchsystem.model.User;


public interface ClientUserDao extends JpaRepository<ClientUser, ClientUserKey> {

	List<AccessRight> getByUserAndClient(User user, Client client);

	@Modifying
	void deleteByUserAndClient(User user, Client client);

}
