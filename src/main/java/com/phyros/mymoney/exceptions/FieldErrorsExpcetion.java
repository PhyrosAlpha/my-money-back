package com.phyros.mymoney.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class FieldErrorsExpcetion extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	List<String> errors = new ArrayList<String>();
	
	
	public FieldErrorsExpcetion(String message, List<String> errors) {
		super(message);
		this.errors = errors;
	}

}
