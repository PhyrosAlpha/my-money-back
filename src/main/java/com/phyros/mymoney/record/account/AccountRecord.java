package com.phyros.mymoney.record.account;

import com.phyros.mymoney.entity.enums.AccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountRecord(
		
		@NotBlank(message = "O nome da conta é obrigatório")
		String name,
		
		String description,
		
		@NotNull(message = "O tipo da conta é obrigatório")
		AccountType type
		) {

}
