package com.phyros.mymoney.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.phyros.mymoney.entity.Account;
import com.phyros.mymoney.record.account.AccountListRecord;

//@RepositoryRestResource(path = "account", collectionResourceRel = "accounts")
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, PagingAndSortingRepository<Account, Long> {
	
	@Query(value="SELECT a FROM Account a "
					+ "WHERE a.createdByUser.id = :userId "
						+ "AND a.active = true",
						
			countQuery = "SELECT COUNT(a) FROM Account a "
					+ "WHERE a.createdByUser.id = :userId "
						+ "AND a.active = true")
	Page<AccountListRecord> findAllByCreatedByUser(Long userId, Pageable pageable);
	
	Account findOneByIdAndActiveTrue(Long id);
		
	//AccountListRecord findByUser(User user);

}
