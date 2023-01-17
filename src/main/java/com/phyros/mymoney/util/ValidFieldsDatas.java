package com.phyros.mymoney.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.phyros.mymoney.exceptions.FieldErrorsExpcetion;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ValidFieldsDatas {
		
	public static void ValidFields(Object entity) {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Collection<ConstraintViolation<Object>> result = validator.validate(entity);
		List<String> errors = new ArrayList<String>();
		if(result.size() > 0) {
			for(ConstraintViolation<Object> element : result) {
				errors.add(element.getMessage());
			}
			throw new FieldErrorsExpcetion("Errors Fields", errors);
		}
	}
}
