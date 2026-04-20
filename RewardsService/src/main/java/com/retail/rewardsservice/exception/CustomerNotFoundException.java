package com.retail.rewardsservice.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long customerId) {
        super("Customer not found for id " + customerId);
    }
}
