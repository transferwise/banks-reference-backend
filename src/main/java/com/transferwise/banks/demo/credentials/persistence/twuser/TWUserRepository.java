package com.transferwise.banks.demo.credentials.persistence.twuser;

import org.springframework.data.repository.CrudRepository;

interface TWUserRepository extends CrudRepository<TWUserEntity, Long> {
}
