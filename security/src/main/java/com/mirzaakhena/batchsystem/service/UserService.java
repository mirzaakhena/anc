package com.mirzaakhena.batchsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mirzaakhena.batchsystem.dao.UserDao;
import com.mirzaakhena.batchsystem.dto.UserDto;
import com.mirzaakhena.batchsystem.model.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDao dao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User add(UserDto dto) {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		return dao.save(user);
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {		
		User user = dao.findOneByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		user.getAuthorities();
		return user;
	}

	public User getById(Long userId) {
		return dao.findOne(userId);
	}

}
