package com.phyros.mymoney.record.account;

import com.phyros.mymoney.entity.enums.AccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AccountUpdateRecord(
		
		@NotNull(message = "O Id não pode ser nulo")
		@Positive(message = "Deve ser um número positivo")
		Long id,
		
		@NotBlank(message = "Não pode estar em branco")
		String name,
		
		String description,
		
		@NotNull(message = "O tipo não pode ser nulo")
		AccountType type
		
		) {


}
