package com.transferwise.banks.demo.customer.domain;

public interface CustomersPersistence {

    Customer findById(Long customerId);

    Customer save(Customer customer);

}
