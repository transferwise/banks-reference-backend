package com.transferwise.banks.demo.customer.domain;

public interface CustomersService {

    Customer find(Long id);

    Customer save(Customer customer);

}
