package com.mirzaakhena.batchsystem.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mirzaakhena.batchsystem.dao.WorkInProcessBalanceDao;
import com.mirzaakhena.batchsystem.dto.FinishWIPDto;
import com.mirzaakhena.batchsystem.dto.JournalTransactionDto;
import com.mirzaakhena.batchsystem.dto.StartWIPDto;
import com.mirzaakhena.batchsystem.dto.TransactionDto;
import com.mirzaakhena.batchsystem.model.FinishedGoods;
import com.mirzaakhena.batchsystem.model.InventoryBalance;
import com.mirzaakhena.batchsystem.model.Journal;
import com.mirzaakhena.batchsystem.model.LabourCostCalculatedLater;
import com.mirzaakhena.batchsystem.model.OverheadCostCalculatedLater;
import com.mirzaakhena.batchsystem.model.RawMaterialDetail;
import com.mirzaakhena.batchsystem.model.SubAccountRelation;
import com.mirzaakhena.batchsystem.model.WIPCalculator;
import com.mirzaakhena.batchsystem.model.WIPItem;
import com.mirzaakhena.batchsystem.model.WorkInProcess;
import com.mirzaakhena.batchsystem.model.WorkInProcessBalance;
import com.mirzaakhena.batchsystem.tools.Tools;

@Service
public class WorkInProcessBalanceService {

	@Autowired
	private WorkInProcessBalanceDao dao;

	@Autowired
	private JournalService journalService;

	@Autowired
	private WorkInProcessService workInProcessService;

	@Autowired
	private SubAccountRelationService subAccountRelationService;

	@Autowired
	private InventoryBalanceService inventoryBalanceService;

