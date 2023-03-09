package com.phyros.mymoney.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.phyros.mymoney.entity.enums.FlowDirection;
import com.phyros.mymoney.record.movement.MovementRecord;
import com.phyros.mymoney.record.movement.MovementUpdateRecord;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MOVEMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(of = "id")
public class Movement extends DefaultColumns {
	
	public Movement(MovementRecord movement) {
		this.flow = movement.flow();
		this.amount = movement.amount();
		this.movementDate = movement.movementDate();
		this.description = movement.description();
	}
	
	public Movement(MovementRecord movement, User user) {
		super(user);
		this.flow = movement.flow();
		this.amount = movement.amount();
		this.movementDate = movement.movementDate();
		this.description = movement.description();
	}

	@Id
	@Column(name = "MOVEMENT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, columnDefinition = "id")
	private Account account;
		
	@Enumerated(EnumType.STRING)
	@Column(name = "FLOW_DIRECTION", nullable = false)
	private FlowDirection flow;
	
	@Column(name = "AMOUNT", nullable = false)
	private BigDecimal amount;
	
	@Column(name = "MOVEMENT_DATE", nullable = false)
	private LocalDate movementDate;
	
	@Column(name = "DESCRIPTION", nullable = true)
	private String description = "";
		
	@Column(name = "DATE_TIME_STAMP", nullable = false)
	private LocalDateTime dateTimeStamp;
	
	public void setDateTimeStamp(LocalDateTime dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}
	
	public void updateData(MovementUpdateRecord data) {	
		if(data.amount() != null) {
			this.setAmount(data.amount());
		}
		
		if(data.description() != null) {
			this.setDescription(data.description());
		}
	}	
}
