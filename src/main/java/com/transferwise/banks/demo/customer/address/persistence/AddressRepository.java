package com.transferwise.banks.demo.customer.address.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface AddressRepository extends CrudRepository<AddressEntity, Long> {

    Optional<AddressEntity> findByCustomerId(Long customerId);
}
