package com.phyros.mymoney.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.phyros.mymoney.entity.User;
import com.phyros.mymoney.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Acessar banco de dados
		return repository.findByUsername(username);	
	}
	
	public User getCurrentUser() {
		User user = (User) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		return user;
	}
	

}
