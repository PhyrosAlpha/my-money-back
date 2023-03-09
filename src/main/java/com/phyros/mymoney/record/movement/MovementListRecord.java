package com.phyros.mymoney.record.movement;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.phyros.mymoney.entity.Movement;
import com.phyros.mymoney.entity.enums.FlowDirection;

public record MovementListRecord(
		Long id,
		FlowDirection flow,
		BigDecimal amount,
		LocalDate movementDate,
		String description) {
	
	public MovementListRecord(Movement movement) {
		this(	movement.getId(),
				movement.getFlow(), 
				movement.getAmount(), 
				movement.getMovementDate(), 
				movement.getDescription());
	}
}
