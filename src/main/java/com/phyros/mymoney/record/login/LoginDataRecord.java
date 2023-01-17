package com.phyros.mymoney.record.login;

import jakarta.validation.constraints.NotNull;

public record LoginDataRecord(
		@NotNull
		String login, 
		
		@NotNull
		String password) {

}
