package com.mirzaakhena.batchsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dto.ClientDto;
import com.mirzaakhena.batchsystem.dto.InviteDto;
import com.mirzaakhena.batchsystem.dto.RegisterDto;
import com.mirzaakhena.batchsystem.dto.UpdateAccessRightDto;
import com.mirzaakhena.batchsystem.model.AccessRight;
import com.mirzaakhena.batchsystem.model.Client;
import com.mirzaakhena.batchsystem.model.User;

@Service
public class UserManagementService {

	@Autowired
	private UserService userService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private AccessRightService accessRightService;

	@Autowired
	private ClientUserService clientUserService;

	@Transactional(rollbackFor = Exception.class)
	public void register(RegisterDto dto) {

		User user = userService.add(dto);

		ClientDto clientDto = new ClientDto();
		clientDto.setName(dto.getClientName());
		Client client = clientService.add(clientDto);

		clientUserService.delegate(client, user, null);

	}

	@Transactional(rollbackFor = Exception.class)
	public void invite(InviteDto dto) throws Exception {
		User user = userService.add(dto);
		Client client = clientService.getById(dto.getClientId());

		for (Long authorityId : dto.getAccessRightIds()) {
			AccessRight ar = accessRightService.getById(authorityId);
			if (ar == null) {
				throw new Exception("access right id not found");				
			}
			clientUserService.delegate(client, user, ar);
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public void authorityUpdate(UpdateAccessRightDto dto) {
		User user = userService.getById(dto.getUserId());
		Client client = clientService.getById(dto.getClientId());

		clientUserService.deleteByUserAndClient(user, client);

		for (Long accessRightId : dto.getAccessRightIds()) {
			AccessRight ar = accessRightService.getById(accessRightId);
			clientUserService.delegate(client, user, ar);
		}
	}

}
