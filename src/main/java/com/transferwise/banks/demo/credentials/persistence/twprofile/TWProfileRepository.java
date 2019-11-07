package com.transferwise.banks.demo.credentials.persistence.twprofile;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface TWProfileRepository extends CrudRepository<TWProfileEntity, Long> {

    Optional<TWProfileEntity> findByCustomerId(Long customerId);
}
