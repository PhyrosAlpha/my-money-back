package com.phyros.mymoney.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.phyros.mymoney.exceptions.AccessForbiddenToDataException;
import com.phyros.mymoney.exceptions.FieldErrorsExpcetion;
import com.phyros.mymoney.exceptions.ObjectNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(FieldErrorsExpcetion.class)
	public ResponseEntity<Object> fieldErrorsException(FieldErrorsExpcetion e){
		return new ResponseEntity<Object>(e.getErrors(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException e){
		return new ResponseEntity<Object>(e.getFieldErrors(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<Object> objectNotFoundException(ObjectNotFoundException e){
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException e){
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InternalAuthenticationServiceException.class)
	public ResponseEntity<Object> internalAuthenticationServiceException(InternalAuthenticationServiceException e){
		return new ResponseEntity<Object>("Login ou Senha estão incorretos", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AccessForbiddenToDataException.class)
	public ResponseEntity<Object> accessForbiddenToDataException(AccessForbiddenToDataException e){
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.FORBIDDEN);
	}
	
}


