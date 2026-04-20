package com.retail.rewardsservice.repository;

import java.time.LocalDate;
import java.util.List;

import com.retail.rewardsservice.domain.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.customerId = ?1 AND t.transactionDate >= ?2 AND t.transactionDate <= ?3")
    List<Transaction> findByCustomerIdAndDateRange(Long customerId, LocalDate startDate, LocalDate endDate);
}
