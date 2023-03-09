package com.phyros.mymoney.entity;

import com.phyros.mymoney.entity.enums.AccountType;
import com.phyros.mymoney.record.account.AccountUpdateRecord;

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
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ACCOUNT")
public class Account extends DefaultColumns {
	
	public Account() {
		
	}
	
	public Account(User user) {
		super(user);
	}
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(referencedColumnName = "USER_ID", nullable = false, columnDefinition = "bigint default 1")
//	private User user;
		
	@Column(name = "ACCOUNT_NAME",nullable = false )
	private String name;
	
	@Column(name = "DESCRIPTION", nullable = true)
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ACCOUNT_TYPE", nullable = false)
	private AccountType type;

	public void updateData(AccountUpdateRecord request) {
		this.setName(request.name());
		this.setDescription(request.description());
		this.setType(request.type());
	}
}
