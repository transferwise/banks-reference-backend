package com.transferwise.banks.demo.customer.address.occupation.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface OccupationRepository extends CrudRepository<OccupationEntity, Long> {

    Optional<OccupationEntity> findByAddressId(Long addressId);
}
