package com.transferwise.banks.demo.customer.persistence;

import com.transferwise.banks.demo.customer.domain.Customer;
import com.transferwise.banks.demo.customer.domain.CustomersPersistence;
import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
class CustomersPersistenceSpringDataImpl implements CustomersPersistence {

    private final CustomersMapperPersistence customersMapperPersistence;
    private final CustomersRepository customersRepository;


    public CustomersPersistenceSpringDataImpl(CustomersMapperPersistence customersMapperPersistence, CustomersRepository customersRepository) {
        this.customersMapperPersistence = customersMapperPersistence;
        this.customersRepository = customersRepository;
    }

    @Override
    public Customer findById(final Long customerId) {
        return customersRepository.findById(customerId)
                .map(customersMapperPersistence::mapToCustomer)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Customer save(final Customer customer) {
        CustomerEntity savedCustomerEntity = customersRepository.save(customersMapperPersistence.mapToCustomerEntity(customer));
        return customersMapperPersistence.mapToCustomer(savedCustomerEntity);
    }
}
