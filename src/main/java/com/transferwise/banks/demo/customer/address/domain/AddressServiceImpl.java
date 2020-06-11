package com.transferwise.banks.demo.customer.address.domain;

import com.transferwise.banks.demo.customer.address.occupation.domain.OccupationPersistence;
import org.springframework.stereotype.Component;

@Component
class AddressServiceImpl implements AddressService {

    private final AddressPersistence addressPersistence;
    private final OccupationPersistence occupationPersistence;

    public AddressServiceImpl(AddressPersistence addressPersistence, OccupationPersistence occupationPersistence) {
        this.addressPersistence = addressPersistence;
        this.occupationPersistence = occupationPersistence;
    }

    public Address find(Long id) {
        Address address = addressPersistence.findById(id);
        address.setOccupations(occupationPersistence.findByAddressId(address.getId()));

        return address;
    }

    public Address findAddress(Long customerId) {
        Address address = addressPersistence.findByCustomerId(customerId);
        address.setOccupations(occupationPersistence.findByAddressId(customerId));

        return address;
    }

    public Address save(Address address) {
        return addressPersistence.save(address);
    }
}
