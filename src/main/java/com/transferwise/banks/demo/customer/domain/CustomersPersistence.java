package com.transferwise.banks.demo.customer.domain;

import java.util.Optional;

public interface CustomersPersistence {

    Optional<Customer> findById(Long customerId);

    Customer save(Customer customer);

}
