package com.mirzaakhena.batchsystem.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.FinishedGoodsDao;
import com.mirzaakhena.batchsystem.dao.RawMaterialDetailDao;
import com.mirzaakhena.batchsystem.dto.FinishedGoodsDto;
import com.mirzaakhena.batchsystem.dto.RawMaterialDetailDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.FinishedGoods;
import com.mirzaakhena.batchsystem.model.InventoryBalance;
import com.mirzaakhena.batchsystem.model.RawMaterialDetail;

@Service
public class FinishedGoodsService extends BaseSubAccountService {

	@Autowired
	private FinishedGoodsDao dao;

	@Autowired
	private RawMaterialDetailDao rawMaterialDetailDao;

	@Autowired
	private RawMaterialService rawMaterialService;

	@Autowired
	private UnitService unitService;

	@Autowired
	private InventoryBalanceService inventoryService;
	
	@Autowired
	private WorkInProcessService workInProcess;

	@Transactional(rollbackFor = Exception.class)
	public FinishedGoods add(FinishedGoodsDto dto) throws Exception {

		Account account = createAccount(FinishedGoods.class, dto.getName());

		FinishedGoods fg = new FinishedGoods();
		fg.setMinimalQuantity(dto.getMinimalQuantity());
		fg.setAccount(account);
		fg.setDescription(dto.getDescription());
		fg.setUnit(unitService.getById(dto.getUnitId()));

		fg.setCostLabour(dto.getLabour());
		fg.setCostOverhead(dto.getOverhead());
		fg.setSalePrice(dto.getSalePrice());
		
		dao.save(fg);
		
		workInProcess.add(fg);
		
		BigDecimal totalCostRawMaterial = BigDecimal.ZERO;

		for (RawMaterialDetailDto rmdDto : dto.getRawMaterialDetails()) {
			RawMaterialDetail rawMaterialDetail = new RawMaterialDetail();
			rawMaterialDetail.setFinishedGoods(fg);
			rawMaterialDetail.setRawMaterial(rawMaterialService.getByCode(rmdDto.getRawMaterialCode()));
			rawMaterialDetail.setQuantity(rmdDto.getQuantity());

			InventoryBalance inv = inventoryService.getLastInventory(account);
			if (inv != null) {
				totalCostRawMaterial = totalCostRawMaterial.add(inv.getBalancePrice().multiply(new BigDecimal(rmdDto.getQuantity())));
			}
			
			rawMaterialDetailDao.save(rawMaterialDetail);
		}

		fg.setCostRawMaterial(totalCostRawMaterial);
		dao.save(fg);

		return fg;
	}

	public List<FinishedGoods> getAll(String name) {
		return dao.findAll(name + "%");
	}

	public FinishedGoods getById(Long id) throws Exception {
		FinishedGoods item = dao.findOne(id);
		if (item == null) {
			throw new Exception("finish goods with id " + id + " not found");
		}
		return item;
	}

	public FinishedGoods getByCode(String code) throws Exception {
		List<FinishedGoods> listFG = dao.findByCode(code);
		if (listFG == null || listFG.isEmpty()) {
			throw new Exception("finish goods with code " + code + " not found");
		}
		return listFG.get(0);
	}

	// @Transactional
	// public FinishGoods update(FinishGoods finishGoods, GoodsDto dto) throws
	// Exception {
	// finishGoods.setDescription(dto.getDescription());
	// finishGoods.setName(dto.getName());
	// finishGoods.setUnit(unitService.getById(dto.getUnitId()));
	// return finishGoodsDao.save(finishGoods);
	// }

}
