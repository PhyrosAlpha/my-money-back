package com.phyros.mymoney.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phyros.mymoney.entity.User;
import com.phyros.mymoney.record.login.LoginDataRecord;
import com.phyros.mymoney.security.TokenJWTRecord;
import com.phyros.mymoney.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<Object> startLogin(@RequestBody @Valid LoginDataRecord request) {
		System.out.println("01");
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				request.login(), 
				request.password());
		
		Authentication auth = manager.authenticate(token);
		String generatedToken = tokenService.generateToken((User) auth.getPrincipal());
		
		System.out.println("03");
		
		return ResponseEntity.ok().body(new TokenJWTRecord(generatedToken));
	}
	
	
}
