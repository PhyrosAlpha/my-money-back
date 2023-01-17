package com.phyros.mymoney.controllers;

import java.net.URI;
import java.time.LocalDateTime;
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
import com.phyros.mymoney.entity.Movement;
import com.phyros.mymoney.entity.User;
import com.phyros.mymoney.exceptions.ObjectNotFoundException;
import com.phyros.mymoney.record.movement.MovementListRecord;
import com.phyros.mymoney.record.movement.MovementRecord;
import com.phyros.mymoney.record.movement.MovementUpdateRecord;
import com.phyros.mymoney.repository.AccountRepository;
import com.phyros.mymoney.repository.MovementRepository;
import com.phyros.mymoney.security.AuthenticationService;
import com.phyros.mymoney.util.ValidFieldsDatas;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/movement")
public class MovementController {
	
	@Autowired
	MovementRepository repository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Object> createMovement(
			@RequestBody @Valid MovementRecord request, 
			UriComponentsBuilder uriBuilder) {		
		
		//ValidFieldsDatas.ValidFields(request);
		User user = authenticationService.getCurrentUser();	
		
		Movement entity = new Movement(request, user);
		Optional<Account> result = accountRepository.findById(request.accountId());

		result.ifPresentOrElse((account) -> {
			account.verifyUserAccess(user);
			LocalDateTime now = LocalDateTime.now();
			entity.setActive(true);
			entity.setAccount(account);
			entity.setDateTimeStamp(now);
			repository.save(entity);
		}, 
		() -> {
			throw new ObjectNotFoundException("A conta em questão não existe!");
		});
		
		URI uri = uriBuilder.path("/movement/{id}").buildAndExpand(entity.getId()).toUri();
			
		return ResponseEntity.created(uri).body(entity);
		//return new ResponseEntity<Object>(entity, HttpStatus.CREATED);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<Object> updateMovement(@RequestBody MovementUpdateRecord request) {	
		ValidFieldsDatas.ValidFields(request);
		
		Optional<Movement> result = repository.findById(request.id());
		
		result.ifPresentOrElse((movement) -> {
			movement.updateData(request);
			
			//É possível omitir a operação save, pois a transação que é aberta
			//detecta a mudança e commita o update do registro.
			//repository.save(movement);
		},
		() -> {
			throw new ObjectNotFoundException("O movimento em questão não existe!");
		});
		
		User user = authenticationService.getCurrentUser();	
		result.get().updateRegister(user);
		
		return new ResponseEntity<Object>(new MovementListRecord(result.get()) ,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> deleteMovement(
			@PathVariable Long id
			) {
		User user = authenticationService.getCurrentUser();	
		Optional<Movement> result = repository.findById(id);
		result.ifPresentOrElse((movement) -> {
			movement.verifyUserAccess(user);
			movement.setActive(false);
			//repository.save(movement);
		},
		() -> {
			throw new ObjectNotFoundException("O movimento em questão não existe!");
		});
		
		//ResponseEntity.noContent().build();
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/account/{accountId}")
	public ResponseEntity<Object> listMovements(
			//@RequestParam int page, @RequestParam int size
			@PathVariable Long accountId,
			@PageableDefault(size = 10, sort = {"movementDate"})Pageable pageable
		) {	
		//Sort sort = new Sort(Direction.ASC, "flow");
		User user = authenticationService.getCurrentUser();	
		
		Page<MovementListRecord> result = repository
				.findAllByCreatedByUser(user.getId(), accountId, pageable).map(MovementListRecord::new);
				//.findAll(pageable).stream().map(MovementListRecord::new).toList();

		//ResponseEntity.ok(result);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getMovement(
			@PathVariable Long id
			){
		Movement result = repository.findOneByIdAndActiveTrue(id);
		if(result == null)
			throw new ObjectNotFoundException("O movimento em questão não existe!");
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		result.verifyUserAccess(user);
		
		return new ResponseEntity<Object>(new MovementListRecord(result), HttpStatus.OK);
	}
	
}
