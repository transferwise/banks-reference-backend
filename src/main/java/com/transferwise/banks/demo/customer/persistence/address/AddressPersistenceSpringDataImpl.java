package com.transferwise.banks.demo.customer.persistence.address;

import com.transferwise.banks.demo.customer.domain.address.Address;
import com.transferwise.banks.demo.customer.domain.address.AddressPersistence;
import com.transferwise.banks.demo.customer.domain.occupation.OccupationPersistence;
import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
class AddressPersistenceSpringDataImpl implements AddressPersistence {

    private final AddressMapperPersistence addressMapperPersistence;
    private final AddressRepository addressRepository;
    private final OccupationPersistence occupationPersistence;

    public AddressPersistenceSpringDataImpl(AddressMapperPersistence addressMapperPersistence, AddressRepository addressRepository, OccupationPersistence occupationPersistence) {
        this.addressMapperPersistence = addressMapperPersistence;
        this.addressRepository = addressRepository;
        this.occupationPersistence = occupationPersistence;
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id)
                .map(addressMapperPersistence::mapToCustomerAddress)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Address findByCustomerId(Long customerId) {
        return addressRepository.findByCustomerId(customerId)
                .map(this::mapToCustomerAddress)
                .orElse(new Address());
    }

    @Override
    public Address save(Address address) {
        AddressEntity savedAddressEntity = addressRepository.save(addressMapperPersistence.mapToCustomerAddressEntity(address));
        return addressMapperPersistence.mapToCustomerAddress(savedAddressEntity);
    }

    private Address mapToCustomerAddress(final AddressEntity addressEntity) {
        return new Address(
                addressEntity.getId(),
                addressEntity.getFirstLine(),
                addressEntity.getPostCode(),
                addressEntity.getCity(),
                addressEntity.getState(),
                addressEntity.getCountry(),
                addressEntity.getCustomerId(),
                occupationPersistence.findByAddressId(addressEntity.getId())
        );
    }
}
