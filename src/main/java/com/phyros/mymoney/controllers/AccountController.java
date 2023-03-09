package com.phyros.mymoney.controllers;


import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.phyros.mymoney.entity.Account;
import com.phyros.mymoney.entity.User;
import com.phyros.mymoney.exceptions.ObjectNotFoundException;
import com.phyros.mymoney.record.account.AccountListRecord;
import com.phyros.mymoney.record.account.AccountRecord;
import com.phyros.mymoney.record.account.AccountUpdateRecord;
import com.phyros.mymoney.repository.AccountRepository;
import com.phyros.mymoney.security.AuthenticationService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {
		
	@Autowired
	AccountRepository repository;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Object> createMovement(
			@RequestBody @Valid AccountRecord request, 
			UriComponentsBuilder uriBuilder) {		
				
		User user = authenticationService.getCurrentUser();
		
		Account entity = new Account(user);
		entity.setName(request.name());
		entity.setDescription(request.description());
		entity.setType(request.type());
		repository.save(entity);
		
		URI uri = uriBuilder.path("/account/{id}").buildAndExpand(entity.getId()).toUri();
			
		return ResponseEntity.created(uri).body(entity);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<Object> updateAccount(@RequestBody @Valid AccountUpdateRecord request) {			
		Optional<Account> result = repository.findById(request.id());
		User user = authenticationService.getCurrentUser();	
		result.ifPresentOrElse((account) -> {
			account.updateData(request);
			account.updateRegister(user);
		},
		() -> {
			throw new ObjectNotFoundException("A account em questão não existe!");
		});
		
		return new ResponseEntity<Object>(new AccountListRecord(result.get()) ,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> deleteAccount(
			@PathVariable Long id
			) {
				
		Optional<Account> result = repository.findById(id);
		result.ifPresentOrElse((account) -> {
			account.setActive(false);
		},
		() -> {
			throw new ObjectNotFoundException("A account em questão não existe!");
		});
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
			
	@GetMapping
	public ResponseEntity<Object> listAccounts(
			@PageableDefault(size = 10, sort = {"type"})Pageable pageable
		) {	
		User user = authenticationService.getCurrentUser();		
		Page<AccountListRecord> result = repository
				.findAllByCreatedByUser(user.getId(), pageable);

		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getAccount(
			@PathVariable Long id
			){		
		Account result = repository.findOneByIdAndActiveTrue(id);
		
		if(result == null)
			throw new ObjectNotFoundException("A account em questão não existe!");
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		result.verifyUserAccess(user);
		
		return new ResponseEntity<Object>(new AccountListRecord(result), HttpStatus.OK);
	}
	
}
