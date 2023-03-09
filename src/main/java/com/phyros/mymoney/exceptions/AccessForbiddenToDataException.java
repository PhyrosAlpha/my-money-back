package com.phyros.mymoney.exceptions;

public class AccessForbiddenToDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AccessForbiddenToDataException(String msg) {
		super(msg);
	}

}
