package com.transferwise.t4b.credentials;

import org.springframework.data.repository.CrudRepository;

interface CredentialsRepository extends CrudRepository<Credentials, Long> {
}