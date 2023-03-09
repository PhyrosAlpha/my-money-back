package com.phyros.mymoney.record.movement;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.phyros.mymoney.entity.enums.FlowDirection;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public record MovementRecord(
		
		@NotNull(message = "O movimento deve estar associado a uma conta.")
		@Positive(message = "A conta do movimento dever um número inteiro.")
		Long accountId,
		
		@NotNull(message = "O registro deve ser débito ou crédito.")
		FlowDirection flow,
		
		@PastOrPresent(message="A data do movimento deve condizer com o presente ou o passado.")
		@NotNull(message = "A data é obrigatória.")
		LocalDate movementDate,
		
		String description,
		
		@NotNull(message = "O valor é obrigatório.")
		BigDecimal amount) {

}

// @Valid - Valida os bean validation de um objeto dentro de outro, deve colocar
// até mesmo onde o objeto raiz é mencionada
