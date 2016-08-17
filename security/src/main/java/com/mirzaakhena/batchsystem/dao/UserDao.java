package com.mirzaakhena.batchsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirzaakhena.batchsystem.model.User;

public interface UserDao extends JpaRepository<User, Long> {

	User findOneByUsername(String username);

}
