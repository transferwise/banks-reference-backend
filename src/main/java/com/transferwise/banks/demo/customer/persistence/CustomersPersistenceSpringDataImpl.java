package com.transferwise.banks.demo.customer.persistence;

import com.transferwise.banks.demo.customer.domain.CustomersPersistence;
import com.transferwise.banks.demo.customer.domain.Customer;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class CustomersPersistenceSpringDataImpl implements CustomersPersistence {

    private final CustomersMapperPersistence customersMapperPersistence;
    private final CustomersRepository customersRepository;


    public CustomersPersistenceSpringDataImpl(CustomersMapperPersistence customersMapperPersistence, CustomersRepository customersRepository) {
        this.customersMapperPersistence = customersMapperPersistence;
        this.customersRepository = customersRepository;
    }

    @Override
    public Optional<Customer> findById(final Long customerId) {
        return customersRepository.findById(customerId).map(customersMapperPersistence::mapToCustomer);
    }

    @Override
    public Customer save(final Customer customer) {
        CustomerEntity savedCustomerEntity = customersRepository.save(customersMapperPersistence.mapToCustomerEntity(customer));
        return customersMapperPersistence.mapToCustomer(savedCustomerEntity);
    }
}
