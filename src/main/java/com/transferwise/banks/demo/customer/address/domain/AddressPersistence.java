package com.transferwise.banks.demo.customer.address.domain;

public interface AddressPersistence {

    Address findById(Long id);

    Address findByCustomerId(Long customerId);

    Address save(Address address);
}
