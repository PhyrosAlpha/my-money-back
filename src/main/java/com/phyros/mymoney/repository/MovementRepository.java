package com.phyros.mymoney.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.phyros.mymoney.entity.Movement;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

	Page<Movement> findAllByActiveTrue(Pageable pageable);
	
	@Query(value="SELECT a FROM Movement a "
			+ "WHERE a.createdByUser.id = :userId "
				+ "AND a.account.id = :accountId "
				+ "AND a.active = true",
				
	countQuery = "SELECT COUNT(a) FROM Movement a "
			+ "WHERE a.createdByUser.id = :userId "
				+ "AND a.account.id = :accountId "
				+ "AND a.active = true")
	
	Page<Movement> findAllByCreatedByUser(
			@Param(value = "userId") Long userId, 
			@Param(value = "accountId") Long accountId, 
			Pageable pageable);
	
	Movement findOneByIdAndActiveTrue(Long id);
	

}
