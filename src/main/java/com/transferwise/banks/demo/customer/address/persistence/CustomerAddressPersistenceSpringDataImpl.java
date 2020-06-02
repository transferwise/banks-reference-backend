package com.transferwise.banks.demo.customer.address.persistence;

import com.transferwise.banks.demo.customer.address.domain.CustomerAddress;
import com.transferwise.banks.demo.customer.address.domain.CustomerAddressPersistence;
import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
class CustomerAddressPersistenceSpringDataImpl implements CustomerAddressPersistence {

    private final CustomerAddressMapperPersistence customerAddressMapperPersistence;
    private final CustomerAddressRepository customerAddressRepository;

    public CustomerAddressPersistenceSpringDataImpl(CustomerAddressMapperPersistence customerAddressMapperPersistence, CustomerAddressRepository customerAddressRepository) {
        this.customerAddressMapperPersistence = customerAddressMapperPersistence;
        this.customerAddressRepository = customerAddressRepository;
    }

    @Override
    public CustomerAddress findById(Long customerId) {
        return customerAddressRepository.findById(customerId)
                .map(customerAddressMapperPersistence::mapToCustomerAddress)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerAddress save(CustomerAddress customerAddress) {
        CustomerAddressEntity savedCustomerAddressEntity = customerAddressRepository.save(customerAddressMapperPersistence.mapToCustomerAddressEntity(customerAddress));
        return customerAddressMapperPersistence.mapToCustomerAddress(savedCustomerAddressEntity);
    }
}
