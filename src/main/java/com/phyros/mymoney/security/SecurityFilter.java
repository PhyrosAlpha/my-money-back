package com.phyros.mymoney.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.phyros.mymoney.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//System.out.println("Filtro sendo executado!");

		String token = this.recoverToken(request);	
		
		if(token != null) {
			String subject = tokenService.getSubject(token);	
			UserDetails user = userRepository.findByUsername(subject);			
			Authentication auth = new UsernamePasswordAuthenticationToken(
					user, null, user.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(auth);			
			//System.out.println("Filtro interno de autorização\n" + token + "\n" + subject);
		}
		filterChain.doFilter(request, response);	
	}
	
	private String recoverToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		
//		if(authHeader == null) {
//			throw new RuntimeException("Token was not sent");
//		}	
		
		if(authHeader != null) {
			return authHeader.replace("Bearer ", "");
		}
		
		return null;
	}
}
