package com.transferwise.banks.demo.customer.address.domain;

public interface AddressService {

    Address find(Long id);

    Address findAddress(Long customerId);

    Address save(Address address);
}
