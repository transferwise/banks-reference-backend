package com.transferwise.banks.demo.customer.persistence.occupation;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

interface OccupationRepository extends CrudRepository<OccupationEntity, Long> {

    Optional<List<OccupationEntity>> findByAddressId(Long addressId);
}
