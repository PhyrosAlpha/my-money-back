package com.phyros.mymoney.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
//		
//		System.out.println("Tentativa de autenticação...");
//		
//		return null;
//	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, jakarta.servlet.ServletException {

		System.out.println("Falha durante autenticação.");

		super.unsuccessfulAuthentication(request, response, failed);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException {
		System.out.println("Sucesso durante a autenticação.");
	}

}
