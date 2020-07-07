package com.transferwise.banks.demo.customer.persistence.address;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface AddressRepository extends CrudRepository<AddressEntity, Long> {

    Optional<AddressEntity> findById(Long id);
}
