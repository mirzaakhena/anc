package com.mirzaakhena.batchsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirzaakhena.batchsystem.model.AccessRight;

public interface AccessRightDao extends JpaRepository<AccessRight, Long> {

	AccessRight findOneByAuthority(String authority);

}
