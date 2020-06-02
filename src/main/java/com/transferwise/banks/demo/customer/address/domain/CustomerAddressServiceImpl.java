package com.transferwise.banks.demo.customer.address.domain;

import org.springframework.stereotype.Component;

@Component
class CustomerAddressServiceImpl implements CustomerAddressService {

    private final CustomerAddressPersistence customerAddressPersistence;

    public CustomerAddressServiceImpl(CustomerAddressPersistence customerAddressPersistence) {
        this.customerAddressPersistence = customerAddressPersistence;
    }

    public CustomerAddress find(Long id) {
        CustomerAddress customerAddress = customerAddressPersistence.findById(id);

        return customerAddress;
    }

    public CustomerAddress save(CustomerAddress customerAddress) {
        return customerAddressPersistence.save(customerAddress);
    }
}
