package com.transferwise.banks.demo.customer.address.domain;

public interface CustomerAddressService {

    CustomerAddress find(Long id);

    CustomerAddress save(CustomerAddress customerAddress);
}
