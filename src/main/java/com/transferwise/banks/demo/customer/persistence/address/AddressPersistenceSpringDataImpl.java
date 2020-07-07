package com.transferwise.banks.demo.customer.persistence.address;

import com.transferwise.banks.demo.customer.domain.address.Address;
import com.transferwise.banks.demo.customer.domain.address.AddressPersistence;
import org.springframework.stereotype.Component;

@Component
class AddressPersistenceSpringDataImpl implements AddressPersistence {

    private final AddressMapperPersistence addressMapperPersistence;
    private final AddressRepository addressRepository;

    public AddressPersistenceSpringDataImpl(AddressMapperPersistence addressMapperPersistence, AddressRepository addressRepository) {
        this.addressMapperPersistence = addressMapperPersistence;
        this.addressRepository = addressRepository;
    }

    @Override
    public Address save(Address address) {
        AddressEntity savedAddressEntity = addressRepository.save(addressMapperPersistence.mapToCustomerAddressEntity(address));
        return addressMapperPersistence.mapToCustomerAddress(savedAddressEntity);
    }
}
