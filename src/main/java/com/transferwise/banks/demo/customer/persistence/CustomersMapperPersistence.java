package com.transferwise.banks.demo.customer.persistence;

import com.transferwise.banks.demo.customer.address.domain.AddressPersistence;
import com.transferwise.banks.demo.customer.domain.Customer;
import org.springframework.stereotype.Component;

@Component
class CustomersMapperPersistence {

    private AddressPersistence addressPersistence;

    public Customer mapToCustomer(final CustomerEntity customerEntity) {
        return new Customer(customerEntity.getId(),
                customerEntity.getFirstName(),
                customerEntity.getLastName(),
                customerEntity.getDateOfBirth(),
                customerEntity.getPhoneNumber(),
                customerEntity.getEmail());
    }

    public CustomerEntity mapToCustomerEntity(final Customer customer) {
        return new CustomerEntity(customer.getFirstName(),
                customer.getLastName(),
                customer.getDateOfBirth(),
                customer.getPhoneNumber(),
                customer.getEmail());
    }
}
