package com.transferwise.banks.demo.credentials.persistence.twprofile;

import com.transferwise.banks.demo.credentials.domain.TWProfile;

import java.util.Optional;

public interface TWProfilePersistence {

    TWProfile save(TWProfile twProfile);

    Optional<TWProfile> findByCustomerId(Long customerId);
}
