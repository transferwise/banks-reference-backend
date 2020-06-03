package com.transferwise.banks.demo.customer.address.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface CustomerAddressRepository extends CrudRepository<CustomerAddressEntity, Long> {

    Optional<CustomerAddressEntity> findByCustomerId(Long customerId);
}
