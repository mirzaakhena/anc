package com.mirzaakhena.batchsystem.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mirzaakhena.batchsystem.model.SubAccountRelation;

public interface SubAccountRelationDao extends JpaRepository<SubAccountRelation, Long> {

	List<SubAccountRelation> findByClassName(String simpleName, Pageable pageable);

}
