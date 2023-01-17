package com.phyros.mymoney.record.account;

import com.phyros.mymoney.entity.Account;
import com.phyros.mymoney.entity.enums.AccountType;

public record AccountListRecord(
		Long id,
		String name,
		String description,
		AccountType type
		) {
	
	public AccountListRecord(Account account) {
		this(account.getId(),
			account.getName(),
			account.getDescription(),
			account.getType());
	}

}
