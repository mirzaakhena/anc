package com.mirzaakhena.batchsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.UnitDao;
import com.mirzaakhena.batchsystem.dto.UnitDto;
import com.mirzaakhena.batchsystem.model.Unit;

@Service
public class UnitService {

	@Autowired
	private UnitDao dao;

	@Transactional
	public Unit add(UnitDto dto) {
		Unit unit = new Unit();
		unit.setName(dto.getName());
		unit.setDescription(dto.getDescription());
		return dao.save(unit);
	}
	
	public Unit getById(Long unitId) {
		return dao.findOne(unitId);
	}
	
	public List<Unit> getAll() {
		return dao.findAll();
	}

}
