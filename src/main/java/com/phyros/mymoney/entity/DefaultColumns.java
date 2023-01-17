package com.phyros.mymoney.entity;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;

import com.phyros.mymoney.exceptions.AccessForbiddenToDataException;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
public class DefaultColumns {
	
	public DefaultColumns() {
		
	}
	
	public DefaultColumns(User user) {
		this.setCreatedByUser(user);
		this.setUpdatedByUser(user);
		
		this.setCreationDate(LocalDateTime.now());
		this.setUpdateDate(LocalDateTime.now());	
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "USER_ID")
	private User createdByUser;
	
	@Column(name = "creation_date")
	private LocalDateTime creationDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "USER_ID")
	private User updatedByUser;
	
	@Column(name = "update_date")
	private LocalDateTime updateDate;
	
	@Column(name = "ACTIVE", nullable = false, columnDefinition = "TINYINT DEFAULT 1")
	private boolean active;


	public void updateRegister(User user) {
		this.setUpdatedByUser(this.verifyUserAccess(user));
		this.setUpdateDate(LocalDateTime.now());
	}
	
	public User verifyUserAccess(User user) {
		boolean result = this.getCreatedByUser().getId().equals(user.getId());
		if(!result) {
			throw new AccessForbiddenToDataException("Proíbido Acesso aos dados!");
		}
		return user;
	}
	
}
