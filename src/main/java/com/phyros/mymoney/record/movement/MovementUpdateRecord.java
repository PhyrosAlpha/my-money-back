package com.phyros.mymoney.record.movement;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovementUpdateRecord(
		
		@NotNull(message = "O Id não pode ser Null")
		@Positive(message = "Deve ser um número positivo")
		Long id,
		String description,
		
		@NotNull(message = "O valor é obrigatório.")
		BigDecimal amount) {

}

// @Valid - Valida os bean validation de um objeto dentro de outro, deve colocar
// até mesmo onde o objeto raiz é mencionada
