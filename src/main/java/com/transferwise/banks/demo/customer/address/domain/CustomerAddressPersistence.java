package com.transferwise.banks.demo.customer.address.domain;

public interface CustomerAddressPersistence {

    CustomerAddress findById(Long id);

    CustomerAddress findByCustomerId(Long customerId);

    CustomerAddress save(CustomerAddress customerAddress);
}
