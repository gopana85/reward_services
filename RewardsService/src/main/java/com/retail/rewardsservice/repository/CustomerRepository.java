package com.retail.rewardsservice.repository;

import com.retail.rewardsservice.domain.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
