package com.testaccounting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mirzaakhena.batchsystem.MainApplicationAccounting;
import com.mirzaakhena.batchsystem.dto.CatalogBankDto;
import com.mirzaakhena.batchsystem.dto.CatalogCashDto;
import com.mirzaakhena.batchsystem.dto.FinishWIPDto;
import com.mirzaakhena.batchsystem.dto.FinishedGoodsDto;
import com.mirzaakhena.batchsystem.dto.ParentInventoryDto;
import com.mirzaakhena.batchsystem.dto.JournalTransactionDto;
import com.mirzaakhena.batchsystem.dto.PersonDto;
import com.mirzaakhena.batchsystem.dto.RawMaterialDetailDto;
import com.mirzaakhena.batchsystem.dto.RawMaterialDto;
import com.mirzaakhena.batchsystem.dto.StartWIPDto;
import com.mirzaakhena.batchsystem.dto.TransactionDto;
import com.mirzaakhena.batchsystem.dto.UnitDto;
import com.mirzaakhena.batchsystem.model.CatalogBank;
import com.mirzaakhena.batchsystem.model.CatalogCash;
import com.mirzaakhena.batchsystem.model.Customer;
import com.mirzaakhena.batchsystem.model.ExpenseCategory;
import com.mirzaakhena.batchsystem.model.FinishedGoods;
import com.mirzaakhena.batchsystem.model.HPP;
import com.mirzaakhena.batchsystem.model.IncomeCategory;
import com.mirzaakhena.batchsystem.model.Journal;
import com.mirzaakhena.batchsystem.model.LabourCostCalculatedLater;
import com.mirzaakhena.batchsystem.model.OverheadCostCalculatedLater;
import com.mirzaakhena.batchsystem.model.Pendapatan;
import com.mirzaakhena.batchsystem.model.RawMaterial;
import com.mirzaakhena.batchsystem.model.Supplier;
import com.mirzaakhena.batchsystem.model.WorkInProcess;
import com.mirzaakhena.batchsystem.service.AccountService;
import com.mirzaakhena.batchsystem.service.CatalogBankService;
import com.mirzaakhena.batchsystem.service.CatalogCashService;
import com.mirzaakhena.batchsystem.service.CustomerService;
import com.mirzaakhena.batchsystem.service.FinishedGoodsService;
import com.mirzaakhena.batchsystem.service.InventoryBalanceService;
import com.mirzaakhena.batchsystem.service.ParentInventoryService;
import com.mirzaakhena.batchsystem.service.JournalService;
import com.mirzaakhena.batchsystem.service.RawMaterialService;
import com.mirzaakhena.batchsystem.service.SubAccountRelationService;
import com.mirzaakhena.batchsystem.service.SupplierService;
import com.mirzaakhena.batchsystem.service.UnitService;
import com.mirzaakhena.batchsystem.service.WorkInProcessBalanceService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainApplicationAccounting.class)
public class TestAccounting {

	@Autowired
	AccountService accountService;

	@Autowired
	JournalService journalService;

	@Autowired
	private SubAccountRelationService subAccountRelationService;
	
	@Autowired
	private UnitService unitService;

	@Autowired
	private RawMaterialService rawMaterialService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private CatalogBankService catalogBankService;

	@Autowired
	private CatalogCashService catalogCashService;

	@Autowired
	private FinishedGoodsService finishGoodsService;

	@Autowired
	private ParentInventoryService parentInventoryService;

	@Autowired
	private InventoryBalanceService inventoryBalanceService;

	@Autowired
	private WorkInProcessBalanceService workInProcessBalanceService;

	public void daftarkanAkunAkun() throws Exception {
		accountService.addByFile("accounts.txt");
	}

