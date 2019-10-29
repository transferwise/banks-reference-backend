package com.transferwise.banks.demo.customer.web;

import com.transferwise.banks.demo.customer.domain.Customer;
import org.springframework.stereotype.Component;

@Component
class CustomersMapperWeb {

    public Customer mapToCustomer(final NewCustomerRequest newCustomerRequest) {

        return new Customer(newCustomerRequest.getFirstName(),
                newCustomerRequest.getLastName(),
                newCustomerRequest.getDateOfBirth(),
                newCustomerRequest.getPhoneNumber(),
                newCustomerRequest.getEmail());
    }

}
