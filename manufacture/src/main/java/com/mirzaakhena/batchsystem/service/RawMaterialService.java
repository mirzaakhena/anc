package com.mirzaakhena.batchsystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.RawMaterialDao;
import com.mirzaakhena.batchsystem.dto.RawMaterialDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.RawMaterial;

@Service
public class RawMaterialService extends BaseSubAccountService {

	@Autowired
	private RawMaterialDao dao;

	@Autowired
	private UnitService unitService;

	@Transactional(rollbackFor = Exception.class)
	public RawMaterial add(RawMaterialDto dto) throws Exception {

		Account account = createAccount(RawMaterial.class, dto.getName());

		RawMaterial rawMaterial = new RawMaterial();
		rawMaterial.setMinimalQuantity(dto.getMinimalQuantity());
		rawMaterial.setAccount(account);
		rawMaterial.setDescription(dto.getDescription());
		rawMaterial.setUnit(unitService.getById(dto.getUnitId()));

		return dao.save(rawMaterial);
	}

	public RawMaterial getById(Long id) throws Exception {
		RawMaterial item = dao.findOne(id);
		if (item == null) {
			throw new Exception("item with id " + id + " not found");
		}
		return item;
	}
	
	public RawMaterial getByCode(String code) throws Exception {
		RawMaterial item = dao.findByCode(code);
		if (item == null) {
			throw new Exception("item with id " + code + " not found");
		}
		return item;
	}

	public List<RawMaterial> getAll(String name) {
		return dao.findAll(name + "%");
	}

	public List<RawMaterial> getListItem(Long[] itemIds) throws Exception {
		List<RawMaterial> items = new ArrayList<>();
		for (Long itemId : itemIds) {
			RawMaterial item = dao.findOne(itemId);
			if (item == null) {
				throw new Exception("no item found");
			}
			items.add(item);
		}
		return items;
	}

}
