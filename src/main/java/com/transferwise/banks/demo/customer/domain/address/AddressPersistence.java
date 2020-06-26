package com.transferwise.banks.demo.customer.domain.address;

public interface AddressPersistence {

    Address findById(Long id);

    Address findByCustomerId(Long customerId);

    Address save(Address address);
}
