package com.transferwise.banks.demo.customer.address.domain;

public interface CustomerAddressService {

    CustomerAddress find(Long id);

    CustomerAddress findAddress(Long customerId);

    CustomerAddress save(CustomerAddress customerAddress);
}
