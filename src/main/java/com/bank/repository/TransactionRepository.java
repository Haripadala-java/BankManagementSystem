package com.bank.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
	
	@Query("""
	SELECT t FROM Transaction t
	WHERE t.fromAccount = :acc
	   OR t.toAccount = :acc
	ORDER BY t.transactionDate DESC
	""")
	List<Transaction> findRecentTransactions(
		    @Param("acc") String accountNumber
		);
	
	@Query("""
	SELECT t FROM Transaction t
	WHERE (t.fromAccount = :acc OR t.toAccount = :acc)
	AND t.transactionDate BETWEEN :start AND :end
	ORDER BY t.transactionDate DESC
	""")
	List<Transaction> findByDateRange(
	        @Param("acc") String acc,
	        @Param("start") LocalDateTime start,
	        @Param("end") LocalDateTime end
	);

}
