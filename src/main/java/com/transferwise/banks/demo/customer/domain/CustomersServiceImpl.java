package com.transferwise.banks.demo.customer.domain;

import org.springframework.stereotype.Component;

@Component
class CustomersServiceImpl implements CustomersService {

    private final CustomersPersistence customersPersistence;

    public CustomersServiceImpl(CustomersPersistence customersPersistence) {
        this.customersPersistence = customersPersistence;
    }

    public Customer find(Long id) {
        return customersPersistence.findById(id);
    }

    public Customer save(Customer customer) {
        return customersPersistence.save(customer);
    }

}
