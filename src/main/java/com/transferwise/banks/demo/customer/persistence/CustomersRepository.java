package com.transferwise.banks.demo.customer.persistence;

import org.springframework.data.repository.CrudRepository;

interface CustomersRepository extends CrudRepository<CustomerEntity, Long> {

}
