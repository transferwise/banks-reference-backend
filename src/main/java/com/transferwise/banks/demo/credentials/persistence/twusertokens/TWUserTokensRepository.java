package com.transferwise.banks.demo.credentials.persistence.twusertokens;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface TWUserTokensRepository extends CrudRepository<TWUserTokensEntity, Long> {

    Optional<TWUserTokensEntity> findByCustomerId(Long customerId);
}
