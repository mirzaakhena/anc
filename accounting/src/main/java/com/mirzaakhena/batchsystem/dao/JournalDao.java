package com.mirzaakhena.batchsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.batchsystem.model.Journal;

public interface JournalDao extends JpaRepository<Journal, Long> {

	@Query("FROM Journal WHERE (DATE_FORMAT(date, '%Y%m%d') BETWEEN ?1 AND ?2) ORDER BY date ASC, id ASC")
	List<Journal> findByDate(String from, String until);
	
}