	public void linkingAccount() throws Exception {

		subAccountRelationService.set(CatalogBank.class, accountService.getByCode("1.1.2"));
		subAccountRelationService.set(CatalogCash.class, accountService.getByCode("1.1.1"));

		subAccountRelationService.set(Customer.class, accountService.getByCode("1.1.6"));
		subAccountRelationService.set(Supplier.class, accountService.getByCode("2.1.1"));

		subAccountRelationService.set(IncomeCategory.class, accountService.getByCode("4.1.3"));
		subAccountRelationService.set(ExpenseCategory.class, accountService.getByCode("5.4.1"));

		subAccountRelationService.set(RawMaterial.class, accountService.getByCode("1.1.3"));
		subAccountRelationService.set(FinishedGoods.class, accountService.getByCode("1.1.5"));

		subAccountRelationService.set(WorkInProcess.class, accountService.getByCode("1.1.4"));

		subAccountRelationService.set(Pendapatan.class, accountService.getByCode("4.1.1"));

		subAccountRelationService.set(HPP.class, accountService.getByCode("5.1.1"));

		subAccountRelationService.set(LabourCostCalculatedLater.class, accountService.getByCode("2.1.3"));

		subAccountRelationService.set(OverheadCostCalculatedLater.class, accountService.getByCode("2.1.4"));

	}

	public void registerParentInventoryAccount() throws Exception {
		parentInventoryService.add(new ParentInventoryDto("1.1.3"));
		parentInventoryService.add(new ParentInventoryDto("1.1.5"));
	}

	public void inputBank() throws Exception {
		catalogBankService.add(new CatalogBankDto("BCA", "Jl Baca Buku no 11", "7770934260"));
		catalogBankService.add(new CatalogBankDto("Mandiri", "Jl Mandiri no 11", "112334567"));
	}

	public void inputCash() throws Exception {
		catalogCashService.add(new CatalogCashDto("Kas Kecil"));
		catalogCashService.add(new CatalogCashDto("Kas Besar"));
	}

	public void inputUnit() throws Exception {

		unitService.add(new UnitDto("unit", "unit of unit"));
		unitService.add(new UnitDto("piece", "potongan"));
		unitService.add(new UnitDto("lembar", "lembar"));
		unitService.add(new UnitDto("buah", "buah"));
		unitService.add(new UnitDto("sachet", "sachet"));
		unitService.add(new UnitDto("kg", "kilogram"));
		unitService.add(new UnitDto("lt", "liter"));

	}

	public void inputRawMaterial() throws Exception {
		rawMaterialService.add(new RawMaterialDto("Papan", "Papan 20mm", 3L, 10));
		rawMaterialService.add(new RawMaterialDto("Paku", "Paku Papan", 6L, 90));
	}

	public void inputFinishedGoods() throws Exception {
		{
			FinishedGoodsDto dto = new FinishedGoodsDto();
			dto.setDescription("Kursi Kaki Tiga");
			dto.setLabour(new BigDecimal(200));
			dto.setOverhead(new BigDecimal(300));
			dto.setMinimalQuantity(5);
			dto.setName("Kursi Kaki Tiga");
			dto.setSalePrice(new BigDecimal(100_000));
			dto.setUnitId(1L);
			{
				List<RawMaterialDetailDto> list = new ArrayList<>();
				{
					RawMaterialDetailDto rDto = new RawMaterialDetailDto("1.1.3.1", 2);
					list.add(rDto);
				}
				{
					RawMaterialDetailDto rDto = new RawMaterialDetailDto("1.1.3.2", 3);
					list.add(rDto);
				}

				dto.setRawMaterialDetails(list);
			}
			finishGoodsService.add(dto);
		}
	}

	public void inputCustomerService() throws Exception {
		customerService.add(new PersonDto("Mirza", "Bandung", "08124124"));
		customerService.add(new PersonDto("Zunan", "Jogja", "09723432"));
		customerService.add(new PersonDto("Tika", "Depok", "07134134"));
	}

	public void inputSupplierService() throws Exception {
		supplierService.add(new PersonDto("Tn Saifudin", "Bakungan", "08124124"));
		supplierService.add(new PersonDto("Mr Soegondo", "Fatmawati", "09723432"));
		supplierService.add(new PersonDto("Mas Tamim", "Kalibata", "07134134"));
	}

