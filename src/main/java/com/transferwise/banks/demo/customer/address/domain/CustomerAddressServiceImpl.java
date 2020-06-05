package com.transferwise.banks.demo.customer.address.domain;

import com.transferwise.banks.demo.customer.address.occupation.domain.OccupationPersistence;
import org.springframework.stereotype.Component;

@Component
class CustomerAddressServiceImpl implements CustomerAddressService {

    private final CustomerAddressPersistence customerAddressPersistence;
    private final OccupationPersistence occupationPersistence;

    public CustomerAddressServiceImpl(CustomerAddressPersistence customerAddressPersistence, OccupationPersistence occupationPersistence) {
        this.customerAddressPersistence = customerAddressPersistence;
        this.occupationPersistence = occupationPersistence;
    }

    public CustomerAddress find(Long id) {
        CustomerAddress customerAddress = customerAddressPersistence.findById(id);
        customerAddress.setOccupations(occupationPersistence.findByAddressId(customerAddress.getId()));

        return customerAddress;
    }

    public CustomerAddress findAddress(Long customerId) {
        CustomerAddress customerAddress = customerAddressPersistence.findByCustomerId(customerId);
        customerAddress.setOccupations(occupationPersistence.findByAddressId(customerId));

        return customerAddress;
    }

    public CustomerAddress save(CustomerAddress customerAddress) {
        return customerAddressPersistence.save(customerAddress);
    }
}