	@Transactional(rollbackOn = Exception.class)
	public WorkInProcessBalance in(StartWIPDto dto) throws Exception {

		WorkInProcess wip = workInProcessService.getByWIPCode(dto.getWipCode());
		if (wip == null) {
			throw new Exception("wip code " + dto.getWipCode() + " not found");
		}

		JournalTransactionDto jDto = new JournalTransactionDto();

		jDto.setDescription("Start Production " + wip.getName());

		List<TransactionDto> listTDto = new ArrayList<>();
		jDto.setTransactions(listTDto);

		// Journal
		// WIP = LABOUR + OVERHEAD + RAW_MATERIAL

		BigDecimal lb = BigDecimal.ZERO;
		BigDecimal oh = BigDecimal.ZERO;
		BigDecimal rm = BigDecimal.ZERO;

		BigDecimal newQuantity = new BigDecimal(dto.getNewQuantity());
		FinishedGoods fg = wip.getFinishedGoods();

		// LABOUR
		{
			lb = fg.getCostLabour().multiply(newQuantity);
			SubAccountRelation sar = subAccountRelationService.get(LabourCostCalculatedLater.class);
			listTDto.add(new TransactionDto(sar.getParentAccount().getCode(), lb));
		}

		// OVERHEAD
		{
			oh = fg.getCostOverhead().multiply(newQuantity);
			SubAccountRelation sar = subAccountRelationService.get(OverheadCostCalculatedLater.class);
			listTDto.add(new TransactionDto(sar.getParentAccount().getCode(), oh));
		}

		// RAW MATERIAL
		{

			for (RawMaterialDetail rmd : fg.getListRawMaterialDetail()) {

				InventoryBalance invBal = inventoryBalanceService.getLastInventory(rmd.getRawMaterial().getAccount());
				if (invBal == null) {
					throw new Exception("raw material " + rmd.getFinishedGoods().getName() + " is empty");
				}

				// biaya yg diperlukan untuk membuat SATU produk
				BigDecimal cost = invBal.getBalancePrice().multiply(new BigDecimal(rmd.getQuantity()));

				// biaya raw material sebuah produk diakumulasikan
				rm = rm.add(cost);

				// kurangi biaya raw material sebanyak jumlah quantity produksi
				listTDto.add(new TransactionDto(rmd.getRawMaterial().getCode(), cost.multiply(newQuantity).negate()));

			}

			// biaya raw material yg sudah diakumulasikan dikalikan dengan
			// jumlah yang akan diproduksi
			rm = rm.multiply(newQuantity);

		}

		// WIP = RM + OH + LB
		{
			listTDto.add(new TransactionDto(wip.getCode(), rm.add(oh).add(lb)));
		}

		// account balance
		Journal journal = journalService.add(jDto);

		for (RawMaterialDetail rmd : fg.getListRawMaterialDetail()) {
			// inventory balance dikurangi
			inventoryBalanceService.out(journal, rmd.getRawMaterial().getAccount(), journal.getDate(), rmd.getQuantity() * dto.getNewQuantity());
		}

		WorkInProcessBalance wipBal = new WorkInProcessBalance();
		wipBal.setDate(journal.getDate());
		wipBal.setWorkInProcess(wip);
		wipBal.setJournalStart(journal);

		// new quantity
		wipBal.setNewQuantity(dto.getNewQuantity());

		// production code
		wipBal.setProductionCode(dto.getProductionCode());

		// new cost
		wipBal.setCostRawMaterial(rm);
		wipBal.setCostLabour(lb);
		wipBal.setCostOverhead(oh);
		
		// cek apakah sebelumnya ada produksi yg pernah dijalankan?
		List<WorkInProcessBalance> listWIPBal = getLastBalance(dto.getWipCode());
		if (listWIPBal == null || listWIPBal.size() == 0) {
			// belum pernah ada produksi sebelumnya.
			// ini yang pertama kali.
			// maka minta input dari user

			// prev quantity
			wipBal.setPrevQuantity(dto.getPrevQuantity());

			// prev cost
			wipBal.setPrevCostRawMaterial(dto.getPrevCostRawMaterial());
			wipBal.setPrevCostLabour(dto.getPrevCostLabour());
			wipBal.setPrevCostOverhead(dto.getPrevCostOverhead());

		} else {

			// isi data sebelumnya secara otomatis

			WorkInProcessBalance wpb = listWIPBal.get(0);

			if (!wpb.isFinished()) {
				throw new Exception("harap selesaikan produksi terlebih dahulu sebelum membuat produksi yang baru");
			}

			wpb.setActive(false);
			dao.save(wpb);

			WIPCalculator wc = new WIPCalculator();
			wc.start(wpb.getNewQuantity(), new WIPItem(wpb.getCostRawMaterial(), wpb.getCostLabour(), wpb.getCostOverhead()));
			wc.finish(wpb.getFinishQuantity(), wpb.getConvertionCostLabour(), wpb.getConvertionCostOverhead());

			// prev quantity
			wipBal.setPrevQuantity(wc.getRemainingQuantity());

			// prev cost
			wipBal.setPrevCostRawMaterial(wc.getSubTotalWP().getRm());
			wipBal.setPrevCostLabour(wc.getSubTotalWP().getLb());
			wipBal.setPrevCostOverhead(wc.getSubTotalWP().getOh());

		}
		
		BigDecimal total = rm.add(lb).add(oh).add(wipBal.getPrevCostRawMaterial()).add(wipBal.getPrevCostLabour()).add(wipBal.getPrevCostOverhead());
		wipBal.setRemainingTotalCost(total);

		wipBal.setActive(true);

		return dao.save(wipBal);

	}

	public List<WorkInProcessBalance> getLastBalance(String code) {
		return dao.findLast(code, new PageRequest(0, 1));
	}

