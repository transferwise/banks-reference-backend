package com.transferwise.banks.demo.credentials;

import org.springframework.data.repository.CrudRepository;

interface CredentialsRepository extends CrudRepository<TransferwiseCredentials, Long> {
}