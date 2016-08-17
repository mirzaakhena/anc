package com.mirzaakhena.batchsystem.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.batchsystem.dao.AccountDao;
import com.mirzaakhena.batchsystem.dto.AccountCreateDto;
import com.mirzaakhena.batchsystem.model.Account;
import com.mirzaakhena.batchsystem.model.AccountSide;
import com.mirzaakhena.batchsystem.model.AccountType;

@Service
public class AccountService {

	@Autowired
	private AccountDao dao;
	
//	@Autowired
//	private PrimaryKeyTableService primaryKeyTableService;

	@Autowired
	private AccountChildIndexService accountChildIndexService;

	@Transactional
	public Account add(AccountCreateDto dto) throws Exception {

		int lastDotIndex = dto.getAccountCode().lastIndexOf(".");

		if (lastDotIndex != -1) { // child akun

			String parentCode = dto.getAccountCode().substring(0, lastDotIndex);

			Account accountParent = dao.findByCode(parentCode);

			if (accountParent == null) {
				throw new Exception(String.format("parent account with code %s does not exist", parentCode));
			}

			if (accountParent.getAccountType() == AccountType.FINAL_ACCOUNT) {
				throw new Exception(String.format("this %s account cannot have a child anymore", dto.getName()));
			}

			int nextChildIndex = accountChildIndexService.getChildCurrentIndex(accountParent).getChildCurrentIndex() + 1;

			AccountSide as = accountParent.getAccountSide();

			if (dto.getAccountCode().contains("a")) {
				as = AccountSide.ACTIVA;
			} else

			if (dto.getAccountCode().contains("p")) {
				as = AccountSide.PASSIVA;
			}

			AccountType parentType = accountParent.getAccountType();

			AccountType at;

			if (parentType == AccountType.CHILD_AS_SUBACCOUNT || dto.getAccountCode().contains("x")) {
				at = AccountType.FINAL_ACCOUNT;
			} else {

				if (dto.getAccountCode().contains("s")) {
					at = AccountType.CHILD_AS_SUBACCOUNT;
				} else

				{
					at = AccountType.REGULAR_ACCOUNT;
				}

			}

			Account account = new Account();
			// account.setChildCount(at == AccountType.FINAL_ACCOUNT ? -1 : 0);
			account.setAccountType(at);
			account.setAccountSide(as);
			account.setCode(parentCode + "." + String.valueOf(nextChildIndex));
			account.setAccountParent(accountParent);
			account.setLevel(accountParent.getLevel() + 1);
			account.setName(dto.getName());
//			account.setId(primaryKeyTableService.getNextId(Account.class, client));
			dao.save(account);

			accountChildIndexService.incrementIndex(accountParent);

			// accountParent.setChildCurrentIndex(nextChildIndex);
			// accountParent.setChildCount(accountParent.getChildCount() + 1);
			// dao.save(accountParent);
			
			return account;

		} else { // root parent

			AccountSide as;
			if (dto.getAccountCode().contains("a")) {
				as = AccountSide.ACTIVA;
			} else

			if (dto.getAccountCode().contains("p")) {
				as = AccountSide.PASSIVA;
			} else

			{
				throw new Exception("root account must specify a(ctiva) or p(assiva) marker");
			}

			int parentCount = dao.findRootAccountParent().size();

			AccountType at = AccountType.REGULAR_ACCOUNT;

			if (dto.getAccountCode().contains("s")) {
				at = AccountType.CHILD_AS_SUBACCOUNT;
			} else

			if (dto.getAccountCode().contains("x")) {
				at = AccountType.FINAL_ACCOUNT;

			}

			Account account = new Account();
			// account.setChildCount(at == AccountType.FINAL_ACCOUNT ? -1 : 0);
			account.setAccountType(at);
			account.setAccountSide(as);
			account.setCode(String.valueOf(parentCount + 1));
			account.setAccountParent(null);
			account.setLevel(1);
			account.setName(dto.getName());
			return dao.save(account);

		}

	}

	@Transactional(rollbackFor = Exception.class)
	public void addByFile(String filePath) throws Exception {
		
		ClassLoader classLoader = getClass().getClassLoader();
		Scanner scanner = new Scanner(classLoader.getResourceAsStream(filePath));
		while (scanner.hasNextLine()) {
			
			String row = (String) scanner.nextLine().trim();
			if (row.length() == 0) {
				continue;
			}

			if (row.startsWith("#")) {
				continue;
			}

			// index spasi pertama
			int firstSpace = row.indexOf(" ");

			// ambil kode nya saja
			String code = row.substring(0, firstSpace);

			String name = row.substring(firstSpace + 1, row.length()).trim();

			add(new AccountCreateDto(code, name));
		}

		scanner.close();

	}

	public Account getByCode(String code) {
		return dao.findByCode(code);
	}

	public List<Account> getAll(int level, String side) throws Exception {
		AccountSide as = null;
		if ("activa".equals(side)) {
			as = AccountSide.ACTIVA;
		} else

		if ("passiva".equals(side)) {
			as = AccountSide.PASSIVA;
		} else

		if ("".equals(side)) {
			return getAll(level);
		} else

		if (as == null) {
			throw new Exception("side value must 'activa' or 'passiva' or empty string");
		}
		return dao.findAllWithLevelAndSide(level, as);
	}

	public List<Account> getAll(int level) {
		return dao.findAllUnderLevel(level);
	}

	public Account getById(Long accountId) {
		return dao.findById(accountId);
	}

	public List<Account> getChild(Long accountId) {
		return dao.findChild(accountId);
	}

}
