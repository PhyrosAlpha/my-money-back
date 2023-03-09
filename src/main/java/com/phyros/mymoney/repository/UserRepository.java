package com.phyros.mymoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.phyros.mymoney.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	UserDetails findByUsername(String login);

}