	public void masukkanSaldoAwal() throws Exception {
		{
			JournalTransactionDto dto = new JournalTransactionDto();
			dto.setDescription("Masukkan saldo awal");
			List<TransactionDto> transactions = new ArrayList<>();
			{
				TransactionDto tDto = new TransactionDto("1.1.1.1", new BigDecimal(2_000_000));
				transactions.add(tDto);
			}
			{
				TransactionDto tDto = new TransactionDto("1.1.1.2", new BigDecimal(3_000_000));
				transactions.add(tDto);
			}
			{
				TransactionDto tDto = new TransactionDto("1.1.2.1", new BigDecimal(504_000_000));
				transactions.add(tDto);
			}
			{
				TransactionDto tDto = new TransactionDto("1.1.2.2", new BigDecimal(6_000_000));
				transactions.add(tDto);
			}
			{
				TransactionDto tDto = new TransactionDto("3.1.1", new BigDecimal(515_000_000));
				transactions.add(tDto);
			}
			dto.setTransactions(transactions);
			journalService.add(dto);
		}
		Thread.sleep(1000);
	}

	public void beliRawMaterialPertama() throws Exception {

		Journal journal;
		{
			JournalTransactionDto dto = new JournalTransactionDto();
			dto.setDescription("Beli Raw Material Papan");
			List<TransactionDto> transactions = new ArrayList<>();
			{
				TransactionDto tDto = new TransactionDto("1.1.2.1", new BigDecimal(-7_500_000));
				transactions.add(tDto);
			}
			{
				TransactionDto tDto = new TransactionDto("1.1.3.1", new BigDecimal(7_500_000));
				transactions.add(tDto);
			}
			dto.setTransactions(transactions);
			journal = journalService.add(dto);
		}

		{
			inventoryBalanceService.in(journal, accountService.getByCode("1.1.3.1"), new Date(), new BigDecimal(2_500), 3_000);
		}
		
		

		Thread.sleep(1000);
	}

	public void beliRawMaterialKedua() throws Exception {

		Journal journal;
		{
			JournalTransactionDto dto = new JournalTransactionDto();
			dto.setDescription("Beli Raw Material Paku");
			List<TransactionDto> transactions = new ArrayList<>();
			{
				TransactionDto tDto = new TransactionDto("1.1.2.1", new BigDecimal(-500_000));
				transactions.add(tDto);
			}
			{
				TransactionDto tDto = new TransactionDto("1.1.3.2", new BigDecimal(500_000));
				transactions.add(tDto);
			}
			dto.setTransactions(transactions);
			journal = journalService.add(dto);
		}

		{
			inventoryBalanceService.in(journal, accountService.getByCode("1.1.3.2"), new Date(), new BigDecimal(100), 5_000);
		}

		Thread.sleep(1000);
	}

	public void startProduksi() throws Exception {

		StartWIPDto dto = new StartWIPDto();
		dto.setWipCode("1.1.4.1");
		dto.setProductionCode("PRO123");
		dto.setNewQuantity(100);
		workInProcessBalanceService.in(dto);

		Thread.sleep(1000);
	}

	public void finishProduksi() throws Exception {

		FinishWIPDto dto = new FinishWIPDto();
		dto.setWipCode("1.1.4.1");
		dto.setFinishQuantity(90);
		dto.setConvertionCostLabour(80);
		dto.setConvertionCostOverhead(80);
		workInProcessBalanceService.out(dto);
		
		Thread.sleep(1000);
	}
	
	public void beliRawMaterialSecaraManual() throws Exception {
		
		{
			
			JournalTransactionDto dto = new JournalTransactionDto();
			dto.setDescription("Beli Raw Material Secara Manual");
			List<TransactionDto> transactions = new ArrayList<>();
			{
				TransactionDto tDto = new TransactionDto("1.1.2.1", new BigDecimal(-52_000));
				transactions.add(tDto);
			}
			{
				TransactionDto tDto = new TransactionDto("1.1.3.1", new BigDecimal(52_000));
				transactions.add(tDto);
			}
			dto.setTransactions(transactions);
			journalService.add(dto);
		}
		
		
		Thread.sleep(1000);
	}

	@Test
	public void doTest() throws Exception {

		// internal
		daftarkanAkunAkun();
		linkingAccount();
		registerParentInventoryAccount();

		// master data
		inputBank();
		inputCash();
		inputUnit();
		inputRawMaterial();
		inputFinishedGoods();
		inputCustomerService();
		inputSupplierService();

		// transaksi persiapan
//		masukkanSaldoAwal();
//		beliRawMaterialPertama();
//		beliRawMaterialKedua();

//		// transaksi produksi
//		startProduksi();
//		finishProduksi();
//		
//		//test bikin jurnal yg melibatkan inventory
//		beliRawMaterialSecaraManual();

	}

}