	@Transactional(rollbackOn = Exception.class)
	public WorkInProcessBalance out(FinishWIPDto dto) throws Exception {

		List<WorkInProcessBalance> listWIPBal = getLastBalance(dto.getWipCode());
		if (listWIPBal == null || listWIPBal.size() == 0) {
			throw new Exception("wip not found");
		}

		WorkInProcessBalance wipBal = listWIPBal.get(0);

		if (wipBal.isFinished()) {
			throw new Exception("wip is already finished");
		}

		wipBal.setConvertionCostLabour(dto.getConvertionCostLabour());
		wipBal.setConvertionCostOverhead(dto.getConvertionCostOverhead());

		wipBal.setFinishQuantity(dto.getFinishQuantity());

		double ccRM = 100;
		double ccLB = wipBal.getConvertionCostLabour();
		double ccOH = wipBal.getConvertionCostOverhead();

		BigDecimal _100 = new BigDecimal(100);

		BigDecimal finQty = new BigDecimal(wipBal.getFinishQuantity());
		BigDecimal remQty = new BigDecimal(wipBal.getRemainingQuantity());

		BigDecimal subTotalRM = wipBal.getPrevCostRawMaterial().add(wipBal.getCostRawMaterial());
		BigDecimal subTotalLB = wipBal.getPrevCostLabour().add(wipBal.getCostLabour());
		BigDecimal subTotalOH = wipBal.getPrevCostOverhead().add(wipBal.getCostOverhead());
		BigDecimal totalCost = subTotalRM.add(subTotalLB).add(subTotalOH);

		// -----------

		double euRM = wipBal.getFinishQuantity() + (wipBal.getRemainingQuantity() * ccRM / 100);
		double euLB = wipBal.getFinishQuantity() + (wipBal.getRemainingQuantity() * ccLB / 100);
		double euOH = wipBal.getFinishQuantity() + (wipBal.getRemainingQuantity() * ccOH / 100);

		BigDecimal cpuFG_RM = subTotalRM.divide(new BigDecimal(euRM), 10, RoundingMode.HALF_DOWN);
		BigDecimal cpuFG_LB = subTotalLB.divide(new BigDecimal(euLB), 10, RoundingMode.HALF_DOWN);
		BigDecimal cpuFG_OH = subTotalOH.divide(new BigDecimal(euOH), 10, RoundingMode.HALF_DOWN);
		BigDecimal totalCPU_FG = cpuFG_RM.add(cpuFG_LB).add(cpuFG_OH);

		BigDecimal cpuWP_RM = cpuFG_RM.multiply(new BigDecimal(ccRM)).divide(_100);
		BigDecimal cpuWP_LB = cpuFG_LB.multiply(new BigDecimal(ccLB)).divide(_100);
		BigDecimal cpuWP_OH = cpuFG_OH.multiply(new BigDecimal(ccOH)).divide(_100);
		BigDecimal totalCPU_WP = cpuWP_RM.add(cpuWP_LB).add(cpuWP_OH);

		BigDecimal subTotalCostFG_RM = cpuFG_RM.multiply(finQty);
		BigDecimal subTotalCostFG_LB = cpuFG_LB.multiply(finQty);
		BigDecimal subTotalCostFG_OH = cpuFG_OH.multiply(finQty);
		BigDecimal totalCostFG = subTotalCostFG_RM.add(subTotalCostFG_LB).add(subTotalCostFG_OH);

		BigDecimal subTotalCostWP_RM = cpuWP_RM.multiply(remQty);
		BigDecimal subTotalCostWP_LB = cpuWP_LB.multiply(remQty);
		BigDecimal subTotalCostWP_OH = cpuWP_OH.multiply(remQty);
		BigDecimal totalCostWP = subTotalCostWP_RM.add(subTotalCostWP_LB).add(subTotalCostWP_OH);

		// WIP berkurang sebanyak FG yang selesai

		JournalTransactionDto jDto = new JournalTransactionDto();

		jDto.setDescription("Finish Production " + wipBal.getWorkInProcess().getName());

		List<TransactionDto> listTDto = new ArrayList<>();
		jDto.setTransactions(listTDto);

		// FG
		{
			listTDto.add(new TransactionDto(wipBal.getWorkInProcess().getFinishedGoods().getCode(), totalCostFG));
		}

		// WIP
		{
			listTDto.add(new TransactionDto(wipBal.getWorkInProcess().getCode(), totalCostFG.negate()));
		}
		
		wipBal.setRemainingTotalCost(totalCostWP);

		// account balance
		Journal journal = journalService.add(jDto);

		wipBal.setJournalFinish(journal);

		// inventory balance
		inventoryBalanceService.in(journal, wipBal.getWorkInProcess().getFinishedGoods().getAccount(), journal.getDate(), totalCPU_FG, wipBal.getFinishQuantity());

		return dao.save(wipBal);

	}

	public List<WorkInProcessBalance> getAll() {
		return dao.findAllFinished();
	}

	public List<WIPCalculator> getDetail(Long wipId, String from, String until) throws Exception {

		Date d1 = Tools.DDMMYYYY.parse(from);
		Date d2 = Tools.DDMMYYYY.parse(until);

		if (d1.after(d2)) {
			Date t = d1;
			d1 = d2;
			d2 = t;
		}

		List<WorkInProcessBalance> listB = dao.findByDateRange(wipId, Tools.YYYYMMDD.format(d1), Tools.YYYYMMDD.format(d2));

		List<WIPCalculator> listC = new ArrayList<>();

		listB.forEach(wipBal -> listC.add(new WIPCalculator(wipBal)));

		return listC;
	}

}
