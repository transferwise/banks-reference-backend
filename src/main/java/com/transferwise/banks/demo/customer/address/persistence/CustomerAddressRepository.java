package com.transferwise.banks.demo.customer.address.persistence;

import org.springframework.data.repository.CrudRepository;

interface CustomerAddressRepository extends CrudRepository<CustomerAddressEntity, Long> {
}
